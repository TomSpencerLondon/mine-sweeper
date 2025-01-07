package org.example.hexagon.application.port;


import org.example.hexagon.application.CellInfo;

import java.util.List;

public interface Printer {
    void print(String message);

    void print(List<CellInfo> cellInfos);
}
