package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.CellType;
import java.util.ArrayList;
import java.util.List;


public class Player extends Actor {
    private Actor currentEnemy;
    private List<Item> inventory;
    public Player(Cell cell) {
        super(cell);
        setStrength(5);
        setDodgeChance(0.4f);
        inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }


    public List<Item> getInventory() {
        return this.inventory;
    }

    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        currentEnemy = nextCell.getActor();
        setTakenDamage(0);
        if (!nextCell.getType().equals(CellType.WALL) && currentEnemy == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (currentEnemy != null) {
            this.attack(currentEnemy);
            if(currentEnemy.getHealth() > 0) {
                currentEnemy.attack(this);
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }

    public Actor getCurrentEnemy() {
        return currentEnemy;
    }

}
