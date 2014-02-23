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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import org.jetbrains.annotations.NotNull;

/** Utility class for I/O.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class IOUtils {

    /** Default size of buffers for copying and similar operations. */
    private static final int DEFAULT_BUF_SIZE = 4096;

    /** Utility class - do not instantiate. */
    private IOUtils() {
    }

    /** Copies all remaining data from one stream to another.
     *
     * The output stream is not flushed after copying.
     * That is left to the caller.
     * The reason is network communication where the caller might want to flush the output stream at a later point.
     *
     * @param in InputStream from which to copy.
     * @param out OutputStream to which the data is copied.
     *
     * @throws IOException in case of I/O problems.
     */
    public static void copy(@NotNull final InputStream in, @NotNull final OutputStream out) throws IOException {
        final byte[] buf = new byte[DEFAULT_BUF_SIZE];
        //noinspection NestedAssignment
        for (int bytesRead; (bytesRead = in.read(buf)) != -1;) {
            out.write(buf, 0, bytesRead);
        }
    }

    /** Copies a file.
     *
     * @param fromFile File to copy.
     * @param toFile File to be the copy.
     *
     * @throws IOException in case of I/O problems.
     */
    public static void copy(@NotNull final File fromFile, @NotNull final File toFile) throws IOException {
        try (final InputStream in = new FileInputStream(fromFile);
             final OutputStream out = new FileOutputStream(toFile)) {
            copy(in, out);
        }
    }

    /** Returns an iterable for lines from the specified reader.
     * @param in Reader for which to return lines.
     * @return An iterable for the lines of the specified reader.
     */
    public static Iterable<String> lines(@NotNull final Reader in) {
        return new BRLineIterable(in);
    }
}
