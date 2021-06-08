package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BearSteak extends Item {
    public BearSteak(Cell cell) {
        super(cell);
        setStrength(2);
        setHealth(5);
    }

    public BearSteak() {
        super();
        setStrength(2);
        setHealth(5);
    }
    @JsonIgnore
    @Override
    public String getTileName() {
        return "bear steak";
    }

    @Override
    public char toChar() {
        return 'E';
    }
}
