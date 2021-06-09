package com.codecool.dungeoncrawl.logic.actortest;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.Bear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BatTest {
    GameMap gameMap;
    Bat bat;

    @BeforeEach
    void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        bat = new Bat(gameMap.getCell(1,1));
    }

    @Test
    void moveMovesBatRandomly() {
        bat.move();
        assertTrue(gameMap.getCell(0,1).getActor() == bat || gameMap.getCell(1,0).getActor() == bat || gameMap.getCell(2,1).getActor() == bat || gameMap.getCell(1,2).getActor() == bat);
        assertNull(gameMap.getCell(1, 1).getActor());
    }

    @Test
    void batStandsIfCannotMove() {
        GameMap smallMap = new GameMap(1, 1, CellType.FLOOR);
        Bat smallBat = new Bat(smallMap.getCell(0, 0));
        smallBat.move();
        assertEquals(0, smallBat.getX());
        assertEquals(0, smallBat.getY());
        assertEquals(smallBat, smallMap.getCell(0, 0).getActor());


    }

    @Test
    void batTeleportsOn6thMove() {
        GameMap tpGameMap = new GameMap(20, 20, CellType.FLOOR);
        Bat tpBat = new Bat(tpGameMap.getCell(9, 9));
        tpBat.setTeleport(0);
        int x = tpBat.getX();
        int y = tpBat.getY();
        tpBat.move();
        int newX = tpBat.getX();
        int newY = tpBat.getY();
        assertTrue((x == newX - 3 && y == newY) || (x == newX + 3 && y == newY) || (x == newX && y == newY - 3) || (x == newX && y == newY + 3));
    }

    @Test
    void batStandsIfCannotTeleport() {
        bat.setTeleport(0);
        Cell currentCell = bat.getCell();
        int x = bat.getX();
        int y = bat.getY();
        bat.move();
        assertEquals(currentCell, bat.getCell());
        assertEquals(bat, gameMap.getCell(x, y).getActor());
    }

    @Test
    void batCannotTpIntoWall() {
        GameMap wallMap = new GameMap(10, 10, CellType.WALL);
        Bat wallBat = new Bat(wallMap.getCell(5, 5));
        wallBat.setTeleport(0);
//        int x = wallBat.getX();
        wallBat.move();
        assertEquals(5, wallBat.getX());
        assertEquals(5, wallBat.getY());
        assertEquals(wallBat, wallMap.getCell(5, 5).getActor());
    }

    @Test
    void statsAreCorrect() {
        assertEquals(3, bat.getHealth());
        assertEquals(1, bat.getStrength());
        assertEquals(0.5f, bat.getDodgeChance());
    }


    @Test
    void getTileNameReturnsCorrect() {
        assertEquals("bat", bat.getTileName());
    }

    @Test
    void toCharReturnsCorrect() {
        assertEquals('b', bat.toChar());
    }


}
