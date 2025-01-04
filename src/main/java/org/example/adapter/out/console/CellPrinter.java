package org.example.adapter.out.console;

import org.example.hexagon.application.port.Printer;

import java.util.Iterator;
import java.util.List;

public class CellPrinter implements Printer {


    private final int rows;
    private final int cols;

    public CellPrinter(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void print(List<CellInfo> cellInfos) {
        Iterator<CellInfo> iterator = cellInfos.iterator();
        System.out.println();
        System.out.println(" |123456789|");
        for (int i = 0; i < rows; i++) {
            System.out.print(i + "|");
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
