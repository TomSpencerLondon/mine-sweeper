package org.example.adapter.out.console;

import org.example.hexagon.application.port.Printer;

import java.util.Iterator;
import java.util.List;

public class GamePrinter implements Printer {


    private final int rows;
    private final int cols;

    public GamePrinter(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public void print(List<CellInfo> cellInfos) {
        Iterator<CellInfo> iterator = cellInfos.iterator();
        System.out.println();
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < cols; j++) {
                if (iterator.hasNext()) {
                    System.out.print(iterator.next().display());
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }
}
