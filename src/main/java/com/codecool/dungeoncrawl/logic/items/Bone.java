package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bone extends Item{
    public Bone(Cell cell) {
        super(cell);
        setStrength(1);
    }

    public Bone() {
        setStrength(1);
    }

    @Override
    public String getTileName() {
        return "bone";
    }

    @Override
    public char toChar() {
        return 'O';
    }
}
