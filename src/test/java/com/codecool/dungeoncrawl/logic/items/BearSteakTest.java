package com.codecool.dungeoncrawl.logic.items;



import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BearSteakTest {
    GameMap gameMap;
    Item bearSteak;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        bearSteak = new BearSteak(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("bear steak", bearSteak.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(2, bearSteak.getStrength());
    }

    @Test
    @Order(3)
    public void getHealth_true() {
        assertEquals(5, bearSteak.getHealth());
    }

    @Test
    @Order(4)
    public void toChar_true() {
        assertEquals('E', bearSteak.toChar());
    }

}