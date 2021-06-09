package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DodgePotion extends Item{

    public DodgePotion(Cell cell) {
        super(cell);
        setDodgeChance(0.1f);
    }

    public DodgePotion() {
        super();
        setDodgeChance(0.1f);
    }
    @JsonIgnore
    @Override
    public String getTileName() {
        return "dodge potion";
    }

    @Override
    public char toChar() {
        return 'D';
    }
}