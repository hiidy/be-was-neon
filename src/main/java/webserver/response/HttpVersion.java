package webserver.response;

public enum HttpVersion {

    HTTP1("HTTP/1.0"),
    HTTP11("HTTP/1.1"),
    HTTP2("HTTP/2.0"),
    HTTP3("HTTP/3.0");

    private final String httpVersion;

    HttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
