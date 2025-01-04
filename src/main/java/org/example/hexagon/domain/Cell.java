package org.example.hexagon.domain;

public class Cell {
    private final Coordinate coordinate;
    private CellType cellType;
    private int neighbourCount;
    private Visibility visibility;

    public Cell(Coordinate coordinate, CellType cellType, Visibility visibility) {
        this.coordinate = coordinate;
        this.cellType = cellType;
        this.visibility = visibility;
    }

    public Cell(Coordinate coordinate, CellType cellType, int neighbourCount, Visibility visibility) {
        this.coordinate = coordinate;
        this.cellType = cellType;
        this.neighbourCount = neighbourCount;
        this.visibility = visibility;
    }

    public Coordinate coordinate() {
        return coordinate;
    }

    public boolean isMine() {
        return cellType == CellType.MINE;
    }

    public boolean isMarked() {
        return visibility == Visibility.MARKED;
    }

    public boolean isNeighbour() {
        return cellType == CellType.NEIGHBOUR;
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

    public void mark() {
        visibility = Visibility.MARKED;
    }
}
