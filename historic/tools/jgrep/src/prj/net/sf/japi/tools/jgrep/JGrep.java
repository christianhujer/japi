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

package net.sf.japi.tools.jgrep;

import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import net.sf.japi.io.args.CharsetDisplaynameComparator;
import net.sf.japi.io.args.OptionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.Collections;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.nio.charset.Charset;

/** JGrep is a Java reincarnation of the famous UNIX command grep.
 * <h3>Differences to UNIX grep</h3>
 * This comparison was done against GNU grep 2.4.2.
 * <h4>Supported Features</h4>
 * <ul>
 *  <li>-R, -r, --recursive for recursing into subdirectories.</li>
 *  <li>-n, --line-number for printing the number of the line of each match.</li>
 *  <li>-Z, --null for printing a null byte after each file name.</li>
 *  <li>--label</li>
 * </ul>
 * <h4>Features found in JGrep but not grep</h4>
 * <ul>
 *  <li>
 *      The extended regular expression of JGrep is more powerful than that of any variant and option known in GNU grep 2.4.2.
 *  </li>
 *  <li>
 *      -e, --encoding for supporting different encodings.
 *  </li>
 * </p>
 * <h4>Unsupported Features (no support planned)</h4>
 * <ul>
 *  <li>
 *      The options -E, -F, -G and -P are not supported.
 *      JGrep always uses Java regular expressions which are very similar to but more powerful than -P.
 *  </li>
 *  <li>JGrep cannot distinguish between text files and binary files.</li>
 *  <li>-b, --byte-offset for printing the byte offset with output lines.</li>
 * </ul>
 * <h4>Unsupported Features (todo)</h4>
 * <ul>
 *  <li>-z, --null-data option to indicate a data line ends with a 0 byte, not newline.</li>
 *  <li>-w, --word-regexp option to force the pattern to match only whole words.</li>
 *  <li>-x, --line-regexp option to force the pattern to match only whole lines.</li>
 *  <li>-s, --no-messages for suppressing error messages.</li>
 *  <li>-v, --revert-match for showing only non-matching lines.</li>
 *  <li>-m, --max-count=NUM stop after NUM matches.</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JGrep extends BasicCommand {

    /** The unmodifiable map with the flags.
     * Key: Flag character.
     * Value: integer flag value for {@link Pattern#compile(String, int)} resp. returned by {@link Pattern#flags()}.
     */
    private static final Map<Character, Integer> FLAG_MAP = createFlagMap();

    /** Whether or not to recurse into subdirectories or not.
     * If <code>true</code>, directories will be opened recursively and substitution performed on all files found.
     * If <code>false</code> (default), directories will simply be ignored.
     */
    private boolean recursive;

    /** The number of errors that occurred.
     * Errors are only cases where actual exceptions were thrown, i.e. {@link FileNotFoundException} or {@link IOException}.
     * If 0 (zero) no errors occurred.
     * This variable will somehow be used for the exit code.
     */
    private int errors;

    /** The file encoding that is used for reading and writing files as weil es parsing substitutions from {@link System#in}. */
    private Charset encoding = Charset.defaultCharset();

    /** Whether or not to display line numbers. */
    private boolean number;

    /** The Matcher to use for matching. */
    private Matcher matcher;

    /** The flags to use for matching. */
    private int flags;

    /** Whether or not to match whole lines. */
    private boolean matchWholeLines;

    /** Whether or not to print a null byte after each file. */
    private boolean printNullByte;

    /** The label to print as filename for stdin. */
    private String label;

    /** Whether or not to print only the matching part of a line. */
    private boolean onlyMatching;

    /** Creates an unmodifiable map with the flags.
     * @return Unmodifiable map with the flags.
     */
    private static Map<Character, Integer> createFlagMap() {
        final Map<Character, Integer> flagMap = new HashMap<Character, Integer>();
        flagMap.put('d', Pattern.UNIX_LINES);
        flagMap.put('i', Pattern.CASE_INSENSITIVE);
        flagMap.put('x', Pattern.COMMENTS);
        flagMap.put('m', Pattern.MULTILINE);
        flagMap.put('l', Pattern.LITERAL);
        flagMap.put('s', Pattern.DOTALL);
        flagMap.put('u', Pattern.UNICODE_CASE);
        flagMap.put('c', Pattern.CANON_EQ);
        return Collections.unmodifiableMap(flagMap);
    }

    /** Converts a flag string into a flag value.
     * @param flagString String that specifies substitution flags.
     * @return Integer value with the flags.
     * @throws IllegalArgumentException in case an invalid flag was specified.
     */
    public static int parseFlags(@Nullable final String flagString) {
        int flags = 0;
        if (flagString != null) {
            StringBuilder bogusFlags = null;
            for (final char c : flagString.toCharArray()) {
                final Integer flag = FLAG_MAP.get(c);
                if (flag == null) {
                    if (bogusFlags == null) {
                        bogusFlags = new StringBuilder();
                    }
                    bogusFlags.append(c);
                } else {
                    flags |= flag;
                }
            }
            if (bogusFlags != null) {
                throw new IllegalArgumentException("Unsupported flags: " + bogusFlags);
            }
        }
        return flags;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention"})
    public int run(@NotNull final List<String> args) throws Exception {
        matcher = Pattern.compile(args.remove(0), flags).matcher("");
        if (args.size() == 0) {
            grep(System.in, label);
        }
        for (final String arg : args) {
            grep(arg);
        }
        return errors;
    }

    /** Grep the specified file.
     * @param filename Name of the file to grep.
     */
    public void grep(@NotNull final String filename) {
        grep(new File(filename));
    }

    /** Grep the specified file.
     * @param file File to grep.
     */
    public void grep(@NotNull final File file) {
        if (recursive && file.isDirectory()) {
            for (final File f : file.listFiles()) {
                grep(f);
            }
        }
        if (file.isFile()) {
            try {
                final InputStream in = new FileInputStream(file);
                try {
                    grep(in, file.toString());
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                System.err.println(e);
                errors++;
            }
        }
    }

    /** Grep the specified stream.
     * @param in Stream to grep.
     * @param currentFile File to grep or <code>null</code> if unknown
     * @throws IOException in case of I/O problems.
     */
    public void grep(@NotNull final InputStream in, @Nullable final String currentFile) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        grep(new InputStreamReader(in, encoding), currentFile);
    }

    /** Grep the specified reader.
     * @param reader Reader to grep.
     * @param currentFile File to grep or <code>null</code> if unknown
     * @throws IOException in case of I/O problems.
     */
    public void grep(@NotNull final Reader reader, @Nullable final String currentFile) throws IOException {
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final BufferedReader in = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        int lineNumber = 0;
        //noinspection NestedAssignment
        for (String line; (line = in.readLine()) != null;) {
            ++lineNumber;
            matcher.reset(line);
            final boolean found = matchWholeLines ? matcher.matches() : matcher.find();
            if (found) {
                if (currentFile != null) {
                    System.out.print(currentFile + (printNullByte ? '\0' : ':'));
                }
                if (number) {
                    System.out.print(lineNumber + ":");
                }
                System.out.print(onlyMatching ? matcher.group() : line);
            }
        }
    }

    /** Sets the recurse option. */
    @Option({"r", "R", "recursive"})
    public void recursive() {
        recursive = true;
    }

    /** Sets the option how to handle directories.
     * @param action Action to use for handling directories, possible values are "read", "recurse" and "skip".
     */
    @Option({"d", "directories"})
    public void setDirectories(@NotNull final String action) {
        if ("recurse".equals(action)) {
            recursive = true;
        } else if ("read".equals(action) || "skip".equals(action)) {
            recursive = false;
        } else {
            throw new IllegalArgumentException("Unsupported value " + action + " for option directories.");
        }
    }

    /** Returns whether or not the recurse option is set.
     * @return <code>true</code> if the recurse option is set, otherwise <code>false</code>.
     */
    public boolean isRecursive() {
        return recursive;
    }

    /** Sets the encoding to use for reading and writing files.
     * @param charsetName Name of the charset to use as encoding for reading and writing files.
     */
    @Option({"e", "encoding"})
    public void setEncoding(@NotNull final String charsetName) {
        encoding = Charset.forName(charsetName);
    }

    /** Returns the currently set encoding for reading and writing files.
     * @return The currently set encoding for reading and writing files.
     */
    @NotNull public String getEncoding() {
        return encoding.name();
    }

    /** Lists available encodings for reading and writing files. */
    @Option(type = OptionType.TERMINAL, value = {"listEncodings"})
    public void listEncodings() {
        final Formatter format = new Formatter(System.err);
        final Map<String, Charset> availableCharsets = Charset.availableCharsets();
        final Collection<Charset> charsetsByDisplayname = new TreeSet<Charset>(new CharsetDisplaynameComparator());
        charsetsByDisplayname.addAll(availableCharsets.values());
        for (final Charset charset : charsetsByDisplayname) {
            format.format("%s (Aliases:", charset.name());
            for (final String alias : charset.aliases()) {
                format.format(" %s", alias);
            }
            format.format(")%n");
        }
        format.flush();
    }

    /** Sets the linenumber option. */
    @Option({"n", "line-number"})
    public void lineNumber() {
        number = true;
    }

    /** Sets the ignore case option. */
    @Option({"i", "ignore-case"})
    public void ignoreCase() {
        flags |= Pattern.CASE_INSENSITIVE;
    }

    /** Sets the flags.
     * @param flags Flags to set.
     */
    @Option({"flags"})
    public void setFlags(@NotNull final String flags) {
        this.flags = parseFlags(flags);
    }

    /** Sets the match whole lines option. */
    @Option({"x", "line-regexp"})
    public void matchWholeLines() {
        matchWholeLines = true;
    }

    /** Sets the null option. */
    @Option({"Z", "null"})
    public void printNullByte() {
        printNullByte = true;
    }

    /** Sets the label option.
     * @param label Label.
     */
    @Option({"label"})
    public void setLabel(@Nullable final String label) {
        this.label = label;
    }

    /** Sets the only matching option. */
    @Option({"o", "only-matching"})
    public void onlyMatching() {
        onlyMatching = true;
    }

}
