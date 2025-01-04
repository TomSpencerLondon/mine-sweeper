package org.example.hexagon.domain;

import org.example.adapter.out.console.CoordinateValidator;
import org.example.hexagon.application.port.MineGenerator;

import java.util.*;

public class Grid {
    private final MineGenerator mineGenerator;
    private final CoordinateValidator validator;
    private List<Cell> cells;
    List<Coordinate> mines;

    Map<Coordinate, Integer> neighbours;
    int totalMines;

    public Grid(int totalMines, MineGenerator mineGenerator, CoordinateValidator validator) {
        this.totalMines = totalMines;
        this.mineGenerator = mineGenerator;
        this.validator = validator;
        this.mines = createMines();
        this.neighbours = createNeighbours();
        this.cells = createCells();
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

        for (int row = 1; row <= rowSize(); row++) {
            for (int col = 1; col <= columnSize(); col++) {
                Cell cell = new Cell(new Coordinate(row, col), CellType.EMPTY, 0, Visibility.HIDDEN);

                if (validator.isValid(cell.coordinate()) && !result.contains(cell)) {
                    result.add(cell);
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
                    int newRow = mine.row() + i;
                    int newCol = mine.column() + j;
                    Coordinate coordinate = new Coordinate(newRow, newCol);
                    if (validator.isValid(coordinate) && !mines.contains(coordinate)) {
                        result.put(coordinate, result.getOrDefault(coordinate, 0) + 1);
                    }
                }
            }
        }

        return result;
    }

    private List<Coordinate> createMines() {
        Set<Coordinate> result = new HashSet<>();
        while (result.size() < totalMines) {
            Coordinate mine = mineGenerator.next();
            if (validator.isValid(mine)) {
                result.add(mine);
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