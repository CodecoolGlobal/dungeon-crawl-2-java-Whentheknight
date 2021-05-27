package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.BearSteak;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.Random;

public class Bear extends Actor{
    public Bear(Cell cell) {
        super(cell);
        setHealth(20);
        setStrength(5);
        setDodgeChance(0.1f);
        initDrop();
    }


    private void initDrop() {
        Item bearSteak = new BearSteak();
        setDrop(bearSteak);
        setDropChance(0.33);
    }

    @Override
    public String getTileName() {
        return "bear";
    }

}
