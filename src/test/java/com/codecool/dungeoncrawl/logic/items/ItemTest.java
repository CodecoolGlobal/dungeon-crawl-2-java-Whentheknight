package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemTest {
    GameMap gameMap;
    Player player;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1, 1));
    }

    @Test
    @Order(1)
    public void getCell_itemOnValidCell_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        Cell expected = gameMap.getCell(1,2);
        assertEquals(expected, axe.getCell());
    }

    @Test
    @Order(2)
    public void getX_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        assertEquals(1, axe.getX());
    }

    @Test
    @Order(3)
    public void getY_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        assertEquals(2, axe.getY());
    }

    @Test
    @Order(4)
    public void getStrength_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        assertEquals(10, axe.getStrength());
    }

    @Test
    @Order(5)
    public void setStrength() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        axe.setStrength(120);
        assertEquals(120, axe.getStrength());
    }

    @Test
    @Order(6)
    public void getHealth_true() {
        Item bearSteak = new BearSteak(gameMap.getCell(1,2));
        assertEquals(5, bearSteak.getHealth());
    }

    @Test
    @Order(7)
    public void setHealth_true() {
        Item bearSteak = new BearSteak(gameMap.getCell(1,2));
        bearSteak.setHealth(100);
        assertEquals(100, bearSteak.getHealth());
    }

    @Test
    @Order(8)
    public void getDodgeChance_true() {
        Item leatherBoots = new LeatherBoots(gameMap.getCell(1,2));
        assertEquals(0.1f, leatherBoots.getDodgeChance());
    }

    @Test
    @Order(9)
    public void setDodgeChance_true() {
        Item leatherBoots = new LeatherBoots(gameMap.getCell(1,2));
        leatherBoots.setDodgeChance(0.5f);
        assertEquals(0.5f, leatherBoots.getDodgeChance());
    }

    @Test
    @Order(10)
    public void toChar_true() {
        Item leatherBoots = new LeatherBoots(gameMap.getCell(1,2));
        assertEquals('l', leatherBoots.toChar());
    }

    @Test
    @Order(11)
    public void hasStats_itemWithStats_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        assertTrue(axe.hasStats());
    }

    @Test
    @Order(12)
    public void hasStats_itemWithNoStats_false() {
        Item key = new Key(gameMap.getCell(1,2), 1);
        assertFalse(key.hasStats());
    }

    @Test
    @Order(13)
    public void itemWithoutParameters_true() {
        Item axe = new Axe();
        assertEquals(10, axe.getStrength());
        assertEquals("axe", axe.getTileName());
        assertEquals('A', axe.toChar());
    }
}
