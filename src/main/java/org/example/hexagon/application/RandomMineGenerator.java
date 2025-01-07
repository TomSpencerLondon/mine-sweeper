package org.example.hexagon.application;


import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.GridSize;

import java.util.Random;
import java.util.Set;

public class RandomMineGenerator implements MineGenerator {
    private final Random random;
    private final GridSize gridSize;

    public RandomMineGenerator(GridSize gridSize, Random random) {
        this.gridSize = gridSize;
        this.random = random;
    }

    public Coordinate next(Set<Coordinate> result) {
        int columnBound = gridSize.totalColumns() + 1;
        int rowBound = gridSize.totalRows() + 1;

        int column = random.nextInt(1, columnBound);
        int row = random.nextInt(1, rowBound);

        while (result.contains(new Coordinate(column, row, gridSize))) {
            column = random.nextInt(1, columnBound);
            row = random.nextInt(1, rowBound);
        }

        return new Coordinate(column, row, gridSize);
    }
}