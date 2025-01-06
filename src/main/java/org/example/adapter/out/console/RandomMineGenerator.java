package org.example.adapter.out.console;

import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.GridSize;

import java.util.*;

public class RandomMineGenerator implements MineGenerator {
    private final Random random;
    private final GridSize gridSize;

    public RandomMineGenerator(GridSize gridSize, Random random) {
        this.gridSize = gridSize;
        this.random = random;
    }

    public Coordinate next() {
        int column = random.nextInt(1, gridSize.totalColumns());
        int row = random.nextInt(1, gridSize.totalRows());
        Coordinate coordinate = new Coordinate(column, row, gridSize);
        System.out.println("Next: " + coordinate.toString());
        return coordinate;
    }
}