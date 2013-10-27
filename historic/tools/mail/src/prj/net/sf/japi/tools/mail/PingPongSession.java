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

package net.sf.japi.tools.mail;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A PingPongSession is a session for simple ping pong protocols like SMTP.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class PingPongSession implements Closeable {

    /** The Socket to communicate with. */
    private final Socket socket;

    /** The Reader to read from. */
    private final BufferedReader sin;

    /** The Writer to write to. */
    private final PrintWriter sout;

    /** Whether or not to print debug messages. */
    private boolean debug;

    /** The debug stream. */
    private PrintStream err = System.err;

    /** Establishes a PingPong session.
     * @param socket Socket for which to establish a PingPong session.
     * @throws IOException in case of I/O problems when connecting the streams of the socket.
     */
    public PingPongSession(@NotNull final Socket socket) throws IOException {
        this(socket.getInputStream(), socket.getOutputStream(), socket);
    }

    /** Establishes a PingPong session.
     * @param in InputStream to communicate with.
     * @param out OutputStream to communicate with.
     * @throws IOException in case of I/O problems when connecting the streams of the socket.
     */
    public PingPongSession(@NotNull final InputStream in, @NotNull final OutputStream out) throws IOException {
        this(in, out, null);
    }

    /** Establishes a PingPong session.
     * @param in InputStream to communicate with.
     * @param out OutputStream to communicate with.
     */
    public PingPongSession(@NotNull final Reader in, @NotNull final Writer out) {
        this(in, out, null);
    }

    /** Establishes a PingPong session.
     * @param in InputStream to communicate with.
     * @param out OutputStream to communicate with.
     * @param socket Socket to communicate with.
     * @throws IOException in case of I/O problems when connecting the streams of the socket.
     */
    public PingPongSession(@NotNull final InputStream in, @NotNull final OutputStream out, @Nullable final Socket socket) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        this(new InputStreamReader(in, "ASCII"), new OutputStreamWriter(out, "ASCII"), socket);
    }

    /** Establishes a PingPong session.
     * @param in Reader to communicate with.
     * @param out Writer to communicate with.
     * @param socket Socket to communicate with.
     */
    public PingPongSession(@NotNull final Reader in, @NotNull final Writer out, @Nullable final Socket socket) {
        //noinspection IOResourceOpenedButNotSafelyClosed
        sin = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        //noinspection IOResourceOpenedButNotSafelyClosed
        sout = new PrintWriter(out, true);
        this.socket = socket;
    }

    /** Sets whether or not to print debug messages.
     * @param debug <code>true</code> if debug messages are desired, otherwise <code>false</code>.
     */
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    /** Returns whether or not debug messages are printed.
     * @return <code>true</code> if debug messages are printed, otherwise <code>false</code>.
     */
    public boolean isDebug() {
        return debug;
    }

    /** Sets the Stream to which debug messages are printed.
     * @param err Stream to which debug messages are printed.
     */
    public void setErr(@NotNull final PrintStream err) {
        this.err = err;
    }

    /** Returns the Stream to which debug messages are printed.
     * @return Stream to which debug messages are printed.
     */
    public PrintStream getErr() {
        return err;
    }

    /** Sends a line of text to the SMTP server, expecting an answer with a specific SMTP return code.
     * @param line Line to send.
     * @param returnCode Return code to wait for after sending the line.
     * @throws IOException in case of I/O problems or the expected return code was not received.
     */
    public void sendAndWait(@NotNull final String line, @NotNull final String returnCode) throws IOException {
        send(line);
        waitFor(returnCode);
    }

    /** Waits for a specific SMTP return code.
     * @param returnCode Return code to wait for.
     * @throws IOException in case the expected return code was not received.
     */
    public void waitFor(@NotNull final String returnCode) throws IOException {
        String line;
        do {
            line = sin.readLine();
            if (debug && line != null) {
                err.println("< " + line);
            }
        } while (line != null && line.startsWith(returnCode + "-"));
        if (line == null) {
            if (debug) {
                err.println("< <EOF>");
            }
            throw new EOFException();
        }
        if (!line.startsWith(returnCode + " ")) {
            throw new IOException("Unexpected response from SMTP server.\nExpected: " + returnCode + "\nReceived: " + line);
        }
    }

    /** Sends a line of text to the SMTP server.
     * @param line Line to send.
     * @throws IOException in case of I/O problems.
     */
    public void send(@NotNull final String line) throws IOException {
        sout.println(line);
        if (debug) {
            err.println("> " + line);
        }
    }

    /** {@inheritDoc} */
    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        } else {
            sout.close();
            sin.close();
        }
    }
}
