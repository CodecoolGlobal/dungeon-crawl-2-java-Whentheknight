package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.model.InventoryState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryDaoJdbc implements InventoryDao{
    private DataSource dataSource;
    private PlayerDao playerDao;

    public InventoryDaoJdbc(DataSource dataSource,PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(InventoryState inventory) {

        try (Connection conn = dataSource.getConnection()) {
            for(Item item: inventory.getInventory()){
            String sql = "INSERT INTO inventory (player_id, item_name, strength_mod, health_mod, dodge_mod) VALUES (?, ?, ?, ?,?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, inventory.getPlayerId());
            statement.setString(2, item.getTileName());
            statement.setInt(3, item.getStrength());
            statement.setInt(4, item.getHealth());
            statement.setFloat(5, item.getDodgeChance());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            inventory.setId(resultSet.getInt(1));}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(InventoryState inventory) {

    }

    @Override
    public InventoryState get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_id, item_name, strength_mod, health_mod, dodge_mod FROM inventory WHERE inventory.player_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int playerId = rs.getInt(1);
            PlayerModel player = playerDao.get(playerId);
            InventoryState inventory = new InventoryState(player.getInventory(),playerId);
            inventory.setId(id);
            return inventory;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InventoryState> getAll() {
        return null;
    }
}
