package org.example.hexagon.domain;

import org.example.hexagon.application.port.MineGenerator;

import java.util.*;

public class Grid {
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
        return cells.stream()
                .sorted(Comparator.comparingInt(Cell::row).thenComparingInt(Cell::col))
                .toList();
    }

    public List<Coordinate> mines() {
        return mines;
    }

    private List<Cell> createCells() {
        List<Cell> result = new ArrayList<>();

        for (Map.Entry<Coordinate, Integer> entry : neighbours.entrySet()) {
            result.add(Cell.createNeighbour(entry.getKey(), entry.getValue()));
        }

        for (Coordinate mine : mines) {
            result.add(new Cell(mine, CellType.MINE));
        }

        for (int column = 1; column <= gridSize.totalRows(); column++) {
            for (int row = 1; row <= gridSize.totalColumns(); row++) {

                try {
                    Coordinate coordinate = new Coordinate(column, row, gridSize);
                    Cell cell = new Cell(coordinate, CellType.EMPTY);
                    if (!result.contains(cell)) {
                        result.add(cell);
                    }

                } catch (InvalidCoordinateException e) {
                }

            }
        }

        return result;
    }

    private Map<Coordinate, Integer> createNeighbours() {
        Map<Coordinate, Integer> result = new HashMap<>();

        for (Coordinate mine : mines) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    try {
                        int newColumn = mine.column() + i;
                        int newRow = mine.row() + j;
                        Coordinate coordinate = new Coordinate(newColumn, newRow, gridSize);
                        if (!mines.contains(coordinate)) {
                            result.put(coordinate, result.getOrDefault(coordinate, 0) + 1);
                        }

                    } catch (InvalidCoordinateException e) {
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
            }
        }
        return new ArrayList<>(result);
    }

    public boolean hasWon() {
        return allMinesAreMarked() ||
                allNonMinesAreRevealed();
    }

    private boolean allMinesAreMarked() {
        return cells.stream()
                .filter(Cell::isMine)
                .allMatch(Cell::isMarked);
    }

    private boolean allNonMinesAreRevealed() {
        return cells.stream()
                .filter(cell -> !cell.isMine())
                .allMatch(Cell::isRevealed);
    }

    public void mark(Coordinate coordinate) {
        cellFor(coordinate)
                .ifPresent(Cell::mark);
    }

    public void reveal(Coordinate coordinate) {
        cellFor(coordinate)
                .ifPresent(this::revealCellFor);
    }

    public void firstReveal(Coordinate coordinate) {
        mines = createMines(coordinate);
        neighbours = createNeighbours();
        cells = createCells();
        reveal(coordinate);
    }

    private List<Coordinate> createMines(Coordinate coordinate) {
        Set<Coordinate> result = new HashSet<>();
        while (result.size() < totalMines) {

            try {
                Coordinate mine = mineGenerator.next();
                if (coordinate.equals(mine)) {
                    throw new InvalidCoordinateException("");
                }

                result.add(mine);
            } catch (InvalidCoordinateException e) {
            }
        }
        return new ArrayList<>(result);
    }

    public void revealMines() {
        for (Cell cell : cells) {
            if (cell.isMine()) {
                cell.reveal();
            }
        }
    }

    private void revealCellFor(Cell cell) {
        if (cell.isMine()) {
            revealMines();
            throw new MineRevealedException(
                    String.format("Game Over. Mine revealed: %d %d",
                            cell.row(),
                            cell.col()));
        } else if (cell.isNeighbour()) {
            cell.reveal();
            return;
        }

        revealAllFrom(cell);
    }

    private void revealAllFrom(Cell cell) {
        Queue<Cell> queue = new LinkedList<>();

        Set<Cell> visited = new HashSet<>();

        queue.add(cell);
        visited.add(cell);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            if (!current.isMine() ||
                    cells.stream().filter(c -> !c.isMine())
                            .allMatch(Cell::isRevealed)
            ) {
                current.reveal();
            }

            List<Cell> surrounding = surrounding(current);

            for (Cell adjacent : surrounding) {
                if (!visited.contains(adjacent)) {
                    queue.add(adjacent);
                    visited.add(adjacent);
                }
            }
        }

    }

    List<Cell> surrounding(Cell current) {
        if (current.isNeighbour()) {
            return Collections.emptyList();
        }

        List<Cell> result = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                try {
                    int newColumn = current.col() + i;
                    int newRow = current.row() + j;
                    Coordinate coordinate = new Coordinate(newColumn, newRow, gridSize);

                    cells.stream().filter(c -> c.coordinate().equals(coordinate))
                            .findFirst()
                            .ifPresent(result::add);

                } catch (InvalidCoordinateException e) {
                }

            }
        }

        return result;
    }

    private Optional<Cell> cellFor(Coordinate coordinate) {
        return cells.stream().filter(cell -> cell.coordinate().equals(coordinate))
                .findFirst();
    }
}