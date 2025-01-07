package org.example;

import org.example.adapter.out.console.command.Command;
import org.example.adapter.out.console.command.CommandFactory;
import org.example.hexagon.application.GamePrinter;
import org.example.hexagon.application.MineSweeperController;
import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.RandomMineGenerator;
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
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinesweeperAcceptanceTest {

    @Mock
    private static MineGenerator mineGenerator = mock(RandomMineGenerator.class);

    private GridSize gridSize;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        gridSize = new GridSize(9, 9);
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
        when(mineGenerator.next(anySet()))
                .thenReturn(
                        createCoordinate(2, 2),
                        createCoordinate(4, 4),
                        createCoordinate(7, 7));

        simulateInput("""
                3
                2 2 mine
                4 4 mine
                7 7 mine
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
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|.........|
                        6|.........|
                        7|.........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.*.......|
                        3|.........|
                        4|.........|
                        5|.........|
                        6|.........|
                        7|.........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.*.......|
                        3|.........|
                        4|...*.....|
                        5|.........|
                        6|.........|
                        7|.........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.*.......|
                        3|.........|
                        4|...*.....|
                        5|.........|
                        6|.........|
                        7|......*..|
                        8|.........|
                        9|.........|
                        -|---------|
                        Congratulations! You found all the mines!
                        """
        );
    }


    @Test
    void givenFourMinesReturnsCorrectOutput() {
        when(mineGenerator.next(anySet()))
                .thenReturn(
                        createCoordinate(1, 6),
                        createCoordinate(3, 5),
                        createCoordinate(5, 7),
                        createCoordinate(5, 8));

        simulateInput("""
                4
                1 7 free
                1 6 mine
                3 5 mine
                5 7 mine
                5 8 mine
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
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|.........|
                        6|.........|
                        7|.........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|.........|
                        6|.........|
                        7|1........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|.........|
                        6|*........|
                        7|1........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|..*......|
                        6|*........|
                        7|1........|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|..*......|
                        6|*........|
                        7|1...*....|
                        8|.........|
                        9|.........|
                        -|---------|
                        Set/unset mines marks or claim a cell as free: >
                        
                        
                         |123456789|
                        -|---------|
                        1|.........|
                        2|.........|
                        3|.........|
                        4|.........|
                        5|..*......|
                        6|*........|
                        7|1...*....|
                        8|....*....|
                        9|.........|
                        -|---------|
                        Congratulations! You found all the mines!
                        """
        );
    }

    private Coordinate createCoordinate(int column, int row) {
        return new Coordinate(column, row, gridSize);
    }


    class TestableMain extends Main {
        public static void main(String[] args) {
            int rows = 9;
            int cols = 9;

            Scanner scanner = new Scanner(System.in);
            System.out.println("How many mines do you want on the field? > ");
            int numMines = scanner.nextInt();

            GridSize gridSize = new GridSize(rows, cols);
            Grid grid = new Grid(numMines, mineGenerator, gridSize);

            MineSweeperService mineSweeperService = new MineSweeperService(grid);
            GamePrinter printer = new GamePrinter(rows, cols);
            MineSweeperController mineSweeperController = new MineSweeperController(mineSweeperService, printer);
            CommandFactory commandFactory = new CommandFactory(mineSweeperController);
            mineSweeperController.displayCells();

            while (!mineSweeperController.hasWon()) {
                printer.print("Set/unset mines marks or claim a cell as free: >\n");
                if (scanner.hasNextInt()) {
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    String action = scanner.next();
                    Command command = commandFactory.create(action);
                    command.execute(new Coordinate(x, y, gridSize));
                }
            }
        }
    }
}
