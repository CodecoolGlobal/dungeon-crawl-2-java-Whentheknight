package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    WALL2("wall2"),
    ODOOR("open door"),
    CDOOR("closed door"),
    TREE("tree"),
    LAVA("lava"),
    TOXIC("toxic"),
    STAIRS("stairs"),
    RIVER("river"),
    BRIDGE("bridge");


    private final String tileName;
    private int stat = 0;


    CellType(String tileName) {
        this.tileName = tileName;
    }


    public String getTileName() {
        return tileName;
    }
}
