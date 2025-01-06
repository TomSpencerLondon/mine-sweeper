package org.example.adapter.out.console.command;

import org.example.adapter.out.console.MineSweeperController;
import org.example.hexagon.domain.Coordinate;

public class Reveal implements Command{
    private final MineSweeperController controller;

    public Reveal(MineSweeperController controller) {

        this.controller = controller;
    }

    @Override
    public void execute(Coordinate coordinate) {
        controller.reveal(coordinate);
    }
}
