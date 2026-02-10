package com.clashroyale.api.dto;

import com.clashroyale.api.model.Player;

public class PlayerResponse {

    private int id;
    private String name;
    private int level;
    private int trophies;

    public static PlayerResponse fromEntity(Player player) {
        PlayerResponse response = new PlayerResponse();
        response.setId(player.getId());
        response.setName(player.getName());
        response.setLevel(player.getLevel());
        response.setTrophies(player.getTrophies());
        return response;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getTrophies() { return trophies; }
    public void setTrophies(int trophies) { this.trophies = trophies; }
}