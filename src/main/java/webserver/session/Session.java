package webserver.session;

import java.util.Map;

public class Session {

    private final String sessionId;
    private Map<String, Object> attributes;

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public Object getAttribute(String attribute) {
        return attributes.getOrDefault(attribute, null);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public String getSessionId() {
        return sessionId;
    }
}
