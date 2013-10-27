/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.io.args;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A TokenReader reads arguments from a file, non-recursive.
 * That means the arguments are read regarding a certain argument syntax which should match with bash-like Quoting, but remain otherwise unparsed.
 * <h4>Quoting and Escaping</h4>
 * <ul>
 *  <li>\ introduces an escape. The escape character will be ignored, and the character following the escape character looses its special meaning if any.</li>
 *  <li>" introduces a string. A string is ended with ". Inside a string, \ only escapes ".</li>
 *  <li>' introduces a literal string. A literal string is ended with '. Inside a literal string, only ' has special meaning.</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.2
 */
public class TokenReader implements Closeable, Iterable<String>, Iterator<String> {

    /** Reader to read from. */
    @NotNull private final Reader in;

    /** The next token. */
    @Nullable private String next;

    /** Creates a TokenReader.
     * @param in InputStream to read from.
     */
    public TokenReader(@NotNull final InputStream in) {
        //noinspection IOResourceOpenedButNotSafelyClosed
        this.in = new InputStreamReader(in);
        next = readNextToken();
    }

    /** {@inheritDoc} */
    public void close() throws IOException {
        in.close();
    }

    /** {@inheritDoc} */
    @NotNull public Iterator<String> iterator() {
        return this;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return next != null;
    }

    /** {@inheritDoc} */
    @NotNull public String next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        try {
            return next;
        } finally {
            next = readNextToken();
        }
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /** Reads the next token from the underlying reader.
     * @return Next token read from the underlying reader or <code>null</code> if no more tokens are available.
     */
    @Nullable public String readNextToken() {
        final StringBuilder nextToken = new StringBuilder();
        boolean tokenValid = false;
        Mode mode = Mode.WHITESPACE;
        try {
            for (int rc; (rc = in.read()) != -1;) {
                final char c = (char) rc;
                switch (mode) {
                case WHITESPACE:
                    if (c == '"') {
                        mode = Mode.STRING;
                        tokenValid = true;
                    } else if (c == '\'') {
                        mode = Mode.CHARS;
                        tokenValid = true;
                    } else if (c == '\\') {
                        mode = Mode.ESCAPE;
                        tokenValid = true;
                    } else if (!Character.isWhitespace(c)) {
                        nextToken.append(c);
                        mode = Mode.NORMAL;
                        tokenValid = true;
                    }
                    break;
                case NORMAL:
                    if (Character.isWhitespace(c)) {
                        assert tokenValid;
                        assert nextToken.length() != 0;
                        return nextToken.toString();
                    } else if (c == '"') {
                        mode = Mode.STRING;
                    } else if (c == '\'') {
                        mode = Mode.CHARS;
                    } else if (c == '\\') {
                        mode = Mode.ESCAPE;
                    } else {
                        nextToken.append(c);
                    }
                    break;
                case STRING:
                    if (c == '"') {
                        mode = Mode.NORMAL;
                    } else if (c == '\\') {
                        mode = Mode.STRING_ESCAPE;
                    } else {
                        nextToken.append(c);
                    }
                    break;
                case STRING_ESCAPE:
                    if (c != '"') {
                        nextToken.append('\\');
                    }
                    nextToken.append(c);
                    mode = Mode.STRING;
                    break;
                case ESCAPE:
                    nextToken.append(c);
                    mode = Mode.NORMAL;
                    break;
                case CHARS:
                    if (c == '\'') {
                        mode = Mode.NORMAL;
                    } else {
                        nextToken.append(c);
                    }
                    break;
                default:
                    assert false;
                }
            }
        } catch (final IOException ignore) {
            // ignore
        }
        return tokenValid ? nextToken.toString() : null;
    }

    /** The mode of the tokenizer. */
    private enum Mode {

        /** White space. Also starting mode. */
        WHITESPACE,

        /** Normal - not whitespace and not inside a String. */
        NORMAL,

        /** String - inside "". */
        STRING,

        /** String Escape - \ inside "". */
        STRING_ESCAPE,

        /** Escape - after \ but outside "". */
        ESCAPE,

        /** String - inside ''. */
        CHARS
    } // enum Mode

} // class TokenReader
