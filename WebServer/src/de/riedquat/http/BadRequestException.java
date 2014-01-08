package de.riedquat.http;

/** Exception that is thrown in case of a bad request.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class BadRequestException extends HttpException {
    public BadRequestException() {
        super(Http11StatusCode.BAD_REQUEST);
    }
}
