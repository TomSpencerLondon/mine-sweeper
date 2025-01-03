package org.example.hexagon.application.port;

import org.example.adapter.out.console.CellInfo;

import java.util.List;

public interface Printer {
    void print(List<CellInfo> cellInfos);
}
