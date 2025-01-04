package org.example;

import org.example.adapter.out.console.GamePrinter;
import org.example.adapter.out.console.CoordinateValidator;
import org.example.adapter.out.console.MineSweeperController;
import org.example.adapter.out.console.RandomMineGenerator;
import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.application.port.Printer;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();

        MineGenerator randomMineGenerator = new RandomMineGenerator(rows, cols, new Random());
        CoordinateValidator validator = new CoordinateValidator(rows, cols);
        Grid grid = new Grid(numMines, randomMineGenerator, validator);
        MineSweeperService mineSweeperService = new MineSweeperService(grid);
        Printer printer = new GamePrinter(rows, cols);
        MineSweeperController mineSweeperController = new MineSweeperController(mineSweeperService, printer);

        mineSweeperController.displayCells();

        while (!mineSweeperController.hasWon()) {
            printer.print("Set/delete mines marks (x and y coordinates): >\n");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            mineSweeperController.mark(new Coordinate(x, y));

        }
    }
}