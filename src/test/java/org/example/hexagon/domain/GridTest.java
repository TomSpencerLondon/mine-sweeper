package org.example.hexagon.domain;

import org.example.adapter.out.console.CellInfo;
import org.example.adapter.out.console.CoordinateValidator;
import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.application.port.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GridTest {

    MineGenerator mineGenerator = mock(MineGenerator.class);

    Grid grid;

    @BeforeEach
    void setUp() {
        when(mineGenerator.next())
                .thenReturn(new Coordinate(2, 5), new Coordinate(5, 1));
        grid = new Grid(2, mineGenerator, new CoordinateValidator(9, 9));
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
}