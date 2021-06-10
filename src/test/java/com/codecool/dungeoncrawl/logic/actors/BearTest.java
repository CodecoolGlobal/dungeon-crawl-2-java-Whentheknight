package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BearTest {
    GameMap gameMap;
    Bear bear;

    @BeforeEach
    void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        bear = new Bear(gameMap.getCell(2,1));
    }

    @Test
    void moveDoesNotChangePosition() {
        bear.move();
        assertEquals(2, bear.getX());
        assertEquals(1, bear.getY());
        assertEquals(bear, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void statsAreCorrect() {
        assertEquals(20, bear.getHealth());
        assertEquals(5, bear.getStrength());
        assertEquals(0.1f, bear.getDodgeChance());
    }

    @Test
    void getTileNameReturnsCorrect() {
        assertEquals("bear", bear.getTileName());
    }

    @Test
    void toCharReturnsCorrect() {
        assertEquals('B', bear.toChar());
    }


}
