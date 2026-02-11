package com.clashroyale.api.controller;

import com.clashroyale.api.dto.PlayerRequest;
import com.clashroyale.api.dto.PlayerResponse;
import com.clashroyale.api.model.Player;
import com.clashroyale.api.patterns.builder.PlayerBuilder;
import com.clashroyale.api.service.interfaces.PlayerServiceInterface;
import com.clashroyale.api.exception.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


 //REST controller for player operations

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerServiceInterface playerService;

    @Autowired
    public PlayerController(PlayerServiceInterface playerService) {
        this.playerService = playerService;
    }

    // GET /api/players - Get all players
    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers() throws DatabaseException {
        List<Player> players = playerService.getAllPlayers();
        List<PlayerResponse> response = players.stream()
                .map(PlayerResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // GET /api/players/{id} - Get player by ID
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable int id)
            throws ResourceNotFoundException, DatabaseException {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(PlayerResponse.fromEntity(player));
    }

     // POST /api/players - Create new player
     // Uses Builder Pattern for flexible construction
    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest request)
            throws InvalidInputException, DatabaseException {

        // BUILDER PATTERN in action
        Player player = PlayerBuilder.builder()
                .name(request.getName())
                .level(request.getLevel() != null ? request.getLevel() : 1)
                .trophies(request.getTrophies() != null ? request.getTrophies() : 0)
                .build();

        playerService.createPlayer(player);
        return new ResponseEntity<>(PlayerResponse.fromEntity(player), HttpStatus.CREATED);
    }

     // PUT /api/players/{id} - Update player
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(
            @PathVariable int id,
            @Valid @RequestBody PlayerRequest request)
            throws InvalidInputException, ResourceNotFoundException, DatabaseException {

        Player player = PlayerBuilder.builder()
                .id(id)
                .name(request.getName())
                .level(request.getLevel())
                .trophies(request.getTrophies())
                .build();

        playerService.updatePlayer(id, player);
        return ResponseEntity.ok(PlayerResponse.fromEntity(player));
    }


    // DELETE /api/players/{id} - Delete player
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable int id)
            throws ResourceNotFoundException, DatabaseException {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

     // POST /api/players/{id}/trophies - Add trophies to player
    @PostMapping("/{id}/trophies")
    public ResponseEntity<PlayerResponse> addTrophies(
            @PathVariable int id,
            @RequestParam int amount)
            throws ResourceNotFoundException, DatabaseException, InvalidInputException {

        playerService.addTrophies(id, amount);
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(PlayerResponse.fromEntity(player));
    }
}