package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class DodgePotion extends Item{

    public DodgePotion(Cell cell) {
        super(cell);
        setDodgeChance(0.1f);
    }

    public DodgePotion() {
        setDodgeChance(0.1f);
    }

    @Override
    public String getTileName() {
        return "dodge potion";
    }

    @Override
    public char toChar() {
        return 'D';
    }
}