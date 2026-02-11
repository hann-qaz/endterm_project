package com.clashroyale.api.repository;

import com.clashroyale.api.model.Player;
import com.clashroyale.api.repository.interfaces.CrudRepository;
import com.clashroyale.api.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

 // REPOSITORY LAYER - database operations for players
 // Demonstrates: SRP (only database operations, no business logic) DIP (implements CrudRepository interface) and SRP (only database operations, no business logic)
@Repository
public class PlayerRepository implements CrudRepository<Player> {

    private final DataSource dataSource;

    // Spring automatically wires DataSource from application.properties
    @Autowired
    public PlayerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Player player) throws DatabaseException {
        String sql = "INSERT INTO players (name, level, trophies) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getLevel());
            stmt.setInt(3, player.getTrophies());

            stmt.executeUpdate();

            // get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                player.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            // handle duplicate key constraint violation
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                throw new DatabaseException("Player with name " + player.getName() + " already exists", e);
            }
            throw new DatabaseException("Failed to create player: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Player> getAll() throws DatabaseException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM players ORDER BY trophies DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                players.add(mapResultSetToPlayer(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all players: " + e.getMessage(), e);
        }

        return players;
    }

    @Override
    public Player getById(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "SELECT * FROM players WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPlayer(rs);
            } else {
                throw new ResourceNotFoundException("Player with id: " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get player by id: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(int id, Player player) throws DatabaseException, ResourceNotFoundException {
        String sql = "UPDATE players SET name = ?, level = ?, trophies = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getName());
            stmt.setInt(2, player.getLevel());
            stmt.setInt(3, player.getTrophies());
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Player with id " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update player: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "DELETE FROM players WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Player with id " + id + " doesn't exist");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete player: " + e.getMessage(), e);
        }
    }

    //Helper method to map ResultSet to Player object
    private Player mapResultSetToPlayer(ResultSet rs) throws SQLException {
        return new Player(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("level"),
                rs.getInt("trophies")
        );
    }
}