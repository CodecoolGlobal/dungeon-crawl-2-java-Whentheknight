package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Key extends Item {
    private int keyNum;

    public Key (Cell cell, int keyNum) {
        super(cell);
        this.keyNum = keyNum;
    }

    public Key() {
        super();
    }
    @JsonIgnore
    public String getTileName() {
        return "key";
    }

    @Override
    public char toChar() {
        return 'k';
    }
}
