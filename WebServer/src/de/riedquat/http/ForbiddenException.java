package de.riedquat.http;

/** Exception that is thrown in case a resource exists but access is forbidden.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class ForbiddenException extends HttpException {
    public ForbiddenException() {
        super(Http11StatusCode.FORBIDDEN);
    }
}
