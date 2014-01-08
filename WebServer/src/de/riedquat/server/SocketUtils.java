package de.riedquat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class SocketUtils {
    static ServerSocket createBoundEphemeralServerSocket() throws IOException {
        return createBoundServerSocket(null);
    }

    static ServerSocket createBoundServerSocket(final SocketAddress socketAddress) throws IOException {
        final ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(socketAddress);
        return serverSocket;
    }
}
