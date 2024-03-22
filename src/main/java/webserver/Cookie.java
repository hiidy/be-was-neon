package webserver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cookie {

    private final Map<String, String> cookies;


    public Cookie() {
        cookies = new HashMap<>();
    }

    public Cookie setPath(String path) {
        cookies.put("Path", path);
        return this;
    }

    public Cookie setSessionID(String sessionID) {
        cookies.put("sid", sessionID);
        return this;
    }

    public Map<String, String> getCookies() {
        return Collections.unmodifiableMap(cookies);
    }
}
