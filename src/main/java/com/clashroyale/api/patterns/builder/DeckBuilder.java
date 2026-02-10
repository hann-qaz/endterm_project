package com.clashroyale.api.patterns.builder;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.model.Deck;
import com.clashroyale.api.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * BUILDER PATTERN
 * Constructs Deck with validation
 */
public class DeckBuilder {

    private int id = 0;
    private String deckName;
    private List<Card> cards = new ArrayList<>();

    public DeckBuilder() {}

    public DeckBuilder id(int id) {
        this.id = id;
        return this;
    }

    public DeckBuilder name(String deckName) {
        this.deckName = deckName;
        return this;
    }

    public DeckBuilder addCard(Card card) {
        if (cards.size() >= 8) {
            throw new IllegalStateException("Deck cannot have more than 8 cards");
        }
        this.cards.add(card);
        return this;
    }

    public DeckBuilder cards(List<Card> cards) {
        if (cards.size() > 8) {
            throw new IllegalStateException("Deck cannot have more than 8 cards");
        }
        this.cards = new ArrayList<>(cards);
        return this;
    }

    public Deck build() throws InvalidInputException {
        if (deckName == null || deckName.isEmpty()) {
            throw new IllegalStateException("Deck name is required");
        }

        Deck deck = new Deck(id, deckName);
        for (Card card : cards) {
            deck.addCard(card);
        }
        return deck;
    }

    public static DeckBuilder builder() {
        return new DeckBuilder();
    }
}