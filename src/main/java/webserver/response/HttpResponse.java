package webserver.response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String CRLF = "\r\n";
    private final HttpResponseStatusLine httpResponseStatusLine;
    private final HttpResponseHeader httpResponseHeader;
    private final HttpResponseBody httpResponseBody;

    public HttpResponse(HttpResponseStatusLine httpResponseStatusLine,
        HttpResponseHeader httpResponseHeader, HttpResponseBody httpResponseBody) {
        this.httpResponseStatusLine = httpResponseStatusLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpResponseBody = httpResponseBody;
    }

    public String getHttpResponseMessage() {
        return httpResponseStatusLine.getHttpStatusLineMessage() + CRLF
            + httpResponseHeader.getHttpResponseHeaderMessage() + CRLF
            + CRLF
            + httpResponseBody.getHttpResponseBodyMessage();
    }

    public HttpResponseStatusLine getHttpResponseStatusLine() {
        return httpResponseStatusLine;
    }

    public HttpResponseHeader getHttpResponseHeader() {
        return httpResponseHeader;
    }

    public HttpResponseBody getHttpResponseBody() {
        return httpResponseBody;
    }

    public void sendRedirect(HttpStatus httpStatus, String Location) {
        setHttpStatus(httpStatus);
        setHttpHeader("Location", Location);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        httpResponseStatusLine.setHttpStatus(httpStatus);
    }

    public void setHttpHeader(String name, String value) {
        httpResponseHeader.setHeader(name, value);
    }

    public void addHttpHeader(String name, String value) {
        httpResponseHeader.addHeader(name, value);

    }

}
