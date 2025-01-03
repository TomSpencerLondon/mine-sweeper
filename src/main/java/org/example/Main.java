package org.example;

import org.example.adapter.out.console.RandomMineGenerator;
import org.example.hexagon.application.MineSweeperService;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 9;
        int cols = 9;

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();

        RandomMineGenerator randomMineGenerator = new RandomMineGenerator(numMines, new Random());

        MineSweeperService game = new MineSweeperService(rows, cols, randomMineGenerator);
        game.displayMinefieldWithHints();
    }
}