package com.codecool.dungeoncrawl.logic.items;


import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Axe;
import com.codecool.dungeoncrawl.logic.items.Item;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);
    Player player = new Player(gameMap.getCell(1, 1));


    @Test
    @Order(1)
    public void getCell_itemOnValidCell_true() {
        Item axe = new Axe(gameMap.getCell(1,2), "axe");
        Cell expected = gameMap.getCell(1,2);
        assertEquals(expected, axe.getCell());
    }
}
