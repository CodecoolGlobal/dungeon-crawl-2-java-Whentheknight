package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class BatSoup extends Item{

    public BatSoup(Cell cell) {
        super(cell);
        setHealth(-3);
        setStrength(-3);
    }

    public BatSoup() {
        setHealth(-3);
        setStrength(-3);
    }


    @Override
    public String getTileName() {
        return "bat soup";
    }

    @Override
    public char toChar() {
        return 'u';
    }
}
