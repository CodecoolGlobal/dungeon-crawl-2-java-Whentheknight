package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class LeatherBoots extends Item{
    public LeatherBoots(Cell cell) {
        super(cell);
        setDodgeChance(0.1f);
        setHealth(3);
    }

    public LeatherBoots() {
        super();
        setDodgeChance(0.1f);
        setHealth(3);
    }
    @JsonIgnore
    public String getTileName() {
        return "leather boots";
    }

    @Override
    public char toChar() {
        return 'l';
    }
}
