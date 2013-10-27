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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import org.jetbrains.annotations.NotNull;

/** Java implementation of the UNIX command <q>cat</q> to demonstrate how to use the argparser library.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class Cat extends BasicCommand {

    /** Size of the internal buffer for performing the Cat. */
    private static final int BUF_SIZE = 4096;

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention", "ProhibitedExceptionDeclared"})
    public int run(@NotNull final List<String> args) throws Exception {
        int returnCode = 0;
        if (args.size() == 0) {
            copy(System.in, System.out);
        } else {
            for (final String arg : args) {
                try {
                    final InputStream in = new FileInputStream(arg);
                    try {
                        copy(in, System.out);
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

    /** Copies data from one input stream to another.
     * @param in InputStream to read from.
     * @param out InputStream to write to.
     * @throws IOException in case of I/O problems.
     */
    private void copy(@NotNull final InputStream in, @NotNull final OutputStream out) throws IOException {
        final byte[] buf = new byte[BUF_SIZE];
        //noinspection NestedAssignment
        for (int bytesRead; (bytesRead = in.read(buf)) != -1;) {
            out.write(buf, 0, bytesRead);
        }
    }

    /** Main method.
     * @param args Command line arguments
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new Cat(), args);
    }

} // class Cat
