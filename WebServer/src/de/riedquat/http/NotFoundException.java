package de.riedquat.http;

/** Exception that is thrown in case a resource was not found.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(Http11StatusCode.NOT_FOUND);
    }
}
