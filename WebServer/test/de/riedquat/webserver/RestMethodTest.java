package de.riedquat.webserver;

import de.riedquat.http.HttpOutputStream;
import de.riedquat.server.Server;
import de.riedquat.http.rest.RestMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static de.riedquat.http.Http11Header.LOCATION;
import static de.riedquat.http.Http11StatusCode.TEMPORARY_REDIRECT;
import static de.riedquat.java.io.Util.copy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestMethodTest {

    private boolean restMethodCalled;
    private Server webServer;

    @Before
    public void init() throws IOException {
        restMethodCalled = false;
        final WebServerFactory webServerFactory = new WebServerFactory();
        webServerFactory.addRest(this);
        webServer = webServerFactory.createWebServer();
        webServer.setSoTimeout(100);
    }

    @After
    public void close() throws IOException {
        webServer.close();
    }

    @Test
    public void givenWebServerWithRestConfig_whenGetRestUri_thenCallsRestMethod() throws IOException {
        final String data = readFromUrl("http://localhost:" + webServer.getPort() + "/restTest");
        assertEquals("Test Data", data);
        assertTrue(restMethodCalled);
    }

    @Test
    public void redirectWorks() throws IOException {
        final String data = readFromUrl("http://localhost:" + webServer.getPort() + "/redirect");
        assertEquals("Test Data", data);
        assertTrue(restMethodCalled);
    }

    @RestMethod("/restTest")
    public void restMethod(final HttpOutputStream out) throws IOException {
        out.write("Test Data".getBytes());
        restMethodCalled = true;
        out.close();
    }

    @RestMethod("/redirect")
    public void redirect(final HttpOutputStream out) throws IOException {
        out.setStatus(TEMPORARY_REDIRECT);
        out.setHeader(LOCATION, "http://localhost:" + webServer.getPort() + "/restTest");
    }

    private String readFromUrl(final String urlString) throws IOException {
        return readFromUrl(new URL(urlString));
    }

    private String readFromUrl(final URL url) throws IOException {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final InputStream in = url.openStream()) {
            copy(in, out);
            return out.toString();
        }
    }
}
