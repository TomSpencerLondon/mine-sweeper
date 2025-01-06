package org.example.adapter.out.console.command;

import org.example.hexagon.domain.Coordinate;

public interface Command {
    void execute(Coordinate coordinate);
}
