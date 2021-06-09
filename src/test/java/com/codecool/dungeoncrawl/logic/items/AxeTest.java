package com.codecool.dungeoncrawl.logic.items;


import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AxeTest {
    GameMap gameMap;
    Item axe;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        axe = new Axe(gameMap.getCell(1,2), "axe");
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("axe", axe.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(10, axe.getStrength());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('A', axe.toChar());
    }

    @Test
    @Order(4)
    public void axe_withoutParameters_true() {
        Item axe2 = new Axe();
        assertEquals(10, axe2.getStrength());
        assertEquals("axe", axe2.getTileName());
        assertEquals('A', axe2.toChar());
    }
}
