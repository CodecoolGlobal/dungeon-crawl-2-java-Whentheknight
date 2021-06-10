package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwordTest {
    GameMap gameMap;
    Item sword;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        sword = new Sword(gameMap.getCell(1,2), "sword");
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("sword", sword.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(5, sword.getStrength());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('W', sword.toChar());
    }

    @Test
    @Order(4)
    public void sword_withoutParameters_true() {
        Item sword2 = new Sword();
        assertEquals(5, sword2.getStrength());
        assertEquals("sword", sword2.getTileName());
        assertEquals('W', sword2.toChar());
    }
}