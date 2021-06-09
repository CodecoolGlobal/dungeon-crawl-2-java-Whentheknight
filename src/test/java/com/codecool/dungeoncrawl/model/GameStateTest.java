package com.codecool.dungeoncrawl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateTest {
    GameState gameState;
    @BeforeEach
    void init(){
    gameState = new GameState();}

    @Test
    void setSavedAt_getSavedAt_Test(){
        gameState.setSavedAt(new Date(System.currentTimeMillis()));

        assertEquals(new Date(System.currentTimeMillis()),gameState.getSavedAt());
    }

    @Test
    void setName_getName_Test(){
        gameState.setName("My name");

        assertEquals("My name", gameState.getName());
    }

    @Test
    void setCurrentMap_getCurrentMap_Test(){
        gameState.setCurrentMap("This map");

        assertEquals("This map", gameState.getCurrentMap());
    }

    @Test
    void getDiscoveredMaps_addDiscoveredMap_Test(){
        gameState.addDiscoveredMap("This is an added map");

        assertEquals("This is an added map", gameState.getDiscoveredMaps().get(0));
    }

    @Test
    void setPlayer_getPlayer_Test(){
        gameState.setPlayer(new PlayerModel("Test",10,4,0,1));

        assertEquals("Test", gameState.getPlayer().getPlayerName());
    }
}
