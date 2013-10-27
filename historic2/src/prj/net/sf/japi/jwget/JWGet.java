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

package net.sf.japi.jwget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import net.sf.japi.io.args.ArgParser;
import net.sf.japi.io.args.LogCommand;
import org.jetbrains.annotations.NotNull;

/** WGet implementation in Java.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JWGet extends LogCommand {

    /** Size of the I/O buffer. */
    private static final int BUF_SIZE = 8192;

    /** Main program.
     * @param args command line arguments
     */
    public static void main(@NotNull final String... args) {
        ArgParser.simpleParseAndRun(new JWGet(), args);
    }

    /** {@inheritDoc} */
    @SuppressWarnings({"ProhibitedExceptionDeclared"})
    public int run(@NotNull final List<String> args) throws Exception {
        int returnCode = 0;
        if (args.size() == 0) {
            log(Level.SEVERE, "noarguments");
            returnCode++;
        }
        final byte[] buf = new byte[BUF_SIZE];
        for (final String arg : args) {
            try {
                final URL url = new URL(arg);
                final URLConnection con = url.openConnection();
                final InputStream in = con.getInputStream();
                try {
                    final String location = con.getHeaderField("Content-Location");
                    final String outputFilename = new File((location != null ? new URL(url, location) : url).getFile()).getName();
                    log(Level.INFO, "writing", arg, outputFilename);
                    final OutputStream out = new FileOutputStream(outputFilename);
                    try {
                        //noinspection NestedAssignment
                        for (int bytesRead; (bytesRead = in.read(buf)) != -1;) {
                            out.write(buf, 0, bytesRead);
                        }
                    } finally {
                        out.close();
                    }
                } finally {
                    in.close();
                }
            } catch (final IOException e) {
                log(Level.WARNING, "cannotopen", arg, e);
                returnCode++;
            }
        }
        return returnCode;
    }

} // class JWGet
