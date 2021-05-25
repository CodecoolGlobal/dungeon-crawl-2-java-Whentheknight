package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        setHealth(15);
        setStrength(2);
        setDodgeChance(0.1f);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
