package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor{
    public Ghost(Cell cell) {
        super(cell);
        setHealth(10);
        setStrength(3);
        setDodgeChance(0.25f);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}
