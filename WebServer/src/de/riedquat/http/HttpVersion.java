package de.riedquat.http;

import de.riedquat.java.lang.NiceComparable;
import org.jetbrains.annotations.NotNull;

/**
 * The standard versions of HTTP.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class HttpVersion implements NiceComparable<HttpVersion> {
    /**
     * HTTP/0.9.
     *
     * @see <a href="http://www.ietf.org/rfc/rfc1945.txt">RFC 1945</a>
     */
    public static final HttpVersion HTTP_09 = new HttpVersion(0, 9);
    /**
     * HTTP/1.0.
     *
     * @see <a href="http://www.ietf.org/rfc/rfc1945.txt">RFC 1945</a>
     */
    public static final HttpVersion HTTP_10 = new HttpVersion(1, 0);
    /**
     * HTTP/1.1.
     *
     * @see <a href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616</a>
     */
    public static final HttpVersion HTTP_11 = new HttpVersion(1, 1);
    private final int majorVersion;
    private final int minorVersion;
    private final String string;

    public HttpVersion(final int majorVersion, final int minorVersion) {
        this("HTTP/" + majorVersion + "." + minorVersion, majorVersion, minorVersion);
    }

    private HttpVersion(final String string, final int majorVersion, final int minorVersion) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.string = string;
    }

    public static HttpVersion getHttpVersion(final int majorVersion, final int minorVersion) {
        if (majorVersion == 0 && minorVersion == 9) return HTTP_09;
        if (majorVersion == 1) {
            if (minorVersion == 0) return HTTP_10;
            if (minorVersion == 1) return HTTP_11;
        }
        return new HttpVersion(majorVersion, minorVersion);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpVersion)) return false;
        final HttpVersion that = (HttpVersion) o;
        return majorVersion == that.majorVersion && minorVersion == that.minorVersion;

    }

    @Override
    public int hashCode() {
        return 31 * majorVersion + minorVersion;
    }

    @Override
    public int compareTo(@NotNull HttpVersion o) {
        if (majorVersion > o.majorVersion) return 1;
        if (majorVersion < o.majorVersion) return -1;
        if (minorVersion > o.minorVersion) return 1;
        if (minorVersion < o.minorVersion) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return string;
    }
}
