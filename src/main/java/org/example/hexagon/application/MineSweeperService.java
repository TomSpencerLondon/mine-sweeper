package org.example.hexagon.application;


import org.example.hexagon.domain.*;

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
        grid.mark(coordinate);
    }

    public void firstReveal(Coordinate coordinate) {
        grid.firstReveal(coordinate);
    }
}
