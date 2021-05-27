package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Actor{
    private List<Cell> bossParts = new ArrayList<>();

    public Boss(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "boss1";
    }

    public void addCells(Cell cell){
        bossParts.add(cell);
    }

    public List<Cell> getBossParts() {
        return bossParts;
    }
}
