package com.clashroyale.api.model;

public class WarriorCard extends Card{
    private int hp;
    private int damage;

    public WarriorCard(int id, String name, String rarity, int elixirCost, int level, int hp, int damage) {
        super (id, name, rarity, elixirCost, level);
        this.hp = hp;
        this.damage = damage;
    }

    @Override
    public String getType() { return "WARRIOR"; }

    @Override
    public String getBasicInfo() {
        return super.getBasicInfo() + ", Hp: " + hp + ", Damage: " + damage;
    }

    ///getters, setters
    public int getHp() { return hp;}
    public int getDamage() { return damage;}
    public void setHp(int hp) { this.hp = hp; }
    public void setDamage(int damage) { this.damage = damage; }
}
