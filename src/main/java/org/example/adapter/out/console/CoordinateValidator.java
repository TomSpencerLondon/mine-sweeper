package org.example.adapter.out.console;

import org.example.hexagon.application.port.Validator;
import org.example.hexagon.domain.Coordinate;

public class CoordinateValidator implements Validator {

    private final int totalRows;
    private final int totalColumns;

    public CoordinateValidator(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    @Override
    public boolean isValid(Coordinate coordinate) {
        return (coordinate.row() >= 0 && coordinate.row() < totalRows) ||
                (coordinate.column() >= 0 && coordinate.column() < totalColumns);
    }

    public int totalRows() {
        return totalRows;
    }

    public int totalColumns() {
        return totalColumns;
    }
}
