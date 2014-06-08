package de.riedquat.webserver;

import de.riedquat.server.Server;
import de.riedquat.java.io.Util;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import static de.riedquat.webserver.Assert.assertHasThreadWithName;
import static de.riedquat.webserver.Assert.assertNotHasThreadWithName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class WebServerThreadTest {

    private static Server webServer;
    private static int port;

    @BeforeClass
    public static void createWebServer() throws IOException {
        assertNotHasThreadWithName("WebServer");
        webServer = new WebServerFactory().createHttpServer((SocketAddress) null);
        port = webServer.getPort();
    }

    @AfterClass
    public static void stopWebServer() throws IOException {
        webServer.close();
        assertNotHasThreadWithName("WebServer");
    }

    @Test
    public void webServerStarted_threadNamedWebServerAvailable() throws IOException {
        assertHasThreadWithName("WebServer");
    }

    @Test(timeout = 200)
    public void acceptsMultipleConnections() throws IOException {
        simpleConnection(port, Hook.DUMMY_HOOK);
        simpleConnection(port, Hook.DUMMY_HOOK);
    }

    private void simpleConnection(final int port, final Hook hook) throws IOException {
        try (
                final Socket socket = new Socket("127.0.0.1", port);
                final InputStream in = socket.getInputStream();
                final OutputStream out = socket.getOutputStream()
        ) {
            hook.run();
            out.write("GET /foo.txt HTTP/1.0\r\n\r\n".getBytes());
            //out.flush();
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            Util.copy(bout, in);
            assertEquals("HTTP/1.0 200 OK\r\nContent-Length: 3\r\n\r\nfoo", bout.toString());
        }
    }

    @Test
    public void acceptsMultipleConnectionsAtTheSameTime() throws IOException {
        simpleConnection(port, () -> simpleConnection(port, Hook.DUMMY_HOOK));
    }

    @Test
    public void webServerStarted_anotherInstanceCanBeStarted() throws IOException {
        try (
                final Server webServer2 = new WebServerFactory().createHttpServer((SocketAddress) null)
        ) {
            assertNotSame(webServer.getPort(), webServer2.getPort());
        }
    }

    @Test
    public void webServerHasClient_clientThreadNameAvailable() throws Exception {
        try (
                final Socket socket = new Socket("127.0.0.1", port);
                final OutputStream out = socket.getOutputStream();
                final InputStream in = socket.getInputStream()
        ) {
            out.write("GET /foo.txt HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\n".getBytes());
            out.flush();
            final int b = in.read();
            assert (b != 0);
            assertHasThreadWithName("WebServer " + socket.getLocalAddress() + ":" + socket.getLocalPort());
        }
    }

    @Test
    public void http11_keepAlive() throws IOException {
        try (
                final Socket socket = new Socket("127.0.0.1", port);
                final OutputStream out = socket.getOutputStream();
                final InputStream in = socket.getInputStream()
        ) {
            out.write("HEAD /foo.txt HTTP/1.1\r\nHost: 127.0.0.1\r\n\r\nHEAD / HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n".getBytes());
            out.flush();
            socket.shutdownOutput();
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            Util.copy(bout, in);
            assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 3\r\n\r\nHTTP/1.1 200 OK\r\nConnection: close\r\nContent-Length: 15\r\n\r\n", bout.toString());
        }
    }
}
