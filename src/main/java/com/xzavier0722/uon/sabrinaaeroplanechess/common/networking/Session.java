package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

import com.xzavier0722.uon.sabrinaaeroplanechess.common.game.PlayerProfile;
import com.xzavier0722.uon.sabrinaaeroplanechess.common.security.AES;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Session {

    private final String id;
    private final long timestamp;
    private final String key;
    private PlayerProfile playerProfile;
    private InetPointInfo inetPoint;
    private int nextPacketId;
    private AES aes;

    protected Session(String id, String base64KeyStr){
        this.id = id;
        this.timestamp = System.currentTimeMillis();
        this.nextPacketId = 0;
        this.key = base64KeyStr;
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getNextPacketId() {
        return nextPacketId++;
    }

    public String getKey() {
        return key;
    }

    public AES getAes() {
        try {
            return aes == null ? (aes = new AES(key)) : aes;
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public void setPlayerProfile(PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
    }

    public InetPointInfo getInetPoint() {
        return inetPoint;
    }

    public void setInetPoint(InetPointInfo inetPoint) {
        this.inetPoint = inetPoint;
    }
}
