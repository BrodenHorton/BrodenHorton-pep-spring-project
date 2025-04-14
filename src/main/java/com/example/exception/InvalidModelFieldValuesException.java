package com.example.exception;

public class InvalidModelFieldValuesException extends RuntimeException {
    
    public InvalidModelFieldValuesException(String message) {
        super(message);
    }

}
