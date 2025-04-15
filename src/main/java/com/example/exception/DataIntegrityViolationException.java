package com.example.exception;

/**
 * DataIntegrityViolationException is a class that represents a runtime exception for when an operation violates
 * data integrity in a database.
 * @author Broden Horton
 */
public class DataIntegrityViolationException extends RuntimeException {
    /**
     * Parameterized constructor that takes in and sets an exception message.
     * @param message An exception message.
     */
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}