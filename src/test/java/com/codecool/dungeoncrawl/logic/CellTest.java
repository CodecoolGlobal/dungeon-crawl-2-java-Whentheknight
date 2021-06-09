package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.Bear;
import com.codecool.dungeoncrawl.logic.items.Sword;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1, 1);
        Cell neighbor = cell.getNeighbor(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbor(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbor(0, 1));
    }

    @Test
    void getType() {
        Cell cell = new Cell(map,2,3,CellType.TOXIC);
        assertEquals(CellType.TOXIC, cell.getType());
    }

    @Test
    void setType() {
        Cell cell = new Cell(map,2,3,CellType.TOXIC);
        cell.setType(CellType.BRIDGE);

        assertEquals(CellType.BRIDGE, cell.getType());
    }

    @Test
    void setActor() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);
        cell.setActor(new Bat(cell));

        assertTrue(cell.getActor() instanceof Bat);
    }

    @Test
    void getActor_IfNoActor() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);

        assertThrows(NullPointerException.class, () -> cell.getActor().getTileName());
    }

    @Test
    void setItem() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);
        cell.setItem(new Sword());

        assertTrue(cell.getItem() instanceof Sword);

    }

    @Test
    void getItem() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);
        cell.setItem(new Sword());

        assertEquals("sword", cell.getItem().getTileName());
    }

    @Test
    void getTileName() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);

        assertEquals("floor", cell.getTileName());

    }

    @Test
    void getX() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);

        assertEquals(2, cell.getX());
    }

    @Test
    void getY() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);

        assertEquals(3, cell.getY());
    }

    @Test
    void removeActor() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);
        Cell cell2 = new Cell(map,4,5,CellType.FLOOR);
        Bat bat = new Bat(cell);
        cell.setActor(bat);

        List<Actor> enemies = new ArrayList<>();

        enemies.add(bat);
        enemies.add(new Bear(cell2));

        map.setEnemies(enemies);
        cell.removeActor();

        assertFalse(map.getEnemies().get(0) instanceof Bat);

    }

    @Test
    void getMap() {
        Cell cell = new Cell(map,2,3,CellType.FLOOR);
        assertEquals(map, cell.getMap());
    }
}