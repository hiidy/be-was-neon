package webserver.response;

public class HttpResponseBody {

    private final byte[] responseBody;

    public HttpResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }

    public int getResponseBodyLength() {
        return responseBody.length;
    }

    public String getHttpResponseBodyMessage() {
        return new String(responseBody);
    }
}
