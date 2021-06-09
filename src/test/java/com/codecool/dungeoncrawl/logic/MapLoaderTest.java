package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapLoaderTest {
    @Test
    public void testUnrecognizedCharacterThrowsException() {
        assertThrows(RuntimeException.class, () -> MapLoader.loadMap("vnaflkjdbvas"));
    }
}
