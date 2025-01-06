package org.example.hexagon.application;


import org.example.hexagon.domain.Cell;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;
import org.example.hexagon.domain.InvalidSelectionException;

import java.util.List;

public class MineSweeperService {
    private final Grid grid;

    public MineSweeperService(Grid grid) {
        this.grid = grid;
    }

    public List<Cell> cells() {
        return grid.cells();
    }

    public boolean hasWon() {
        return grid.hasWon();
    }

    public void reveal(Coordinate coordinate) {
        grid.reveal(coordinate);
    }

    public void mark(Coordinate coordinate) {
        grid.cells().forEach(cell -> {
            if (cell.coordinate().equals(coordinate) && cell.isNeighbour()) {
                throw new InvalidSelectionException(
                        String.format("Invalid selection at coordinate %d,%d. Cannot select a neighbour",
                                coordinate.column(), coordinate.row())
                );
            }
        });

        grid.mark(coordinate);
    }
}
