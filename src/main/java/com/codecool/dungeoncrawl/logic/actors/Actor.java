package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health = 10;
    private int strength;
    private float dodgeChance;
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

    public void move() {}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public float getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(float dodgeChance) {
        this.dodgeChance = dodgeChance;
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
        Random rand = new Random();
        float dodge = rand.nextFloat();
        if (dodge < enemy.dodgeChance) {
            enemy.setCurrentDamage(-1);
        } else {
            enemy.setHealth(enemy.getHealth() - strength);
            enemy.setCurrentDamage(strength);
        }
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(int damage) {
        currentDamage = damage;
    }
}
