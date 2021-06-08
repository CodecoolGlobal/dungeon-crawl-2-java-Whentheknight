package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    private Item item;
    private List<Actor> enemies = new ArrayList<>();
    private Boss boss;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBoss(Boss boss){
        this.boss = boss;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setItem(Item item) {this.item = item;}

    public void addEnemy(Actor enemy){
        enemies.add(enemy);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Actor> getEnemies(){
        return enemies;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(cells[0].length + " " + cells[1].length + "\n");
        for(int i = 0; i < cells.length; i++) {
            for(Cell cell : cells[i]) {
                if(cell.getActor() != null) {
                    result.append(cell.getActor().toChar());
                } else if (cell.getItem() != null) {
                    result.append(cell.getItem().toChar());
                } else {
                    result.append(cell.getType().toChar());
                }
            }
            result.append("\n");
            }
        return result.toString();
    }
}
