package webserver;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String method;
    private String requestURI;

    private Map<String, String> queries = new HashMap<>();

    public HttpRequest(String requestLine) {
        String[] requestLines = requestLine.split(" ");
        method = requestLines[0];
        if (hasQueryParameter(requestLines[1])) {
            extractQueryFromURI(requestLines[1]);
        } else {
            requestURI = requestLines[1];
        }
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    private boolean hasQueryParameter(String requestLine) {
        return requestLine.contains("?");
    }

    private void extractQueryFromURI(String URI) {
        String[] URIs = URI.split("\\?");
        requestURI = URIs[0];
        queries = parseQueryString(URIs[1]);
    }

    private Map<String, String> parseQueryString(String string) {
        Map<String, String> queryMap = new HashMap<>();

        String[] pairs = string.split("&");

        for (String pair : pairs) {

            String[] keyValue = pair.split("=");

            String key = keyValue[0];
            String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

            queryMap.put(key, value);
        }
        return queryMap;
    }

    public Map<String, String> getQueries() {
        return Collections.unmodifiableMap(queries);
    }

    public String getValue(String key) {
        return getQueries().get(key);
    }

}
