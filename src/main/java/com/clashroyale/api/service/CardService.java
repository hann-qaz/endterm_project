package com.clashroyale.api.service;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.repository.CardRepository;
import com.clashroyale.api.service.interfaces.CardServiceInterface;
import com.clashroyale.api.exception.*;
import com.clashroyale.api.patterns.singleton.LoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//business logic for cards.  demonstrates SRP and DIP and
 //Spring Integration: uses @Service annotation for component scanning

@Service
public class CardService implements CardServiceInterface {

    private final CardRepository cardRepository;
    private final LoggerService logger = LoggerService.getInstance();

     // Demonstrates DIP High-level module Service don't depend on low-level modules Repository
     //Spring automatically pass implementation of CrudRepository automatically
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void createCard(Card card) throws InvalidInputException, DatabaseException {
        logger.info("Creating card... ");

        card.validate();    // BUSINESS LOGIC: Validate card before saving
        cardRepository.create(card);   // Delegate to repository

        logger.info(" Card created successfully: " + card.getName() +
                " ID: " + card.getId() + ", Type: " + card.getType());
    }

    @Override
    public List<Card> getAllCards() throws DatabaseException {
        logger.info("Showing all cards...");
        return cardRepository.getAll();
    }

    @Override
    public Card getCardById(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Showing card with ID: " + id + "...");
        return cardRepository.getById(id);
    }

    @Override
    public void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        logger.info("Updating card with ID: " + id + "...");

        card.validate(); // BUSINESS LOGIC: Validate before updating

        Card existingCard = cardRepository.getById(id);
        logger.debug("Found existing card: " + existingCard.getName());  // check card existing

        cardRepository.update(id, card); // delegate task to repository

        logger.info(" Card updated successfully: " + card.getName());
    }

    @Override
    public void deleteCard(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting card with ID: " + id + "...");

        Card card = cardRepository.getById(id);
        logger.debug("Found card to delete: " + card.getName());

        cardRepository.delete(id);

        logger.info(" Card deleted successfully ID: " + id);
    }

    @Override
    public void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        logger.info("Upgrading card with ID: " + id + "...");

        Card card = cardRepository.getById(id);

        // check upgrade is possible
        if (!card.canUpgrade()) {
            throw new InvalidInputException("Card is already at max level 16");
        }
        card.upgrade();
        cardRepository.update(id, card);  // save updated card

        logger.info(" Card upgraded to " + card.getLevel());
    }

    @Override
    public List<Card> getCardsByType(String type) throws DatabaseException {
        logger.info("Showing cards by type: " + type + "...");

        // Validate type
        if (!isValidCardType(type)) {
            logger.error("Wrong card type: " + type);
            throw new DatabaseException("Must be WARRIOR, SPELL, or BUILDING");
        }

        return cardRepository.getByType(type);
    }

    @Override
    public List<Card> getCardsByRarity(String rarity) throws DatabaseException {
        logger.info("Showing cards by rarity: " + rarity + "...");

        // Validate rarity
        if (!isValidRarity(rarity)) {
            logger.error("Wrong rarity: " + rarity);
            throw new DatabaseException("Wrong rarity. Must be COMMON, RARE, EPIC, or LEGENDARY");
        }

        return cardRepository.getByRarity(rarity);
    }


    //Helper method to validate card type
    private boolean isValidCardType(String type) {
        if (type != null &&
                (type.equals("WARRIOR") ||
                        type.equals("SPELL") ||
                        type.equals("BUILDING"))) {
            return true;
        } else {
            return false;
        }
    }

    //method to validate rarity
    private boolean isValidRarity(String rarity) {
        if (rarity != null &&
                (rarity.equals("COMMON") ||
                        rarity.equals("RARE") ||
                        rarity.equals("EPIC") ||
                        rarity.equals("LEGENDARY"))) {
            return true;
        } else {
            return false;
        }
    }
}