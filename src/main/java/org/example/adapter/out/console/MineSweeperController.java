package org.example.adapter.out.console;

import org.example.hexagon.application.MineSweeperService;
import org.example.hexagon.application.port.Printer;

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
}
