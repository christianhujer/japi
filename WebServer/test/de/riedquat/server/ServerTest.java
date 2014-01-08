package de.riedquat.server;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private int numberOfConnectionsAcceptedInThisTestCase;

    @Test
    public void connectionAttempt_connectionHandled() throws IOException {
        final String uniqueThreadName = "TestServer" + hashCode();
        try (
                final Server server = new Server(uniqueThreadName, this::handleSocket);
                final Socket socket = new Socket("127.0.0.1", server.getPort())
        ) {
            socket.getOutputStream().write(0);
            assertEquals("NUL", 0, socket.getInputStream().read());
            assertEquals("EOF", -1, socket.getInputStream().read());
            socket.shutdownOutput();
        }
        assertNumberOfConnections(1);
    }

    @Test
    public void twoConnectionAttempts_handlesBothConnections() throws IOException {
        final String uniqueThreadName = "TestServer" + hashCode();
        try (
                final Server server = new Server(uniqueThreadName, this::handleSocket);
                final Socket socket1 = new Socket("127.0.0.1", server.getPort());
                final Socket socket2 = new Socket("127.0.0.1", server.getPort())
        ) {
            socket1.getOutputStream().write(0);
            socket2.getOutputStream().write(0);
            assertEquals("NUL", 0, socket1.getInputStream().read());
            assertEquals("NUL", 0, socket2.getInputStream().read());
            assertEquals("EOF", -1, socket1.getInputStream().read());
            assertEquals("EOF", -1, socket2.getInputStream().read());
            socket1.shutdownOutput();
            socket2.shutdownOutput();
        }
        assertNumberOfConnections(2);
    }

    public void handleSocket(final Socket socket) throws IOException {
        assertEquals(0, socket.getInputStream().read());
        socket.getOutputStream().write(0);
        socket.shutdownOutput();
        assertEquals(-1, socket.getInputStream().read());
        socket.close();
        numberOfConnectionsAcceptedInThisTestCase++;
    }

    private void assertNumberOfConnections(final int expected) {
        assertEquals("Number of connections", expected, numberOfConnectionsAcceptedInThisTestCase);
    }
}
