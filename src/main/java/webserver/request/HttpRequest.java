package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;
    private static final String CRLF = "\r\n";

    public HttpRequest(BufferedReader httpRequest) throws IOException {
        httpRequestStartLine = new HttpRequestStartLine(readRequestLine(httpRequest));
        httpRequestHeader = new HttpRequestHeader(readHeaderLine(httpRequest));
//        if (httpRequestStartLine.getMethod().equals("POST")) {
//            httpRequestBody = new HttpRequestBody(readBodyLine(httpRequest));
//        }
    }

    private String readRequestLine(BufferedReader httpRequest) throws IOException {
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        if ((line = httpRequest.readLine()) != null && line != CRLF) {
            requestBuilder.append(line).append('\n');
            logger.debug("Request Line: {}", line);
        }

        return requestBuilder.toString();
    }

    private String readHeaderLine(BufferedReader httpRequest) throws IOException {
        StringBuilder requestHeaderBuilder = new StringBuilder();
        String line;

        while (!(line = httpRequest.readLine()).isEmpty()) {
            requestHeaderBuilder.append(line).append('\n');
            logger.debug("Header Line: {}", line);
        }
        return requestHeaderBuilder.toString();
    }

    private String readBodyLine(BufferedReader httpRequest, int contentLength) throws IOException {
        StringBuilder requestBodyBuilder = new StringBuilder();

        char[] buffer = new char[contentLength];
        int bytesRead = httpRequest.read(buffer, 0, contentLength);
        requestBodyBuilder.append(buffer, 0, bytesRead);
        return requestBodyBuilder.toString();
    }


    public HttpRequestStartLine getHttpRequestStartLine() {
        return httpRequestStartLine;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }
}
