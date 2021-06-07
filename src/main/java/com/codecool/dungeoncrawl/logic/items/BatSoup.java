package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BatSoup extends Item{
    public BatSoup() {
        setHealth(-3);
        setStrength(-3);
    }

    @Override
    public String getTileName() {
        return "bat soup";
    }
}
