package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class Item implements Drawable {
    @JsonIgnore
    private Cell cell;
    private int strength;
    private int health;
    private float dodgeChance;

    public Item (Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Item() {
        super();
    }

    public Cell getCell() {
        return cell;
    }

    @JsonIgnore
    public int getX() {
        return cell.getX();
    }

    @JsonIgnore
    public int getY() {
        return cell.getY();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(float dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public boolean hasStats() {
        return strength != 0 || health != 0 || dodgeChance != 0;
    }

    public abstract char toChar();

}
