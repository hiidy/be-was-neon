package webserver.session;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private SessionStore() {
    }

    public static void addSession(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    public static Map<String, Session> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}