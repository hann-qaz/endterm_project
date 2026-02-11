package com.clashroyale.api.repository.interfaces;

import com.clashroyale.api.exception.DatabaseException;
import com.clashroyale.api.exception.ResourceNotFoundException;
import java.util.List;

/*demonstrates: 1)OCP: open for expanding, closed for modification
 2)ISP: focused interface with only CRUD operations
 3)DIP: high-level modules depend on abstraction
 4)GENERICS: parameter T if available for any entity
 <T> - some entity type (card, player, oth) */

public interface CrudRepository<T> {

    //create some entity in the database and trows exception if database operation fails
    void create(T entity) throws DatabaseException;

    //retrieves all entities from the database, shows them
    List<T> getAll() throws DatabaseException;


    //retrieve entity with some id and shows him and throws ResourceNotFoundException if entity not found
    T getById(int id) throws ResourceNotFoundException, DatabaseException;

    //updates entity with some id and shows him
    void update(int id, T entity) throws DatabaseException, ResourceNotFoundException;

    //delete entity with some id
    void delete(int id) throws ResourceNotFoundException, DatabaseException;
}