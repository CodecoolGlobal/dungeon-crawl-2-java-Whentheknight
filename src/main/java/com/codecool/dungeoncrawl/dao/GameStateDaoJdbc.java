package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
            String[] maps = new String[state.getDiscoveredMaps().size()];
            for (int i = 0; i < state.getDiscoveredMaps().size(); i++) {
                maps[i] = state.getDiscoveredMaps().get(i);
            }
            String sql = "INSERT INTO game_state (name, current_map, discovered_maps, saved_at, player_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getName());
            statement.setString(2, state.getCurrentMap());
            statement.setArray(3, connection.createArrayOf("VARCHAR", maps));
            statement.setDate(4, state.getSavedAt());
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
        try (Connection connection = dataSource.getConnection()) {
            String[] maps = new String[state.getDiscoveredMaps().size()];
            for (int i = 0; i < state.getDiscoveredMaps().size(); i++) {
                maps[i] = state.getDiscoveredMaps().get(i);
            }
            String sql = "UPDATE game_state SET name = ?, current_map = ?, discovered_maps = ?, saved_at = ?, player_id = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getName());
            statement.setString(2, state.getCurrentMap());
            statement.setArray(3, connection.createArrayOf("VARCHAR", maps));
            statement.setDate(4, state.getSavedAt());
            statement.setInt(5, state.getPlayer().getId());
            statement.setString(6, state.getName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(String saveName) {
        try (Connection connection = dataSource.getConnection()) {

            String sql = "SELECT name, current_map, discovered_maps, saved_at, player_id FROM game_state WHERE name = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, saveName);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;
            String name = resultSet.getString(1);
            String currentMap = resultSet.getString(2);
            Array map = resultSet.getArray(3);
            String[]maps = (String[])map.getArray();
            List<String> discovered_maps = new ArrayList<>();
            discovered_maps.addAll(Arrays.asList(maps));
            java.sql.Date savedAt = resultSet.getDate(4);
            int playerId = resultSet.getInt(5);
            PlayerModel player = playerDao.get(playerId);
            GameState gameState = new GameState(currentMap, name, savedAt, player, discovered_maps);
            return gameState;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameState> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT name, current_map, discovered_maps, saved_at, player_id FROM game_state";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            List<GameState> result = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString(1);
                String currentMap = rs.getString(2);
                Array map = rs.getArray(3);
                String[]maps = (String[])map.getArray();
                List<String> discovered_maps = new ArrayList<>();
                discovered_maps.addAll(Arrays.asList(maps));
                java.sql.Date savedAt = rs.getDate(4);
                int playerId = rs.getInt(5);

                PlayerModel player = playerDao.get(playerId);
                GameState gameState = new GameState(currentMap, name, savedAt, player, discovered_maps);
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

    public int getPlayerId(String saveName) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = " SELECT player_id FROM game_state WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, saveName);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return -1;
            return resultSet.getInt(1);
        }
        catch (SQLException e) {throw new RuntimeException();}
    }
}
