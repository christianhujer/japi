package de.riedquat.http;

/** HTTP/1.1 Header names.
 * The purpose of this class is to have constants for the HTTP Headers defined in HTTP/1.1 in order to get compile time errors in case of typos.
 * This is better than plain Strings where typos might cause spurious failures which then are unexpected, hard to debug.
 * Worse: Such bugs might remain undetected until a user finds them.
 *
 * The rule when using this class is very simple.
 * You use the String named your desired header, transforming all characters to uppercase and replacing - (dash) with _ (underscore).
 * If the String exists, it is a valid HTTP/1.1 header field.
 * If the String does not exist, you either have a typo or are trying to use a header field not defined by HTTP/1.1, which may be what you want.
 * But in any case, the compiler will warn you.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 *
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">RFC 2616 - 14 Header Field Definitions</a>
 */
@SuppressWarnings({"ConstantNamingConvention", "UnusedDeclaration"}) // Names are from RFC 2616 HTTP/1.1.
public final class Http11Header {

    /** Utility class - do not instantiate. */
    private Http11Header() {
    }

    /** Http Header Field "Accept".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1">RFC 2616 - 14.1 Accept</a>
     */
    public static final String ACCEPT = "Accept";

    /** Http Header Field "Accept-Charset".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.2">RFC 2616 - 14.2 Accept-Charset</a>
     */
    public static final String ACCEPT_CHARSET = "Accept-Charset";

    /** Http Header Field "Accept-Encoding".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.3">RFC 2616 - 14.3 Accept-Encoding</a>
     */
    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    /** Http Header Field "Accept-Language".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.4">RFC 2616 - 14.4 Accept-Language</a>
     */
    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    /** Http Header Field "Accept-Ranges".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.5">RFC 2616 - 14.5 Accept-Ranges</a>
     */
    public static final String ACCEPT_RANGES = "Accept-Ranges";

    /** Http Header Field "Age".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.6">RFC 2616 - 14.6 Age</a>
     */
    public static final String AGE = "Age";

    /** Http Header Field "Allow".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.7">RFC 2616 - 14.7 Allow</a>
     */
    public static final String ALLOW = "Allow";

    /** Http Header Field "Authorization".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.8">RFC 2616 - 14.8 Authorization</a>
     */
    public static final String AUTHORIZATION = "Authorization";

    /** Http Header Field "Cache-Control".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">RFC 2616 - 14.9 Cache-Control</a>
     */
    public static final String CACHE_CONTROL = "Cache-Control";

    /** Http Header Field "Connection".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.10">RFC 2616 - 14.10 Connection</a>
     */
    public static final String CONNECTION = "Connection";

    /** Http Header Field "Content-Encoding".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.11">RFC 2616 - 14.11 Content-Encoding</a>
     */
    public static final String CONTENT_ENCODING = "Content-Encoding";

    /** Http Header Field "Content-Language".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.12">RFC 2616 - 14.12 Content-Language</a>
     */
    public static final String CONTENT_LANGUAGE = "Content-Language";

    /** Http Header Field "Content-Length".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13">RFC 2616 - 14.13 Content-Length</a>
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /** Http Header Field "Content-Location".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.14">RFC 2616 - 14.14 Content-Location</a>
     */
    public static final String CONTENT_LOCATION = "Content-Location";

    /** Http Header Field "Content-MD5".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.15">RFC 2616 - 14.15 Content-MD5</a>
     */
    public static final String CONTENT_MD5 = "Content-MD5";

    /** Http Header Field "Content-Range".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.16">RFC 2616 - 14.16 Content-Range</a>
     */
    public static final String CONTENT_RANGE = "Content-Range";

    /** Http Header Field "Content-Type".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">RFC 2616 - 14.17 Content-Type</a>
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /** Http Header Field "Date".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18">RFC 2616 - 14.18 Date</a>
     */
    public static final String DATE = "Date";

    /** Http Header Field "ETag".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19">RFC 2616 - 14.19 ETag</a>
     */
    public static final String ETAG = "ETag";

    /** Http Header Field "Expect".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.20">RFC 2616 - 14.20 Expect</a>
     */
    public static final String EXPECT = "Expect";

