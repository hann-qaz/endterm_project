package com.clashroyale.api.service;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.repository.CardRepository;
import com.clashroyale.api.service.interfaces.CardServiceInterface;
import com.clashroyale.api.exception.*;
import com.clashroyale.api.patterns.singleton.LoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SERVICE LAYER - Business logic for cards
 * Demonstrates:
 *   - SRP: Only contains business logic, no database or UI code
 *   - DIP: Depends on CardRepository interface (though we use concrete class for additional methods)
 *   - Spring Integration: Uses @Service annotation for component scanning
 */
@Service
public class CardService implements CardServiceInterface {

    private final CardRepository cardRepository;
    private final LoggerService logger = LoggerService.getInstance();

    /**
     * Constructor injection - Spring automatically injects CardRepository
     * Demonstrates DIP: High-level module (Service) doesn't depend on low-level module (Repository implementation)
     */
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(Card card) throws InvalidInputException, DatabaseException {
        logger.info("Creating card: " + card.getName());

        // BUSINESS LOGIC: Validate card before saving
        card.validate();

        // Delegate to repository
        cardRepository.create(card);

        logger.info(" Card created successfully: " + card.getName() +
                " (ID: " + card.getId() + ", Type: " + card.getType() + ")");
    }

    @Override
    public List<Card> getAllCards() throws DatabaseException {
        logger.info("Fetching all cards");
        return cardRepository.getAll();
    }

    @Override
    public Card getCardById(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Fetching card with ID: " + id);
        return cardRepository.getById(id);
    }

    @Override
    public void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        logger.info("Updating card with ID: " + id);

        // BUSINESS LOGIC: Validate before updating
        card.validate();

        // Check if card exists
        Card existingCard = cardRepository.getById(id);
        logger.debug("Found existing card: " + existingCard.getName());

        // Delegate to repository
        cardRepository.update(id, card);

        logger.info(" Card updated successfully: " + card.getName());
    }

    @Override
    public void deleteCard(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting card with ID: " + id);

        // Check if card exists before deletion
        Card card = cardRepository.getById(id);
        logger.debug("Found card to delete: " + card.getName());

        // Delegate to repository
        cardRepository.delete(id);

        logger.info(" Card deleted successfully (ID: " + id + ")");
    }

    @Override
    public void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        logger.info("Upgrading card with ID: " + id);

        // Get existing card
        Card card = cardRepository.getById(id);

        // BUSINESS LOGIC: Check if upgrade is possible
        if (!card.canUpgrade()) {
            throw new InvalidInputException("Card '" + card.getName() +
                    "' is already at max level (" + card.getLevel() + ")");
        }

        // Perform upgrade
        int oldLevel = card.getLevel();
        card.upgrade();

        // Save updated card
        cardRepository.update(id, card);

        logger.info(" Card upgraded: " + card.getName() +
                " from level " + oldLevel + " to " + card.getLevel());
    }

    @Override
    public List<Card> getCardsByType(String type) throws DatabaseException {
        logger.info("Fetching cards by type: " + type);

        // Validate type
        if (!isValidCardType(type)) {
            logger.error("Invalid card type: " + type);
            throw new DatabaseException("Invalid card type. Must be WARRIOR, SPELL, or BUILDING");
        }

        return cardRepository.getByType(type);
    }

    @Override
    public List<Card> getCardsByRarity(String rarity) throws DatabaseException {
        logger.info("Fetching cards by rarity: " + rarity);

        // Validate rarity
        if (!isValidRarity(rarity)) {
            logger.error("Invalid rarity: " + rarity);
            throw new DatabaseException("Invalid rarity. Must be COMMON, RARE, EPIC, or LEGENDARY");
        }

        return cardRepository.getByRarity(rarity);
    }

    /**
     * ADDITIONAL METHOD: Get card count
     */
    public int getCardCount() throws DatabaseException {
        logger.info("Getting total card count");
        return cardRepository.count();
    }

    /**
     * Helper method to validate card type
     */
    private boolean isValidCardType(String type) {
        return type != null &&
                (type.equalsIgnoreCase("WARRIOR") ||
                        type.equalsIgnoreCase("SPELL") ||
                        type.equalsIgnoreCase("BUILDING"));
    }

    /**
     * Helper method to validate rarity
     */
    private boolean isValidRarity(String rarity) {
        return rarity != null &&
                (rarity.equalsIgnoreCase("COMMON") ||
                        rarity.equalsIgnoreCase("RARE") ||
                        rarity.equalsIgnoreCase("EPIC") ||
                        rarity.equalsIgnoreCase("LEGENDARY"));
    }
}