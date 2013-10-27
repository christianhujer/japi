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
import java.io.Reader;
import java.util.List;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.BasicCommand;
import net.sf.japi.io.args.Option;
import org.jetbrains.annotations.NotNull;

/** Java implementation of the UNIX command <q>uniq</q> to demonstrate how to use the argparser library.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class Uniq extends BasicCommand {

    /** Whether to prefix lines with the count of their occurrence. */
    private boolean count;

    /** Whether to only print lines that occurred more than once. */
    private boolean repeated;

    /** Whether to ignore case. */
    private boolean ignoreCase;

    /** Sets that lines should prefixed with the count of their occurrence. */
    @Option({"c", "count"})
    public void setCount() {
        count = true;
    }

    /** Sets that only lines that occurred more than once will be printed. */
    @Option({"d", "repeated"})
    public void setRepeated() {
        repeated = true;
    }

    /** Sets that the case should be ignored. */
    @Option({"i", "ignore-case"})
    public void setIgnoreCase() {
        ignoreCase = true;
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"InstanceMethodNamingConvention", "ProhibitedExceptionDeclared"})
    public int run(@NotNull final List<String> args) throws Exception {
        int returnCode = 0;
        for (final String arg : args) {
            try {
                final InputStream in = new BufferedInputStream(new FileInputStream(arg));
                try {
                    uniq(in);
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                returnCode = 1;
                System.err.println(e);
            }
        }
        return returnCode;
    }

    /** Prints unique lines from the specified InputStream.
     * @param in InputStream to print unique lines from.
     * @throws IOException In case of I/O problems.
     */
    private void uniq(@NotNull final InputStream in) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        uniq(new InputStreamReader(in));
    }

    /** Prints unique lines from the specified Reader.
     * @param in Reader to print unique lines from.
     * @throws IOException In case of I/O problems.
     */
    private void uniq(@NotNull final Reader in) throws IOException {
        //noinspection IOResourceOpenedButNotSafelyClosed
        final BufferedReader bin = in instanceof BufferedReader ? (BufferedReader) in : new BufferedReader(in);
        String previousLine = bin.readLine();
        if (previousLine != null) {
            String line;
            int lineCount = 1;
            do {
                line = bin.readLine();
                if (!(ignoreCase ? previousLine.equalsIgnoreCase(line) : previousLine.equals(line)) && (!repeated || lineCount > 1)) {
                    if (count) {
                        System.out.format("%7d %s%n", lineCount, previousLine);
                    } else {
                        System.out.println(previousLine);
                    }
                    lineCount = 0;
                }
                previousLine = line;
                lineCount++;
            } while (line != null);
        }
    }

    /** Main method.
     * @param args Command line arguments
     */
    public static void main(final String... args) {
        ArgParser.simpleParseAndRun(new Uniq(), args);
    }

} // class Uniq
