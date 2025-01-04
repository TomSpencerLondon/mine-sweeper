package org.example.hexagon.domain;

import java.util.function.Predicate;

public enum MineStatus implements Predicate<MineStatus> {
    MARKED, UNMARKED;

    @Override
    public boolean test(MineStatus mineStatus) {
        return mineStatus == MARKED;
    }
}
