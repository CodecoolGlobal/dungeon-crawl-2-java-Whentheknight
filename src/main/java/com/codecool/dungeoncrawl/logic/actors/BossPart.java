package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class BossPart extends Actor{
    private Boss boss;
    private int part;
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
        return Character.forDigit(part % 10, 10);
    }
}
