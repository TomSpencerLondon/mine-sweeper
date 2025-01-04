package org.example.hexagon.domain;

public class InvalidSelectionException extends RuntimeException{
    public InvalidSelectionException(String message) {
        super(message);
    }
}
