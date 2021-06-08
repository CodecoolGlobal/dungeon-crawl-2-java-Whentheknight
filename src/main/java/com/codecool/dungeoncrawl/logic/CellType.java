package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", ' '),
    FLOOR("floor", '.'),
    WALL("wall", '#'),
    WALL2("wall2", '%'),
    ODOOR("open door", 'o'),
    CDOOR("closed door", 'd'),
    TREE("tree", 't'),
    LAVA("lava", 'a'),
    TOXIC("toxic", 'x'),
    STAIRS("stairs", 'S'),
    RIVER("river", 'R'),
    BRIDGE("bridge", 'r');



    private final String tileName;
    private final char tileChar;
    private int stat = 0;


    CellType(String tileName, char tileChar) {
        this.tileName = tileName;
        this.tileChar = tileChar;
    }


    public String getTileName() {
        return tileName;
    }

    public char toChar() {
        return tileChar;
    }
}
