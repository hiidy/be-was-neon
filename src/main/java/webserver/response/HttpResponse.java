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


}
