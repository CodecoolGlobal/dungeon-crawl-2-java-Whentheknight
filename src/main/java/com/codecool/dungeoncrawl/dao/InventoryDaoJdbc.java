package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.*;
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
            String sql = "INSERT INTO inventory (player_id, item_name, strength_mod, health_mod, dodge_mod) VALUES (?, ?, ?, ?,?);";
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
            ResultSet rs;
            String sql = "SELECT player_id, item_name, strength_mod, health_mod, dodge_mod FROM inventory WHERE inventory.player_id = ?;";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1,id);
            rs = st.executeQuery();
            List<Item> items = new ArrayList<>();

            while(rs.next()){
            String itemName = rs.getString(2);
            switch (itemName){
                case "axe":
                    Axe axe = new Axe();
                    items.add(axe);
                    break;
                case "bone":
                    Bone bone = new Bone();
                    items.add(bone);
                    break;
                case "bat soup":
                    BatSoup batSoup = new BatSoup();
                    items.add(batSoup);
                    break;
                case "bear steak":
                    BearSteak bearSteak = new BearSteak();
                    items.add(bearSteak);
                    break;
                case "health potion":
                    HealthPotion healthPotion = new HealthPotion();
                    items.add(healthPotion);
                    break;
                case "key":
                    Key key = new Key();
                    items.add(key);
                    break;
                case "leather boots":
                    LeatherBoots leatherBoots = new LeatherBoots();
                    items.add(leatherBoots);
                    break;
                case "strength potion":
                    StrengthPotion strengthPotion = new StrengthPotion();
                    items.add(strengthPotion);
                    break;
                case "dodge potion":
                    DodgePotion dodgePotion = new DodgePotion();
                    items.add(dodgePotion);
                    break;
                case "sword":
                    Sword sword = new Sword();
                    items.add(sword);
                    break;
            }}

            InventoryState inventory = new InventoryState(items,id);
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
