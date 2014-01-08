package de.riedquat.http;

/**
 * Methods of the HTTP protocol version 1.1.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616.html">RFC 2616 Hypertext Transfer Protocol -- HTTP/1.1</a>
 */
@SuppressWarnings({"ConstantNamingConvention", "UnusedDeclaration"}) // Names are from RFC 2616 HTTP/1.1.
public final class Http11Method {

    /** Utility class - do not instantiate. */
    private Http11Method() {
    }

    /**
     * Http Method <code>OPTIONS</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2">RFC 2616 - 9.2 OPTIONS</a>
     */
    public static final String OPTIONS = "OPTIONS";

    /**
     * Http Method <code>GET</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3">RFC 2616 - 9.3 GET</a>
     */
    public static final String GET = "GET";

    /**
     * Http Method <code>HEAD</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.4">RFC 2616 - 9.4 HEAD</a>
     */
    public static final String HEAD = "HEAD";

    /**
     * Http Method <code>POST</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">RFC 2616 - 9.5 POST</a>
     */
    public static final String POST = "POST";

    /**
     * Http Method <code>PUT</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.6">RFC 2616 - 9.6 PUT</a>
     */
    public static final String PUT = "PUT";

    /**
     * Http Method <code>DELETE</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7">RFC 2616 - 9.7 DELETE</a>
     */
    public static final String DELETE = "DELETE";

    /**
     * Http Method <code>TRACE</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.8">RFC 2616 - 9.8 TRACE</a>
     */
    public static final String TRACE = "TRACE";

    /**
     * Http Method <code>CONNECT</code>.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.9">RFC 2616 - 9.9 CONNECT</a>
     */
    public static final String CONNECT = "CONNECT";
}
