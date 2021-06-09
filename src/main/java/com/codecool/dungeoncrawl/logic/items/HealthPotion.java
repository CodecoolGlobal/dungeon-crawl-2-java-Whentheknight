package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class HealthPotion extends Item{

    public HealthPotion(Cell cell) {
        super(cell);
        setHealth(10);
    }

    public HealthPotion() {
        super();
        setHealth(10);
    }
    @JsonIgnore
    @Override
    public String getTileName() {
        return "health potion";
    }

    @Override
    public char toChar() {
        return 'h';
    }
}
