package org.example.hexagon.application.port;

import org.example.hexagon.domain.Coordinate;

import java.util.List;

public interface MineGenerator {
    Coordinate next();
}
