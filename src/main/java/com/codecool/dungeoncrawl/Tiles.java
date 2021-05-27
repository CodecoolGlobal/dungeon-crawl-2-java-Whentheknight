package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(7, 0));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));

        tileMap.put("sword", new Tile(0, 30));
        tileMap.put("axe", new Tile(10, 30));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("health potion", new Tile(1, 14));
        tileMap.put("dodge potion", new Tile(16, 25));
        tileMap.put("strength potion", new Tile(18, 25));
        tileMap.put("leather boots", new Tile(7, 23));

        tileMap.put("stairs", new Tile(3, 6));
        tileMap.put("closed door", new Tile(7,10));
        tileMap.put("open door", new Tile(8, 10));
        tileMap.put("ghost", new Tile(26,6));
        tileMap.put("bat", new Tile(26,8));
        tileMap.put("tree", new Tile(4,2));
        tileMap.put("lava", new Tile(6,0));
        tileMap.put("toxic", new Tile(19,11));
        tileMap.put("bear", new Tile(30, 8));
        tileMap.put("river", new Tile(8, 4));
        tileMap.put("bridge", new Tile(4, 6));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
