package webserver.request;

public class HttpRequestBody {

    private String bodyMessage;

    public HttpRequestBody(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }
}
