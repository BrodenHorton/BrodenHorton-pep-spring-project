package com.example.exception;

/**
 * InvalidModelFieldValuesException is a class that represents a runtime exception for when invalid model field
 * values are attempted to be added to a database.
 * @author Broden Horton
 */
public class InvalidModelFieldValuesException extends RuntimeException {
    
    /**
     * Parameterized constructor that takes in and sets an exception message.
     * @param message An exception message.
     */
    public InvalidModelFieldValuesException(String message) {
        super(message);
    }

}
