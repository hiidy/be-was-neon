package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);
    private final HttpRequestStartLine httpRequestStartLine;
    private final HttpRequestHeader httpRequestHeader;

    public HttpRequest(BufferedReader httpRequest) throws IOException {
        httpRequestStartLine = new HttpRequestStartLine(readRequestLine(httpRequest));
        httpRequestHeader = new HttpRequestHeader(readHeaderLine(httpRequest));
    }

    private String readRequestLine(BufferedReader httpRequest) throws IOException {
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        if ((line = httpRequest.readLine()) != null && !line.isEmpty()) {
            requestBuilder.append(line).append('\n');
            logger.debug("Request Line: {}", line);
        }

        return requestBuilder.toString();
    }

    private String readHeaderLine(BufferedReader httpRequest) throws IOException {
        StringBuilder requestHeaderBuilder = new StringBuilder();
        String line;

        while ((line = httpRequest.readLine()) != null && !line.isEmpty()) {
            requestHeaderBuilder.append(line).append('\n');
            logger.debug("Header Line: {}", line);
        }
        return requestHeaderBuilder.toString();
    }

    public HttpRequestStartLine getHttpRequestStartLine() {
        return httpRequestStartLine;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }
}
