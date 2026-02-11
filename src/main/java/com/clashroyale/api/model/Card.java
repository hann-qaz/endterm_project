package com.clashroyale.api.model;
import com.clashroyale.api.exception.InvalidInputException;

public abstract class Card extends GameEntity implements Upgradable, Printable {
    protected String rarity;
    protected int elixirCost;
    protected int level;

    public Card(int id, String name, String rarity, int elixirCost, int level) {
        super (id, name);
        this.rarity = rarity;
        this.elixirCost = elixirCost;
        this.level = level;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Card name cannot be empty!");
        }
        if (elixirCost < 1 || elixirCost > 10) {
            throw new InvalidInputException("Elixir cost must be less than 1 and greater than 10!");
        }
        if(level < 1 || level > 16) {
            throw new InvalidInputException("Level must be between 1 and 16!");
        }
    }

    @Override
    public void upgrade() {
        if(canUpgrade() == true) {
            level++;
        }
    }

    @Override
    public boolean canUpgrade() {
        return level < 16;
    }

    @Override
    public String toFormattedString() {
        return String.format("Card[id=%d, name=%s, type=%s, rarity=%s, elixir=%d, level=%d]",
                id, name, getType(), rarity, elixirCost, level);
    }
    //getteri i setteri
    public String getRarity() { return rarity; }
    public int getElixirCost() { return elixirCost; }
    public int getLevel() { return level; }
    public void setRarity( String rarity) { this.rarity = rarity; }
    public void setElixirCost(int elixirCost) { this.elixirCost = elixirCost; }
    public void setLevel(int level) { this.level=level; }

}
