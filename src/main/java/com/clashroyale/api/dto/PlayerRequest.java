package com.clashroyale.api.dto;

import jakarta.validation.constraints.*;

public class PlayerRequest {

    @NotBlank(message = "Player name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Min(value = 1, message = "Level must be at least 1")
    private Integer level = 1;

    @Min(value = 0, message = "Trophies cannot be negative")
    private Integer trophies = 0;

    // Constructors
    public PlayerRequest() {}

    public PlayerRequest(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }

    public Integer getTrophies() { return trophies; }
    public void setTrophies(Integer trophies) { this.trophies = trophies; }
}