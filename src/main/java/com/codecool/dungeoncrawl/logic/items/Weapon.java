package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Item {

    private String name;

    public Weapon(Cell cell, String name) {
        super(cell);
        this.name = name;
    }

    public String getTileName() {
        return "weapon";
    }
}
