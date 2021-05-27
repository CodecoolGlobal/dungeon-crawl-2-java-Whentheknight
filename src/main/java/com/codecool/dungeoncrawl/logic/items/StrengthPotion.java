package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class StrengthPotion extends Item{

    public StrengthPotion(Cell cell) {
        super(cell);
        setStrength(10);
    }

    @Override
    public String getTileName() {
        return "strength potion";
    }
}