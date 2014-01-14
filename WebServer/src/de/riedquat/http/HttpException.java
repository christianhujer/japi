package de.riedquat.http;

public abstract class HttpException extends Throwable {
    private final HttpStatusCode responseCode;

    public HttpException(final HttpStatusCode responseCode) {
        super(responseCode.toString());
        this.responseCode = responseCode;
    }

    public HttpStatusCode getResponseCode() {
        return responseCode;
    }

}
