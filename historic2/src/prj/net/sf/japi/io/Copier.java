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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.annotations.NotNull;

/** A Runnable that copies from an InputStream to an OutputStream.
 *
 * <h4>Usage example</h4>
 * <pre>
 *      // Copies data from System.in to System.out.
 *      new Copier(System.in, System.out).start();
 *
 *      // Copies data from one file to another.
 *      new Copier(new FileInputStream(inputFilename), new FileOutputStream(outputFilename)).start();
 * </pre>
 *
 * @note Copying is done in a separate thread.
 *       The starting thread is not notified of any exceptions that occur.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
// TODO:2009-06-14:christianhujer:Implement reasonable exception handling.
public class Copier implements Runnable {

    /** Default buffer size when copying. */
    public static final int DEFAULT_BUF_SIZE = 8192;

    /** Default automatic flush. */
    public static final boolean DEFAULT_AUTO_FLUSH = true;

    /** Default automatic close. */
    public static final boolean DEFAULT_AUTO_CLOSE = true;

    /** The InputStream to read from. */
    @NotNull private final InputStream in;

    /** The OutputStream to write to. */
    @NotNull private final OutputStream out;

    /** The buffer size to use. */
    private final int bufSize;

    /** Whether to flush automatically. */
    private final boolean autoFlush;

    /** Whether to close streams automatically after copying. */
    private final boolean autoClose;

    /** Create a Copier with default buffer size and autoFlush.
     * @param in the InputStream to read from
     * @param out the OutputStream to write to
     */
    public Copier(@NotNull final InputStream in, @NotNull final OutputStream out) {
        this(in, out, DEFAULT_BUF_SIZE, DEFAULT_AUTO_FLUSH, DEFAULT_AUTO_CLOSE);
    }

    /** Create a Copier with specified buffer size and automatic flush behaviour.
     * @param in the InputStream to read from
     * @param out the OutputStream to write to
     * @param bufSize buffer size to use while copying
     */
    public Copier(@NotNull final InputStream in, @NotNull final OutputStream out, final int bufSize) {
        this(in, out, bufSize, DEFAULT_AUTO_FLUSH, DEFAULT_AUTO_CLOSE);
    }

    /** Create a Copier with specified buffer size and specified flush behaviour.
     * @param in the InputStream to read from
     * @param out the OutputStream to write to
     * @param bufSize buffer size to use while copying
     * @param autoFlush whether to flush automatically (true for automatic flush, false for flush on close)
     * @param autoClose whether to close the streams automatically (true for automatic close, false for no close)
     */
    public Copier(@NotNull final InputStream in, @NotNull final OutputStream out, final int bufSize, final boolean autoFlush, final boolean autoClose) {
        this.in = in;
        this.out = out;
        this.bufSize = bufSize;
        this.autoFlush = autoFlush;
        this.autoClose = autoClose;
    }

    /** Start the copier in a new thread.
     * @return the newly created thread
     */
    @NotNull public Thread start() {
        final Thread thread = new Thread(this);
        thread.start();
        return thread;
    }

    /** {@inheritDoc} */
    public void run() {
        final byte[] buf = new byte[bufSize];
        try {
            try {
                for (int bytesRead; (bytesRead = in.read(buf)) != -1;) {
                    out.write(buf, 0, bytesRead);
                    if (autoFlush) {
                        out.flush();
                    }
                }
            } finally {
                if (autoFlush) {
                    out.flush();
                }
                if (autoClose) {
                    out.close();
                    in.close();
                }
            }
        } catch (final IOException e) {
            System.err.println(e);
        }
    }

} // class Copier
