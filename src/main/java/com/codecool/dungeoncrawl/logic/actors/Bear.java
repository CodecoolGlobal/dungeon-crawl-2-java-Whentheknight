package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bear extends Actor{
    public Bear(Cell cell) {
        super(cell);
        setHealth(20);
        setStrength(5);
        setDodgeChance(0.1f);
    }

    @Override
    public String getTileName() {
        return "bear";
    }

}
