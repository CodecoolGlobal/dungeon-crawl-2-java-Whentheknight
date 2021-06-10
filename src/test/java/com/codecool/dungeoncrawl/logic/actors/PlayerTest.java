package com.codecool.dungeoncrawl.logic.actors;


import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerTest {
    GameMap gameMap;
    Player player;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1,1));
    }


    @Test
    @Order(1)
    public void getTileName_true() {
        assertEquals("player", player.getTileName());
    }

    @Test
    @Order(2)
    public void addToInventory_playerHasKey_true() {
        player.addToInventory(new Key());
        assertTrue(player.hasKey());
    }

    @Test
    @Order(3)
    public void removeKey_false() {
        player.addToInventory(new Key());
        player.removeKey();
        assertFalse(player.hasKey());
    }

    @Test
    @Order(4)
    public void getCurrentMap_true() {
        player.setCurrentMap(1);
        assertEquals(1, player.getCurrentMap());
    }

    @Test
    @Order(5)
    public void hasGhostNeighbor_true() {
        Actor ghost = new Ghost(gameMap.getCell(1,2));
        assertTrue(player.hasGhostNeighbor());
    }

    @Test
    @Order(6)
    public void getName() {
        player.setName("admin");
        assertEquals("admin", player.getName());
    }

    @Test
    @Order(7)
    public void toChar_true() {
        assertEquals('@', player.toChar());
    }


    @Test
    @Order(8)
    public void addStats_true() {
        player.addStats(new Axe());
        assertEquals(15, player.getStrength());
    }

    @Test
    @Order(9)
    public void getIsHaunted_true() {
        player.setIsHaunted(true);
        assertTrue(player.getIsHaunted());
    }

    @Test
    @Order(10)
    public void getIsBurning_true() {
        player.setIsBurning(true);
        assertTrue(player.getIsBurning());
    }

    @Test
    @Order(11)
    public void getIsPoisoned_true() {
        player.setIsPoisoned(true);
        assertTrue(player.getIsPoisoned());
    }

    @Test
    @Order(12)
    public void move_moveToLava_true() {
        gameMap.setCell(1,2, CellType.LAVA);
        player.move(0,1);
        assertTrue(player.getIsBurning());
        assertEquals(-2, player.getHealthChange());
    }

    @Test
    @Order(13)
    public void move_moveToToxic_true() {
        gameMap.setCell(1,2, CellType.TOXIC);
        player.move(0,1);
        assertTrue(player.getIsPoisoned());
        assertEquals(-1, player.getHealthChange());
    }

    @Test
    @Order(14)
    public void getCurrentEnemy() {
        Actor bear = new Bear(gameMap.getCell(1,2));
        player.move(0,1);
        assertEquals(bear, player.getCurrentEnemy());
    }

    @Test
    @Order(15)
    public void getCurrentEnemy_enemyIsBoss_true() {
        Actor boss = new Boss(gameMap.getCell(1,2));
        player.move(0,1);
        assertEquals(boss, player.getCurrentEnemy());
    }

    @Test
    @Order(16)
    public void getInventory_true() {
        Item key = new Key();
        Item axe = new Axe();
        player.addToInventory(key);
        player.addToInventory(axe);
        List<Item> expected = new ArrayList<>();
        expected.add(key);
        expected.add(axe);
        assertEquals(expected, player.getInventory());
    }

    @Test
    @Order(17)
    public void move_adminCanMoveThroughWalls() {
        gameMap.setCell(1,2, CellType.WALL);
        player.setName("admin");
        player.move(0,1);
        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
    }


    @Test
    @Order(18)
    public void decreaseBurning_true() {
        player.setBurningFor(3);
        player.decreaseBurning();
        assertEquals(2, player.getBurningFor());
    }

    @Test
    @Order(19)
    public void decreasePoison_true() {
        player.setPoisonedFor(3);
        player.decreasePoison();
        assertEquals(2, player.getPoisonedFor());
    }


}