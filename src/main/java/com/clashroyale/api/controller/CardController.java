package com.clashroyale.api.controller;

import com.clashroyale.api.dto.CardRequest;
import com.clashroyale.api.dto.CardResponse;
import com.clashroyale.api.model.Card;
import com.clashroyale.api.patterns.factory.CardFactory;
import com.clashroyale.api.service.interfaces.CardServiceInterface;
import com.clashroyale.api.exception.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// REST Controller for managing Cards in Clash Royale API
@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*")
public class CardController {

    private final CardServiceInterface cardService;
    private final CardFactory cardFactory;

    @Autowired
    public CardController(CardServiceInterface cardService, CardFactory cardFactory) {
        this.cardService = cardService;
        this.cardFactory = cardFactory;
    }

   // GET /api/cards - get all cards
    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards() throws DatabaseException {
        List<Card> cards = cardService.getAllCards();
        List<CardResponse> response = cards.stream()
                .map(CardResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // GET /api/cards/{id} - get card by id
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable int id)
            throws ResourceNotFoundException, DatabaseException {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(CardResponse.fromEntity(card));
    }

     // POST /api/cards - Create new card
     // uses factory pattern to create appropriate card type
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request)
            throws InvalidInputException, DatabaseException {

        // factory pattern to create card based on type
        Card card = cardFactory.createCard(
                request.getType(),
                0,
                request.getName(),
                request.getRarity(),
                request.getElixirCost(),
                request.getLevel(),
                request.getHp(),
                request.getDamage(),
                request.getRadius(),
                request.getLifetime()
        );

        cardService.createCard(card);
        return new ResponseEntity<>(CardResponse.fromEntity(card), HttpStatus.CREATED);
    }

     // PUT /api/cards/{id} - update card

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable int id,
            @Valid @RequestBody CardRequest request)
            throws InvalidInputException, ResourceNotFoundException, DatabaseException {

        Card card = cardFactory.createCard(
                request.getType(),
                id,
                request.getName(),
                request.getRarity(),
                request.getElixirCost(),
                request.getLevel(),
                request.getHp(),
                request.getDamage(),
                request.getRadius(),
                request.getLifetime()
        );

        cardService.updateCard(id, card);
        return ResponseEntity.ok(CardResponse.fromEntity(card));
    }


     // DELETE /api/cards/{id} - delete card

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable int id)
            throws ResourceNotFoundException, DatabaseException {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }


     // PUT /api/cards/{id}/upgrade - upgrade card level

    @PutMapping("/{id}/upgrade")
    public ResponseEntity<CardResponse> upgradeCard(@PathVariable int id)
            throws ResourceNotFoundException, DatabaseException, InvalidInputException {
        cardService.upgradeCard(id);
        Card upgraded = cardService.getCardById(id);
        return ResponseEntity.ok(CardResponse.fromEntity(upgraded));
    }


    // GET /api/cards/type/{type} - get cards by type

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CardResponse>> getCardsByType(@PathVariable String type)
            throws DatabaseException {
        List<Card> cards = cardService.getAllCards().stream()
                .filter(card -> card.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());

        List<CardResponse> response = cards.stream()
                .map(CardResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}