package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Actor{
    public Ghost(Cell cell) {
        super(cell);
        setHealth(10);
        setStrength(3);
        setDodgeChance(0.25f);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void move() {
        try{
            Cell nextCell;
            int x = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1+1);
        if (x == 0) {
            if (randomNum == 0){
                nextCell = cell.getNeighbor(x, -1);
            } else {
                nextCell = cell.getNeighbor(x, 1);
            }
        }else{
            nextCell = cell.getNeighbor(x, 0);

        }

        if(nextCell.getActor() == null && !nextCell.getType().equals(CellType.CDOOR)){
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }}
        catch (Exception  e){
            move();
        }
    }
}
