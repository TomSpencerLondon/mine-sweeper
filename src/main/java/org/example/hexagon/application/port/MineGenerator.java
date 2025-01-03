package org.example.hexagon.application.port;

public interface MineGenerator {
    int numMines();

    int next(int seed);
}
