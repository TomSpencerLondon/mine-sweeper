package org.example.hexagon.domain;

import java.util.Objects;

public final class Coordinate {
    private final int column;
    private final int row;

    public Coordinate(int column, int row, GridSize gridSize) {
        if (!isValid(gridSize, column, row)) {
            throw new InvalidCoordinateException(
                    String.format("Invalid coordinates provided: %d %d. For grid totalRows: %d, totalColumns: %d",
                            column, row, gridSize.totalRows(), gridSize.totalColumns()));
        }

        this.column = column;
        this.row = row;
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

        if (column != that.column) return false;
        return row == that.row;
    }

    public int column() {
        return column;
    }

    public int row() {
        return row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Coordinate[" +
                "row=" + row + ", " +
                "column=" + column + ']';
    }


}
