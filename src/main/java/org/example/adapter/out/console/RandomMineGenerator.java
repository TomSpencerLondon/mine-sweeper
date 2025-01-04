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
        int row = random.nextInt(1, gridSize.totalRows());
        int column = random.nextInt(1, gridSize.totalColumns());
        return new Coordinate(column, row, gridSize);
    }
}