package webserver;

public class HttpRequest {

    private String method;
    private String requestURI;


    public HttpRequest(String requestLine) {
        String[] requestLines = requestLine.split(" ");
        method = requestLines[0];
        requestURI = requestLines[1];

    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
