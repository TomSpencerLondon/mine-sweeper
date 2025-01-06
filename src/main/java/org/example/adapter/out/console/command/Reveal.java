package org.example.adapter.out.console.command;

import org.example.adapter.out.console.MineSweeperController;
import org.example.hexagon.domain.Coordinate;

public class Reveal implements Command{
    private final MineSweeperController controller;
    private final GameState gameState;

    public Reveal(MineSweeperController controller, GameState gameState) {
        this.controller = controller;
        this.gameState = gameState;
    }

    @Override
    public void execute(Coordinate coordinate) {
        if (gameState == GameState.FIRST_REVEAL) {
            controller.firstReveal(coordinate);
        } else {
            controller.reveal(coordinate);
        }
    }
}
