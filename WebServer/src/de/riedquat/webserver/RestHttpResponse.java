package de.riedquat.webserver;

import de.riedquat.http.Http11StatusCode;
import de.riedquat.http.HttpOutputStream;
import de.riedquat.http.HttpVersion;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RestHttpResponse extends HttpResponse {
    private final Object restObject;
    private final Method restMethod;

    public RestHttpResponse(final HttpVersion httpVersion, final Object restObject, final Method restMethod) {
        super(Http11StatusCode.OK, httpVersion);
        this.restObject = restObject;
        this.restMethod = restMethod;
    }

    @Override
    public void sendResponse(final HttpOutputStream out) throws IOException {
        try {
            restMethod.invoke(restObject, out);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
