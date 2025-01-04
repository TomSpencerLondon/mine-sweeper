package org.example;

import org.example.adapter.out.console.GamePrinter;
import org.example.adapter.out.console.MineSweeperController;
import org.example.adapter.out.console.RandomMineGenerator;
import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;
import org.example.hexagon.domain.GridSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
                         |123456789|
                        -|---------|  
                        1|111......|
                        2|1.1......|
                        3|111......|
                        4|...111...|
                        5|...1.1...|
                        6|...11211.|
                        7|.....1.1.|
                        8|.....111.|
                        9|.........|
                        -|---------|
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
                         |123456789|
                        -|---------|   
                        1|....1.1..|
                        2|...1221..|
                        3|...1.1...|
                        4|...112221|
                        5|.....1..1|
                        6|.....1221|
                        7|.........|
                        8|.........|
                        9|.........|
                        -|---------|
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

            Grid grid = new Grid(numMines, mineGenerator, new GridSize(rows, cols));

            MineSweeperService mineSweeperService = new MineSweeperService(grid);
            GamePrinter printer = new GamePrinter(rows, cols);
            MineSweeperController mineSweeperController = new MineSweeperController(mineSweeperService, printer);

            mineSweeperController.displayCells();
        }
    }
}
