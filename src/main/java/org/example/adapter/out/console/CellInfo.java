package org.example.adapter.out.console;

import org.example.hexagon.domain.Cell;

public class CellInfo {
    String display;

    CellInfo(String display) {
        this.display = display;
    }

    public static CellInfo from(Cell cell) {
        if (cell.isMine()) {
            return createMineCellInfoFrom(cell);
        } else if (cell.isNeighbour()) {

            if (cell.isMarked()) {
                return new CellInfo("*");
            }

            return new CellInfo(
                    cell.isRevealed() ?
                            Integer.toString(cell.neighbourCount()) :
                            ".");
        }

        if (cell.isRevealed()) {
            return new CellInfo("/");
        }

        return new CellInfo(cell.isRevealed() || cell.isMarked() ? "*" : ".");
    }

    private static CellInfo createMineCellInfoFrom(Cell cell) {
        if (cell.isRevealed()) {
            return new CellInfo("X");
        }

        return new CellInfo(cell.isMarked() ? "*" : ".");
    }

    public String display() {
        return display;
    }
}
