package org.example.adapter.out.console;

import org.example.hexagon.domain.Cell;

public class CellInfo {
    String display;

    CellInfo(String display) {
        this.display = display;
    }

    public static CellInfo from(Cell cell) {
        if (cell.isMine()) {
           return new CellInfo(cell.isMarked() ? "*" : ".");
        } else if (cell.isNeighbour()) {
            return new CellInfo(Integer.toString(cell.neighbourCount()));
        }

        return new CellInfo(".");
    }

    public String display() {
        return display;
    }
}
