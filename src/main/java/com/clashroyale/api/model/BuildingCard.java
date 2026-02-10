package com.clashroyale.api.model;

public class BuildingCard extends Card {
    private int hp;
    private int lifetime;

    public BuildingCard(int id, String name, String rarity, int elixirCost, int level, int hp, int lifetime) {
        super (id, name, rarity, elixirCost, level);
        this.hp = hp;
        this.lifetime = lifetime;
    }

    @Override
    public String getType() { return "Building"; }


    @Override
    public String getBasicInfo() {
        return super.getBasicInfo() + ", HP: " + hp + ", Lifetime: " + lifetime + "s";
    }

    /// getters setters
    public int getHp() { return hp; }
    public int getLifetime() { return lifetime; }
    public void setHp(int hp) { this.hp = hp; }
    public void SetLifetime(int lifetime) { this.lifetime = lifetime; }
}
