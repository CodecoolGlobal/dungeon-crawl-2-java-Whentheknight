package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {

    @Test
    public void testWidthEquals() {
        assertEquals(MapLoader.loadMap("10 0\n.").getWidth(), 10);
    }

    @Test
    public void testHeightEquals() {
        assertEquals(MapLoader.loadMap("0 2\n.\n.").getHeight(), 2);
    }

    @Test
    public void testWidthUnrecognizedCharacterThrowsException() {
        assertThrows(RuntimeException.class, () -> MapLoader.loadMap("a 0\n.")) ;
    }

    @Test
    public void testHeightUnrecognizedCharacterThrowsException() {
        assertThrows(RuntimeException.class, () -> MapLoader.loadMap("0 a\n.")) ;
    }

    @Test
    public void testLoadMapUnrecognizedCharacterThrowsException() {
        assertThrows(RuntimeException.class, () -> MapLoader.loadMap("10 10\nvnaflkjdbvas"));
    }

    @Test
    public void testLoadMapTypes() {
        String expectedString = "EMPTY, FLOOR, WALL, FLOOR, WALL2, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, FLOOR, RIVER, FLOOR, BRIDGE, CDOOR, FLOOR, ODOOR, FLOOR, TREE, FLOOR, LAVA, FLOOR, TOXIC, FLOOR, STAIRS, FLOOR, FLOOR, FLOOR";
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : MapLoader.loadMap("/testtext.txt").getCells()) {
            for (Cell cell : row) {
                sb.append(cell.getType().toString())
                        .append(", ");
            }
        }
        String testCellsToString = sb.substring(0, sb.length() - 2);
        assertEquals(expectedString, testCellsToString);
    }

    @Test
    public void testLoadMapActors() {
        String expectedString = "j, 1, 2, 3, s, 4, b, 5, g, 6, B, 7, @, 8, 9, 0";
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : MapLoader.loadMap("/testtext.txt").getCells()) {
            for (Cell cell : row) {
                if (cell.getActor() != null) {
                    sb.append(cell.getActor().toChar())
                            .append(", ");
                }
            }
        }
        String testCellsToString = sb.substring(0, sb.length() - 2);
        assertEquals(expectedString, testCellsToString);
    }
}
