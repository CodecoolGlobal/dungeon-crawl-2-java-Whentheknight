package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.BatSoup;
import com.codecool.dungeoncrawl.logic.items.Bone;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        setHealth(15);
        setStrength(2);
        setDodgeChance(0.1f);
        initDrop();
    }

    private void initDrop() {
        Item bone = new Bone();
        setDrop(bone);
        setDropChance(0.75);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public char toChar() {
        return 's';
    }
}
