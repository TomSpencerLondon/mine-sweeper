package org.example.adapter.out.console.command;

import org.example.adapter.out.console.MineSweeperController;

public class CommandFactory {
    private final MineSweeperController controller;

    public CommandFactory(MineSweeperController controller) {
        this.controller = controller;
    }

    public Command create(String command) {
        if (command.equalsIgnoreCase("free")) {
            return new Reveal(controller);
        }

        return new Mark(controller);
    }
}
