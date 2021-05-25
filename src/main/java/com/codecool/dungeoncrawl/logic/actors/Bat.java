package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bat extends Actor {
    public Bat(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "bat";
    }
}
