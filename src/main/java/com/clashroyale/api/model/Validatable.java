package com.clashroyale.api.model;

import com.clashroyale.api.exception.InvalidInputException;

//ISP - interface focused on validation entities
public interface Validatable {
    void validate() throws InvalidInputException;
}