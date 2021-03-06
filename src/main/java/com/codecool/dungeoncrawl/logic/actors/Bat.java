package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.BatSoup;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.concurrent.ThreadLocalRandom;

public class Bat extends Actor {
    private int teleport = 5;

    public Bat(Cell cell) {
        super(cell);
        setHealth(3);
        setStrength(1);
        setDodgeChance(0.5f);
        initDrop();
    }

    @Override
    public String getTileName() {
        return "bat";
    }

    @Override
    public void move() {
        Cell nextCell;
        try {
            int x = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
            int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            if (teleport > 0) {
                if (x == 0) {
                    if (randomNum == 0) {
                        move(x, -1);
                    } else {
                        move(x, 1);
                    }
                } else {
                    move(x, 0);

                }

//                if (!nextCell.getType().equals(CellType.WALL) && nextCell.getActor() == null &&
//                    !nextCell.getType().equals(CellType.CDOOR) && !nextCell.getType().equals(CellType.TREE)
//                    && !nextCell.getType().equals(CellType.EMPTY) && !nextCell.getType().equals(CellType.WALL2)) {
//                    cell.setActor(null);
//                    nextCell.setActor(this);
//                    cell = nextCell;
//                } else {
//                    cell.setActor(this);
//                }

                teleport--;
            } else {
                if (x == 0) {
                    if (randomNum == 0) {
                        nextCell = cell.getNeighbor(x, -3);
                    } else {
                        nextCell = cell.getNeighbor(x, 3);
                    }
                } else {
                    nextCell = cell.getNeighbor(-3, 0);

                }
                if (!nextCell.getType().equals(CellType.WALL) && !nextCell.getType().equals(CellType.EMPTY) && nextCell.getActor() == null && !nextCell.getType().equals(CellType.CDOOR) && !nextCell.getType().equals(CellType.TREE) && !nextCell.getType().equals(CellType.WALL2)) {
                    cell.setActor(null);
                    nextCell.setActor(this);
                    cell = nextCell;
                    teleport = 5;
                } else {
                    cell.setActor(this);
                    teleport = 5;
                }
            }
        } catch (Exception e) {
            teleport = 5;
        }
    }

    private void initDrop() {
        Item batSoup = new BatSoup();
        setDrop(batSoup);
        setDropChance(0.5);
    }
//
//    public int getTeleport() {
//        return teleport;
//    }

    public void setTeleport(int t) {
        teleport = t;
    }

    @Override
    public char toChar() {
        return 'b';
    }
}
