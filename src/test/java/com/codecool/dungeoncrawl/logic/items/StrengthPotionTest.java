package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StrengthPotionTest {
    GameMap gameMap;
    Item strengthPotion;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        strengthPotion = new StrengthPotion(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("strength potion", strengthPotion.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(10, strengthPotion.getStrength());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('P', strengthPotion.toChar());
    }

    @Test
    @Order(4)
    public void strengthPotionWithoutParameters_true() {
        Item strengthPotion2 = new StrengthPotion();
        assertEquals(10, strengthPotion2.getStrength());
        assertEquals("strength potion", strengthPotion2.getTileName());
        assertEquals('P', strengthPotion2.toChar());
    }
}