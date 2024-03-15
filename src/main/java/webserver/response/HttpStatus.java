package webserver.response;

public enum HttpStatus {

    OK(202),
    FOUND(302);

    private final int statusCode;

    HttpStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}