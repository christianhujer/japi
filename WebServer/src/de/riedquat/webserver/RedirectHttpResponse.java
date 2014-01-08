package de.riedquat.webserver;

import de.riedquat.http.HttpOutputStream;
import de.riedquat.http.HttpVersion;
import de.riedquat.webserver.redirect.Redirect;

import java.io.IOException;

import static de.riedquat.http.Http11Header.LOCATION;
import static de.riedquat.http.Http11StatusCode.TEMPORARY_REDIRECT;

public class RedirectHttpResponse extends HttpResponse {
    private final Redirect redirect;
    private final String requestUri;

    public RedirectHttpResponse(final HttpVersion httpVersion, final Redirect redirect, final String requestUri) {
        super(TEMPORARY_REDIRECT, httpVersion);
        this.redirect = redirect;
        this.requestUri = requestUri;
    }

    @Override
    public void sendResponse(final HttpOutputStream out) throws IOException {
        out.setStatus(TEMPORARY_REDIRECT);
        out.setHeader(LOCATION, redirect.getRedirectTarget(requestUri));
    }
}
