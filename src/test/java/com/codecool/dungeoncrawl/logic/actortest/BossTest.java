package com.codecool.dungeoncrawl.logic.actortest;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Boss;
import com.codecool.dungeoncrawl.logic.actors.BossPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BossTest {
    Boss boss;
    GameMap map = new GameMap(4,4, CellType.FLOOR);
    Cell cell = new Cell(map, 1,1,CellType.FLOOR);


    @BeforeEach
    void setUp(){
        boss = new Boss(cell);
    }
    @Test
    void getTileName() {
        assertEquals("boss", boss.getTileName());
    }


    @Test
    void getBossParts_IfEmpty(){
        assertEquals(0, boss.getBossParts().size());
    }

    @Test
    void addCells() {
        Cell bossCell = new Cell(map, 1,2,CellType.LAVA);
        boss.addCells(bossCell);

        assertEquals(CellType.LAVA, boss.getBossParts().get(0).getType());

    }

    @Test
    void toChar_Test() {
        assertEquals('j', boss.toChar());
    }
}
