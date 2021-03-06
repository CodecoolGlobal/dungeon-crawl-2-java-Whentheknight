package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.InventoryState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private InventoryDao inventoryDao;
    private GameStateDao gameStateDao;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        inventoryDao = new InventoryDaoJdbc(dataSource);
        playerDao = new PlayerDaoJdbc(dataSource, inventoryDao);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
    }

    public List<GameState> getGameStates() throws SQLException {
        return gameStateDao.getAll();
    }

    public int getPlayerIdBySaveName(String saveName) {
        return gameStateDao.getPlayerId(saveName);
    }


    public GameState getGameState(String saveName) {
        return gameStateDao.get(saveName);
    }

    public InventoryState getInventoryByPLayerId(int id) {
        return inventoryDao.get(id);
    }


    public List<String> getAllSaveName() {
        return gameStateDao.getAllSaveName();
    }

    public void saveGameState(GameState gameState) {
        gameStateDao.add(gameState);
    }

    public void updateGameState(GameState gameState) {
        gameStateDao.update(gameState);
    }

    public void updatePlayer(PlayerModel player) {
        playerDao.update(player);
        updateInventory(player.getInventory(), player.getId());
    }

    public void savePlayer(Player player, GameState gameState) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
        saveInventory(player.getInventory(), model);
        gameState.setPlayer(model);
        saveGameState(gameState);
    }


    public void updateInventory(List<Item> inventory, int playerId) {
        inventoryDao.update(new InventoryState(inventory, playerId));
    }

    public void saveInventory(List<Item> inventory, PlayerModel player){
        InventoryState inventoryS = new InventoryState(inventory, player.getId());
        inventoryDao.add(inventoryS);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
