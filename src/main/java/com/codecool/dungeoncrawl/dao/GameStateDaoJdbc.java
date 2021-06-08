package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private PlayerDao playerDao;
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
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
    public List<GameState> getAll() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, current_map, saved_at, player_id FROM game_state";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<GameState> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString(1);
                String currentMap = rs.getString(2);
                Date savedAt = rs.getDate(3);
                int playerId = rs.getInt(4);

                PlayerModel player = playerDao.get(playerId);
                GameState gameState = new GameState(currentMap, name, savedAt, player);
                result.add(gameState);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
