package de.riedquat.webserver;

import org.junit.Test;

import java.io.IOException;

public abstract class AbstractHttpSessionTest {

    public abstract void assertResponse(String request, String expectedResponse) throws IOException;

    @Test
    public void badRequest_replies400() throws IOException {
        assertResponse("foo bar buzz", "<h1>400 Bad Request</h1>\n");
    }

    @Test
    public void http09EmptyRequest_returnsEmptyResponse() throws IOException {
        assertResponse("", "");
    }

    @Test
    public void http09get_repliesWithDocument() throws IOException {
        assertResponse("GET /foo.txt\r\n", "foo");
    }

    @Test
    public void http09head_onlyAllowsGet() throws IOException {
        assertResponse("HEAD /foo.txt\r\n", "<h1>400 Bad Request</h1>\n");
    }

    @Test
    public void http10NotImplemented_replies501() throws IOException {
        assertResponse("FOO / HTTP/1.0\r\n\r\n", "HTTP/1.0 501 Not Implemented\r\nContent-Length: 29\r\n\r\n<h1>501 Not Implemented</h1>\n");
    }

    @Test
    public void http10getDirectory_returnsIndex() throws IOException {
        assertResponse("GET / HTTP/1.0\r\n\r\n", "HTTP/1.0 200 OK\r\nContent-Length: 15\r\n\r\n<h1>Index</h1>\n");
    }

    @Test
    public void http10getNonExistingPage_returns404() throws IOException {
        assertResponse("GET /nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 404 Not Found\r\nContent-Length: 23\r\n\r\n<h1>404 Not Found</h1>\n");
    }

    @Test
    public void http10getExistingPage_returns200AndPage() throws IOException {
        assertResponse("GET /foo.txt HTTP/1.0\r\n\r\n", "HTTP/1.0 200 OK\r\nContent-Length: 3\r\n\r\nfoo");
    }

    @Test
    public void http10headBadURI_replies400() throws IOException {
        assertResponse("HEAD /%: HTTP/1.0\r\n\r\n", "HTTP/1.0 400 Bad Request\r\nContent-Length: 25\r\n\r\n");
    }

    @Test
    public void http10headNonExistingPage_returns404() throws IOException {
        assertResponse("HEAD /nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 404 Not Found\r\nContent-Length: 23\r\n\r\n");
    }

    @Test
    public void http10headExistingPage_returns200() throws IOException {
        assertResponse("HEAD /foo.txt HTTP/1.0\r\n\r\n", "HTTP/1.0 200 OK\r\nContent-Length: 3\r\n\r\n");
    }

    @Test
    public void http10dotAttackWithExistingFile_returns403() throws IOException {
        assertResponse("HEAD /%2e%2e/WebServer.iml HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /../WebServer.iml HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /%2e./WebServer.iml HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /.%2e/WebServer.iml HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
    }

    @Test
    public void http10dotAttackWithNonExistingFile_returns403() throws IOException {
        assertResponse("HEAD /%2e%2e/nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /../nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /%2e./nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
        assertResponse("HEAD /.%2e/nonexistent HTTP/1.0\r\n\r\n", "HTTP/1.0 403 Forbidden\r\nContent-Length: 23\r\n\r\n");
    }

    @Test
    public void http11WithoutHost_returns400() throws IOException {
        assertResponse("HEAD /nonexistent HTTP/1.1\r\nConnection: close\r\n\r\n", "HTTP/1.1 400 Bad Request\r\nConnection: close\r\nContent-Length: 25\r\n\r\n");
    }

    @Test
    public void http11headNonExistingPage_returns404() throws IOException {
        assertResponse("HEAD /nonexistent HTTP/1.1\r\nConnection: close\r\nHost: www.riedquat.de\r\n\r\n", "HTTP/1.1 404 Not Found\r\nConnection: close\r\nContent-Length: 23\r\n\r\n");
    }

    @Test
    public void http11noConnection_keepAlive() throws Exception {
        assertResponse("HEAD / HTTP/1.1\r\nHost: www.riedquat.de\r\n\r\nGET / HTTP/1.1\r\nConnection: close\r\nHost: www.riedquat.de\r\n\r\n", "HTTP/1.1 200 OK\r\nContent-Length: 15\r\n\r\nHTTP/1.1 200 OK\r\nConnection: close\r\nContent-Length: 15\r\n\r\n<h1>Index</h1>\n");
    }

    @Test
    public void http11unknownConnection_keepAlive() throws Exception {
        assertResponse("HEAD / HTTP/1.1\r\nHost: www.riedquat.de\r\nConnection: foo\r\n\r\nGET / HTTP/1.1\r\nHost: www.riedquat.de\r\nConnection: close\r\n\r\n", "HTTP/1.1 200 OK\r\nContent-Length: 15\r\n\r\nHTTP/1.1 200 OK\r\nConnection: close\r\nContent-Length: 15\r\n\r\n<h1>Index</h1>\n");
    }

    @Test
    public void http11NoConnection_keepAlive() throws Exception {
        assertResponse("HEAD / HTTP/1.1\r\nHost: www.riedquat.de\r\n\r\nGET / HTTP/1.1\r\nHost: www.riedquat.de\r\n\r\n", "HTTP/1.1 200 OK\r\nContent-Length: 15\r\n\r\nHTTP/1.1 200 OK\r\nContent-Length: 15\r\n\r\n<h1>Index</h1>\n");
    }

    @Test
    public void http11NotFound_connectionStaysOpen() throws Exception {
        assertResponse("HEAD /nonexistent HTTP/1.1\r\nHost: www.riedquat.de\r\n\r\nGET / HTTP/1.1\r\nHost: www.riedquat.de\r\n\r\n", "HTTP/1.1 404 Not Found\r\nContent-Length: 23\r\n\r\nHTTP/1.1 200 OK\r\nContent-Length: 15\r\n\r\n<h1>Index</h1>\n");
    }
}
