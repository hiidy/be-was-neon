package webserver.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import webserver.Cookie;

public class HttpResponseHeader {

    private static final String CLRF = "\r\n";


    private final List<HttpResponseHeaderField> responseHeader = new ArrayList<>();


    public void addHeader(String name, String value) {
        HttpResponseHeaderField headerField = createHeaderField();
        headerField.setName(name);
        headerField.setValue(value);
    }

    private HttpResponseHeaderField createHeaderField() {
        HttpResponseHeaderField headerField = new HttpResponseHeaderField();
        responseHeader.add(headerField);
        return headerField;
    }

    public void setHeader(String name, String value) {
        HttpResponseHeaderField headerField = responseHeader.stream()
            .filter(header -> header.getName().equals(name))
            .findFirst()
            .orElseGet(this::createHeaderField);
        headerField.setName(name);
        headerField.setValue(value);
    }

    public HttpResponseHeader setLocation(String redirectionPath) {
        addHeader("Location", redirectionPath);
        return this;
    }

    public HttpResponseHeader setContentType(ContentType contentType) {
        addHeader("Content-Type", contentType.getMimeName());
        return this;
    }

    public HttpResponseHeader setContentLength(int value) {
        addHeader("Content-Length", String.valueOf(value));
        return this;

    }

    public HttpResponseHeader setCookie(Cookie cookie) {
        addHeader("Set-Cookie", concatenateCookies(cookie));
        return this;
    }

    public String concatenateCookies(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        Map<String, String> cookies = cookie.getAttributes();
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
        return responseHeader.stream()
            .map(header -> header.getName() + ": " + header.getValue())
            .reduce((s1, s2) -> s1 + CLRF + s2)
            .orElse("");
    }

    public List<HttpResponseHeaderField> getResponseHeader() {
        return Collections.unmodifiableList(responseHeader);
    }

    public String getHeaderValue(String name) {
        return responseHeader.stream()
            .filter(header -> header.getName().equals(name))
            .map(HttpResponseHeaderField::getValue)
            .findFirst()
            .orElse("");
    }
}
