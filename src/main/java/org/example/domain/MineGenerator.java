package org.example.domain;

import java.util.Random;

public class MineGenerator {
    private final int numMines;
    private final Random random;

    public MineGenerator(int numMines, Random random) {
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
