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

package net.sf.japi.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** An Iterator to get lines from a Reader.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class BRLineIterator implements Iterator<String> {

    /** The BufferedReader from which the lines are read. */
    private final BufferedReader in;

    /** The next line.
     * It is pre-fetched to return a meaningful value in {@link #hasNext()}.
     */
    private String nextLine;

    /** Create a BRLineIterator.
     * @param in BufferedReader from which to return lines.
     */
    public BRLineIterator(@NotNull final Reader in) {
        //noinspection IOResourceOpenedButNotSafelyClosed
        this.in = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        nextLine = readLine();
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String next() {
        if (nextLine == null) {
            throw new NoSuchElementException();
        }
        try {
            return nextLine;
        } finally {
            nextLine = readLine();
        }
    }

    /** Read the next line from {@link #in} without throwing an exception.
     * @return Next line or {@code null} if there are no more lines or {@code null} as well in case of exceptions.
     */
    @Nullable
    private String readLine() {
        try {
            return in.readLine();
        } catch (final IOException ignore) {
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
