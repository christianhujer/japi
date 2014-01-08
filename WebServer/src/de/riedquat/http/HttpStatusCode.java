package de.riedquat.http;

import java.io.Serializable;

/** Interface to specify Http Status-Code and Reason-Phrase.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6.1.1">RFC 2616 - 6.1.1 Status Code and Reason Phrase</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6.1">RFC 2616 - 6.1 Status-Line</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">RFC 2616 - 10 Status Code Definitions</a>
 */
@SuppressWarnings("UnusedDeclaration")
public interface HttpStatusCode extends Serializable {

    /** 1xx Informational minimum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.1">RFC 2616 - 10.1 Informational 1xx</a>
     */
    int INFORMATIONAL_MIN = 100;

    /** 1xx Informational maximum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.1">RFC 2616 - 10.1 Informational 1xx</a>
     */
    int INFORMATIONAL_MAX = 199;

    /** 2xx Success minimum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2">RFC 2616 - 10.2 Successful 2xx</a>
     */
    int SUCCESS_MIN = 200;

    /** 2xx Success maximum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.2">RFC 2616 - 10.2 Successful 2xx</a>
     */
    int SUCCESS_MAX = 299;

    /** 3xx Redirection minimum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3">RFC 2616 - 10.3 Redirection 3xx</a>
     */
    int REDIRECTION_MIN = 300;

    /** 3xx Redirection maximum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3">RFC 2616 - 10.3 Redirection 3xx</a>
     */
    int REDIRECTION_MAX = 399;

    /** 4xx Client Error minimum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">RFC 2616 - 10.4 Client Error 4xx</a>
     */
    int CLIENT_ERROR_MIN = 400;

    /** 4xx Client Error maximum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4">RFC 2616 - 10.4 Client Error 4xx</a>
     */
    int CLIENT_ERROR_MAX = 499;

    /** 5xx Server Error minimum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">RFC 2616 - 10.5 Server Error 5xx</a>
     */
    int SERVER_ERROR_MIN = 500;

    /** 5xx Server Error maximum value.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">RFC 2616 - 10.5 Server Error 5xx</a>
     */
    int SERVER_ERROR_MAX = 599;

    /** Returns the Status-Code.
     * @return The Status-Code.
     */
    int getStatusCode();

    /** Returns the Reason-Phrase.
     * @return The Reason-Phrase.
     */
    String getReasonPhrase();

    /**
     * {@inheritDoc}
     * The String representation of a HttpStatusCode should be {@code getStatusCode() + " " + getReasonPhrase()}.
     */
    @Override
    String toString();

    /** Returns if this HttpStatusCode is an error code.
     * @return <code>true</code> if this HttpStatusCode is an error code, otherwise <code>false</code>.
     */
    boolean isErrorCode();
}
