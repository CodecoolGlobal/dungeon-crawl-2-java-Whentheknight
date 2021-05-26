package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;

                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;

                        case 'b':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Bat(cell));
                            break;

                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Ghost(cell));
                            break;

                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;

                        case 'W':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell, "sword");
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell, 1);
                            break;
                        case 'l':
                            cell.setType(CellType.FLOOR);
                            new LeatherBoots(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.CDOOR);
                            break;
                        case 'o':
                            cell.setType(CellType.ODOOR);
                            break;
                        case 't':
                            cell.setType(CellType.TREE);
                            break;
                        case 'a':
                            cell.setType(CellType.LAVA);
                            break;
                        case 'x':
                            cell.setType(CellType.TOXIC);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
