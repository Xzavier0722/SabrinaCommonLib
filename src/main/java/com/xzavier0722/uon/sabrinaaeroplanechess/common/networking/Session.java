package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;

public class Session {

    private final String id;
    private final long timestamp;
    private String key;
    private PlayerProfile playerProfile;
    private InetPoint inetPoint;

    protected Session(String id) {
        this.id = id;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
