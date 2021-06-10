package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {

    public static GameMap loadMap(String currentMap) {
        Scanner scanner;
        if (!(currentMap.contains(".txt"))) {
             scanner = new Scanner(currentMap);
        }
        else {
            scanner = new Scanner(MapLoader.class.getResourceAsStream(currentMap));
        }
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
                        case '%':
                            cell.setType(CellType.WALL2);
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

                        case 'B':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Bear(cell));
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
                        case 'S':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Axe(cell, "axe");
                            break;


                        case 'j':
                            cell.setType(CellType.FLOOR);
                            map.setBoss(new Boss(cell));
                            map.getBoss().addCells(cell);
                            break;

                        case '1':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 2);
                            map.getBoss().addCells(cell);
                            break;
                        case '0':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 11);
                            map.getBoss().addCells(cell);
                            break;
                        case '2':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 3);
                            map.getBoss().addCells(cell);
                            break;
                        case '3':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 4);
                            map.getBoss().addCells(cell);
                            break;
                        case '4':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 5);
                            map.getBoss().addCells(cell);
                            break;
                        case '5':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 6);
                            map.getBoss().addCells(cell);
                            break;
                        case '6':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 7);
                            map.getBoss().addCells(cell);
                            break;
                        case '7':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 8);
                            map.getBoss().addCells(cell);
                            break;
                        case '8':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 9);
                            map.getBoss().addCells(cell);
                            break;
                        case '9':
                            cell.setType(CellType.FLOOR);
                            new BossPart(cell, map.getBoss(), 10);
                            map.getBoss().addCells(cell);
                            break;


                        case 'R':
                            cell.setType(CellType.RIVER);
                            break;
                        case 'r':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 'D':
                            cell.setType(CellType.FLOOR);
                            new DodgePotion(cell);
                            break;
                        case 'P':
                            cell.setType(CellType.FLOOR);
                            new StrengthPotion(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            new BatSoup(cell);
                            break;
                        case 'E':
                            cell.setType(CellType.FLOOR);
                            new BearSteak(cell);
                            break;
                        case 'O':
                            cell.setType(CellType.FLOOR);
                            new Bone(cell);
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
