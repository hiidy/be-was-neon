package webserver.session;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void addSession(Session session) {
        sessions.put(session.getSessionId(), session);
    }

    public Map<String, Session> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

//    public static void removeSession(String sessionId) {
//        sessions.entrySet().removeIf(entry -> entry.getKey().getSessionId().equals(sessionId));
//    }

    public static void removeSession(Session session) {
        sessions.remove(session.getSessionId());
    }
}
