package com.clashroyale.api.service.interfaces;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.exception.*;
import java.util.List;

//demonstrates ISP and DIP
public interface PlayerServiceInterface {

    //create a new player, throws InvalidInputException if player data is wrong
    // throws DatabaseException if database operation fails
    void createPlayer(Player player) throws InvalidInputException, DatabaseException;

    //lists all players
    List<Player> getAllPlayers() throws DatabaseException;

    //list player by id, throws ResourceNotFoundException if player not found
    Player getPlayerById(int id) throws ResourceNotFoundException, DatabaseException;

    //update player by id
    void updatePlayer(int id, Player player) throws InvalidInputException, ResourceNotFoundException, DatabaseException;

    //delete player by id
    void deletePlayer(int id) throws ResourceNotFoundException, DatabaseException;

    //add trophies to player
    void addTrophies(int playerId, int trophies) throws ResourceNotFoundException, DatabaseException;
}