package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.ArrayList;
import java.util.List;


public class Player extends Actor {
    private Actor currentEnemy;
    private final List<Item> inventory;

    private int currentMap = 0;
    private boolean isHaunted;
    private String name;
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

    public boolean hasKey(){
        for(Item item: inventory){
            if(item instanceof Key){
               return true;
            }
    }return false;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        currentEnemy = nextCell.getActor();
        setHealthChange(0);
        setStrengthChange(0);
        setDodgeChanceChange(0);
        setIsHaunted(false);
        setHasDodged(false);
      
        if(hasGhostNeighbor()) {
            setIsHaunted(true);
            setHealthChange(-1);
            setHealth(getHealth()-1);
        }
        if (!nextCell.getType().equals(CellType.WALL) && currentEnemy == null && !nextCell.getType().equals(CellType.CDOOR)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (!nextCell.getType().equals(CellType.WALL) && currentEnemy != null) {
            this.attack(currentEnemy);
            if(currentEnemy.getHealth() > 0) {
                currentEnemy.attack(this);
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
        if (this.name.equalsIgnoreCase("admin") && currentEnemy == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public Actor getCurrentEnemy() {
        return currentEnemy;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public void addToInventory(Item item) {
        this.inventory.add(item);
    }

    public void addStats(Item item) {
        setStrength(getStrength() + item.getStrength());
        setStrengthChange(item.getStrength());
        setHealth(getHealth() + item.getHealth());
        setHealthChange(item.getHealth());
        setDodgeChanceChange((1-getDodgeChance()) * item.getDodgeChance());
        setDodgeChance(getDodgeChance() + (1-getDodgeChance()) * item.getDodgeChance());
    }

  
    public boolean hasGhostNeighbor() {
        return cell.getNeighbor(1,0).getActor() instanceof Ghost
        || cell.getNeighbor(0, 1).getActor() instanceof Ghost
        || cell.getNeighbor(-1, 0).getActor() instanceof Ghost
        || cell.getNeighbor(0, -1).getActor() instanceof Ghost;
    }

    public void setIsHaunted(boolean b) {
        isHaunted = b;
    }

    public boolean getIsHaunted() {
        return isHaunted;
      
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
