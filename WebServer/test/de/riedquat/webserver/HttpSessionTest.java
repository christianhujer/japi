package de.riedquat.webserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class HttpSessionTest extends AbstractHttpSessionTest {

    @Override
    public void assertResponse(final String request, final String expectedResponse) throws IOException {
        final InputStream in = new ByteArrayInputStream(request.getBytes());
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final HttpSession httpSession = new HttpSession(in, out);
        httpSession.requestResponseLoop();
        assertEquals(expectedResponse, out.toString());
    }

}
