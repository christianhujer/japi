/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.cstyle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** CStyle is a simple program that checks whether a C source or header file adhers to a specific code convention.
 * This is work under progress, so don't expect too much from it yet.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class CStyle extends BasicCommand {

    /** Main program.
     * @param args Command line arguments.
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new CStyle(), args);
    }

    /** The encoding to use when reading file.
     * Defaults to the system's default encoding.
     */
    @NotNull private String encoding = Charset.defaultCharset().name();

    /** The previous physicalMode of the parser. */
    @NotNull private PhysicalMode previousPhysicalMode = PhysicalMode.NORMAL;

    /** The current physicalMode of the parser. */
    @NotNull private PhysicalMode physicalMode = PhysicalMode.NORMAL;

    /** The current token of the parser. */
    @SuppressWarnings({"StringBufferField"})
    @NotNull private final StringBuilder currentToken = new StringBuilder();

    /** Creates a CStyle command. */
    public CStyle() {
        reset();
    }

    /** Sets the encoding to use when reading source files.
     * @param encoding Encoding to use
     */
    @Option({"e", "encoding"})
    public void setEncoding(@NotNull final String encoding) {
        this.encoding = Charset.forName(encoding).name();
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention", "ProhibitedExceptionDeclared"})
    public int run(@NotNull final List<String> args) throws Exception {
        if (args.size() == 0) {
            check(System.in);
        }
        return 0;
    }

    /** Checks a single file.
     * @param filename Name of the file to read from.
     * @throws IOException In case of I/O problems.
     */
    public void check(@NotNull final String filename) throws IOException {
        check(new File(filename));
    }

    /** Checks a single file.
     * @param file File to read from.
     * @throws IOException In case of I/O problems.
     */
    public void check(@NotNull final File file) throws IOException {
        final InputStream in = new FileInputStream(file);
        try {
            check(in);
        } finally {
            in.close();
        }
    }

    /** Checks a single stream.
     * @param stream Stream to read from.
     * @throws IOException In case of I/O problems.
     */
    public void check(@NotNull final InputStream stream) throws IOException {
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final Reader in = new BufferedReader(new InputStreamReader(stream, encoding));
        check(in);
    }

    /** Checks a single reader.
     * @param in Reader to read from.
     * @throws IOException In case of I/O problems.
     */
    public void check(@NotNull final Reader in) throws IOException {
        physicalMode = PhysicalMode.NORMAL;
        //noinspection NestedAssignment
        for (int c; (c = in.read()) != -1;) {
            parse((char) c);
        }
    }

    /** Reset the parser to its initial state. */
    public void reset() {
        physicalMode = PhysicalMode.NORMAL;
        previousPhysicalMode = PhysicalMode.NORMAL;
        currentToken.setLength(0);
    }

    /** Processes a single character in the parser.
     * @param c Character to parse
     */
    public void parse(final char c) {
        previousPhysicalMode = physicalMode;
        // Step 1: Parse the character and determine the new mode.
        switch (previousPhysicalMode) {
        case NORMAL:
            switch (c) {
            case '\\': physicalMode = PhysicalMode.ESCAPE; break;
            case '/':  physicalMode = PhysicalMode.SLASH;  break;
            case '"':  physicalMode = PhysicalMode.STRING; break;
            case '\'': physicalMode = PhysicalMode.CHAR;   break;
            default:
            }
            break;
        case ESCAPE:
            physicalMode = PhysicalMode.NORMAL;
            break;
        case SLASH:
            switch (c) {
            case '/': physicalMode = PhysicalMode.EOL_COMMENT; break;
            case '*': physicalMode = PhysicalMode.ML_COMMENT;  break;
            default:  physicalMode = PhysicalMode.NORMAL;      break;
            }
            break;
        case EOL_COMMENT:
            switch (c) {
            case '\n': physicalMode = PhysicalMode.NORMAL; break;
            default:
            }
            break;
        case ML_COMMENT:
            switch (c) {
            case '*': physicalMode = PhysicalMode.ML_COMMENT_STAR; break;
            default:
            }
            break;
        case ML_COMMENT_STAR:
            switch (c) {
            case '/': physicalMode = PhysicalMode.NORMAL;          break;
            case '*': physicalMode = PhysicalMode.ML_COMMENT_STAR; break;
            default:  physicalMode = PhysicalMode.ML_COMMENT;      break;
            }
            break;
        case STRING:
            switch (c) {
            case '"':  physicalMode = PhysicalMode.NORMAL;        break;
            case '\\': physicalMode = PhysicalMode.STRING_ESCAPE; break;
            default:
            }
            break;
        case STRING_ESCAPE:
            physicalMode = PhysicalMode.STRING;
            break;
        case CHAR:
            switch (c) {
            case '\'': physicalMode = PhysicalMode.NORMAL;      break;
            case '\\': physicalMode = PhysicalMode.CHAR_ESCAPE; break;
            default:
            }
            break;
        case CHAR_ESCAPE:
            physicalMode = PhysicalMode.CHAR;
            break;
        default:
            assert false;
        }
        // Step 2: Tokenize
        if (physicalMode == PhysicalMode.NORMAL && previousPhysicalMode == PhysicalMode.NORMAL) {
            currentToken.append(c);
            // tokens to detect:
            // keyword
            // symbol
            // operator
            // literal
            // separator
            // whitespace
            // eol-comment
            // multiline-comment
        }
    }

    /** Returns the previous physicalMode of the parser.
     * @return The previous physicalMode of the parser.
     */
    @NotNull public PhysicalMode getPreviousPhysicalMode() {
        return previousPhysicalMode;
    }

    /** Returns the current physicalMode of the parser.
     * @return The current physicalMode of the parser.
     */
    @NotNull public PhysicalMode getPhysicalMode() {
        return physicalMode;
    }

    /** The physicalMode of the physical parser. */
    public enum PhysicalMode {

        /** Normal source code. */
        NORMAL,

        /** After the \ of an escape sequence in NORMAL mode. */
        ESCAPE,

        /** After the / in normal mode, might introduce a comment. */
        SLASH,

        /** In an EOL comment (after //). */
        EOL_COMMENT,

        /** In a multiline comment (after /*). */
        ML_COMMENT,

        /** After * in a multiline comment. */
        ML_COMMENT_STAR,

        /** After " in normal mode. */
        STRING,

        /** After \ in a string. */
        STRING_ESCAPE,

        /** After ' in normal mode. */
        CHAR,

        /** After \ in a char. */
        CHAR_ESCAPE

    } // enum PhysicalMode

} // class CStyle
