package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LeatherBootsTest {
    GameMap gameMap;
    Item leatherBoots;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        leatherBoots = new LeatherBoots(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("leather boots", leatherBoots.getTileName());
    }

    @Test
    @Order(2)
    public void getHealth_true() {
        assertEquals(3, leatherBoots.getHealth());
    }

    @Test
    @Order(2)
    public void getDodgeChance_true() {
        assertEquals(0.1f, leatherBoots.getDodgeChance());
    }

    @Test
    @Order(4)
    public void toChar_true() {
        assertEquals('l', leatherBoots.toChar());
    }

    @Test
    @Order(5)
    public void leatherBoots_withoutParameters_true() {
        Item leatherBoots2 = new LeatherBoots();
        assertEquals(0.1f, leatherBoots2.getDodgeChance());
        assertEquals(3, leatherBoots2.getHealth());
        assertEquals("leather boots", leatherBoots2.getTileName());
        assertEquals('l', leatherBoots2.toChar());
    }
}