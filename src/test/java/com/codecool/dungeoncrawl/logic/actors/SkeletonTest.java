package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkeletonTest {
    GameMap gameMap;
    Skeleton skeleton;

    @BeforeEach
    void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        skeleton = new Skeleton(gameMap.getCell(2,1));
    }

    @Test
    void moveDoesNotChangePosition() {
        skeleton.move();
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void statsAreCorrect() {
        assertEquals(15, skeleton.getHealth());
        assertEquals(2, skeleton.getStrength());
        assertEquals(0.1f, skeleton.getDodgeChance());
    }

    @Test
    void getTileNameReturnsCorrect() {
        assertEquals("skeleton", skeleton.getTileName());
    }

    @Test
    void toCharReturnsCorrect() {
        assertEquals('s', skeleton.toChar());
    }
}
