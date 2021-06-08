package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BearSteak extends Item {
    public BearSteak(Cell cell) {
        super(cell);
        setStrength(2);
        setHealth(5);
    }

    public BearSteak() {
        setStrength(2);
        setHealth(5);
    }

    @Override
    public String getTileName() {
        return "bear steak";
    }

    @Override
    public char toChar() {
        return 'E';
    }
}
