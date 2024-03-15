package webserver.response;

public class HttpResponseBody {

    private final String responseBody;

    public HttpResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseBodyLength() {
        return responseBody.getBytes().length;
    }
}
