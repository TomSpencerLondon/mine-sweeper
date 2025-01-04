package org.example;

import org.example.adapter.out.console.CellPrinter;
import org.example.adapter.out.console.CoordinateValidator;
import org.example.adapter.out.console.MineSweeperController;
import org.example.adapter.out.console.RandomMineGenerator;
import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinesweeperAcceptanceTest {

    @Mock
    private static MineGenerator mineGenerator = mock(RandomMineGenerator.class);



    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private void simulateInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private String getCapturedOutput() {
        return outputStream.toString();
    }


    @Test
    void givenThreeMinesReturnsCorrectOutput() {
        when(mineGenerator.next())
                .thenReturn(
                        new Coordinate(2, 2),
                        new Coordinate(5, 5),
                        new Coordinate(7, 7));

        simulateInput("""
                3
                """);

        TestableMain.main(new String[0]);

        String output = getCapturedOutput();


        assertThat(
                output
        ).isEqualTo(
                """
                        How many mines do you want on the field? >\s
                        111......
                        1X1......
                        111......
                        ...111...
                        ...1X1...
                        ...11211.
                        .....1X1.
                        .....111.
                        .........
                        """
        );
    }


    @Test
    void givenFourMinesReturnsCorrectOutput() {
        when(mineGenerator.next())
                .thenReturn(
                        new Coordinate(1, 6),
                        new Coordinate(3, 5),
                        new Coordinate(5, 7),
                        new Coordinate(5, 8));

        simulateInput("""
                4
                """);

        TestableMain.main(new String[0]);

        String output = getCapturedOutput();


        assertThat(
                output
        ).isEqualTo(
                """
                        How many mines do you want on the field? >\s
                        111......
                        1X1......
                        111......
                        ...111...
                        ...1X1...
                        ...11211.
                        .....1X1.
                        .....111.
                        .........
                        """
        );
    }


    class TestableMain extends Main {
        public static void main(String[] args) {
            int rows = 9;
            int cols = 9;

            Scanner scanner = new Scanner(System.in);
            System.out.print("How many mines do you want on the field? > ");
            int numMines = scanner.nextInt();

            Grid grid = new Grid(numMines, mineGenerator, new CoordinateValidator(rows, cols));

            MineSweeperService mineSweeperService = new MineSweeperService(grid);
            CellPrinter printer = new CellPrinter(rows, cols);
            MineSweeperController mineSweeperController = new MineSweeperController(mineSweeperService, printer);

            mineSweeperController.displayCells();
        }
    }
}
