package com.clashroyale.api.dto;

import jakarta.validation.constraints.*;

public class CardRequest {

    @NotBlank(message = "Card name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Card type is required")
    @Pattern(regexp = "WARRIOR|SPELL|BUILDING", message = "Type must be WARRIOR, SPELL, or BUILDING")
    private String type;

    @NotBlank(message = "Rarity is required")
    private String rarity;

    @Min(value = 1, message = "Elixir cost must be at least 1")
    @Max(value = 10, message = "Elixir cost cannot exceed 10")
    private int elixirCost;

    @Min(value = 1, message = "Level must be at least 1")
    @Max(value = 16, message = "Level cannot exceed 16")
    private int level;

    private Integer damage;
    private Integer hp;
    private Integer radius;
    private Integer lifetime;

    // Constructors
    public CardRequest() {}

    public CardRequest(String name, String type, String rarity, int elixirCost, int level) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public int getElixirCost() { return elixirCost; }
    public void setElixirCost(int elixirCost) { this.elixirCost = elixirCost; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public Integer getDamage() { return damage; }
    public void setDamage(Integer damage) { this.damage = damage; }

    public Integer getHp() { return hp; }
    public void setHp(Integer hp) { this.hp = hp; }

    public Integer getRadius() { return radius; }
    public void setRadius(Integer radius) { this.radius = radius; }

    public Integer getLifetime() { return lifetime; }
    public void setLifetime(Integer lifetime) { this.lifetime = lifetime; }
}