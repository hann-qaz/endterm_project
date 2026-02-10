package com.clashroyale.api.repository.interfaces;

import com.clashroyale.api.exception.DatabaseException;
import com.clashroyale.api.exception.ResourceNotFoundException;
import java.util.List;

/**
 * GENERIC CRUD REPOSITORY INTERFACE
 * Demonstrates:
 *   - GENERICS: Type parameter T for any entity
 *   - ISP (Interface Segregation Principle): Focused interface with only CRUD operations
 *   - DIP (Dependency Inversion Principle): High-level modules depend on this abstraction
 *   - OCP (Open/Closed Principle): Open for extension (new implementations), closed for modification
 *
 * @param <T> The entity type (Card, Player, etc.)
 */
public interface CrudRepository<T> {

    /**
     * Create a new entity in the database
     * @param entity Entity to create
     * @throws DatabaseException if database operation fails
     */
    void create(T entity) throws DatabaseException;

    /**
     * Retrieve all entities from the database
     * @return List of all entities
     * @throws DatabaseException if database operation fails
     */
    List<T> getAll() throws DatabaseException;

    /**
     * Retrieve entity by ID
     * @param id Entity ID
     * @return Entity with the given ID
     * @throws ResourceNotFoundException if entity not found
     * @throws DatabaseException if database operation fails
     */
    T getById(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * Update existing entity
     * @param id Entity ID to update
     * @param entity Updated entity data
     * @throws DatabaseException if database operation fails
     * @throws ResourceNotFoundException if entity not found
     */
    void update(int id, T entity) throws DatabaseException, ResourceNotFoundException;

    /**
     * Delete entity by ID
     * @param id Entity ID to delete
     * @throws ResourceNotFoundException if entity not found
     * @throws DatabaseException if database operation fails
     */
    void delete(int id) throws ResourceNotFoundException, DatabaseException;

    /**
     * DEFAULT METHOD - Count all entities
     * Demonstrates: Interface default methods (Java 8+)
     * Provides a default implementation that can be overridden
     *
     * @return Total count of entities
     * @throws DatabaseException if database operation fails
     */
    default int count() throws DatabaseException {
        return getAll().size();
    }

    /**
     * STATIC METHOD - Validate ID
     * Demonstrates: Interface static methods (Java 8+)
     * Utility method that doesn't require instance
     *
     * @param id ID to validate
     * @return true if ID is valid (positive), false otherwise
     */
    static boolean isValidId(int id) {
        return id > 0;
    }

    /**
     * DEFAULT METHOD - Check if entity exists
     * @param id Entity ID
     * @return true if entity exists, false otherwise
     */
    default boolean exists(int id) {
        try {
            getById(id);
            return true;
        } catch (ResourceNotFoundException | DatabaseException e) {
            return false;
        }
    }
}