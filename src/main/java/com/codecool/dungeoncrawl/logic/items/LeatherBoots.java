package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class LeatherBoots extends Item{
    public LeatherBoots(Cell cell) {
        super(cell);
        setDodgeChance(0.1f);
        setHealth(3);
    }

    public LeatherBoots() {
        setDodgeChance(0.1f);
        setHealth(3);
    }

    public String getTileName() {
        return "leather boots";
    }
}
