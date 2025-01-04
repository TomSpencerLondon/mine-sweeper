package org.example.adapter.out.console;

import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.domain.Coordinate;

import java.util.*;

public class RandomMineGenerator implements MineGenerator {
    private final int rows;
    private final int cols;
    private final Random random;

    public RandomMineGenerator(int rows, int cols, Random random) {
        this.rows = rows;
        this.cols = cols;
        this.random = random;
    }

    public Coordinate next() {
        int row = random.nextInt(1, rows);
        int column = random.nextInt(1, cols);
        return new Coordinate(row, column);
    }
}
