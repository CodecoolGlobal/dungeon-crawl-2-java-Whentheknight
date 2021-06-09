package com.codecool.dungeoncrawl.logic.items;


import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoneTest {
    GameMap gameMap;
    Item bone;

    @BeforeEach
    public void init() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        bone = new Bone(gameMap.getCell(1,2));
    }

    @Test
    @Order(1)
    public void getTitleName_true() {
        assertEquals("bone", bone.getTileName());
    }

    @Test
    @Order(2)
    public void getStrength_true() {
        assertEquals(1, bone.getStrength());
    }

    @Test
    @Order(3)
    public void toChar_true() {
        assertEquals('O', bone.toChar());
    }

}