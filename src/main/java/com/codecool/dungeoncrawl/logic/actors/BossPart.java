package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class BossPart extends Actor{
    private Boss boss;
    private int part;
    private static int currentPart = 1;
    public BossPart(Cell cell, Boss boss, int part) {
        super(cell);
        this.boss = boss;
        this.part = part;
    }

    public Boss getBoss() {
        return boss;
    }

    @Override
    public String getTileName() {
        return "boss"+part;
    }

    @Override
    public char toChar() {
        int toReturn = currentPart;
        currentPart = (currentPart+1) % 10;
        return Character.forDigit(toReturn, 10);
    }
}
