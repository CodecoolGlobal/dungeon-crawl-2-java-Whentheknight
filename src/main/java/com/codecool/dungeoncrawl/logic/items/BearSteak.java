package com.codecool.dungeoncrawl.logic.items;

public class BearSteak extends Item {
    public BearSteak() {
        setStrength(2);
        setHealth(5);
    }

    @Override
    public String getTileName() {
        return "bear steak";
    }
}
