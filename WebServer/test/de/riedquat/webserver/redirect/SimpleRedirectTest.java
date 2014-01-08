package de.riedquat.webserver.redirect;

import de.riedquat.http.HttpOutputStream;
import de.riedquat.server.Server;
import de.riedquat.webserver.WebServerFactory;
import de.riedquat.http.rest.RestMethod;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static de.riedquat.java.io.Util.copy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleRedirectTest {
    private boolean restMethodCalled;
    private Server webServer;

    @Before
    public void init() throws IOException {
        restMethodCalled = false;
        final WebServerFactory webServerFactory = new WebServerFactory();
        webServerFactory.addRest(this);
        final Map<String, String> redirectMap = new HashMap<>();
        webServer = webServerFactory.createWebServer();
        redirectMap.put("/redirectMe", "http://localhost:" + webServer.getPort() + "/redirectTarget");
        final Redirect redirect = new SimpleRedirect("", redirectMap);
        webServerFactory.setRedirect(redirect);
        webServer.setSoTimeout(100);
    }

    @After
    public void close() throws IOException {
        webServer.close();
    }

    @Test
    public void testRedirect() throws IOException {
        final String data = readFromUrl("http://localhost:" + webServer.getPort() + "/redirectMe");
        assertEquals("Test Data", data);
        assertTrue(restMethodCalled);
    }

    @RestMethod("/redirectTarget")
    public void restMethod(final HttpOutputStream out) throws IOException {
        out.write("Test Data".getBytes());
        restMethodCalled = true;
        out.close();
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
