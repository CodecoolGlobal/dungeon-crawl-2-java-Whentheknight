package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BossPartsTest {
    GameMap map = new GameMap(5,5, CellType.FLOOR);
    Cell bossCell = new Cell(map, 1,1,CellType.FLOOR);
    Cell bossPartCell = new Cell(map, 1,2,CellType.FLOOR);
    Boss boss = new Boss(bossCell);

    BossPart bossPart = new BossPart(bossPartCell, boss, 1);

    @Test
    void getBoss() {
        assertEquals(boss, bossPart.getBoss());
    }

    @Test
    void getTileName_Test() {
        String expected = "boss1";

        assertEquals(expected, bossPart.getTileName());
    }

    @Test
    void toChar_Test() {
        char expected = '1';

        assertEquals(expected, bossPart.toChar());
    }
}
