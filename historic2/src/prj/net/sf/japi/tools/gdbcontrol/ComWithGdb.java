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

package net.sf.japi.tools.gdbcontrol;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.annotations.NotNull;

/** A Java wrapper for communication with gdb.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public final class ComWithGdb {

    /** The prompt String. */
    private final CharSequence prompt = "(gdb) ";

    /** The process of gdb. */
    private Process gdbProcess;

    /** InputStream to read data from gdb's stdout. */
    private final InputStream gdbIn;

    /** OutputStream to write data to gdb's stdin. */
    private final OutputStream gdbOut;

    /** OutputStream to read data from gdb's stderr. */
    private final InputStream gdbErr;

    /** Own InputStream to read data from usually stdin. */
    private final InputStream stdIn;

    /** Own OutputStream to write data to usually stdout. */
    private final OutputStream stdOut;

    /** Own OutputStream to write data to usually stderr. */
    private final OutputStream stdErr;

    /** The encoding to use. */
    private final String encoding = "US-ASCII";

    /** Collector for read operations. */
    private final ByteArrayOutputStream collector = new ByteArrayOutputStream();

    /** Creates a ComWithGdb.
     * @param name Name of the program to debug.
     * @throws IOException in case of I/O problems.
     */
    public ComWithGdb(final String name) throws IOException {
        gdbProcess = Runtime.getRuntime().exec("gdb " + name);
        gdbIn = gdbProcess.getInputStream();
        gdbOut = gdbProcess.getOutputStream();
        gdbErr = gdbProcess.getErrorStream();
        stdIn = System.in;
        stdOut = System.out;
        stdErr = System.err;
        readUntilPrompt();
    }

    /** Starts a debug session.
     * @throws IOException in case of I/O problems.
     */
    public void start() throws IOException {
        execute("start");
    }

    /** Reads from gdb until gdb sends a prompt.
     * @return The text until and including the prompt.
     * @throws IOException in case of I/O problems.
     */
    public String readUntilPrompt() throws IOException {
        return readUntil(prompt);
    }

    /** Reads until a specific String is found.
     * @warning This implementation will only work correctly if the text does not contain duplicate characters.
     *          It works fine for Strings like "(gdb) ".
     *          It will not work reliably for Strings like "A_AB" because this will not find "A_AB" in "A_A_AB".
     * @param text The text to search, usually a prompt.
     * @return The data that was read until the text was found, including that text itself.
     * @throws IOException in case of I/O problems.
     */
    public synchronized String readUntil(@NotNull final CharSequence text) throws IOException {
        try {
            int match = 0;
            for (int c; (c = gdbIn.read()) != -1;) {
                stdOut.write(c);
                collector.write(c);
                if (c == text.charAt(match) || c == text.charAt(match = 0)) {
                    match++;
                    if (match == text.length()) {
                        return collector.toString(encoding);
                    }
                }
            }
            throw new EOFException("Unexpected end of data from gdb.");
        } finally {
            stdOut.flush();
        }
    }

    /** Sends a Command to gdb and waits for the next prompt.
     * @param command Command to send.
     * @return What gdb responded to that command (including the prompt).
     * @throws IOException in case of I/O problems.
     */
    public String execute(@NotNull final String command) throws IOException {
        send(command);
        return readUntilPrompt();
    }

    /** Sends a Command to gdb.
     * @param command Command to send.
     * @throws IOException in case of I/O problems.
     */
    public void send(@NotNull final String command) throws IOException {
        final byte[] data = command.getBytes(encoding);
        stdOut.write(ANSI.L_BLUE);
        gdbOut.write(data);
        stdOut.write(data);
        gdbOut.write('\n');
        stdOut.write('\n');
        gdbOut.flush();
        stdOut.write(ANSI.NORMAL);
        stdOut.flush();
    }

    /** Quits the gdb.
     * @return exit value of the gdb process.
     * @throws IOException in case of I/O problems.
     * @throws InterruptedException in case the calling thread was interrupted while waiting for gdb's termination.
     */
    public int quit() throws IOException, InterruptedException {
        send("quit");
        gdbProcess.waitFor();
        final int retVal = gdbProcess.exitValue();
        gdbProcess.destroy();
        //noinspection AssignmentToNull
        gdbProcess = null;
        return retVal;
    }

    /** Main program.
     * @param args Command line arguments - name of the executable to debug.
     * @throws IOException in case of I/O problems.
     * @throws InterruptedException in case the calling thread was interrupted while waiting for gdb's termination.
     */
    public static void main(final String... args) throws IOException, InterruptedException {
        final ComWithGdb gdb = new ComWithGdb(args[0]);
        gdb.execute("start");
        gdb.quit();
    }

    /** ANSI color escape sequences. */
    @SuppressWarnings({"MagicNumber", "ClassNamingConvention"})
    private static final class ANSI {

        /** Ansi control sequence for switching to dark black. */
        static final byte[] D_BLACK   = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x30, 0x6D };

        /** Ansi control sequence for switching to dark red. */
        static final byte[] D_RED     = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x31, 0x6D };

        /** Ansi control sequence for switching to dark green. */
        static final byte[] D_GREEN   = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x32, 0x6D };

        /** Ansi control sequence for switching to dark yellow. */
        static final byte[] D_YELLOW  = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x33, 0x6D };

        /** Ansi control sequence for switching to dark blue. */
        static final byte[] D_BLUE    = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x34, 0x6D };

        /** Ansi control sequence for switching to dark magenta. */
        static final byte[] D_MAGENTA = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x35, 0x6D };

        /** Ansi control sequence for switching to dark cyan. */
        static final byte[] D_CYAN    = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x36, 0x6D };

        /** Ansi control sequence for switching to dark white. */
        static final byte[] D_WHITE   = { 0x1B, 0x5B, 0x30, 0x30, 0x3B, 0x33, 0x37, 0x6D };


        /** Ansi control sequence for switching to light black. */
        static final byte[] L_BLACK   = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x30, 0x6D };

        /** Ansi control sequence for switching to light red. */
        static final byte[] L_RED     = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x31, 0x6D };

        /** Ansi control sequence for switching to light green. */
        static final byte[] L_GREEN   = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x32, 0x6D };

        /** Ansi control sequence for switching to light yellow. */
        static final byte[] L_YELLOW  = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x33, 0x6D };

        /** Ansi control sequence for switching to light blue. */
        static final byte[] L_BLUE    = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x34, 0x6D };

        /** Ansi control sequence for switching to light magenta. */
        static final byte[] L_MAGENTA = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x35, 0x6D };

        /** Ansi control sequence for switching to light cyan. */
        static final byte[] L_CYAN    = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x36, 0x6D };

        /** Ansi control sequence for switching to light white. */
        static final byte[] L_WHITE   = { 0x1B, 0x5B, 0x30, 0x31, 0x3B, 0x33, 0x37, 0x6D };

        /** Ansi control sequence for switching to normal. */
        static final byte[] NORMAL  = { 0x1B, 0x5B, 0x30, 0x30, 0x6D };

        /** Utility class - do not instanciate. */
        private ANSI() {
        }
    }
}
