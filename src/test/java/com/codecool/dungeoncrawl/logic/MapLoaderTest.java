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
}
