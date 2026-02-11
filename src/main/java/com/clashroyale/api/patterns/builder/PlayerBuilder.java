package com.clashroyale.api.patterns.builder;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.model.Deck;

/**
 * BUILDER PATTERN
 * Constructs complex Player objects with fluent API
 * Handles optional parameters elegantly
 */
public class PlayerBuilder {

    private int id = 0;
    private String name;
    private int level = 1;
    private int trophies = 0;
    private Deck deck;

    public PlayerBuilder() {}

    public PlayerBuilder id(int id) {
        this.id = id;
        return this;
    }

    public PlayerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PlayerBuilder level(int level) {
        this.level = level;
        return this;
    }

    public PlayerBuilder trophies(int trophies) {
        this.trophies = trophies;
        return this;
    }

    public PlayerBuilder deck(Deck deck) {
        this.deck = deck;
        return this;
    }

    public Player build() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Player name is required");
        }

        Player player = new Player(id, name, level, trophies);
        if (deck != null) {
            player.setDeck(deck);
        }
        return player;
    }

     //Static factory method for fluent API
    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }
}