package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;
    private InventoryDao inventoryDao;

    public PlayerDaoJdbc(DataSource dataSource, InventoryDao inventoryDao) {
        this.dataSource = dataSource;
        this.inventoryDao = inventoryDao;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, strength, x, y) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getStrength());
            statement.setInt(4, player.getX());
            statement.setInt(5, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "UPDATE player SET player_name = ?, hp = ?, strength = ?, x = ?, y = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, player.getPlayerName());
            st.setInt(2, player.getHp());
            st.setInt(3, player.getStrength());
            st.setInt(4, player.getX());
            st.setInt(5, player.getY());
            st.setInt(6, player.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PlayerModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_name, hp, strength, x, y FROM player WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }
            PlayerModel player = new PlayerModel(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
            player.setInventory(inventoryDao.get(id).getInventory());
            player.setId(id);
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, hp, strength, x, y FROM player";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            List<PlayerModel> result = new ArrayList<>();
            while (rs.next()) {
                PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                player.setId(rs.getInt(1));
                result.add(player);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all authors", e);
        }
    }
}

