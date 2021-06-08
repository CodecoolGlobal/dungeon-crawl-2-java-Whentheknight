package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (name, current_map, discoverd_maps, save_at, player_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getName());
            statement.setString(2, state.getCurrentMap());
            statement.setArray(3, (Array) state.getDiscoveredMaps());
            statement.setDate(4, new java.sql.Date(new Date().getTime()));
            statement.setInt(5, state.getPlayer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        }
        catch (SQLException e ) {
            throw new RuntimeException(e);
        }
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
                java.sql.Date savedAt = rs.getDate(3);
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
