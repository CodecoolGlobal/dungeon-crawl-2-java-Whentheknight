package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.actors.Actor;

import com.codecool.dungeoncrawl.logic.items.Key;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main extends Application {
    private final int mapWidth = 25;
    private final int mapHeight = 20;

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


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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

        ui.add(new Label(""), 0, 12);
        ui.add(nameLabel, 0, 13);
        nameLabel.setStyle("-fx-font-weight: bold;");
        Button submit = new Button("Enter");
        Button close = new Button("Close");
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
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ui.getChildren().remove(nameLabel);
                ui.getChildren().remove(nameInput);
                ui.getChildren().remove(submit);
                ui.getChildren().remove(close);
            }
        });

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

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

    private void changeMap(int mapNumber, int positionX, int positionY, boolean forward) {
        map.getPlayer().setCurrentMap(mapNumber);
        Player player = map.getPlayer();
        player.removeKey();
        if (forward) {
            earlierMaps.add(map);
            map = MapLoader.loadMap(mapList[mapNumber]);
        }
        else {
            map = earlierMaps.get(earlierMaps.size()-1);
            earlierMaps.remove(earlierMaps.size()-1);
        }
        player.setCell(map.getCell(positionX, positionY));
        map.setPlayer(player);
        refresh();
    }
}
