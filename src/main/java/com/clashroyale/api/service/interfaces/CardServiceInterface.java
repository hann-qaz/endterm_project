package com.clashroyale.api.service.interfaces;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.exception.*;
import java.util.List;

/**
 * SERVICE INTERFACE for Card operations
 * Demonstrates:
 *   - ISP (Interface Segregation Principle): Focused interface
 *   - DIP (Dependency Inversion Principle): Controllers depend on this abstraction
 */
public interface CardServiceInterface {

    /**
     * Create a new card
     * @param card Card entity to create
     * @throws InvalidInputException if card data is invalid
     * @throws DatabaseException if database operation fails
     */
    void createCard(Card card) throws InvalidInputException, DatabaseException;

    /**
     * Get all cards
     * @return List of all cards
     * @throws DatabaseException if database operation fails
     */
    List<Card> getAllCards() throws DatabaseException;

    /**
     * Get card by ID
     * @param id Card ID
     * @return Card entity
     * @throws ResourceNotFoundException if card not found
     * @throws DatabaseException if database operation fails
     */
    Card getCardById(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * Update existing card
     * @param id Card ID to update
     * @param card Updated card data
     * @throws InvalidInputException if card data is invalid
     * @throws ResourceNotFoundException if card not found
     * @throws DatabaseException if database operation fails
     */
    void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException;

    /**
     * Delete card
     * @param id Card ID to delete
     * @throws ResourceNotFoundException if card not found
     * @throws DatabaseException if database operation fails
     */
    void deleteCard(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * Upgrade card to next level
     * @param id Card ID to upgrade
     * @throws ResourceNotFoundException if card not found
     * @throws DatabaseException if database operation fails
     * @throws InvalidInputException if card is already at max level
     */
    void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException;

    /**
     * Get cards by type
     * @param type Card type (WARRIOR, SPELL, BUILDING)
     * @return List of cards of specified type
     * @throws DatabaseException if database operation fails
     */
    List<Card> getCardsByType(String type) throws DatabaseException;

    /**
     * Get cards by rarity
     * @param rarity Card rarity (COMMON, RARE, EPIC, LEGENDARY)
     * @return List of cards of specified rarity
     * @throws DatabaseException if database operation fails
     */
    List<Card> getCardsByRarity(String rarity) throws DatabaseException;
}