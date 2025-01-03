package org.example;

import org.example.adapter.out.console.RandomMineGenerator;
import org.example.hexagon.application.MineSweeperService;
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
import static org.mockito.Mockito.when;

public class MinesweeperAcceptanceTest {

    @Mock
    private static Random random;



    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(random.nextInt(anyInt()))
                .thenReturn(2, 2, 5, 5, 7, 7);
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
    void name() {
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
                        .........
                        .111.....
                        .1X1.....
                        .111.....
                        ....111..
                        ....1X1..
                        ....11211
                        ......1X1
                        ......111
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

            RandomMineGenerator randomMineGenerator = new RandomMineGenerator(numMines, random);

            MineSweeperService game = new MineSweeperService(rows, cols, randomMineGenerator);
            game.displayMinefieldWithHints();
        }
    }
}
