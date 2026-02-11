package com.clashroyale.api.patterns.factory;

import com.clashroyale.api.model.*;
import org.springframework.stereotype.Component;

/**
 * FACTORY PATTERN
 * Creates appropriate Card subclass based on type
 * Demonstrates Open/Closed Principle - easy to extend with new card types
 */
@Component
public class CardFactory {

    /**
     * Factory method to create cards
     * @param type Card type (WARRIOR, SPELL, BUILDING)
     * @return Appropriate Card subclass instance
     */
    public Card createCard(String type, int id, String name, String rarity,
                           int elixirCost, int level,
                           Integer hp, Integer damage, Integer radius, Integer lifetime) {

        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Card type can't be null or empty");
        }

        return switch (type.toUpperCase()) {
            case "WARRIOR" -> new WarriorCard(id, name, rarity, elixirCost, level,
                    hp != null ? hp : 0,
                    damage != null ? damage : 0);

            case "SPELL" -> new SpellCard(id, name, rarity, elixirCost, level,
                    radius != null ? radius : 0,
                    damage != null ? damage : 0);

            case "BUILDING" -> new BuildingCard(id, name, rarity, elixirCost, level,
                    hp != null ? hp : 0,
                    lifetime != null ? lifetime : 0);

            default -> throw new IllegalArgumentException("Wrong card type: " + type);
        };
    }
}