package org.example.hexagon.domain;

public class InvalidCoordinateException extends RuntimeException {
    public InvalidCoordinateException(String message) {
        super(message);
    }
}
