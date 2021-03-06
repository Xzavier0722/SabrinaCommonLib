package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

public class Packet {

    private final byte identifier;
    private int id;
    private String sessionId;
    private Request request;
    private int sequence;
    private long timestamp;
    private String sign;
    private String data;

    public Packet() {
        // Default identifier for the protocol is 0x48 (72)
        this((byte)0x48);
    }

    public Packet(byte identifier) {
        this.identifier = identifier;
        this.id = -1;
        this.sequence = -1;
        this.timestamp = -1;
    }

    public byte getIdentifier() {
        return identifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
