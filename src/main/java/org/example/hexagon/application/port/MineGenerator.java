package org.example.hexagon.application.port;


import org.example.hexagon.domain.Coordinate;

import java.util.Set;

public interface MineGenerator {
    Coordinate next(Set<Coordinate> result);
}
