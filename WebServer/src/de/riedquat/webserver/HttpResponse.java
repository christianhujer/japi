package de.riedquat.webserver;

import de.riedquat.http.Http11StatusCode;
import de.riedquat.http.HttpOutputStream;
import de.riedquat.http.HttpStatusCode;
import de.riedquat.http.HttpVersion;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public abstract class HttpResponse {
    protected final HttpVersion httpVersion;
    protected final HttpStatusCode responseCode;
    private final Map<String, String> messageHeaders = new TreeMap<>();

    public HttpResponse(final HttpStatusCode responseCode, final HttpVersion httpVersion) {
        this.responseCode = responseCode;
        this.httpVersion = httpVersion;
    }

    public void setMessageHeader(final String headerName, final String headerValue) {
        messageHeaders.put(headerName, headerValue);
    }

    public String getMessageHeader(final String headerName) {
        return messageHeaders.get(headerName);
    }

    public abstract void sendResponse(final HttpOutputStream out) throws IOException;
}
