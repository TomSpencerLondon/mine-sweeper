package org.example.hexagon.application;


import org.example.hexagon.domain.Cell;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;

import java.util.List;

public class MineSweeperService {
    private final Grid grid;

    public MineSweeperService(Grid grid) {
        this.grid = grid;
    }

    public List<Cell> cells() {
        return grid.cells();
    }
}
