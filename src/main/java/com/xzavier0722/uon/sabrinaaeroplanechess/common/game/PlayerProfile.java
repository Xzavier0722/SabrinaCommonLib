package com.xzavier0722.uon.sabrinaaeroplanechess.common.game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {

    private final UUID uuid;
    private String name;
    private int playCount;
    private int wins;

    public PlayerProfile(String name) {
        // New player profile
        this(UUID.randomUUID(), name, 0, 0);
    }

    public PlayerProfile(UUID uuid, String name, int playCount, int wins) {
        this.uuid = uuid;
        this.name = name;
        this.playCount = playCount;
        this.wins = wins;
    }

    public static PlayerProfile getFromMap(Map<String, String> dataMap) {
        UUID uuid = UUID.fromString(dataMap.get("UUID"));
        String name = dataMap.get("Name");
        int playCount = Integer.parseInt(dataMap.get("PlayCount"));
        int wins = Integer.parseInt(dataMap.get("Wins"));
        return new PlayerProfile(uuid, name, playCount, wins);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void addCount() {
        this.playCount++;
    }

    public void setPlayCount(int newVal) {
        this.playCount = newVal;
    }

    public void addWins() {
        this.wins++;
    }

    public void setWins(int newVal) {
        this.wins = newVal;
    }

    public int getWins() {
        return wins;
    }

    public Map<String, String> toMap() {
        Map<String, String> re = new HashMap<>();
        re.put("UUID", uuid.toString());
        re.put("Name", name);
        re.put("PlayCount", String.valueOf(playCount));
        re.put("Wins", String.valueOf(wins));
        return re;
    }
}
