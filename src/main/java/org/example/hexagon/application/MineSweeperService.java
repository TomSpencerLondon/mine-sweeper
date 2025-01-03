package org.example.hexagon.application;


import org.example.adapter.out.console.RandomMineGenerator;

import java.util.ArrayList;
import java.util.List;

public class MineSweeperService {
    private final List<List<Character>> minefield;
    private final int rows;
    private final int cols;
    private final RandomMineGenerator randomMineGenerator;

    public MineSweeperService(int rows, int cols, RandomMineGenerator randomMineGenerator) {
        this.rows = rows;
        this.cols = cols;
        this.randomMineGenerator = randomMineGenerator;
        this.minefield = createMinefield(rows, cols, this.randomMineGenerator.numMines());
    }

    private List<List<Character>> createMinefield(int rows, int cols, int numMines) {
        List<List<Character>> minefield = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add('.');
            }
            minefield.add(row);
        }

        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = randomMineGenerator.next(rows);
            int col = randomMineGenerator.next(cols);
            if (minefield.get(row).get(col) == '.') {
                minefield.get(row).set(col, 'X');
                minesPlaced++;
            }
        }

        return minefield;
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (minefield.get(newRow).get(newCol) == 'X') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void displayMinefieldWithHints() {
        List<List<Character>> hintField = new ArrayList<>();

        System.out.println();

        for (int i = 0; i < rows; i++) {
            List<Character> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                if (minefield.get(i).get(j) == 'X') {
                    row.add('X');
                } else {
                    int minesAround = countAdjacentMines(i, j);
                    row.add(minesAround > 0 ? Character.forDigit(minesAround, 10) : '.');
                }
            }
            hintField.add(row);
        }

        for (List<Character> row : hintField) {
            for (Character cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}
