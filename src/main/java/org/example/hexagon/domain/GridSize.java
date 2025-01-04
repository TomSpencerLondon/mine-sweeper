package org.example.hexagon.domain;

public class GridSize {
    private final int totalRows;
    private final int totalColumns;

    public GridSize(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    public int totalRows() {
        return totalRows;
    }

    public int totalColumns() {
        return totalColumns;
    }
}
