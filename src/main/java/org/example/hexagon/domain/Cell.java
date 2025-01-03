package org.example.hexagon.domain;

public class Cell {
    private State state;
    private Visibility visibility;
    private final int adjacentMines;

    public Cell(int adjacentMines, State state, Visibility visibility) {
        this.state = state;
        this.visibility = visibility;
        this.adjacentMines = adjacentMines;
    }
}
