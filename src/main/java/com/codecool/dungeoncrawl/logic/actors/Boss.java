package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Actor{
    private List<Cell> bossParts = new ArrayList<>();

    public Boss(Cell cell) {
        super(cell);
        setHealth(250);
        setStrength(10);
        setDodgeChance(0.50f);
    }

    @Override
    public String getTileName() {
        return "boss";
    }

    public void addCells(Cell cell){
        bossParts.add(cell);
    }

    public List<Cell> getBossParts() {
        return bossParts;
    }

    @Override
    public char toChar() {
        return 'j';
    }
}
