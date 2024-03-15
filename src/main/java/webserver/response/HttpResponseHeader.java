package webserver.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseHeader {

    private static final String CLRF = "\r\n";


    private final Map<String, String> responseHeader;

    public HttpResponseHeader() {
        this.responseHeader = new LinkedHashMap<>();
    }

    public HttpResponseHeader setLocation(String redirectionPath) {
        responseHeader.put("Location", redirectionPath);
        return this;
    }

    public HttpResponseHeader setContentType(ContentType contentType) {
        responseHeader.put("Content-Type", contentType.getMimeName());
        return this;
    }

    public HttpResponseHeader setContentLength(HttpResponseBody httpResponseBody) {
        responseHeader.put("Content-Length",
            String.valueOf(httpResponseBody.getResponseBodyLength()));
        return this;

    }

    public String getHttpResponseHeaderMessage() {
        return responseHeader.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .reduce((s1, s2) -> s1 + CLRF + s2)
            .orElse("");
    }
}
