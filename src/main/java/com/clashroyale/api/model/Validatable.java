package com.clashroyale.api.model;

import com.clashroyale.api.exception.InvalidInputException;

/**
 * VALIDATABLE INTERFACE
 * Demonstrates: Interface Segregation Principle (ISP)
 * Focused interface for entities that require validation
 */
public interface Validatable {

    /**
     * Validate entity data
     * @throws InvalidInputException if validation fails
     */
    void validate() throws InvalidInputException;
}