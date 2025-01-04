package org.example.adapter.out.console;

import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.Printer;
import org.example.hexagon.domain.Coordinate;
import org.example.hexagon.domain.InvalidSelectionException;

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

    public void mark(Coordinate coordinate) {
        try {
            service.mark(coordinate);

            if (service.hasWon()) {
                printer.print("Congratulations! You found all the mines!");
            } else {
                displayCells();
            }

        } catch(InvalidSelectionException e) {
            printer.print("There is a number here!");
        }
    }
}
