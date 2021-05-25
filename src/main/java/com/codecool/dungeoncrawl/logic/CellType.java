package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    ODOOR("open door"),
    CDOOR("closed door");


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
