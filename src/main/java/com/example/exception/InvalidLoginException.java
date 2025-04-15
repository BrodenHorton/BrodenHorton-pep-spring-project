package com.example.exception;

/**
 * InvalidLoginException is a class that represents a runtime exception for when the login credentials
 * of a user are invalid.
 * @author Broden Horton
 */
public class InvalidLoginException extends RuntimeException {

    /**
     * Parameterized constructor that takes in and sets an exception message.
     * @param message An exception message.
     */
    public InvalidLoginException(String message) {
        super(message);
    }

}