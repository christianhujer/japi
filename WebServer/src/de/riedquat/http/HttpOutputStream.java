package de.riedquat.http;

import de.riedquat.http.rest.RestMethod;
import org.jetbrains.annotations.NotNull;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A RestOutputStream is used by a {@link RestMethod} to send its response to the HTTP Client.
 *
 * The design is meant to be similar to the HttpOutputStream + HttpResponse of the Servlet API wherever it makes sense.
 *
 * The HTTP is handled internally by this stream.
 * For this, you can
 * <ul>
 *  <li>set response headers with {@link #setHeader(String, String)}.</li>
 * </ul>
 * The RestOutputStream will automatically send the header at one of the following occasions, whichever comes first:
 * <ul>
 *  <li>The first data byte is written to this stream using one of the {@link #write} methods.</li>
 *  <li>This stream is {@link #flush()}ed.</li>
 *  <li>This stream is {@link #close()}d.</li>
 * </ul>
 * The invocation of a method that changes the header when the header was already sent will always result in an {@link IllegalStateException}.
 *
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html">RFC 2616 - 6 Response</a>
 */
public class HttpOutputStream extends FilterOutputStream {

    /** The default status is 200 OK. */
    private static final HttpStatusCode DEFAULT_STATUS = Http11StatusCode.OK;

    /** The ResponseCode for this RestOutputStream. */
    private HttpStatusCode status = DEFAULT_STATUS;

    /** The response headers for this RestOutputStream. */
    private final Map<String, String> headers = new HashMap<>();

    /** Whether or not the header was already sent. */
    private boolean headerSent;

    /** Whether the message body is activated.
     * This is used to handle responses to HEAD requests via the implementation of GET requests.
     */
    private boolean messageBody = true;

    /** The Http Version to use. */
    private final HttpVersion httpVersion;

    /** Creates a RestOutputStreamImpl.
     * @param out OutputStream for writing to the client.
     */
    public HttpOutputStream(final HttpVersion httpVersion, @NotNull final OutputStream out) {
        super(out);
        this.httpVersion = httpVersion;
    }

    @SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters", "StandardVariableNames"}) // Contract of InputStream
    @Override
    public void write(final int b) throws IOException {
        if (!headerSent) {
            sendHeader();
        }
        if (messageBody) {
            out.write(b);
        }
    }

    @SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters", "StandardVariableNames"}) // Contract of InputStream
    @Override
    public void write(@NotNull final byte[] b) throws IOException {
        if (!headerSent) {
            sendHeader();
        }
        if (messageBody) {
            out.write(b);
        }
    }

    @SuppressWarnings({"StandardVariableNames"}) // Contract of InputStream
    @Override
    public void write(@NotNull final byte[] b, final int off, final int len) throws IOException {
        if (!headerSent) {
            sendHeader();
        }
        if (messageBody) {
            out.write(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        if (!headerSent) {
            sendHeader();
        }
        super.flush();
    }

    /** Returns the status code for the response.
     * @return The status code for the response.
     * @note if a {@link RestMethod} throws a {@link HttpException}, the response code of that {@link HttpException} takes precedence.
     * @note if a {@link RestMethod} throws an Exception, the response code is overridden to be 500.
     * @throws IllegalStateException if this method is invoked when the HTTP header was already sent.
     */
    @NotNull
    public HttpStatusCode getStatus() throws IllegalStateException {
        return status;
    }

    /** Sets the status code for the response.
     * @param status HTTP status code for the response.
     * @note if a {@link RestMethod} throws a {@link HttpException}, the response code of that {@link HttpException} takes precedence.
     * @note if a {@link RestMethod} throws an Exception, the response code is overridden to be 500.
     * @throws IllegalStateException if this method is invoked when the HTTP header was already sent.
     */
    public void setStatus(@NotNull final HttpStatusCode status) throws IllegalStateException {
        if (headerSent) {
            throw new IllegalStateException("HTTP header already sent.");
        }
        this.status = status;
    }

    /** Sets a response header with the given name and value.
     * If the header had already been set, the new value overwrites the previous one.
     * The {@link #containsHeader(String)} method can be used to test the presence of a header before setting its value.
     * @param name The name of the header.
     * @param value The header value.
     * @throws IllegalStateException if this method is invoked when the HTTP header was already sent.
     * @see Http11Header To prevent typos HttpHeader povides String constants for all header field definitions from HTTP/1.1.
     */
    public void setHeader(final String name, final String value) throws IllegalStateException {
        if (headerSent) {
            throw new IllegalStateException("HTTP header already sent.");
        }
        headers.put(name, value);
    }

    /** Returns a boolean indicating whether the named response header has already been set.
     * @param name The name of the header.
     * @return <code>true</code> if the named response header has already been set, otherwise <code>false</code>.
     * @see Http11Header To prevent typos HttpHeader povides String constants for all header field definitions from HTTP/1.1.
     */
    public boolean containsHeader(final String name) {
        return headers.containsKey(name);
    }

    /** Returns a previously set response header.
     * @param name The name of the header to return.
     * @return The header value if set, otherwise <code>false</code>.
     */
    public String getHeader(@NotNull final String name) {
        return headers.get(name);
    }

    /** Sends the configured header.
     * @throws IOException in case of I/O problems.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html">RFC 2616 - 6 Response</a>
     */
    @SuppressWarnings({"HardcodedLineSeparator"})
    private void sendHeader() throws IOException {
        headerSent = true;
        if (HttpVersion.HTTP_09.equals(httpVersion)) {
            return;
        }
        //noinspection HardcodedFileSeparator
        sendStatusLine();
        sendHeaderLines();
        sendCRLF();
        flush();
    }

    @SuppressWarnings({"HardcodedLineSeparator"})
    private void sendCRLF() throws IOException {
        out.write('\r');
        out.write('\n');
    }

    /** Sends the status line.
     * @throws IOException in case of I/O problems.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html#sec6.1">RFC 2616 - 6.1 Status-Line</a>
     */
    private void sendStatusLine() throws IOException {
        out.write((httpVersion + " " + status).getBytes());
        sendCRLF();
    }

    /** Sends the header lines.
     * @throws IOException in case of I/O problems.
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2">RFC 2616 - 4.2 Message Headers</a>
     */
    private void sendHeaderLines() throws IOException {
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            out.write(entry.getKey().getBytes());
            out.write(':');
            out.write(' ');
            out.write(entry.getValue().getBytes());
            sendCRLF();
        }
    }

    /** Deactivates the message body.
     * It is used to implement {@link Http11Method#HEAD} requests.
     */
    public void deactivateMessageBody() {
        messageBody = false;
    }
}
