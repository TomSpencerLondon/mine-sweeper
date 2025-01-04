package org.example.hexagon.domain;

public class Cell {
    private final Coordinate coordinate;
    private State state;
    private int neighbourCount;
    private final Visibility visibility;

    public Cell(Coordinate coordinate, State state, Visibility visibility) {
        this.coordinate = coordinate;
        this.state = state;
        this.visibility = visibility;
    }

    public Cell(Coordinate coordinate, State state, int neighbourCount, Visibility visibility) {
        this.coordinate = coordinate;
        this.state = state;
        this.neighbourCount = neighbourCount;
        this.visibility = visibility;
    }

    public Coordinate coordinate() {
        return coordinate;
    }

    public boolean isMine() {
        return state == State.MINE;
    }

    public boolean isNeighbour() {
        return state == State.NUMBER;
    }

    public int neighbourCount() {
        return neighbourCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return coordinate.equals(cell.coordinate);
    }

    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }

    public int row() {
        return coordinate.row();
    }

    public int col() {
        return coordinate.column();
    }
}
