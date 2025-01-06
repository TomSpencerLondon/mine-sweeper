package org.example.adapter.out.console.command;

import org.example.adapter.out.console.MineSweeperController;

public class CommandFactory {
    private final MineSweeperController controller;
    private GameState gameState;

    public CommandFactory(MineSweeperController controller) {
        this.controller = controller;
        this.gameState = GameState.FIRST_REVEAL;
    }

    public Command create(String command) {
        if (command.equalsIgnoreCase("free")) {
            Reveal reveal = new Reveal(controller, gameState);
            gameState = GameState.MAIN_GAME;

            return reveal;
        }

        return new Mark(controller);
    }
}
