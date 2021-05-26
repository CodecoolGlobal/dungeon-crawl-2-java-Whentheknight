package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Axe extends Item {

    private String name;

    public Axe(Cell cell, String name) {
        super(cell);
        this.name = name;
        setStrength(10);
    }

    public String getTileName() {
        return "axe";
    }
}