    /** Http Header Field "Expires".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.21">RFC 2616 - 14.21 Expires</a>
     */
    public static final String EXPIRES = "Expires";

    /** Http Header Field "From".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.22">RFC 2616 - 14.22 From</a>
     */
    public static final String FROM = "From";

    /** Http Header Field "Host".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.23">RFC 2616 - 14.23 Host</a>
     */
    public static final String HOST = "Host";

    /** Http Header Field "If-Match".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.24">RFC 2616 - 14.24 If-Match</a>
     */
    public static final String IF_MATCH = "If-Match";

    /** Http Header Field "If-Modified-Since".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.25">RFC 2616 - 14.25 If-Modified-Since</a>
     */
    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    /** Http Header Field "If-None-Match".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.26">RFC 2616 - 14.26 If-None-Match</a>
     */
    public static final String IF_NONE_MATCH = "If-None-Match";

    /** Http Header Field "If-Range".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.27">RFC 2616 - 14.27 If-Range</a>
     */
    public static final String IF_RANGE = "If-Range";

    /** Http Header Field "If-Unmodified-Since".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.28">RFC 2616 - 14.28 If-Unmodified-Since</a>
     */
    public static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    /** Http Header Field "Last-Modified".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.29">RFC 2616 - 14.29 Last-Modified</a>
     */
    public static final String LAST_MODIFIED = "Last-Modified";

    /** Http Header Field "Location".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.30">RFC 2616 - 14.30 Location</a>
     */
    public static final String LOCATION = "Location";

    /** Http Header Field "Max-Forwards".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.31">RFC 2616 - 14.31 Max-Forwards</a>
     */
    public static final String MAX_FORWARDS = "Max-Forwards";

    /** Http Header Field "Pragma".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">RFC 2616 - 14.32 Pragma</a>
     */
    public static final String PRAGMA = "Pragma";

    /** Http Header Field "Proxy-Authenticate".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.33">RFC 2616 - 14.33 Proxy-Authenticate</a>
     */
    public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate";

    /** Http Header Field "Proxy-Authorization".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.34">RFC 2616 - 14.34 Proxy-Authorization</a>
     */
    public static final String PROXY_AUTHORIZATION = "Proxy-Authorization";

    /** Http Header Field "Range".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.35">RFC 2616 - 14.35 Range</a>
     */
    public static final String RANGE = "Range";

    /** Http Header Field "Referer".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.36">RFC 2616 - 14.36 Referer</a>
     */
    public static final String REFERER = "Referer";

    /** Http Header Field "Retry-After".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.37">RFC 2616 - 14.37 Retry-After</a>
     */
    public static final String RETRY_AFTER = "Retry-After";

    /** Http Header Field "Server".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.38">RFC 2616 - 14.38 Server</a>
     */
    public static final String SERVER = "Server";

    /** Http Header Field "TE".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.39">RFC 2616 - 14.39 TE</a>
     */
    public static final String TE = "TE";

    /** Http Header Field "Trailer".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.40">RFC 2616 - 14.40 Trailer</a>
     */
    public static final String TRAILER = "Trailer";

    /** Http Header Field "Transfer-Encoding".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.41">RFC 2616 - 14.41 Transfer-Encoding</a>
     */
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";

    /** Http Header Field "Upgrade".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.42">RFC 2616 - 14.42 Upgrade</a>
     */
    public static final String UPGRADE = "Upgrade";

    /** Http Header Field "User-Agent".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.43">RFC 2616 - 14.43 User-Agent</a>
     */
    public static final String USER_AGENT = "User-Agent";

    /** Http Header Field "Vary".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.44">RFC 2616 - 14.44 Vary</a>
     */
    public static final String VARY = "Vary";

    /** Http Header Field "Via".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.45">RFC 2616 - 14.45 Via</a>
     */
    public static final String VIA = "Via";

    /** Http Header Field "Warning".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.46">RFC 2616 - 14.46 Warning</a>
     */
    public static final String WARNING = "Warning";

    /** Http Header Field "WWW-Authenticate".
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.47">RFC 2616 - 14.47 WWW-Authenticate</a>
     */
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
}
