package org.example.hexagon.domain;

import org.example.adapter.out.console.CoordinateValidator;
import org.example.hexagon.application.port.MineGenerator;

import java.util.*;

public class Grid {
    private final MineGenerator mineGenerator;
    private final CoordinateValidator validator;
    private final List<Cell> cells;
    List<Coordinate> mines;
    Map<Coordinate, Integer> neighbours;
    int totalMines;

    public Grid(int totalMines, MineGenerator mineGenerator, CoordinateValidator validator) {
        this.totalMines = totalMines;
        this.mineGenerator = mineGenerator;
        this.validator = validator;
        this.mines = new ArrayList<>();
        this.neighbours = new HashMap<>();
        this.cells = new ArrayList<>();
        create();
    }

    public List<Cell> cells() {
        return cells;
    }

    public int rowSize() {
        return validator.totalRows();
    }

    public int columnSize() {
        return validator.totalColumns();
    }

    private void create() {
        for (int i = 0; i < totalMines; i++) {
            mines.add(mineGenerator.next());
        }

        for (Coordinate mine : mines) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = mine.row() + i;
                    int newCol = mine.column() + j;
                    Coordinate coordinate = new Coordinate(newRow, newCol);
                    if (validator.isValid(coordinate) && !mines.contains(coordinate)) {
                        neighbours.put(coordinate, neighbours.getOrDefault(coordinate, 0) + 1);
                    }
                }
            }
        }

        for (Coordinate mine : mines) {
            cells.add(new Cell(mine, State.MINE, Visibility.HIDDEN));
        }

        for (Map.Entry<Coordinate, Integer> entry : neighbours.entrySet()) {
            cells.add(new Cell(entry.getKey(), State.NUMBER, entry.getValue(), Visibility.HIDDEN));
        }


        for (int row = 0; row < rowSize(); row++) {
            for (int col = 0; col < columnSize(); col++) {
                Cell cell = new Cell(new Coordinate(row, col), State.EMPTY, 0, Visibility.HIDDEN);

                if (!cells.contains(cell)) {
                    cells.add(cell);
                }
            }
        }

        cells.sort(Comparator.comparingInt(Cell::row).thenComparingInt(Cell::col));

    }
}
