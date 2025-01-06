package org.example.adapter.out.console;

import org.example.hexagon.domain.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CellInfoTest {

    @Test
    void givenRevealedMineShowsX() {
        Cell cell = new Cell(createCoordinate(1, 1), CellType.MINE);

        cell.reveal();

        CellInfo cellInfo = CellInfo.from(cell);
        assertThat(cellInfo.display())
                .isEqualTo("X");
    }

    @Test
    void givenHiddenUnmarkedMineShowsDot() {
        Cell cell = new Cell(createCoordinate(1, 1), CellType.MINE);

        CellInfo cellInfo = CellInfo.from(cell);

        assertThat(cellInfo.display())
                .isEqualTo(".");
    }

    @Test
    void givenHiddenMarkedMineShowsStar() {
        Cell cell = new Cell(createCoordinate(1, 1), CellType.MINE);
        cell.mark();
        CellInfo cellInfo = CellInfo.from(cell);

        assertThat(cellInfo.display())
                .isEqualTo("*");
    }

    @Test
    void givenHiddenNeighbourShowsDot() {
        Cell cell = Cell.createNeighbour(createCoordinate(1, 1), 2);

        CellInfo cellInfo = CellInfo.from(cell);

        assertThat(cellInfo.display())
                .isEqualTo(".");
    }

    @Test
    void givenRevealedNeighbourShowsNeighbouringMineCount() {
        Cell cell = Cell.createNeighbour(createCoordinate(1, 1), 2);
        cell.reveal();
        CellInfo cellInfo = CellInfo.from(cell);

        assertThat(cellInfo.display())
                .isEqualTo("2");
    }

    private static Coordinate createCoordinate(int column, int row) {
        return new Coordinate(column, row, new GridSize(9, 9));
    }
}
