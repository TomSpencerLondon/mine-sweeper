package org.example.adapter.out.console;

import org.example.hexagon.application.port.MineGenerator;

import java.util.Random;

public class RandomMineGenerator implements MineGenerator {
    private final int numMines;
    private final Random random;

    public RandomMineGenerator(int numMines, Random random) {
        this.numMines = numMines;
        this.random = random;
    }

    public int numMines() {
        return numMines;
    }

    public int next(int seed) {
        return random.nextInt(seed);
    }
}
