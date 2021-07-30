package com.xzavier0722.uon.sabrinaaeroplanechess.common.networking;

public class Packet {

    private final byte identifier;
    private int id;
    private String sessionId;
    private byte command;
    private int sequence;
    private long timestamp;
    private String data;

    public Packet() {
        // Default identifier for the protocol is 0x48 (72)
        this((byte)0x48);
    }

    public Packet(byte identifier) {
        this.identifier = identifier;
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

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
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
}
