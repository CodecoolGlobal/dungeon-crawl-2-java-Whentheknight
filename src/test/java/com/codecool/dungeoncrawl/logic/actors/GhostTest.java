package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {
    GameMap gameMap;
    Ghost ghost;
    Player player;

    @BeforeEach
    void init() {
        gameMap = new GameMap(6, 6, CellType.FLOOR);
        ghost = new Ghost(gameMap.getCell(3,4));
        player = new Player(gameMap.getCell(2, 2));
        gameMap.setPlayer(player);
    }

    @Test
    void ghostMovesTowardsPlayer() {
        Ghost ghost2 = new Ghost(gameMap.getCell(4, 2));
        Ghost ghost3 = new Ghost(gameMap.getCell(2, 0));
        Ghost ghost4 = new Ghost(gameMap.getCell(0, 2));
        ghost.move();
        ghost2.move();
        ghost3.move();
        ghost4.move();
        assertEquals(3, ghost.getX());
        assertEquals(3, ghost.getY());
        assertEquals(3, ghost2.getX());
        assertEquals(2, ghost2.getY());
        assertEquals(2, ghost3.getX());
        assertEquals(1, ghost3.getY());
        assertEquals(1, ghost4.getX());
        assertEquals(2, ghost4.getY());
        assertEquals(ghost, gameMap.getCell(3, 3).getActor());
        assertEquals(ghost2, gameMap.getCell(3, 2).getActor());
        assertNull(gameMap.getCell(3, 4).getActor());
        assertNull(gameMap.getCell(4, 0).getActor());
    }

    @Test
    void ghostHasNeighbor() {
        Ghost ghost2 = new Ghost(gameMap.getCell(1, 2));
        Ghost ghost3 = new Ghost(gameMap.getCell(2, 1));
        Ghost ghost4 = new Ghost(gameMap.getCell(3, 2));
        Ghost ghost5 = new Ghost(gameMap.getCell(2, 3));
        assertTrue(ghost2.isPlayerNeighbor());
        assertTrue(ghost3.isPlayerNeighbor());
        assertTrue(ghost4.isPlayerNeighbor());
        assertTrue(ghost5.isPlayerNeighbor());
        assertFalse(ghost.isPlayerNeighbor());
    }

    @Test
    void ghostDoesNotMoveIfHasPlayerNeighbor() {
        Ghost ghost2 = new Ghost(gameMap.getCell(2, 1));
        ghost2.move();
        assertEquals(2, ghost2.getX());
        assertEquals(1, ghost2.getY());
        assertEquals(ghost2, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void ghostCanMoveThroughWalls() {
        GameMap wallMap = new GameMap(5, 5, CellType.WALL);
        Ghost wallGhost = new Ghost(wallMap.getCell(3, 4));
        Player wallPlayer = new Player(wallMap.getCell(1, 1));
        wallMap.setPlayer(wallPlayer);
        wallGhost.move();
        assertEquals(3, wallGhost.getX());
        assertEquals(3, wallGhost.getY());
        assertEquals(wallGhost, wallMap.getCell(3, 3).getActor());
        assertNull(wallMap.getCell(3, 4).getActor());
    }

    @Test
    void ghostMovesAroundOtherMonsters() {
        GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
        Player player = new Player(gameMap.getCell(5, 5));
        gameMap.setPlayer(player);
        Ghost ghost1 = new Ghost(gameMap.getCell(0, 5));
        gameMap.getCell(1, 5).setActor(new Skeleton(gameMap.getCell(1, 5)));
        Ghost ghost2 = new Ghost(gameMap.getCell(5, 0));
        gameMap.getCell(5, 1).setActor(new Skeleton(gameMap.getCell(5, 1)));
        Ghost ghost3 = new Ghost(gameMap.getCell(9, 3));
        gameMap.getCell(8, 3).setActor(new Skeleton(gameMap.getCell(8, 3)));
        Ghost ghost4 = new Ghost(gameMap.getCell(5, 9));
        gameMap.getCell(5, 8).setActor(new Skeleton(gameMap.getCell(5, 8)));
        Ghost ghost5 = new Ghost(gameMap.getCell(0, 8));
        gameMap.getCell(1, 8).setActor(new Skeleton(gameMap.getCell(1, 8)));
        Ghost ghost6 = new Ghost(gameMap.getCell(8, 0));
        gameMap.getCell(8, 1).setActor(new Skeleton(gameMap.getCell(8, 1)));
        Ghost ghost7 = new Ghost(gameMap.getCell(8, 6));
        gameMap.getCell(7, 6).setActor(new Skeleton(gameMap.getCell(7, 6)));
        Ghost ghost8 = new Ghost(gameMap.getCell(6, 7));
        gameMap.getCell(6, 6).setActor(new Skeleton(gameMap.getCell(6, 6)));
        ghost1.move();
        ghost2.move();
        ghost3.move();
        ghost4.move();
        ghost5.move();
        ghost6.move();
        ghost7.move();
        ghost8.move();
        assertEquals(0, ghost1.getX());
        assertEquals(6, ghost1.getY());
        assertEquals(6, ghost2.getX());
        assertEquals(0, ghost2.getY());
        assertEquals(9, ghost3.getX());
        assertEquals(4, ghost3.getY());
        assertEquals(6, ghost4.getX());
        assertEquals(9, ghost4.getY());
        assertEquals(0, ghost5.getX());
        assertEquals(7, ghost5.getY());
        assertEquals(7, ghost6.getX());
        assertEquals(0, ghost6.getY());
        assertEquals(8, ghost7.getX());
        assertEquals(5, ghost7.getY());
        assertEquals(5, ghost8.getX());
        assertEquals(7, ghost8.getY());



    }

    @Test
    void statsAreCorrect() {
        assertEquals(5, ghost.getHealth());
        assertEquals(3, ghost.getStrength());
        assertEquals(0.25f, ghost.getDodgeChance());
    }

    @Test
    void getTileNameReturnsCorrect() {
        assertEquals("ghost", ghost.getTileName());
    }

    @Test
    void toCharReturnsCorrect() {
        assertEquals('g', ghost.toChar());
    }

}
