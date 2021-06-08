package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Sword extends Item {

    private String name;

    public Sword(Cell cell, String name) {
        super(cell);
        this.name = name;
        setStrength(5);
    }

    public Sword() {
        setStrength(5);
    }

    public String getTileName() {
        return "sword";
    }

    @Override
    public char toChar() {
        return 'W';
    }
}
