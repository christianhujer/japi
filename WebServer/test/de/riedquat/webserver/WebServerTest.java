package de.riedquat.webserver;

import de.riedquat.java.io.NullOutputStream;
import de.riedquat.server.Server;
import de.riedquat.java.io.Util;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;

public class WebServerTest extends AbstractHttpSessionTest {

    private static Server webServer;
    private static int port;

    @BeforeClass
    public static void startWebServer() throws IOException {
        webServer = new WebServerFactory().createHttpServer();
        port = webServer.getPort();
    }

    @AfterClass
    public static void stopServer() throws IOException {
        webServer.close();
    }

    @Test
    public void testWithURL() throws IOException {
        final URL url = new URL("http://127.0.0.1:" + port + "/foo.txt");
        final URLConnection connection = url.openConnection();
        connection.addRequestProperty("Connection", "close");
        try (final InputStream in = connection.getInputStream()) {
            Util.copy(NullOutputStream.getInstance(), in);
        }
    }

    @Override
    public void assertResponse(final String request, final String expectedResponse) throws IOException {
        try (
                final Socket socket = new Socket("127.0.0.1", port);
                final InputStream in = socket.getInputStream();
                final OutputStream out = socket.getOutputStream()
        ) {
            out.write(request.getBytes());
            socket.shutdownOutput();
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            Util.copy(output, in);
            assertEquals(expectedResponse, output.toString());
        }
    }

}
