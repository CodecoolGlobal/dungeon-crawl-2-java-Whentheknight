package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.InventoryState;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class InventoryDaoJdbc implements InventoryDao{
    private DataSource dataSource;

    public InventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
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
        return null;
    }

    @Override
    public List<InventoryState> getAll() {
        return null;
    }
}
