package com.clashroyale.api.service.interfaces;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.exception.*;
import java.util.List;

/**
 * Service interface for Player operations
 * Demonstrates: ISP (Interface Segregation Principle)
 *              DIP (Dependency Inversion Principle)
 */
public interface PlayerServiceInterface {

    /**
     * Create a new player
     * @param player Player entity to create
     * @throws InvalidInputException if player data is invalid
     * @throws DatabaseException if database operation fails
     */
    void createPlayer(Player player) throws InvalidInputException, DatabaseException;

    /**
     * Get all players
     * @return List of all players
     * @throws DatabaseException if database operation fails
     */
    List<Player> getAllPlayers() throws DatabaseException;

    /**
     * Get player by ID
     * @param id Player ID
     * @return Player entity
     * @throws ResourceNotFoundException if player not found
     * @throws DatabaseException if database operation fails
     */
    Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * Update existing player
     * @param id Player ID to update
     * @param player Updated player data
     * @throws InvalidInputException if player data is invalid
     * @throws ResourceNotFoundException if player not found
     * @throws DatabaseException if database operation fails
     */
    void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException;

    /**
     * Delete player
     * @param id Player ID to delete
     * @throws ResourceNotFoundException if player not found
     * @throws DatabaseException if database operation fails
     */
    void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * Add trophies to player
     * @param playerId Player ID
     * @param trophies Number of trophies to add
     * @throws ResourceNotFoundException if player not found
     * @throws DatabaseException if database operation fails
     * @throws InvalidInputException if trophies amount is invalid
     */
    void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException, InvalidInputException;
}