package webserver;

public class Cookie {

    private final String cookieSessionID;
    private String cookiePath;


    public Cookie(String sessionID) {
        this.cookieSessionID = sessionID;
    }

    public Cookie setPath(String path) {
        this.cookiePath = path;
        return this;
    }

    public String getCookieSessionID() {
        return cookieSessionID;
    }

    public String getCookiePath() {
        return cookiePath;
    }
}
