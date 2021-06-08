package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {
    private int keyNum;

    public Key (Cell cell, int keyNum) {
        super(cell);
        this.keyNum = keyNum;
    }

    public Key() {
    }

    public String getTileName() {
        return "key";
    }

    @Override
    public char toChar() {
        return 'k';
    }
}
