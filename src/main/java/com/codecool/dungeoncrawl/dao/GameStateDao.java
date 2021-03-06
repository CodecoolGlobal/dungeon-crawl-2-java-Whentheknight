package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.sql.SQLException;
import java.util.List;

public interface GameStateDao {
    void add(GameState state);
    void update(GameState state);
    GameState get(String saveName);
    List<GameState> getAll() throws SQLException;
    List<String> getAllSaveName();
    int getPlayerId(String saveName);
}
