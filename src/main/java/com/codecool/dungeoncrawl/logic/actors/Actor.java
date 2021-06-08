package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Actor implements Drawable {
    @JsonIgnore
    protected Cell cell;
    private int health = 10;
    private int strength;
    private float dodgeChance;
    protected int healthChange;
    protected int strengthChange;
    protected float dodgeChanceChange;
    private boolean hasDodged;
    private Item drop;
    private double dropChance;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(!nextCell.getType().equals(CellType.WALL) && nextCell.getActor() == null &&
                !nextCell.getType().equals(CellType.CDOOR) && !nextCell.getType().equals(CellType.TREE)){
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

    public void setCell(Cell cell) {
        this.cell = cell;
    }


    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void attack(Actor enemy){
        enemy.setHealthChange(0);
        Random rand = new Random();
        float dodge = rand.nextFloat();
        if (dodge < enemy.getDodgeChance()) {
            enemy.hasDodged = true;
            enemy.setHealthChange(0);
        } else {
            enemy.hasDodged = false;
            enemy.setHealth(enemy.getHealth() - strength);
            enemy.setHealthChange(enemy.getHealthChange() + strength*-1);
        }
        if(enemy.getHealth() <= 0) {
            enemy.getCell().removeActor();
            if (enemy.getDropChance() > rand.nextFloat()) {
                enemy.getCell().setItem(enemy.getDrop());
            }
        }
    }

    public int getHealthChange() {
        return healthChange;
    }

    public void setHealthChange(int healthChange) {
        this.healthChange = healthChange;
    }

    public boolean getHasDodged() {
        return hasDodged;
    }

    public void setHasDodged(boolean b) {
        hasDodged = b;
    }

    public int getStrengthChange() {
        return strengthChange;
    }

    public void setStrengthChange(int strengthChange) {
        this.strengthChange = strengthChange;
    }

    public float getDodgeChanceChange() {
        return dodgeChanceChange;
    }

    public void setDodgeChanceChange(float dodgeChanceChange) {
        this.dodgeChanceChange = dodgeChanceChange;
    }


    public Item getDrop() {
        return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public double getDropChance() {
        return dropChance;
    }

    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }

    public abstract char toChar();
}
