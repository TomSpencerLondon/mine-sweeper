package org.example.hexagon.domain;

import org.example.adapter.out.console.CellInfo;
import org.example.hexagon.application.port.MineGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GridTest {

    MineGenerator mineGenerator = mock(MineGenerator.class);

    Grid grid;
    GridSize gridSize;

    @BeforeEach
    void setUp() {
        gridSize = new GridSize(9, 9);
        when(mineGenerator.next())
                .thenReturn(createCoordinate(2, 5), createCoordinate(5, 1));
        grid = new Grid(2, mineGenerator, gridSize);
    }

    @Test
    void givenTwoMinesShowsCorrectNeighbourCount() {
        List<CellInfo> cellInfos = grid.cells()
                .stream()
                .map(CellInfo::from)
                .toList();

        Iterator<CellInfo> iterator = cellInfos.iterator();
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (iterator.hasNext()) {
                    System.out.print(iterator.next().display());
                }
            }
            System.out.println();
        }

    }

    @Test
    void givenFourMinesShowsCorrectNeighbourCount() {
        when(mineGenerator.next())
                .thenReturn(
                        createCoordinate(2, 1),
                        createCoordinate(5, 4),
                        createCoordinate(6, 1),
                        createCoordinate(6, 2)
                );
        grid = new Grid(4, mineGenerator, new GridSize(9, 9));


        List<CellInfo> cellInfos = grid.cells()
                .stream()
                .map(CellInfo::from)
                .toList();

        Iterator<CellInfo> iterator = cellInfos.iterator();
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (iterator.hasNext()) {
                    System.out.print(iterator.next().display());
                }
            }
            System.out.println();
        }
    }

    @Test
    void givenSevenMinesShouldReturnCorrectNeighbourCount() {
        when(mineGenerator.next())
                .thenReturn(
                        createCoordinate(2, 2),
                        createCoordinate(2, 4),
                        createCoordinate(2, 7),
                        createCoordinate(3, 1),
                        createCoordinate(3, 7),
                        createCoordinate(6, 6),
                        createCoordinate(6, 7)
                );
        grid = new Grid(7, mineGenerator, new GridSize(9, 9));


        List<CellInfo> cellInfos = grid.cells()
                .stream()
                .map(CellInfo::from)
                .toList();

        Iterator<CellInfo> iterator = cellInfos.iterator();
        System.out.println();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (iterator.hasNext()) {
                    System.out.print(iterator.next().display());
                }
            }
            System.out.println();
        }
    }

    @Test
    void givenOneMineIncorrectThenCorrectMarkingReturnsWin() {
        when(mineGenerator.next())
                .thenReturn(
                        createCoordinate(1, 2)
                );
        grid = new Grid(1, mineGenerator, new GridSize(9, 9));

        grid.mark(createCoordinate(4, 8));
        grid.mark(createCoordinate(1, 2));
        grid.mark(createCoordinate(4, 8));

        assertThat(grid.hasWon())
                .isTrue();
    }

    @Test
    void givenOneMineAndAllOtherCellsRevealedReturnsWin() {
        when(mineGenerator.next())
                .thenReturn(
                        createCoordinate(1, 2)
                );
        grid = new Grid(1, mineGenerator, new GridSize(3, 3));

        grid.reveal(createCoordinate(1, 1));
        grid.reveal(createCoordinate(1, 3));
        grid.reveal(createCoordinate(2, 1));
        grid.reveal(createCoordinate(2, 2));
        grid.reveal(createCoordinate(2, 3));
        grid.reveal(createCoordinate(3, 1));
        grid.reveal(createCoordinate(3, 2));
        grid.reveal(createCoordinate(3, 3));

        assertThat(grid.hasWon())
                .isTrue();
    }

    @Test
    void revealMineShouldThrowException() {
        Coordinate coordinate = createCoordinate(1, 2);
        when(mineGenerator.next())
                .thenReturn(
                        coordinate
                );

        grid = new Grid(1, mineGenerator, new GridSize(3, 3));

        assertThatThrownBy(() -> grid.reveal(coordinate))
                .isInstanceOf(MineRevealedException.class)
                .hasMessage("Game Over. Mine revealed: %d %d",
                        coordinate.row(), coordinate.column());
    }

    @Test
    void revealShouldOnlyShowNonMineCellsAndNotRevealEmptyCellsWithinMineNeighbours() {
        Coordinate mine1 = createCoordinate(7, 3);
        Coordinate mine2 = createCoordinate(2, 4);
        Coordinate mine5 = createCoordinate(2, 9);
        Coordinate mine4 = createCoordinate(7, 6);
        Coordinate mine3 = createCoordinate(9, 5);
        when(mineGenerator.next())
                .thenReturn(
                        mine1, mine2, mine3, mine4, mine5
                );

        grid = new Grid(5, mineGenerator, new GridSize(9, 9));

        grid.reveal(createCoordinate(5, 5));

        List<Cell> revealedCells = grid.cells().stream().filter(Cell::isRevealed).toList();
        List<Cell> hiddenCells = grid.cells().stream().filter(Cell::isHidden).toList();
        assertThat(revealedCells).hasSize(71);
        assertThat(hiddenCells).hasSize(10)
                .containsExactly(
                        new Cell(createCoordinate(7, 3), CellType.MINE),
                        Cell.createNeighbour(createCoordinate(1, 4), 1),
                        new Cell(createCoordinate(2, 4), CellType.MINE),
                        Cell.createNeighbour(createCoordinate(7, 4), 1),
                        Cell.createNeighbour(createCoordinate(7, 5), 1),
                        new Cell(createCoordinate(8, 5), CellType.MINE),
                        new Cell(createCoordinate(9, 5), CellType.MINE),
                        Cell.createNeighbour(createCoordinate(7, 6), 1),
                        Cell.createNeighbour(createCoordinate(1, 9), 1),
                        new Cell(createCoordinate(2, 9), CellType.MINE)
                );
    }

    private Coordinate createCoordinate(int column, int row) {
        return new Coordinate(column, row, gridSize);
    }
}