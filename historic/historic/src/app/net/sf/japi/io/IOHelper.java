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
import java.io.InputStream;
import java.io.OutputStream;

/** A class with helper methods for In- and Output.
 * @author <a href="mailto:chris@riedquat.de">Christian Hujer</a>
 */
public class IOHelper {

    /** Default buffer size. */
    public static final int DEFAULT_BUF_SIZE = 8192;

    /** Copies all bytes from one stream to another using a default buffer size (see {@link #DEFAULT_BUF_SIZE}).
     * @param in InputStream source
     * @param out OutputStream destination
     * @throws IOException on i/o problems
     */
    public static void copy(final InputStream in, final OutputStream out) throws IOException {
        copy(in, out, DEFAULT_BUF_SIZE);
    }

    /** Copies all bytes from one stream to another using a specified buffer size.
     * @param in InputStream source
     * @param out OutputStream destination
     * @param size buffer size in bytes
     * @throws IOException on i/o problems
     */
    public static void copy(final InputStream in, final OutputStream out, final int size) throws IOException {
        final byte[] buf = new byte[size];
        int bytesRead;
        while ((bytesRead = in.read(buf)) != -1) {
            out.write(buf, 0, bytesRead);
        }
        out.flush();
    }

    /** Copies all bytes from one stream to another using a bytewise copy.
     * @param in InputStream source
     * @param out OutputStream destination
     * @throws IOException on i/o problems
     */
    public static void copyBW(final InputStream in, final OutputStream out) throws IOException {
        int byteRead;
        while ((byteRead = in.read()) != -1) {
            out.write(byteRead);
        }
    }

    /** private constructor, this class shall not be instantiated because it does not contain any instance information. */
    private IOHelper() { }

} // class IOHelper
