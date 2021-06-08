package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BatSoup extends Item{

    public BatSoup(Cell cell) {
        super(cell);
        setHealth(-3);
        setStrength(-3);
    }

    public BatSoup() {
        super();
        setHealth(-3);
        setStrength(-3);
    }

    @JsonIgnore
    @Override
    public String getTileName() {
        return "bat soup";
    }

    @Override
    public char toChar() {
        return 'u';
    }
}
