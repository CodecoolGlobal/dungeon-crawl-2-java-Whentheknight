package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DodgePotionTest {
    GameMap gameMap;
    Item dodgePotion;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        dodgePotion = new DodgePotion(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("dodge potion", dodgePotion.getTileName());
    }

    @Test
    @Order(2)
    public void getDodge_true() {
        assertEquals(0.1f, dodgePotion.getDodgeChance());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('D', dodgePotion.toChar());
    }

    @Test
    @Order(4)
    public void dodgePotionWithoutParameters_true() {
        Item dodgePotion2 = new DodgePotion();
        assertEquals(0.1f, dodgePotion2.getDodgeChance());
        assertEquals("dodge potion", dodgePotion2.getTileName());
        assertEquals('D', dodgePotion2.toChar());
    }
}