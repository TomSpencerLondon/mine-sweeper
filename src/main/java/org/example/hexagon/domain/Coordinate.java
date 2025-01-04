package org.example.hexagon.domain;

import java.util.Objects;

public final class Coordinate {
    private final int row;
    private final int column;

    @Deprecated
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Coordinate(int row, int column, GridSize gridSize) {
        if (!isValid(gridSize, row, column)) {
            throw new InvalidCoordinateException(
                    String.format("Invalid coordinates provided: %d %d. For grid totalRows: %d, totalColumns: %d",
                    row, column, gridSize.totalRows(), gridSize.totalColumns()));
        }

        this.row = row;
        this.column = column;
    }

    private boolean isValid(GridSize gridSize, int row, int column) {
        return (row > 0 &&  row <= gridSize.totalRows()) &&
                (column > 0 && column <= gridSize.totalColumns());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (row != that.row) return false;
        return column == that.column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Coordinate[" +
                "row=" + row + ", " +
                "column=" + column + ']';
    }


}
