package de.riedquat.server;

import java.io.IOException;
import java.net.Socket;

public interface SocketHandler {
    void handleSocket(Socket socket) throws IOException;
}
