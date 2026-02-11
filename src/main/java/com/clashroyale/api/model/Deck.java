package com.clashroyale.api.model;

import com.clashroyale.api.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private int id;
    private String deckName;
    private List<Card> cards;

    public Deck(int id, String deckName) {
        this.id = id;
        this.deckName = deckName;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) throws InvalidInputException {
        if (cards.size() >= 8) {
            throw new InvalidInputException("Deck cannot have more than 8 cards");
        }
        if (cards.contains(card)) {
            throw new InvalidInputException("Card already exists in deck");
        }
        cards.add(card);
    }

    public void removeCard(Card card) throws InvalidInputException {
        if (!cards.remove(card)) {
            throw new InvalidInputException("Card not found in deck");
        }
    }

    public int getTotalElixirCost() {
        int total = 0;
        for (Card card : cards) {
            total += card.getElixirCost();
        }
        return total;
    }

    public double getAverageElixirCost() {
        if (cards.isEmpty()) {
            return 0;
        }
        return (double) getTotalElixirCost() / 8;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }
    public List<Card> getCards() { return new ArrayList<>(cards); }
}