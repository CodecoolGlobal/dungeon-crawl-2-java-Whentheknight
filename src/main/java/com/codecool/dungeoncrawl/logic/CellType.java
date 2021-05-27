package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    ODOOR("open door"),
    CDOOR("closed door"),
    TREE("tree"),
    LAVA("lava"),
    TOXIC("toxic"),
    STAIRS("stairs");



    private final String tileName;
    private int stat = 0;


    CellType(String tileName) {
        this.tileName = tileName;
    }


    public String getTileName() {
        return tileName;
    }
}
