package com.clashroyale.api.repository;

import com.clashroyale.api.model.*;
import com.clashroyale.api.repository.interfaces.CrudRepository;
import com.clashroyale.api.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//demonstrates 1)SRP: do only database operations, no business logic
// 2)DIP: implemet interface
// 3)Polymorphism: uses Warrior, Spell, Building card types
// 4)Spring Integration: uses @Repository and DataSource

@Repository
public class CardRepository implements CrudRepository<Card> {

    private final DataSource dataSource;


    @Autowired
    public CardRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Card card) throws DatabaseException {
        String sql = "INSERT INTO cards (name, card_type, rarity, elixir_cost, level, damage, hp, radius, lifetime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());

            // POLYMORPHISM: Set specific attributes based on card type
            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHp());
                stmt.setInt(8, 0);  // No radius
                stmt.setInt(9, 0);  // No lifetime
            } else if (card instanceof SpellCard) {
                SpellCard spell = (SpellCard) card;
                stmt.setInt(6, spell.getDamage());
                stmt.setInt(7, 0);  // No HP
                stmt.setInt(8, spell.getRadius());
                stmt.setInt(9, 0);  // No lifetime
            } else if (card instanceof BuildingCard) {
                BuildingCard building = (BuildingCard) card;
                stmt.setInt(6, 0);  // No damage
                stmt.setInt(7, building.getHp());
                stmt.setInt(8, 0);  // No radius
                stmt.setInt(9, building.getLifetime());
            }

            stmt.executeUpdate();

            // Retrieve generated ID and set it on the card object
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                card.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create card: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Card> getAll() throws DatabaseException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards ORDER BY level DESC, name ASC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Card card = mapResultSetToCard(rs);
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all cards: " + e.getMessage(), e);
        }

        return cards;
    }

    @Override
    public Card getById(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "SELECT * FROM cards WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCard(rs);
            } else {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get card by id: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(int id, Card card) throws DatabaseException, ResourceNotFoundException {
        String sql = "UPDATE cards SET name = ?, card_type = ?, rarity = ?, elixir_cost = ?, level = ?, " +
                "damage = ?, hp = ?, radius = ?, lifetime = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, card.getName());
            stmt.setString(2, card.getType());
            stmt.setString(3, card.getRarity());
            stmt.setInt(4, card.getElixirCost());
            stmt.setInt(5, card.getLevel());

            // polymorphism: Handle different card types
            if (card instanceof WarriorCard) {
                WarriorCard warrior = (WarriorCard) card;
                stmt.setInt(6, warrior.getDamage());
                stmt.setInt(7, warrior.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, 0);
            } else if (card instanceof SpellCard) {
                SpellCard spell = (SpellCard) card;
                stmt.setInt(6, spell.getDamage());
                stmt.setInt(7, 0);
                stmt.setInt(8, spell.getRadius());
                stmt.setInt(9, 0);
            } else if (card instanceof BuildingCard) {
                BuildingCard building = (BuildingCard) card;
                stmt.setInt(6, 0);
                stmt.setInt(7, building.getHp());
                stmt.setInt(8, 0);
                stmt.setInt(9, building.getLifetime());
            }

            stmt.setInt(10, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update card: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws ResourceNotFoundException, DatabaseException {
        String sql = "DELETE FROM cards WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new ResourceNotFoundException("Card with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete card: " + e.getMessage(), e);
        }
    }

    public List<Card> getByType(String type) throws DatabaseException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE (card_type) = (?) ORDER BY name DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cards.add(mapResultSetToCard(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get cards by type: " + e.getMessage(), e);
        }

        return cards;
    }

    public List<Card> getByRarity(String rarity) throws DatabaseException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE UPPER(rarity) = UPPER(?) ORDER BY level DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rarity);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cards.add(mapResultSetToCard(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get cards by rarity: " + e.getMessage(), e);
        }

        return cards;
    }

    //method to map ResultSet to appropriate Card subclass.  demonstrates factory and polymorphism

    private Card mapResultSetToCard(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String cardType = rs.getString("card_type");
        String rarity = rs.getString("rarity");
        int elixirCost = rs.getInt("elixir_cost");
        int level = rs.getInt("level");
        int damage = rs.getInt("damage");
        int hp = rs.getInt("hp");
        int radius = rs.getInt("radius");
        int lifetime = rs.getInt("lifetime");

        // polymorphism Create appropriate subclass based on card type
        Card card;

        if ("WARRIOR".equals(cardType)) {
            card = new WarriorCard(id, name, rarity, elixirCost, level, hp, damage);
        } else if ("SPELL".equalsIgnoreCase(cardType)) {
            card = new SpellCard(id, name, rarity, elixirCost, level, radius, damage);
        } else if ("BUILDING".equalsIgnoreCase(cardType)) {
            card = new BuildingCard(id, name, rarity, elixirCost, level, hp, lifetime);
        } else {
            throw new SQLException("Wrong card type: " + cardType);
        }

        return card;
    }
}