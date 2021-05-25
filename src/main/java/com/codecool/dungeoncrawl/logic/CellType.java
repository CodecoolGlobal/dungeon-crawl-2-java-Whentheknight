package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SWORD("sword"),
    KEY1("key1", 1),
    KEY2("key2", 1),
    HEALTH_POTION("health potion");

    private final String tileName;
    private int stat = 0;


    CellType(String tileName) {
        this.tileName = tileName;
    }

    CellType(String tileName, int stat) {
        this.tileName = tileName;
        this.stat = stat;
    }

    public String getTileName() {
        return tileName;
    }
}
