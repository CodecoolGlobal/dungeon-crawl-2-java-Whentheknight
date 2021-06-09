package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KeyTest {
    GameMap gameMap;
    Item key;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        key = new Key(gameMap.getCell(1,2), 1);
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("key", key.getTileName());
    }

    @Test
    @Order(2)
    public void toChar_true() {
        assertEquals('k', key.toChar());
    }

    @Test
    @Order(3)
    public void keyWithoutParameters_true() {
        Item key2 = new Key();
        assertEquals("key", key2.getTileName());
        assertEquals('k', key2.toChar());
    }
}