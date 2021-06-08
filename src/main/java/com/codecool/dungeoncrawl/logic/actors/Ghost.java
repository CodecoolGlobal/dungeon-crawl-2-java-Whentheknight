package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Actor{
    public Ghost(Cell cell) {
        super(cell);
        setHealth(5);
        setStrength(3);
        setDodgeChance(0.25f);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public void move() {
        GameMap map = cell.getMap();
        Player player = map.getPlayer();
        if(Math.abs(getX() - player.getX()) > Math.abs(getY() - player.getY())) {
            if(isPlayerNeighbor()) {
                // don't move
            }
            else if(getX() > player.getX()){
                if(cell.getNeighbor(-1, 0).getActor() == null) {
                    move(-1, 0);
                } else {
                    if(getY() > player.getY()) {
                        move(0, -1);
                    } else {
                        move(0, 1);
                    }
                }
            } else {
                if(cell.getNeighbor(1, 0).getActor() == null) {
                    move(1, 0);
                } else {
                    if(getY() > player.getY()) {
                        move(0, -1);
                    } else {
                        move(0, 1);
                    }
                }
            }
        } else {
            if(getY() > player.getY()) {
                if(cell.getNeighbor(0, -1).getActor() == null) {
                    move(0, -1);
                } else {
                    if(getX() > player.getX()) {
                        move(-1, 0);
                    } else {
                        move(1, 0);
                    }
                }
            } else if (getY() < player.getY()){
                if(cell.getNeighbor(0, 1).getActor() == null) {
                    move(0, 1);
                } else {
                    if(getX() > player.getX()) {
                        move(-1, 0);
                    } else {
                        move(1, 0);
                    }
                }
            }
        }


//        try{
//            Cell nextCell;
//            int x = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
//        int randomNum = ThreadLocalRandom.current().nextInt(0, 1+1);
//        if (x == 0) {
//            if (randomNum == 0){
//                nextCell = cell.getNeighbor(x, -1);
//            } else {
//                nextCell = cell.getNeighbor(x, 1);
//            }
//        }else{
//            nextCell = cell.getNeighbor(x, 0);
//
//        }
//
//        if(nextCell.getActor() == null && !nextCell.getType().equals(CellType.CDOOR)){
//            cell.setActor(null);
//            nextCell.setActor(this);
//            cell = nextCell;
//        }}
//        catch (Exception  e){
//            move();
//        }
    }

    public boolean isPlayerNeighbor() {
        return cell.getNeighbor(1,0).getActor() instanceof Player
            || cell.getNeighbor(0, 1).getActor() instanceof Player
            || cell.getNeighbor(-1, 0).getActor() instanceof Player
            || cell.getNeighbor(0, -1).getActor() instanceof Player;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    @Override
    public char toChar() {
        return 'g';
    }
}
