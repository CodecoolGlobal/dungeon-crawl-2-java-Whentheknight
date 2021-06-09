package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {
    @Test
    public void testUnrecognizedCharacterThrowsException() {
        assertThrows(RuntimeException.class, () -> MapLoader.loadMap("vnaflkjdbvas"));
    }

    @Test
    public void testWidthEquals() {
        assertEquals(MapLoader.loadMap("10 0\n.").getWidth(), 10);
    }

    @Test
    public void testHeightEquals() {
        assertEquals(MapLoader.loadMap("10 0\n.").getWidth(), 10);
    }
}
