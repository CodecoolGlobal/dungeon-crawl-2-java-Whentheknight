package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health = 10;
    protected int strength;
    protected int currentDamage;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(!nextCell.getType().equals(CellType.WALL) && nextCell.getActor() == null && !nextCell.getType().equals(CellType.CDOOR)){
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public int getStrength() {
        return strength;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void attack(Actor enemy){
        enemy.setHealth(enemy.getHealth() - strength);
        enemy.setCurrentDamage(strength);
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(int damage) {
        currentDamage = damage;
    }
}
