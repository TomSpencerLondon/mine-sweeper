package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();

        MinesweeperGame game = new MinesweeperGame(rows, cols, numMines);
        game.displayMinefieldWithHints();
    }
}