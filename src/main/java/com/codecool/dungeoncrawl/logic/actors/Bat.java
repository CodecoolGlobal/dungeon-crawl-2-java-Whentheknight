package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bat extends Actor {
    public Bat(Cell cell) {
        super(cell);
        setHealth(5);
        setStrength(1);
        setDodgeChance(0.5f);
    }

    @Override
    public String getTileName() {
        return "bat";
    }
}
