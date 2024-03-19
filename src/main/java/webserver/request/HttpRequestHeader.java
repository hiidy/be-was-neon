package webserver.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHeader {

    private static final String CRLF = "\r\n";


    private final Map<String, String> requestHeader;

    public HttpRequestHeader(String headersInput) {
        this.requestHeader = new HashMap<>();
        parseHeader(headersInput);
    }

    private void parseHeader(String headersInput) {
        String[] headers = headersInput.split(CRLF);
        for (String header : headers) {
            String[] keyValue = header.split(":", 2);
            requestHeader.put(keyValue[0].trim(), keyValue[1].trim());
        }
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(requestHeader);
    }

    public String getValue(String key) {
        return getHeaders().get(key);
    }

}
