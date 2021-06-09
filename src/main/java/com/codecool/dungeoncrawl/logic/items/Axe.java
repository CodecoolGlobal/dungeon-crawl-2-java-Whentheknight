package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Axe extends Item {

    private String name;

    public Axe(Cell cell, String name) {
        super(cell);
        this.name = name;
        setStrength(10);
    }

    public Axe() {
        super();
        setStrength(10);
    }
    @JsonIgnore
    public String getTileName() {
        return "axe";
    }

    @Override
    public char toChar() {
        return 'A';
    }
}