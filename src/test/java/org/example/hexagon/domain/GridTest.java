package org.example.hexagon.domain;

import org.example.adapter.out.console.CellInfo;
import org.example.hexagon.application.port.MineGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GridTest {

    MineGenerator mineGenerator = mock(MineGenerator.class);

    Grid grid;

    @BeforeEach
    void setUp() {
        when(mineGenerator.next())
                .thenReturn(new Coordinate(2, 5), new Coordinate(5, 1));
        grid = new Grid(2, mineGenerator, new GridSize(9, 9));
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
                        new Coordinate(2, 1),
                        new Coordinate(5, 4),
                        new Coordinate(6, 1),
                        new Coordinate(6, 2)
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
                        new Coordinate(2, 2),
                        new Coordinate(2, 4),
                        new Coordinate(2, 7),
                        new Coordinate(3, 1),
                        new Coordinate(3, 7),
                        new Coordinate(6, 6),
                        new Coordinate(6, 7)
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
    void givenOneMineIncorrectThenCorrectMarkingReturnsWinner() {
        when(mineGenerator.next())
                .thenReturn(
                        new Coordinate(1, 2)
                );
        grid = new Grid(1, mineGenerator, new GridSize(9, 9));

        grid.mark(new Coordinate(4, 8));
        grid.mark(new Coordinate(1, 2));
        grid.mark(new Coordinate(4, 8));

        assertThat(grid.hasWon())
                .isTrue();
    }
}