package com.clashroyale.api.dto;

import com.clashroyale.api.model.Card;

public class CardResponse {

    private int id;
    private String name;
    private String type;
    private String rarity;
    private int elixirCost;
    private int level;
    private Integer damage;
    private Integer hp;
    private Integer radius;
    private Integer lifetime;

    // Static factory method
    public static CardResponse fromEntity(Card card) {
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setName(card.getName());
        response.setType(card.getType());
        response.setRarity(card.getRarity());
        response.setElixirCost(card.getElixirCost());
        response.setLevel(card.getLevel());
        return response;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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