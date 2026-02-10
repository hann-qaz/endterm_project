package com.clashroyale.api.model;

import com.clashroyale.api.exception.InvalidInputException;

public class Player extends GameEntity {
    private int level;
    private int trophies;
    private Deck deck;

    public Player(int id, String name, int level, int trophies) {
        super(id, name);
        this.level = level;
        this.trophies = trophies;
    }

    @Override
    public String getType() {
        return "PLAYER";
    }

    @Override
    public void validate() throws InvalidInputException {
        if (name == null) {
            throw new InvalidInputException("Player name cannot be empty");
        }
        if (level < 1) {
            throw new InvalidInputException("Player level must be at least 1");
        }
        if (trophies < 0) {
            throw new InvalidInputException("Trophies cannot be negative");
        }
    }

    // getters
    public int getLevel() { return level; }
    public int getTrophies() { return trophies; }
    public Deck getDeck() { return deck; }
    /// setters
    public void setLevel(int level) { this.level = level; }
    public void setTrophies(int trophies) { this.trophies = trophies; }
    public void setDeck(Deck deck) { this.deck = deck; }
}