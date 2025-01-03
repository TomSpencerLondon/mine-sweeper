package org.example.hexagon.domain;

public class Cell {
    private State state;
    private Visibility visibility;

    public Cell(State state, Visibility visibility) {
        this.state = state;
        this.visibility = visibility;
    }
}
