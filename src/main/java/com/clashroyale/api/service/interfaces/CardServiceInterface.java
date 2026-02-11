package com.clashroyale.api.service.interfaces;

import com.clashroyale.api.model.Card;
import com.clashroyale.api.exception.*;
import java.util.List;

//demonstrates ISP and DIP
public interface CardServiceInterface {

    //create a new card, throws InvalidInputException if card data is wrong
    // throws DatabaseException if database operation fails
    void createCard(Card card) throws InvalidInputException, DatabaseException;

    //lists all cards
    List<Card> getAllCards() throws DatabaseException;

    //list card by id, throws ResourceNotFoundException if card not found
    Card getCardById(int id) throws ResourceNotFoundException, DatabaseException;

    //list cards by type
    List<Card> getCardsByType(String type) throws DatabaseException;

    //list cards by rarity
    List<Card> getCardsByRarity(String rarity) throws DatabaseException;

    //update card by id
    void updateCard(int id, Card card) throws InvalidInputException, ResourceNotFoundException, DatabaseException;

    //upgrade card by id
    void upgradeCard(int id) throws ResourceNotFoundException, DatabaseException, InvalidInputException;

    //delete card by id
    void deleteCard(int id) throws ResourceNotFoundException, DatabaseException;
}