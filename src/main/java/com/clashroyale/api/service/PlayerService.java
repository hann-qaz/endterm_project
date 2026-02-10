package com.clashroyale.api.service;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.repository.interfaces.CrudRepository;
import com.clashroyale.api.service.interfaces.PlayerServiceInterface;
import com.clashroyale.api.exception.*;
import com.clashroyale.api.patterns.singleton.LoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SERVICE LAYER - Business logic for players
 * Demonstrates: SRP (Single Responsibility - only business logic)
 *              DIP (Dependency Inversion - depends on CrudRepository interface)
 */
@Service
public class PlayerService implements PlayerServiceInterface {

    private final CrudRepository<Player> playerRepository;
    private final LoggerService logger = LoggerService.getInstance();

    /**
     * Constructor injection - DIP in action
     * Spring automatically injects PlayerRepository implementation
     */
    @Autowired
    public PlayerService(CrudRepository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void createPlayer(Player player) throws InvalidInputException, DatabaseException {
        logger.info("Creating player: " + player.getName());
        player.validate(); // Business rule: validate before saving
        playerRepository.create(player);
        logger.info(" Player created: " + player.getName());
    }

    @Override
    public List<Player> getAllPlayers() throws DatabaseException {
        logger.info("Fetching all players");
        return playerRepository.getAll();
    }

    @Override
    public Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Fetching player with ID: " + id);
        return playerRepository.getById(id);
    }

    @Override
    public void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        logger.info("Updating player with ID: " + id);
        player.validate(); // Business rule: validate before updating
        playerRepository.update(id, player);
        logger.info(" Player updated: " + player.getName());
    }

    @Override
    public void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Deleting player with ID: " + id);
        playerRepository.delete(id);
        logger.info(" Player deleted with ID: " + id);
    }

    @Override
    public void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        logger.info("Adding " + trophies + " trophies to player ID: " + playerId);

        // Business rule: cannot add negative trophies
        if (trophies < 0) {
            throw new InvalidInputException("Cannot add negative trophies");
        }

        Player player = playerRepository.getById(playerId);
        player.setTrophies(player.getTrophies() + trophies);
        playerRepository.update(playerId, player);

        logger.info(" Added " + trophies + " trophies to " + player.getName() +
                " (Total: " + player.getTrophies() + ")");
    }
}