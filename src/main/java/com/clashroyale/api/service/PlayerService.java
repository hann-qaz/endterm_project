package com.clashroyale.api.service;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.repository.interfaces.CrudRepository;
import com.clashroyale.api.service.interfaces.PlayerServiceInterface;
import com.clashroyale.api.exception.*;
import com.clashroyale.api.patterns.singleton.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

//business logic for players.  demonstrates SRP and DIP
@Service
public class PlayerService implements PlayerServiceInterface {

    private final CrudRepository<Player> playerRepository;
    private final LoggerService logger = LoggerService.getInstance();

    // Demonstrates DIP High-level module Service don't depend on low-level modules Repository
    // Spring pass implementation of CrudRepository automatically
    @Autowired
    public PlayerService(CrudRepository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    //create player with validation and logging
    @Override
    public void createPlayer(Player player) throws InvalidInputException, DatabaseException {
        logger.info("Creating player... ");

        player.validate(); // business rule: validate before creating
        playerRepository.create(player); //delegate to repository for database operation

        logger.info(" Player created: " + player.getName());
    }

    //list all players with logging
    @Override
    public List<Player> getAllPlayers() throws DatabaseException {
        logger.info("Showing all players...");
        return playerRepository.getAll();
    }

    @Override
    public Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("Showing player with ID: " + id);
        return playerRepository.getById(id);
    }

    @Override
    public void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException {
        logger.info("updating player...");

        player.validate(); // business rule: validate before updating
        playerRepository.update(id, player);

        logger.info(" Player updated: " + player.getName());
    }

    @Override
    public void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException {
        logger.info("deleting player...");
        playerRepository.delete(id);
        logger.info("Player deleted with ID: " + id);
    }

    @Override
    public void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException {
        logger.info("Adding trophies...");

        Player player = playerRepository.getById(playerId);
        player.setTrophies(player.getTrophies() + trophies);
        playerRepository.update(playerId, player);

        logger.info(" Added " + trophies + " trophies to " + player.getName() +
                " Total: " + player.getTrophies());
    }
}