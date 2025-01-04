package org.example.hexagon.domain;

import org.example.hexagon.application.port.MineGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Grid {
    Logger logger = LoggerFactory.getLogger(Grid.class);
    private final MineGenerator mineGenerator;
    private final GridSize gridSize;
    private List<Cell> cells;
    List<Coordinate> mines;

    Map<Coordinate, Integer> neighbours;
    int totalMines;

    public Grid(int totalMines, MineGenerator mineGenerator, GridSize gridSize) {
        this.totalMines = totalMines;
        this.mineGenerator = mineGenerator;
        this.gridSize = gridSize;
        this.mines = createMines();
        this.neighbours = createNeighbours();
        this.cells = createCells();
    }
    public List<Cell> cells() {
        return cells;
    }

    public List<Coordinate> mines() {
        return mines;
    }

    private List<Cell> createCells() {
        List<Cell> result = new ArrayList<>();
        for (Map.Entry<Coordinate, Integer> entry : neighbours.entrySet()) {
            result.add(new Cell(entry.getKey(), CellType.NEIGHBOUR, entry.getValue(), Visibility.HIDDEN));
        }

        for (Coordinate mine : mines) {
            result.add(new Cell(mine, CellType.MINE, Visibility.HIDDEN));
        }

        for (int row = 1; row <= gridSize.totalRows(); row++) {
            for (int col = 1; col <= gridSize.totalColumns(); col++) {

                try {
                    Coordinate coordinate = new Coordinate(row, col, gridSize);
                    Cell cell = new Cell(coordinate, CellType.EMPTY, 0, Visibility.HIDDEN);
                    if (!result.contains(cell)) {
                        result.add(cell);
                    }

                } catch (InvalidCoordinateException e) {
                    logger.info(e.getMessage());
                }

            }
        }

        return result.stream()
                .sorted(Comparator.comparingInt(Cell::row).thenComparingInt(Cell::col))
                .toList();
    }

    private Map<Coordinate, Integer> createNeighbours() {
        Map<Coordinate, Integer> result = new HashMap<>();

        for (Coordinate mine : mines) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    try {
                        int newRow = mine.row() + i;
                        int newCol = mine.column() + j;
                        Coordinate coordinate = new Coordinate(newRow, newCol, gridSize);
                        if (!mines.contains(coordinate)) {
                            result.put(coordinate, result.getOrDefault(coordinate, 0) + 1);
                        }

                    } catch (InvalidCoordinateException e) {
                        logger.info(e.getMessage());
                    }

                }
            }
        }

        return result;
    }

    private List<Coordinate> createMines() {
        Set<Coordinate> result = new HashSet<>();
        while (result.size() < totalMines) {

            try {
                Coordinate mine = mineGenerator.next();
                result.add(mine);
            } catch (InvalidCoordinateException e) {
                logger.info(e.getMessage());
            }
        }
        return new ArrayList<>(result);
    }

    public boolean hasWon() {
        for (Cell cell : cells) {
            if (cell.isEmpty() && cell.isMarked()) {
                return false;
            }
            if (cell.isMine() && !cell.isMarked()) {
                return false;
            }
        }



        return true;
    }

    public void mark(Coordinate coordinate) {
        cells.stream().filter(cell -> cell.coordinate().equals(coordinate))
                .findFirst()
                .ifPresent(Cell::mark);
    }
}