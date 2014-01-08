package de.riedquat.http;

public abstract class HttpException extends Throwable {
    private final Http11StatusCode responseCode;

    public HttpException(final Http11StatusCode responseCode) {
        super(responseCode.toString());
        this.responseCode = responseCode;
    }

    public Http11StatusCode getResponseCode() {
        return responseCode;
    }

}
