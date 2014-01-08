package de.riedquat.http;

import org.jetbrains.annotations.NotNull;

/** HttpStatusCodes for HTTP/1.1.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">RFC 2116 - 10 Status Code Definitions</a>
 */
@SuppressWarnings("UnusedDeclaration")
public enum Http11StatusCode implements HttpStatusCode {

    /** HTTP/1.1 Status-Code / Reason-Phrase 100 Continue.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.1.1">RFC 2116 - 10.1.1 Continue</a>
     */
    CONTINUE(100, "Continue"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 101 Switching Protocols.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.1.2">RFC 2116 - 10.1.2 Switching Protocols</a>
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 200 OK.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.1">RFC 2116 - 10.2.1 OK</a>
     */
    OK(200, "OK"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 201 Created.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.2">RFC 2116 - 10.2.2 Created</a>
     */
    CREATED(201, "Created"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 202 Accepted.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.3">RFC 2116 - 10.2.3 Accepted</a>
     */
    ACCEPTED(202, "Accepted"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 203 Non-Authoritative Information.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.4">RFC 2116 - 10.2.4 Non-Authoritative Information</a>
     */
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 204 No Content.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.5">RFC 2116 - 10.2.5 No Content</a>
     */
    NO_CONTENT(204, "No Content"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 205 Reset Content.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.6">RFC 2116 - 10.2.6 Reset Content</a>
     */
    RESET_CONTENT(205, "Reset Content"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 206 Partial Content.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2.7">RFC 2116 - 10.2.7 Partial Content</a>
     */
    PARTIAL_CONTENT(206, "Partial Content"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 300 Multiple Choices.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.1">RFC 2116 - 10.3.1 Multiple Choices</a>
     */
    MULTIPLE_CHOICES(300, "Multiple Choices"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 301 Moved Permanently.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.2">RFC 2116 - 10.3.2 Moved Permanently</a>
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 302 Found.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.3">RFC 2116 - 10.3.3 Found</a>
     */
    FOUND(302, "Found"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 303 See Other.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.4">RFC 2116 - 10.3.4 See Other</a>
     */
    SEE_OTHER(303, "See Other"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 304 Not Modified.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.5">RFC 2116 - 10.3.5 Not Modified</a>
     */
    NOT_MODIFIED(304, "Not Modified"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 305 Use Proxy.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.6">RFC 2116 - 10.3.6 Use Proxy</a>
     */
    USE_PROXY(305, "Use Proxy"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 307 Temporary SimpleRedirect.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3.8">RFC 2116 - 10.3.8 Temporary SimpleRedirect</a>
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 400 Bad Request.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">RFC 2116 - 10.4.1 Bad Request</a>
     */
    BAD_REQUEST(400, "Bad Request"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 401 Unauthorized.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2">RFC 2116 - 10.4.2 Unauthorized</a>
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 402 Payment Required.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.3">RFC 2116 - 10.4.3 Payment Required</a>
     */
    PAYMENT_REQUIRED(402, "Payment Required"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 403 Forbidden.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4">RFC 2116 - 10.4.4 Forbidden</a>
     */
    FORBIDDEN(403, "Forbidden"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 404 Not Found.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5">RFC 2116 - 10.4.5 Not Found</a>
     */
    NOT_FOUND(404, "Not Found"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 405 Method Not Allowed.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6">RFC 2116 - 10.4.6 Method Not Allowed</a>
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 406 Not Acceptable.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7">RFC 2116 - 10.4.7 Not Acceptable</a>
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 407 Proxy Authentication Required.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.8">RFC 2116 - 10.4.8 Proxy Authentication Required</a>
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 408 Request Time-out.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.9">RFC 2116 - 10.4.9 Request Time-out</a>
     */
    REQUEST_TIME_OUT(408, "Request Time-out"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 409 Conflict.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.10">RFC 2116 - 10.4.10 Conflict</a>
     */
    CONFLICT(409, "Conflict"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 410 Gone.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.11">RFC 2116 - 10.4.11 Gone</a>
     */
    GONE(410, "Gone"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 411 Length Required.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.12">RFC 2116 - 10.4.12 Length Required</a>
     */
    LENGTH_REQUIRED(411, "Length Required"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 412 Precondition Failed.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.13">RFC 2116 - 10.4.13 Precondition Failed</a>
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 413 Request Entity Too Large.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.14">RFC 2116 - 10.4.14 Request Entity Too Large</a>
     */
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 414 Request-URI Too Large.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.15">RFC 2116 - 10.4.15 Request-URI Too Large</a>
     */
    REQUEST_URI_TOO_LARGE(414, "Request-URI Too Large"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 415 Unsupported Media Type.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16">RFC 2116 - 10.4.16 Unsupported Media Type</a>
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 416 Requested range not satisfiable.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.17">RFC 2116 - 10.4.17 Requested range not satisfiable</a>
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 417 Expectation Failed.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.18">RFC 2116 - 10.4.18 Expectation Failed</a>
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 500 Internal Server Error.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1">RFC 2116 - 10.5.1 Internal Server Error</a>
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 501 Not Implemented.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.2">RFC 2116 - 10.5.2 Not Implemented</a>
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 502 Bad Gateway.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.3">RFC 2116 - 10.5.3 Bad Gateway</a>
     */
    BAD_GATEWAY(502, "Bad Gateway"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 503 Service Unavailable.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.4">RFC 2116 - 10.5.4 Service Unavailable</a>
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 504 Gateway Time-out.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.5">RFC 2116 - 10.5.5 Gateway Time-out</a>
     */
    GATEWAY_TIME_OUT(504, "Gateway Time-out"),

    /** HTTP/1.1 Status-Code / Reason-Phrase 505 HTTP Version not supported.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.6">RFC 2116 - 10.5.6 HTTP Version not supported</a>
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");

    /** The Status-Code. */
    private final int statusCode;

    /** The Reason-Phrase. */
    private final String reasonPhrase;

    /** Creates a HttpStatusCode.
     * @param statusCode Status-Code.
     * @param reasonPhrase Reason-Phrase.
     */
    Http11StatusCode(final int statusCode, @NotNull final String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public boolean isErrorCode() {
        return statusCode >= CLIENT_ERROR_MIN && statusCode <= CLIENT_ERROR_MAX
                || statusCode >= SERVER_ERROR_MIN && statusCode <= SERVER_ERROR_MAX;
    }

    @Override
    public String toString() {
        return getStatusCode() + " " + getReasonPhrase();
    }
}
