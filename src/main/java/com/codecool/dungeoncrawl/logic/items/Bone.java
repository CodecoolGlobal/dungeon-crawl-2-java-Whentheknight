package com.codecool.dungeoncrawl.logic.items;

public class Bone extends Item{
    public Bone() {
        setStrength(1);
    }

    @Override
    public String getTileName() {
        return "bone";
    }
}
