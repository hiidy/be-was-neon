package webserver.request;

public class HttpRequestStartLine {

    private final String method;
    private String requestURI;

    private String queryString;

    public HttpRequestStartLine(String requestLine) {
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
        queryString = URIs[1];
    }

    public String getQueryString() {
        return queryString;
    }

}
