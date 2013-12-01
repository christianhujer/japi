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

package net.sf.japi.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import net.sf.japi.io.Copier;
import org.jetbrains.annotations.NotNull;

/** This class forwards incoming TCP connections to another host and port.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Forwarder implements Runnable {

    /** Main program.
     * @param args command line arguments (currently ignored)
     * @throws IOException In case of I/O problems.
     * @todo Extract main() into a tool that uses argparser.
     */
    public static void main(final String... args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        //noinspection InfiniteLoopStatement
        while (true) {
            final Socket server = serverSocket.accept();
            try {
                final Socket client = new Socket(args[1], Integer.parseInt(args[2]));
                new Forwarder(client, server).start();
            } catch (final IOException ignore) {
                try {
                    server.close();
                } catch (final IOException ignored) {
                    /* ignore */
                }
            }
        }
    }

    /** First socket. */
    @NotNull private final Socket socket1;

    /** Second socket. */
    @NotNull private final Socket socket2;

    /** Create a new Forwarder.
     * @param socket1 first socket
     * @param socket2 second socket
     */
    public Forwarder(@NotNull final Socket socket1, @NotNull final Socket socket2) {
        this.socket1 = socket1;
        this.socket2 = socket2;
    }

    /** Start the forwarder. */
    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
                try {
                    final Thread c1 = new Copier(socket1.getInputStream(), socket2.getOutputStream()).start();
                    final Thread c2 = new Copier(socket2.getInputStream(), socket1.getOutputStream()).start();
                    c1.join();
                    c2.join();
                } finally {
                    socket1.close();
                }
            } finally {
                socket2.close();
            }
        } catch (final InterruptedException ignore) {
            /* ignore */
        } catch (final IOException e) {
            e.printStackTrace();  // TODO:2009-02-23:christianhujer:Better handling of the exception.
        }
    }

} // class Forwarder
