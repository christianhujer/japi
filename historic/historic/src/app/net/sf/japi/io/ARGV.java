/* JAPI - (Yet another (hopefully) useful) Java API
 *
 * Copyright (C) 2004-2006 Christian Hujer
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package net.sf.japi.io;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A special delegate of ARGV Reader supplying lines via an Iterator.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class ARGV implements Iterable<String>, Iterator<String> {

    /** ARGVReader. */
    @Nullable private ARGVReader argvReader;

    /** Next line. */
    @Nullable private String nextLine;

    /** Create an ARGV.
     * @param args Command line arguments or some other String array containing 0 or more file names.
     */
    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
    public ARGV(@NotNull final String... args) {
        argvReader = new ARGVReader(args);
        try {
            nextLine = argvReader.readLine();
        } catch (final IOException ignore) {
            close();
        }
    }

    /** Close the current ARGVReader. */
    private void close() {
        nextLine = null;
        try {
            assert argvReader != null;
            argvReader.close();
        } catch (final IOException ignore) {
            // ignore
        } finally {
            argvReader = null;
        }
    }

    /** {@inheritDoc} */
    public ARGV iterator() {
        return this;
    }

    /** {@inheritDoc} */
    public boolean hasNext() {
        return nextLine != null;
    }

    /** {@inheritDoc} */
    @NotNull public String next() {
        if (nextLine == null) {
            throw new NoSuchElementException();
        }
        try {
            return nextLine;
        } finally {
            try {
                assert argvReader != null;
                nextLine = argvReader.readLine();
            } catch (final IOException ignore) {
                close();
            }
        }
    }

    /** {@inheritDoc} */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /** Get the name of the current file.
     * @return name of the current file
     * @throws IllegalStateException if all arguments from the list have been used up, so there is no current file
     */
    public String getCurrentFilename() throws IllegalStateException {
        if (argvReader != null) {
            return argvReader.getCurrentFilename();
        } else {
            throw new IllegalStateException("name of current file not available after argument list has been used up.");
        }
    }

} // class ARGV
