package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BatSoupTest {
    GameMap gameMap;
    Item batSoup;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        batSoup = new BatSoup(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("bat soup", batSoup.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(-3, batSoup.getStrength());
    }

    @Test
    @Order(3)
    public void getHealth_true() {
        assertEquals(-3, batSoup.getHealth());
    }

    @Test
    @Order(4)
    public void toChar_true() {
        assertEquals('u', batSoup.toChar());
    }
}
