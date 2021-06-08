package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state) {

    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<GameState> getAll() {
        return null;
    }


    public List<String> getAllSaveName() {
       try (Connection connection = dataSource.getConnection()) {
           String sql = "SELECT name FROM game_state";
           ResultSet resultSet = connection.createStatement().executeQuery(sql);
           List<String> saveNames = new ArrayList<>();
           while (resultSet.next()) {
               String saveName = resultSet.getString(1);
               saveNames.add(saveName);
           }
           return saveNames;
       }
       catch (SQLException e) {
           throw new RuntimeException("Error while reading all save name");
       }
    }
}
