package org.example.hexagon.domain;

public class Cell {
    private final Coordinate coordinate;
    private CellType cellType;
    private int neighbouringMines;
    private Visibility visibility;

    public Cell(Coordinate coordinate, CellType cellType) {
        this.coordinate = coordinate;
        this.cellType = cellType;
        this.visibility = Visibility.HIDDEN;
    }

    public static Cell createNeighbour(Coordinate coordinate, int neighbouringMines) {
        return new Cell(coordinate, CellType.NEIGHBOUR, neighbouringMines);
    }

    private Cell(Coordinate coordinate, CellType cellType, int neighbouringMines) {
        this.coordinate = coordinate;
        this.cellType = cellType;
        this.neighbouringMines = neighbouringMines;
        this.visibility = Visibility.HIDDEN;
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
        return neighbouringMines;
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

    public void unMark() {
        visibility = Visibility.HIDDEN;
    }

    public void mark() {
        if (visibility == Visibility.REVEALED)  {
            return;
        }

        visibility = this.visibility == Visibility.MARKED ?
                Visibility.HIDDEN :
                Visibility.MARKED;
    }

    public void reveal() {
        visibility = Visibility.REVEALED;
    }

    public boolean isEmpty() {
        return cellType == CellType.EMPTY;
    }

    public boolean isRevealed() {
        return visibility == Visibility.REVEALED;
    }

    public boolean isHidden() {
        return visibility == Visibility.HIDDEN;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "coordinate=" + coordinate +
                ", cellType=" + cellType +
                ", neighbouringMines=" + neighbouringMines +
                ", visibility=" + visibility +
                '}';
    }
}
