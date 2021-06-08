package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StrengthPotion extends Item{

    public StrengthPotion(Cell cell) {
        super(cell);
        setStrength(10);
    }

    public StrengthPotion() {
        super();
        setStrength(10);
    }
    @JsonIgnore
    @Override
    public String getTileName() {
        return "strength potion";
    }

    @Override
    public char toChar() {
        return 'P';
    }
}