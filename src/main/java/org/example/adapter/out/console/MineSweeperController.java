package org.example.adapter.out.console;

import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.Printer;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.InvalidSelectionException;
import org.example.hexagon.domain.MineRevealedException;

import java.util.List;


public class MineSweeperController {

    private final MineSweeperService service;
    private final Printer printer;

    public MineSweeperController(MineSweeperService service, Printer printer) {
        this.service = service;
        this.printer = printer;
    }

    public void displayCells() {
        List<CellInfo> cellInfos = service.cells()
                .stream()
                .map(CellInfo::from)
                .toList();

        printer.print(cellInfos);

    }

    public boolean hasWon() {
        return service.hasWon();
    }

    public void firstReveal(Coordinate coordinate) {
        service.firstReveal(coordinate);
        if (service.hasWon()) {
            displayCells();
            printer.print("Congratulations! You found all the mines!");
        } else {
            displayCells();
        }
    }

    public void reveal(Coordinate coordinate) {
        try {
            service.reveal(coordinate);

            if (service.hasWon()) {
                displayCells();
                printer.print("Congratulations! You found all the mines!");
            } else {
                displayCells();
            }
        } catch (MineRevealedException e) {
            displayCells();
            printer.print("You stepped on a mine and failed!");
        }
    }

    public void mark(Coordinate coordinate) {
        service.mark(coordinate);

        if (service.hasWon()) {
            displayCells();
            printer.print("Congratulations! You found all the mines!");
        } else {
            displayCells();
        }

    }
}

