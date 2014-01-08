package de.riedquat.http;

/** Exception that is thrown in case the desired request method is not implemented.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class NotImplementedException extends HttpException {
    public NotImplementedException() {
        super(Http11StatusCode.NOT_IMPLEMENTED);
    }
}
