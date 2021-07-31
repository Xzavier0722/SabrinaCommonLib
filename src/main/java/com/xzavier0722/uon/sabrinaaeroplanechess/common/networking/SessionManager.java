package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SessionManager {

    private final Map<String, Session> sessions = new HashMap<>();

    public synchronized Session createSession() {
        String id = createId();
        Session re = new Session(id, createKey());
        sessions.put(id, re);
        return re;
    }

    public synchronized void destroySession(String id) {
        sessions.remove(id);
    }

    public synchronized Optional<Session> getSession(String id) {
        return Optional.ofNullable(sessions.get(id));
    }

    private String createId() {
        String re = Utils.randomString(32);
        return sessions.containsKey(re) ? createId() : re;
    }

    private String createKey() {
        return Utils.base64(Utils.sha256(Utils.randomString(256)));
    }

}
