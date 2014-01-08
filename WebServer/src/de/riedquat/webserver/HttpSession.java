package de.riedquat.webserver;

import de.riedquat.http.*;
import de.riedquat.webserver.redirect.Redirect;
import de.riedquat.http.rest.RestMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import static de.riedquat.http.Http11Header.CONNECTION;

// Beware, this is partially crap.
// It works, but design is poor.
public class HttpSession {

    private final InputStream in;
    private final OutputStream out;
    private final Map<String, Object> restObjectMap = new HashMap<>();
    private final Map<String, Method> restMethodMap = new HashMap<>();
    private Redirect redirect;

    public HttpSession(final InputStream in, final OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void requestResponseLoop() throws IOException {
        try {
            tryRequestResponseLoop();
        } catch (final NoDataFromClientException ignored) {
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void tryRequestResponseLoop() throws NoDataFromClientException, IOException {
        while (requestResponse()) ;
    }

    private boolean requestResponse() throws NoDataFromClientException, IOException {
        final HttpRequest httpRequest = new HttpRequest();
        final HttpResponse httpResponse = getHttpResponse(httpRequest);
        return httpResponse != null && processResponse(httpRequest, httpResponse);
    }

    private boolean processResponse(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        final boolean keepGoing = keepGoing(httpRequest);
        if (!keepGoing && isHttp11OrNewer(httpRequest)) {
            httpResponse.setMessageHeader("Connection", "close");
        }
        sendResponse(httpRequest, httpResponse);
        return keepGoing;
    }

    private boolean keepGoing(final HttpRequest httpRequest) {
        return isHttp11OrNewer(httpRequest) && isKeepAlive(httpRequest);
    }

    private boolean isKeepAlive(final HttpRequest httpRequest) {
        return !"close".equals(httpRequest.getMessageHeader("Connection"));
    }

    private boolean isHttp11OrNewer(final HttpRequest httpRequest) {
        return httpRequest.getHttpMajorVersion() >= 1 && httpRequest.getHttpMinorVersion() >= 1 || httpRequest.getHttpMajorVersion() >= 2;
    }

    private HttpResponse getHttpResponse(final HttpRequest httpRequest) throws NoDataFromClientException {
        try {
            httpRequest.parse(in);
            return tryGetHttpResponse(httpRequest);
        } catch (final HttpException e) {
            return new FileHttpResponse(httpRequest.getHttpVersion(), FileUtil.getErrorFile(e), e.getResponseCode());
        } catch (final SocketException e) {
            return null;
        } catch (final SocketTimeoutException e) {
            final FileHttpResponse httpResponse = new FileHttpResponse(httpRequest.getHttpVersion(), FileUtil.getErrorFile(Http11StatusCode.REQUEST_TIME_OUT), Http11StatusCode.REQUEST_TIME_OUT);
            httpResponse.setMessageHeader("Connection", "close");
            return httpResponse;
        } catch (final IOException e) {
            return new FileHttpResponse(httpRequest.getHttpVersion(), FileUtil.getErrorFile(Http11StatusCode.INTERNAL_SERVER_ERROR), Http11StatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    private HttpResponse tryGetHttpResponse(final HttpRequest httpRequest) throws HttpException {
        final String requestUri = httpRequest.getUriString();
        // TODO this shouldn't be hard-coded. Instead it should be possible to configure them.
        // TODO it should be possible for one "handler" to delegate to another.
        if (redirect != null && redirect.canRedirect(requestUri)) {
            return new RedirectHttpResponse(httpRequest.getHttpVersion(), redirect, requestUri);
        }
        if (isRestUri(requestUri)) {
            return new RestHttpResponse(httpRequest.getHttpVersion(), getRestObject(requestUri), getRestMethod(requestUri));
        }
        return new FileHttpResponse(httpRequest.getHttpVersion(), FileUtil.getFile(requestUri), Http11StatusCode.OK);
    }

    public void addRest(final Object target) {
        for (final Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(RestMethod.class)) {
                final RestMethod restMethod = method.getAnnotation(RestMethod.class);
                final String requestUri = restMethod.value();
                restObjectMap.put(requestUri, target);
                restMethodMap.put(requestUri, method);
            }
        }
    }

    private Object getRestObject(final String requestUri) {
        return restObjectMap.get(requestUri);
    }

    private Method getRestMethod(final String requestUri) {
        return restMethodMap.get(requestUri);
    }

    private boolean isRestUri(final String requestUri) {
        return restObjectMap.containsKey(requestUri);
    }

    private void sendResponse(final HttpRequest httpRequest, final HttpResponse httpResponse) throws IOException {
        HttpOutputStream out1 = new HttpOutputStream(httpRequest.getHttpVersion(), out);
        if (!HttpVersion.HTTP_09.equals(httpResponse.httpVersion) && "HEAD".equals(httpRequest.getMethod())) {
            out1.deactivateMessageBody();
        }
        if ("close".equals(httpResponse.getMessageHeader("Connection"))) {
            out1.setHeader(CONNECTION, "close");
        }
        out1.setStatus(httpResponse.responseCode);
        httpResponse.sendResponse(out1);
        out1.flush();
    }

    public void setRedirect(final Redirect redirect) {
        this.redirect = redirect;
    }
}
