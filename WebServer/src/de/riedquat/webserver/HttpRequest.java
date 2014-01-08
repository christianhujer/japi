package de.riedquat.webserver;

import de.riedquat.http.*;
import de.riedquat.java.io.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.riedquat.java.util.Arrays.asUnmodifiableSet;

public class HttpRequest {

    private static final String requestRegex = "([A-Z]+) ([^ ]*)( (?:HTTP/(\\d+).(\\d+)))?";
    private static final Pattern requestPattern = Pattern.compile(requestRegex);
    private static final Set<String> supportedMethods = asUnmodifiableSet("GET", "HEAD");

    private final Map<String, String> messageHeaders = new HashMap<>();
    private String method;
    private int httpMajorVersion = 0;
    private int httpMinorVersion = 9;
    private HttpVersion httpVersion = HttpVersion.HTTP_09;

    private String uriString;

    public void parse(final InputStream in) throws IOException, HttpException, NoDataFromClientException {
        parseRequestLine(in);
        if (httpVersion.isGreaterThan(HttpVersion.HTTP_09)) {
            parseMessageHeaders(in);
        }
        validate();
    }

    public String getMethod() {
        return method;
    }

    public String getUriString() {
        return uriString;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public int getHttpMajorVersion() {
        return httpMajorVersion;
    }

    public int getHttpMinorVersion() {
        return httpMinorVersion;
    }

    public String getMessageHeader(final String headerName) {
        return messageHeaders.get(headerName);

    }

    private void parseRequestLine(final String requestLine) throws BadRequestException, NoDataFromClientException {
        if (requestLine == null) {
            throw new NoDataFromClientException();
        }
        final Matcher matcher = requestPattern.matcher(requestLine);
        if (!matcher.matches()) {
            throw new BadRequestException();
        }
        int group = 0;
        method = matcher.group(++group);
        uriString = matcher.group(++group);
        final String httpVersionInformation = matcher.group(++group);
        if (httpVersionInformation != null) {
            httpMajorVersion = Integer.parseInt(matcher.group(++group));
            httpMinorVersion = Integer.parseInt(matcher.group(++group));
            httpVersion = HttpVersion.getHttpVersion(httpMajorVersion, httpMinorVersion);
        }
    }

    private void validate() throws HttpException {
        if (!isMethodSupported()) {
            throw new NotImplementedException();
        }
        if (isBadRequest()) {
            throw new BadRequestException();
        }
    }

    private boolean isMethodSupported() {
        return supportedMethods.contains(method);
    }
    private boolean isBadRequest() {
        return httpMajorVersion == 0 && !"GET".equals(method) || httpMajorVersion >= 1 && httpMinorVersion >= 1 && getMessageHeader("Host") == null;
    }

    private void parseRequestLine(final InputStream in) throws IOException, HttpException, NoDataFromClientException {
        parseRequestLine(Util.readLine(in));
    }

    private void parseMessageHeaders(final InputStream in) throws IOException, HttpException {
        for (String messageHeader; (messageHeader = Util.readLine(in)) != null && !"".equals(messageHeader); ) {
            parseMessageHeader(messageHeader);
        }
    }

    private void parseMessageHeader(final String messageHeader) throws HttpException {
        final Matcher matcher = Pattern.compile("([a-zA-Z0-9-]+):\\s*(.*)").matcher(messageHeader);
        if (!matcher.matches()) {
            throw new BadRequestException();
        }
        final String fieldName = matcher.group(1);
        final String fieldValue = matcher.group(2);
        messageHeaders.put(fieldName, fieldValue);
    }
}
