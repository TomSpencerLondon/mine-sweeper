package org.example;

import org.example.adapter.out.console.command.Command;
import org.example.adapter.out.console.command.CommandFactory;
import org.example.hexagon.application.GamePrinter;
import org.example.hexagon.application.MineSweeperController;
import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.RandomMineGenerator;
import org.example.hexagon.application.port.MineGenerator;
import org.example.hexagon.application.port.Printer;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.Grid;
import org.example.hexagon.domain.GridSize;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();

        GridSize gridSize = new GridSize(rows, cols);
        MineGenerator randomMineGenerator = new RandomMineGenerator(gridSize, new Random());
        Grid grid = new Grid(numMines, randomMineGenerator, gridSize);
        MineSweeperService mineSweeperService = new MineSweeperService(grid);
        Printer printer = new GamePrinter(rows, cols);
        MineSweeperController mineSweeperController = new MineSweeperController(mineSweeperService, printer);
        CommandFactory commandFactory = new CommandFactory(mineSweeperController);

        mineSweeperController.displayCells();

        while (!mineSweeperController.hasWon()) {
            printer.print("Set/unset mines marks or claim a cell as free: >\n");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String action = scanner.next();
            Command command = commandFactory.create(action);
            command.execute(new Coordinate(x, y, gridSize));
        }
    }
}