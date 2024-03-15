package webserver.response;

public class HttpResponseStatusLine {


    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;

    public HttpResponseStatusLine(HttpVersion httpVersion, HttpStatus httpStatus) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
    }

    public String getHttpStatusLineMessage() {
        return httpVersion.getHttpVersion() + ' ' + httpStatus.getStatusCode() + ' '
            + httpStatus.name();
    }
}
