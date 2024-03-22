package webserver.response;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import webserver.Cookie;

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

    public HttpResponseHeader setContentLength(int value) {
        responseHeader.put("Content-Length",
            String.valueOf(value));
        return this;

    }

    public HttpResponseHeader setCookie(Cookie cookie) {
        responseHeader.put("Set-Cookie", concatenateCookies(cookie));
        return this;
    }

    public String concatenateCookies(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        Map<String, String> cookies = cookie.getCookies();
        int i = 0;
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            if (i < cookies.size() - 1) {
                sb.append("; ");
            }
            i++;
        }
        return sb.toString();
    }


    public String getHttpResponseHeaderMessage() {
        return responseHeader.entrySet().stream()
            .map(entry -> entry.getKey() + ": " + entry.getValue())
            .reduce((s1, s2) -> s1 + CLRF + s2)
            .orElse("");
    }

    public Map<String, String> getResponseHeader() {
        return Collections.unmodifiableMap(responseHeader);
    }
}
