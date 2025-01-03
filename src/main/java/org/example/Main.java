package org.example;

import org.example.domain.MineGenerator;
import org.example.domain.MinesweeperGame;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();

        MineGenerator mineGenerator = new MineGenerator(numMines, new Random());

        MinesweeperGame game = new MinesweeperGame(rows, cols, mineGenerator);
        game.displayMinefieldWithHints();
    }
}