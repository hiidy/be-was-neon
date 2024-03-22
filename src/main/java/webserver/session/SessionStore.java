package webserver.session;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static final Map<Session, String> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session session, String userId) {
        sessions.put(session, userId);
    }

    public Map<Session, String> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

    public static void removeSession(String sessionId) {
        sessions.entrySet().removeIf(entry -> entry.getKey().getSessionId().equals(sessionId));
    }
}
