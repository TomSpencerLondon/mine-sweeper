package org.example.hexagon.application.port;

import org.example.hexagon.domain.Coordinate;

public interface Validator {
    public boolean isValid(Coordinate coordinate);
}
