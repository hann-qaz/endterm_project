package com.clashroyale.api.model;

import com.clashroyale.api.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private int id;
    private String deckName;
    private List<Card> cards;
    private static final int MAX_CARDS = 8;

    public Deck(int id, String deckName) {
        this.id = id;
        this.deckName = deckName;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) throws InvalidInputException {
        if (cards.size() >= MAX_CARDS) {
            throw new InvalidInputException("Deck cannot have more than " + MAX_CARDS + " cards");
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
        return cards.stream().mapToInt(Card::getElixirCost).sum();
    }

    public double getAverageElixirCost() {
        return cards.isEmpty() ? 0 : (double) getTotalElixirCost() / cards.size();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDeckName() { return deckName; }
    public void setDeckName(String deckName) { this.deckName = deckName; }
    public List<Card> getCards() { return new ArrayList<>(cards); }
}