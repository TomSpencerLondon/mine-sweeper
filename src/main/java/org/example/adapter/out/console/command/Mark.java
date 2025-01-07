package org.example.adapter.out.console.command;

import org.example.hexagon.application.MineSweeperController;
import org.example.hexagon.domain.Coordinate;

public class Mark implements Command{
    private final MineSweeperController controller;

    public Mark(MineSweeperController controller) {
        this.controller = controller;
    }

    @Override
    public void execute(Coordinate coordinate) {
        controller.mark(coordinate);
    }
}
