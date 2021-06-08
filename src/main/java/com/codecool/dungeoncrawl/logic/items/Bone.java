package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Bone extends Item{
    public Bone(Cell cell) {
        super(cell);
        setStrength(1);
    }

    public Bone() {
        super();
        setStrength(1);
    }
    @JsonIgnore
    @Override
    public String getTileName() {
        return "bone";
    }

    @Override
    public char toChar() {
        return 'O';
    }
}
