package com.clashroyale.api.service;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.repository.CardRepository;
import com.clashroyale.api.service.interfaces.CardServiceInterface;
import com.clashroyale.api.exception.*;
import com.clashroyale.api.patterns.singleton.LoggerService;
import com.clashroyale.api.patterns.singleton.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService implements CardServiceInterface {

    private final CardRepository cardRepository;
    private final LoggerService logger = LoggerService.getInstance();
    private final CacheService cache = CacheService.getInstance();

    private static final String ALL_CARDS_CACHE_KEY = "all_cards";

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(Card card) throws InvalidInputException, DatabaseException {
        logger.info("Creating card: " + card.getName());
        card.validate();
        cardRepository.create(card);

        // invalidate cache only after creating new card
        cache.clear(ALL_CARDS_CACHE_KEY);
        logger.info("Cache invalidated after card creation");

        logger.info("Card created successfully: " + card.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Card> getAllCards() throws DatabaseException {
        logger.info("Fetching all cards");

        if (cache.contains(ALL_CARDS_CACHE_KEY)) {
            logger.info("Returning cards from cache");
            return (List<Card>) cache.get(ALL_CARDS_CACHE_KEY).orElse(null);
        }

        // if not in cache, fetch from database
        logger.info("Cache miss - fetching from database");
        List<Card> cards = cardRepository.getAll();

        // store in cache for future requests
        cache.put(ALL_CARDS_CACHE_KEY, cards);
        logger.info("Cards cached for future requests");

        return cards;
    }

    @Override
    public Card getCardById(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Fetching card with ID: " + id);
        return cardRepository.getById(id);
    }

    @Override
    public void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        logger.info("Updating card with ID: " + id);
        card.validate();
        Card existingCard = cardRepository.getById(id);
        logger.debug("Found existing card: " + existingCard.getName());

        cardRepository.update(id, card);

        // invalidate cache after update
        cache.clear(ALL_CARDS_CACHE_KEY);
        logger.info("Cache invalidated after card update");

        logger.info("Card updated successfully: " + card.getName());
    }

    @Override
    public void deleteCard(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting card with ID: " + id);
        Card card = cardRepository.getById(id);
        logger.debug("Found card to delete: " + card.getName());

        cardRepository.delete(id);

        // invalidate cache after delete
        cache.clear(ALL_CARDS_CACHE_KEY);
        logger.info("Cache invalidated after card deletion");

        logger.info("Card deleted successfully (ID: " + id + ")");
    }

    @Override
    public void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        logger.info("Upgrading card with ID: " + id);
        Card card = cardRepository.getById(id);

        if (!card.canUpgrade()) {
            throw new InvalidInputException("Card '" + card.getName() +
                    "' is already at max level (" + card.getLevel() + ")");
        }

        int oldLevel = card.getLevel();
        card.upgrade();
        cardRepository.update(id, card);

        // invalidate cache after upgrade
        cache.clear(ALL_CARDS_CACHE_KEY);
        logger.info("Cache invalidated after card upgrade");

        logger.info(" Card upgraded: " + card.getName() +
                " from level " + oldLevel + " to " + card.getLevel());
    }

    @Override
    public List<Card> getCardsByType(String type) throws DatabaseException {
        logger.info("Fetching cards by type: " + type);

        if (!isValidCardType(type)) {
            logger.error("Invalid card type: " + type);
            throw new DatabaseException("Invalid card type. Must be WARRIOR, SPELL, or BUILDING");
        }

        return cardRepository.getByType(type);
    }

    @Override
    public List<Card> getCardsByRarity(String rarity) throws DatabaseException {
        logger.info("Fetching cards by rarity: " + rarity);

        if (!isValidRarity(rarity)) {
            logger.error("Invalid rarity: " + rarity);
            throw new DatabaseException("Invalid rarity. Must be COMMON, RARE, EPIC, or LEGENDARY");
        }

        return cardRepository.getByRarity(rarity);
    }

    private boolean isValidCardType(String type) {
        return type != null &&
                (type.equalsIgnoreCase("WARRIOR") ||
                        type.equalsIgnoreCase("SPELL") ||
                        type.equalsIgnoreCase("BUILDING"));
    }

    private boolean isValidRarity(String rarity) {
        return rarity != null &&
                (rarity.equalsIgnoreCase("COMMON") ||
                        rarity.equalsIgnoreCase("RARE") ||
                        rarity.equalsIgnoreCase("EPIC") ||
                        rarity.equalsIgnoreCase("LEGENDARY"));
    }

    //manually clear cache (for admins)
    public void clearCache() {
        cache.clear(ALL_CARDS_CACHE_KEY);
        logger.info("Cache manually cleared by admin");
    }
}