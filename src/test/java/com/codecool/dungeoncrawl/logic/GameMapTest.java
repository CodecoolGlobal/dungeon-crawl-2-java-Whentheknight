package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    GameMap map;

    @BeforeEach
    void setUp(){
        map = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void getCell() {
        map.setCell(0,1,CellType.LAVA);

        assertEquals(CellType.LAVA, map.getCell(0,1).getType());
    }

    @Test
    void setCell() {
        Cell[][] cells = new Cell[3][3];
        cells[1][1] = new Cell(map, 1, 1, CellType.BRIDGE);

        map.setCell(1,1,CellType.BRIDGE);

        assertEquals(cells[1][1].getType(), map.getCell(1,1).getType());
    }

    @Test
    void getCells() {
        Cell[][] expectedCells = new Cell[3][3];

        assertEquals(expectedCells.length, map.getCells().length);
        assertEquals(expectedCells[0].length, map.getCells()[0].length);
    }

    @Test
    void setPlayer() {
        Player player = new Player(new Cell(map,1,1,CellType.FLOOR));
        player.setName("Tom");
        map.setPlayer(player);

        assertEquals(map.getPlayer().getName(), player.getName());
    }

    @Test
    void getPlayer_IfNoPlayer() {
        assertNull(map.getPlayer());
    }

    @Test
    void setBoss() {
        Boss boss = new Boss(new Cell(map,0,1,CellType.FLOOR));
        map.setBoss(boss);

        assertNotNull(map.getBoss());

    }

    @Test
    void getBoss() {
        Boss boss = new Boss(new Cell(map,0,1,CellType.FLOOR));
        map.setBoss(boss);

        assertEquals(0, map.getBoss().getX());
    }

    @Test
    void addMultipleEnemies() {
        Bat bat = new Bat(new Cell(map,1,2,CellType.FLOOR));
        Skeleton skeleton = new Skeleton(new Cell(map,1,2,CellType.FLOOR));

        map.addEnemy(bat);
        map.addEnemy(skeleton);

        assertEquals(2, map.getEnemies().size());
    }

    @Test
    void getEnemies_If_There_Are_No_Enemies() {
        assertEquals(0,map.getEnemies().size());
    }

    @Test
    void setEnemies() {
        Bat bat = new Bat(new Cell(map,1,2,CellType.FLOOR));
        Skeleton skeleton = new Skeleton(new Cell(map,1,2,CellType.FLOOR));
        List<Actor> enemyList = new ArrayList<>();
       enemyList.add(bat);
       enemyList.add(skeleton);

        map.setEnemies(enemyList);

        assertEquals(2, map.getEnemies().size());
    }

    @Test
    void getWidth() {
        assertEquals(3,map.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(3,map.getHeight());
    }

    @Test
    void toString_Test() {
        StringBuilder expected = new StringBuilder();
        expected.append(map.getHeight()).append(" ").append(map.getWidth()).append("\n");
        for(int i=0; i < map.getHeight(); i++){
            expected.append(".".repeat(map.getWidth())).append("\n");
        }
        assertEquals(expected.toString(), map.toString());
    }
}
