package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.GameStateDao;
import com.codecool.dungeoncrawl.dao.GameStateDaoJdbc;
import com.codecool.dungeoncrawl.dao.PlayerDao;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.model.InventoryState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.postgresql.ds.PGSimpleDataSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import javax.sql.DataSource;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main extends Application {
    private final int mapWidth = 25;
    private final int mapHeight = 20;
    private boolean s1 = false;
    private boolean isLoad = false;
    private GameState loadGameState;
    GameDatabaseManager databaseM = new GameDatabaseManager();
    String[] mapList = {"/map.txt", "/map2.txt", "/bossmap.txt"};
    List<GameMap> earlierMaps = new ArrayList<>();

    GameMap map = MapLoader.loadMap(mapList[0]);

    Canvas canvas = new Canvas(
            mapWidth * Tiles.TILE_WIDTH,
            mapHeight * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    GridPane ui = new GridPane();

    Label playerLabel = new Label("Player");
    Label hauntedLabel = new Label();
    Label burningLabel = new Label();
    Label poisonLabel = new Label();
    Label healthLabel = new Label(), strengthLabel = new Label(), dodgeLabel = new Label(), inventory = new Label();
    Label enemyLabel = new Label(), enemyHealthTextLabel = new Label("Health: "), enemyHealthNumLabel = new Label();
    Label enemyStrengthTextLabel = new Label("Strength: "), enemyStrengthNumLabel = new Label();
    Label enemyDodgeTextLabel = new Label("Dodge Chance: "), enemyDodgeNumLabel = new Label();
    Label playerHealthChangeLabel = new Label(), enemyHealthChangeLabel = new Label();
    Label playerStrengthChangeLabel = new Label(), enemyStrengthChangeLabel = new Label();
    Label playerDodgeChanceChangeLabel = new Label(), enemyDodgeChanceChangeLabel = new Label();
    Label inventoryLabel = new Label("Inventory: ");
    Label nameLabel = new Label("Name: ");
    TextField nameInput = new TextField();
    Button exportBtn = new Button("Export game");
    Button submit = new Button("Enter");
    Button close = new Button("Close");



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        databaseM.setup();
        ui.setPrefWidth(400);
        ui.setPadding(new Insets(10));
        ui.add(playerLabel, 0, 0);
        playerLabel.setStyle("-fx-font-weight: bold;");
        ui.add(hauntedLabel, 1, 0);
        ui.add(poisonLabel, 3, 0);
        ui.add(burningLabel, 5, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(playerHealthChangeLabel, 2, 1);
        ui.add(new Label("Strength: "), 0, 2);
        ui.add(strengthLabel, 1, 2);
        ui.add(playerStrengthChangeLabel, 2, 2);
        ui.add(new Label("Dodge chance: "), 0, 3);
        ui.add(dodgeLabel, 1, 3);
        ui.add(playerDodgeChanceChangeLabel, 2, 3);
        ui.add(new Label(""), 0, 4);
        ui.add(enemyLabel, 0, 5);
        enemyLabel.setStyle("-fx-font-weight: bold;");

        ui.add(enemyHealthTextLabel, 0, 6);
        ui.add(enemyHealthNumLabel, 1, 6);
        ui.add(enemyHealthChangeLabel, 2, 6);
        ui.add(enemyStrengthTextLabel, 0, 7);
        ui.add(enemyStrengthNumLabel, 1, 7);
        ui.add(enemyStrengthChangeLabel, 2, 7);
        ui.add(enemyDodgeTextLabel, 0, 8);
        ui.add(enemyDodgeNumLabel, 1, 8);
        ui.add(enemyDodgeChanceChangeLabel, 2, 8);
        ui.add(new Label(""), 0, 9);

        ui.add(inventoryLabel, 0, 10);
        inventoryLabel.setStyle("-fx-font-weight: bold;");
        ui.add(new Label("-----------"), 1, 10);
        ui.add(inventory, 1, 11);

        ui.add(new Label(""), 0, 15);
        ui.add(exportBtn, 0, 16);

        exportBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                exportGame();
            }
        });

        openLoadPopUp();
        importFromMenu();
        if (!isLoad) {
            ui.add(new Label(""), 0, 12);
            ui.add(nameLabel, 0, 13);
            nameLabel.setStyle("-fx-font-weight: bold;");
            nameInput.setPrefWidth(100);
            ui.add(nameInput, 1, 13);
            ui.add(submit, 1, 14);
            ui.add(close, 2, 14);

            submit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    map.getPlayer().setName(nameInput.getText());
                    playerLabel.setText(nameInput.getText());
                    ui.getChildren().remove(nameLabel);
                    ui.getChildren().remove(nameInput);
                    ui.getChildren().remove(submit);
                    ui.getChildren().remove(close);
                    if (nameInput.getText().equalsIgnoreCase("admin")) {
                        map.getPlayer().setHealth(9000);
                        map.getPlayer().setStrength(1000);
                    }
                    canvas.requestFocus();
                }
            });

            close.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ui.getChildren().remove(nameLabel);
                    ui.getChildren().remove(nameInput);
                    ui.getChildren().remove(submit);
                    ui.getChildren().remove(close);
                    canvas.requestFocus();
                }
            });
        }


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::keyReleased);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void enemyMove(){
            for(Actor actor : map.getEnemies()){
                actor.move();
            }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                enemyMove();
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                enemyMove();
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                enemyMove();
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                enemyMove();
                refresh();
                keyEvent.consume();
                break;
            case CONTROL:
                s1 = true;
                break;

            case S:
                if(s1){
                    openSaveWindow("New Save");
                }
                break;


        }
        openPopUpWindow();
        if (map.getPlayer().getCell().getType().equals(CellType.ODOOR)) {
            changeMap(map.getPlayer().getCurrentMap()+1, 4 ,2, true);
        }
        if (map.getPlayer().getCell().getType().equals(CellType.STAIRS)) {
            changeMap(map.getPlayer().getCurrentMap()-1, 21, 19, false);
        }
    }

    public void openSaveWindow(String saveName){

        TextInputDialog saveDialog = new TextInputDialog(saveName);
        saveDialog.setHeaderText("");
        saveDialog.setTitle("Save Game");
        saveDialog.setContentText("Name:");

        Optional<String> result = saveDialog.showAndWait();
        if (result.isPresent()){
            List<String> saveNames = databaseM.getAllSaveName();
            saveName = result.get();
            if (saveNames.contains(saveName)) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");
                confirmation.setHeaderText("");
                confirmation.setContentText("Would you like to overwrite the already existing state?");

                Optional<ButtonType> choice = confirmation.showAndWait();
                if (choice.get() == ButtonType.OK){
                    List<String> discoveredMaps = new ArrayList<>();
                    for (GameMap map : earlierMaps) {
                        discoveredMaps.add(map.toString());
                    }
                    GameState state = databaseM.getGameState(saveName);
                    int playerId = databaseM.getPlayerIdBySaveName(saveName);
                    PlayerModel player = new PlayerModel(map.getPlayer());
                    player.setId(playerId);
                    GameState gameState = new GameState(map.toString(), saveName, new Date(System.currentTimeMillis()), player, discoveredMaps);
                    databaseM.updatePlayer(player);
                    databaseM.updateGameState(gameState);

                } else {
                    openSaveWindow(saveName);
                }

            }
            else {
                List<String> discoveredMaps = new ArrayList<>();
                for (GameMap map : earlierMaps) {
                    discoveredMaps.add(map.toString());
                }
                GameState gameState = new GameState(map.toString(), saveName, new Date(System.currentTimeMillis()), new PlayerModel(map.getPlayer()), discoveredMaps);
                databaseM.savePlayer(map.getPlayer(), gameState);
            }

        }

    }
    public void keyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case CONTROL:
                s1 = false;
                break;
        }
    }

    private void refresh() {


        context.setFill(Color.BLACK);
        int shiftX = 0;
        int shiftY = 0;

        if(map.getWidth() >28){
            if(map.getPlayer().getX() >= 14){
                shiftX = map.getPlayer().getX()-14;
            }
            if(map.getPlayer().getY() >= 12){
                shiftY = map.getPlayer().getY()-12;
            }
            if(map.getPlayer().getX() >= map.getWidth() - 10){
                shiftX = map.getWidth() - 25;
            }
            if(map.getPlayer().getY() >= map.getHeight() - 7){
                shiftY = map.getHeight() - 20;
            }
        }

        if(map.getPlayer().getIsBurning()){
        map.getPlayer().decreaseBurning();}
        if(map.getPlayer().getIsPoisoned()){
        map.getPlayer().decreasePoison();}

        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            int relativeX = x-shiftX;
            for (int y = 0; y < map.getHeight(); y++) {
                int relativeY = y-shiftY;
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), relativeX, relativeY);
                }
                else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), relativeX, relativeY);

                } else if(cell.getType() == CellType.CDOOR){
                    if(map.getPlayer().hasKey()){
                            cell.setType(CellType.ODOOR);
                            Tiles.drawTile(context, new Cell(map,relativeX,relativeY, CellType.ODOOR), relativeX,relativeY);
                    }
                    else{
                        Tiles.drawTile(context, cell,relativeX,relativeY);
                    }
                }
                else {
                    Tiles.drawTile(context, cell, relativeX, relativeY);
                }
            }
        }
        hauntedLabel.setText(map.getPlayer().getIsHaunted() ? "HAUNTED!" : "");
        hauntedLabel.setTextFill(Color.GREY);
        burningLabel.setText(map.getPlayer().getIsBurning() ? "  BURNING!" : "");
        burningLabel.setTextFill(Color.RED);
        poisonLabel.setText(map.getPlayer().getIsPoisoned() ? "POISONED!" : "");
        poisonLabel.setTextFill(Color.GREEN);
        healthLabel.setText("" + map.getPlayer().getHealth());
        StringBuilder sb = new StringBuilder("");
        for (Item item : map.getPlayer().getInventory()) {
            sb.append(item.getTileName()).append("\n");
        }
        inventory.setText("" + sb);
        playerHealthChangeLabel.setText(map.getPlayer().getHasDodged() ? " Dodged" :  map.getPlayer().getHealthChange() > 0 ? " +" + map.getPlayer().getHealthChange() : map.getPlayer().getHealthChange() < 0 ? " " + map.getPlayer().getHealthChange() : "");
        playerHealthChangeLabel.setTextFill(map.getPlayer().getHealthChange() >= 0 ? Color.GREEN : Color.RED);
        strengthLabel.setText("" + map.getPlayer().getStrength());
        playerStrengthChangeLabel.setText(map.getPlayer().getStrengthChange() > 0 ? " +" + map.getPlayer().getStrengthChange() : map.getPlayer().getStrengthChange() < 0 ? " " + map.getPlayer().getStrengthChange() : "");
        playerStrengthChangeLabel.setTextFill(map.getPlayer().getStrengthChange() >= 0 ? Color.GREEN : Color.RED);

        dodgeLabel.setText("" + (int) (map.getPlayer().getDodgeChance() * 100) + "%");
        playerDodgeChanceChangeLabel.setText(map.getPlayer().getDodgeChanceChange() > 0 ? " +" + (int)(map.getPlayer().getDodgeChanceChange() * 100) + "%" : map.getPlayer().getDodgeChanceChange() < 0 ? " " + (int) (map.getPlayer().getDodgeChanceChange() * 100) : "");
        playerDodgeChanceChangeLabel.setTextFill(map.getPlayer().getDodgeChanceChange() >= 0 ? Color.GREEN : Color.RED);

        if(map.getPlayer().getCurrentEnemy() == null) {
            setEnemyVisible(false);
        } else {
            setEnemyVisible(true);
            Actor enemy = map.getPlayer().getCurrentEnemy();
            enemyLabel.setText("Enemy " + enemy.getTileName().substring(0, 1).toUpperCase(Locale.ROOT) + enemy.getTileName().substring(1));
            enemyHealthNumLabel.setText("" + enemy.getHealth());
            enemyHealthChangeLabel.setText(enemy.getHasDodged() ? " Dodged" : enemy.getHealthChange() > 0 ? " +" + enemy.getHealthChange() : enemy.getHealthChange() < 0 ? " " + enemy.getHealthChange() : "");
            enemyHealthChangeLabel.setTextFill(enemy.getHealthChange() >= 0 ? Color.GREEN : Color.RED);
            enemyStrengthNumLabel.setText("" + enemy.getStrength());
            enemyStrengthChangeLabel.setText(enemy.getStrengthChange() > 0 ? " +" + enemy.getStrengthChange() : enemy.getStrengthChange() < 0 ? " " + enemy.getStrengthChange() : "");
            enemyStrengthChangeLabel.setTextFill(enemy.getStrengthChange() >= 0 ? Color.GREEN : Color.RED);

            enemyDodgeNumLabel.setText("" + (int) (enemy.getDodgeChance() * 100) + "%");
            enemyDodgeChanceChangeLabel.setText(enemy.getDodgeChanceChange() > 0 ? " +" + (int)(enemy.getDodgeChanceChange() * 100) + "%" : enemy.getDodgeChanceChange() < 0 ? " " + (int) (enemy.getDodgeChanceChange() * 100) : "");
            enemyDodgeChanceChangeLabel.setTextFill(enemy.getDodgeChanceChange() >= 0 ? Color.GREEN : Color.RED);
        }
        String name = nameLabel.getText();
        if (map.getPlayer().getHealth() <= 0) {
            openGameOverPopUp();
        }
        if (map.getPlayer().getCurrentMap() == 2) {
            if (map.getBoss().getHealth() <= 0) {
                openWinPopup();
            }
        }
    }

    private void setEnemyVisible(boolean b) {
        enemyLabel.setVisible(b);
        enemyHealthTextLabel.setVisible(b);
        enemyHealthNumLabel.setVisible(b);
        enemyHealthChangeLabel.setVisible(b);
        enemyStrengthTextLabel.setVisible(b);
        enemyStrengthNumLabel.setVisible(b);
        enemyDodgeTextLabel.setVisible(b);
        enemyDodgeNumLabel.setVisible(b);
    }

    private void openPopUpWindow() {
        if (map.getPlayer().getCell().getItem() != null) {
            Stage popUpWindow = new Stage();

            popUpWindow.initModality(Modality.APPLICATION_MODAL);
            popUpWindow.setTitle("Pick Up Item");
            Item item = map.getPlayer().getCell().getItem();
            Label label1= new Label("Do you want to pick up (a(n)) " + item.getTileName().substring(0, 1).toUpperCase(Locale.ROOT) + item.getTileName().substring(1) + "?");
            StringBuilder sb = new StringBuilder("");
            if(item.hasStats()) {
                if(item.getStrength() > 0) {
                    sb.append("+" + item.getStrength() + " strength\n");
                }
                if(item.getHealth() > 0) {
                    sb.append("+" + item.getHealth() + " health\n");
                }
                if(item.getDodgeChance() > 0) {
                    sb.append("+" + (int)(item.getDodgeChance()*100) + "% dodge chance\n");
                }
            }
            Label label2 = new Label(sb.toString());

            Button pickUpButton = new Button("Pick Up");
            Button closeButton = new Button("Close");

            closeButton.setOnAction(e -> popUpWindow.close());

            pickUpButton.setOnAction((EventHandler<ActionEvent>) actionEvent -> {
                map.getPlayer().addToInventory(map.getPlayer().getCell().getItem());
                map.getPlayer().addStats(map.getPlayer().getCell().getItem());
                map.getPlayer().getCell().setItem(null);

                popUpWindow.close();
                refresh();
            });

            VBox layout= new VBox(10);
            layout.getChildren().addAll(label1, label2, pickUpButton, closeButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 400, 250);
            popUpWindow.setScene(scene1);
            popUpWindow.showAndWait();
        }
    }

    private void openGameOverPopUp() {
        Stage gameOverPopUp = new Stage();

        gameOverPopUp.initModality(Modality.APPLICATION_MODAL);
        gameOverPopUp.setTitle("Game Over");

        Label label1= new Label("Do you want to play again?");

        Button restartButton = new Button("Play Again");
        Button closeButton = new Button("Quit");

        closeButton.setOnAction(e -> System.exit(0));

        restartButton.setOnAction((EventHandler<ActionEvent>) actionEvent -> {
            try {
                Runtime.getRuntime().exec("java App");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameOverPopUp.close();
        });

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, restartButton, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 250, 150);
        gameOverPopUp.setScene(scene1);
        gameOverPopUp.showAndWait();
    }


    private void openLoadPopUp() throws SQLException {
        Stage loadPopUp = new Stage();

        loadPopUp.initModality(Modality.APPLICATION_MODAL);
        loadPopUp.setTitle("Load Game");

        Label label1= new Label("Choose a saved game or start a new one");

        ListView listView = new ListView();


        List<GameState> saveList =  databaseM.getGameStates();
        for (GameState save : saveList) {
            listView.getItems().add(save.getName());
        }

        Button loadGameButton = new Button("Load Game");
        Button newGameButton = new Button("New Game");

        loadGameButton.setOnAction(event -> {
            this.isLoad = true;
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            int stateIndex = 0;
            for (GameState save : saveList) {
                if (stateIndex == selectedIndex) {
                    loadGame(save);
                    stateIndex++;
                    loadPopUp.close();
                }
                else {
                    stateIndex++;
                }
            }
        });

        newGameButton.setOnAction((EventHandler<ActionEvent>) actionEvent -> {
            loadPopUp.close();
        });

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, listView, loadGameButton, newGameButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 250, 150);
        loadPopUp.setScene(scene1);
        loadPopUp.showAndWait();
    }

    private void loadGame(GameState gameStateToLoad) {

        if(gameStateToLoad.getDiscoveredMaps().size() == 0) {
            mapList[0] = gameStateToLoad.getCurrentMap();
            map = MapLoader.loadMap(mapList[0]);
            map.getPlayer().setCurrentMap(0);
            mapList[1] = mapList[1].replace("@", ".");
        }
        else if (gameStateToLoad.getDiscoveredMaps().size() == 1) {
            earlierMaps.add(MapLoader.loadMap(gameStateToLoad.getDiscoveredMaps().get(0)));
            Cell playerCell = earlierMaps.get(0).getCell(21, 19);
            earlierMaps.get(0).setCell(21, 19, CellType.ODOOR);
            playerCell.setActor(map.getPlayer());
            mapList[0] = gameStateToLoad.getDiscoveredMaps().get(0);
            mapList[1] = gameStateToLoad.getCurrentMap();
            map = MapLoader.loadMap(mapList[1]);
            map.getPlayer().setCurrentMap(1);
            mapList[1] = mapList[1].replace("@", ".");
        }
        else {
            earlierMaps.add(MapLoader.loadMap(gameStateToLoad.getDiscoveredMaps().get(0)));
            earlierMaps.add(MapLoader.loadMap(gameStateToLoad.getDiscoveredMaps().get(1)));
            Cell playerCell = earlierMaps.get(1).getCell(93, 10);
            earlierMaps.get(0).setCell(21, 19, CellType.ODOOR);
            earlierMaps.get(1).setCell(93, 10, CellType.ODOOR);
            playerCell.setActor(map.getPlayer());
            mapList[0] = gameStateToLoad.getDiscoveredMaps().get(0);
            mapList[1] = gameStateToLoad.getDiscoveredMaps().get(1);
            mapList[2] = gameStateToLoad.getCurrentMap();
            map = MapLoader.loadMap(mapList[2]);
            map.getPlayer().setCurrentMap(2);
            mapList[2] = mapList[2].replace("@", ".");
        }

        List<Item> inventory = gameStateToLoad.getPlayer().getInventory();
        map.getPlayer().setHealth(gameStateToLoad.getPlayer().getHp());
        map.getPlayer().setStrength(gameStateToLoad.getPlayer().getStrength());
        map.getPlayer().setName(gameStateToLoad.getPlayer().getPlayerName());
        playerLabel.setText(map.getPlayer().getName());
        for (Item item : inventory) {
            map.getPlayer().addToInventory(item);
        }
    }

    private void openWinPopup() {
        Stage winPopUp = new Stage();

        winPopUp.initModality(Modality.APPLICATION_MODAL);

        Label label1= new Label("You won!");
        label1.setStyle("-fx-font-weight: bold;");

        Button closeButton = new Button("Quit");

        closeButton.setOnAction(e -> System.exit(0));

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 250, 150);
        winPopUp.setScene(scene1);
        winPopUp.showAndWait();
    }

    private void changeMap(int mapNumber, int positionX, int positionY, boolean forward) {
        map.getPlayer().setCurrentMap(mapNumber);
        Player player = map.getPlayer();
        player.removeKey();
        if (forward) {
            earlierMaps.add(map);
            map = MapLoader.loadMap(mapList[mapNumber]);
        }
        else {
            String mapSave = map.toString().replace("@", "S");
            mapList[mapNumber + 1] = mapSave;
            map = earlierMaps.get(earlierMaps.size()-1);
            earlierMaps.remove(earlierMaps.size()-1);
        }
        player.setCell(map.getCell(positionX, positionY));
        map.setPlayer(player);
        refresh();
    }

    private void exportGame() {
        Stage exportPopup = new Stage();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        AtomicReference<File> selectedDirectory = new AtomicReference<>();

        exportPopup.setTitle("Export Game");
        Label nameLabel = new Label("File Name: ");
        TextField nameField = new TextField();

        Label chooseLabel = new Label("Directory: ");
        Button chooseBtn = new Button("Choose");
        chooseBtn.setOnAction(e -> {
            selectedDirectory.set(directoryChooser.showDialog(exportPopup));
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            // Export game to file
            PlayerModel playerModel = new PlayerModel(map.getPlayer());
            List<String> discoveredMaps = earlierMaps.stream()
                                                     .map(GameMap::toString)
                                                     .collect(Collectors.toList());
            GameState gameState = new GameState(map.toString(),
                                                nameField.getText(),
                                                new Date(System.currentTimeMillis()),
                                                playerModel,
                                                discoveredMaps);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(new File(selectedDirectory.get().getAbsolutePath() +
                                                "/" + nameField.getText() +
                                                ".json"), gameState);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            exportPopup.close();
            canvas.requestFocus();
        });
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            exportPopup.close();
            canvas.requestFocus();
        });

        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(chooseLabel, 0, 1);
        gridPane.add(chooseBtn, 1, 1);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(saveBtn, closeBtn);
        gridPane.add(hbBtn, 1, 2);

        Scene scene1= new Scene(gridPane, 300, 200);
        exportPopup.setScene(scene1);
        exportPopup.showAndWait();

    }


    private void importFromMenu(){
        Stage stage = new Stage();

        Button importButton = new Button("Import game");
        ui.add(importButton,1,16);

        final FileChooser fileChooser = new FileChooser();

        importButton.setOnAction(
                new EventHandler<>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        try{
                        if (file != null && file.getName().endsWith(".json")) {
                            GameState gameState = new ObjectMapper().readValue(file, GameState.class);
                            loadGame(gameState);
                            ui.getChildren().remove(nameLabel);
                            ui.getChildren().remove(nameInput);
                            ui.getChildren().remove(submit);
                            ui.getChildren().remove(close);
                            canvas.requestFocus();
                            refresh();
                        }
                        else if(!file.getName().endsWith(".json")){

                            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            Alert alert = new Alert(Alert.AlertType.ERROR,"",ButtonType.OK,cancel);
                            alert.setTitle("IMPORT ERROR!");
                            alert.setHeaderText("IMPORT ERROR!");
                            alert.setContentText("Unfortunately the given file is in wrong format.\nPlease try another one!");

                            alert.showAndWait()
                                    .filter(response -> response == ButtonType.OK)
                                    .ifPresent(response -> importButton.fire());

                        }}catch(NullPointerException ignored){} catch (IOException jsonMappingException) {
                            jsonMappingException.printStackTrace();
                        }
                    }
                });


    }

}
