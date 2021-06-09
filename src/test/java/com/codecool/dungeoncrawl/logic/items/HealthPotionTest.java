package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HealthPotionTest {
    GameMap gameMap;
    Item healthPotion;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        healthPotion = new HealthPotion(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("health potion", healthPotion.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(10, healthPotion.getHealth());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('h', healthPotion.toChar());
    }

}