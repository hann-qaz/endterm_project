package com.clashroyale.api.exception;

public class DuplicateResourceException extends DatabaseException {
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateResourceException(String message) {
        super(message, null);
    }
}