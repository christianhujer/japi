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

package examples;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** Java implementation of the UNIX command <q>head</q> to demonstrate how to use the argparser library.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class Head extends BasicCommand {

    /** Default number of items to print. */
    private static final int DEFAULT_NUM_ITEMS = 10;

    /** The number of items to print. */
    private int numItems = DEFAULT_NUM_ITEMS;

    /** The kind of items to print.
     * <code>false</code>: print lines.
     * <code>true</code>: print bytes.
     */
    private boolean printBytes;

    /** Quiety / Verbosity.
     * 0 = never print filenames.
     * 1 = print filenames if more than one file.
     * 2 = always print filenames.
     */
    private int verbose = 1;

    /** Sets the number of lines to print.
     * @param lines number of lines to print
     */
    @Option({"n", "lines"})
    public void setLines(final Integer lines) {
        numItems = lines;
        printBytes = false;
    }

    /** Sets the number of bytes to print.
     * @param bytes number of bytes to print.
     */
    @Option({"c", "bytes"})
    public void setBytes(final Integer bytes) {
        numItems = bytes;
        printBytes = true;
    }

    /** Sets the command to be quiet. */
    @Option({"q", "quiet", "silent"})
    public void setQuiet() {
        verbose = 0;
    }

    /** Sets the command to be verbose. */
    @Option({"v", "verbose"})
    public void setVerbose() {
        verbose = 2;
    }

    /** {@inheritDoc} */
    public int run(@NotNull final List<String> args) throws IOException {
        int returnCode = 0;
        if (args.size() == 0) {
            if (verbose == 2) {
                System.out.println("==> STDIN <==");
            }
            copyItems(System.in);
        } else {
            for (final String arg : args) {
                if (verbose == 2 || verbose == 1 && args.size() > 1) {
                    System.out.println("==> " + arg + " <==");
                }
                try {
                    final InputStream in = new BufferedInputStream(new FileInputStream(arg));
                    try {
                        copyItems(in);
                    } finally {
                        in.close();
                    }
                } catch (final IOException e) {
                    returnCode = 1;
                    System.err.println(e);
                }
            }
        }
        return returnCode;
    }

    /** Copies the configured number of items from the specified InputStream to System.out.
     * @param in InputStream to run on
     * @throws IOException In case of I/O problems.
     */
    private void copyItems(@NotNull final InputStream in) throws IOException {
        if (printBytes) {
            copyBytes(in);
        } else {
            copyLines(in);
        }
    }

    /** Copies the first {@link #numItems} bytes from the supplied input stream to {@link System#out}.
     * @param in InputStream to copy bytes from.
     * @throws IOException in case of I/O errors.
     */
    private void copyBytes(@NotNull final InputStream in) throws IOException {
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final InputStream lin = in instanceof BufferedInputStream ? in : new BufferedInputStream(in);
        final byte[] buf = new byte[numItems];
        final int bytesRead = lin.read(buf, 0, numItems);
        System.out.write(buf, 0, bytesRead);
    }

    /** Copies the first {@link #numItems} lines from the supplied input stream to {@link System#out}.
     * @param in InputStream to copy lines from.
     * @throws IOException in case of I/O errors.
     */
    private void copyLines(@NotNull final InputStream in) throws IOException {
        @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed"})
        final BufferedReader lin = new BufferedReader(new InputStreamReader(in));
        String line;
        //noinspection NestedAssignment
        for (int i = 0; i < numItems && (line = lin.readLine()) != null; i++) {
            System.out.println(line);
        }
    }

    /** Main method.
     * @param args Command line arguments
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new Head(), args);
    }

} // class Head
