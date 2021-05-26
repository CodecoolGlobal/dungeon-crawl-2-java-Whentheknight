package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {
    private final int mapWidth = 25;
    private final int mapHeight = 20;
    GameMap map = MapLoader.loadMap();

    Canvas canvas = new Canvas(
            mapWidth * Tiles.TILE_WIDTH,
            mapHeight * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    GridPane ui = new GridPane();;

    Label healthLabel = new Label(), strengthLabel = new Label(), dodgeLabel = new Label(), inventory = new Label();
    Label enemyLabel = new Label(), enemyHealthTextLabel = new Label("Health: "), enemyHealthNumLabel = new Label();
    Label enemyStrengthTextLabel = new Label("Strength: "), enemyStrengthNumLabel = new Label();
    Label enemyDodgeTextLabel = new Label("Dodge Chance: "), enemyDodgeNumLabel = new Label();
    Label playerDamageLabel = new Label();
    Label enemyDamageLabel = new Label();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ui.setPrefWidth(250);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Player"), 0, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(playerDamageLabel, 2, 1);
        ui.add(new Label("Strength: "), 0, 2);
        ui.add(strengthLabel, 1, 2);
        ui.add(new Label("Dodge chance: "), 0, 3);
        ui.add(dodgeLabel, 1, 3);
        ui.add(new Label(""), 0, 4);
        ui.add(enemyLabel, 0, 5);
        ui.add(enemyHealthTextLabel, 0, 6);
        ui.add(enemyHealthNumLabel, 1, 6);
        ui.add(enemyDamageLabel, 2, 6);
        ui.add(enemyStrengthTextLabel, 0, 7);
        ui.add(enemyStrengthNumLabel, 1, 7);
        ui.add(enemyDodgeTextLabel, 0, 8);
        ui.add(enemyDodgeNumLabel, 1, 8);
        ui.add(new Label(""), 0, 9);

        ui.add(new Label("Inventory: "), 0, 10);
        ui.add(inventory, 1, 11);


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
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        int shiftX = 0;
        int shiftY = 0;
        
        if(map.getWidth() >25){
        if(map.getPlayer().getX() >= 20){
            shiftX = map.getPlayer().getX()-12;
        }
        if(map.getPlayer().getY() >= 15){
            shiftY = map.getPlayer().getY()-10;
        }}


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
        healthLabel.setText("" + map.getPlayer().getHealth());
        StringBuilder sb = new StringBuilder("");
        for (Item item : map.getPlayer().getInventory()) {
            sb.append(item.getTileName()).append("\n");
        }
        inventory.setText("" + sb);
        playerDamageLabel.setText(map.getPlayer().getTakenDamage() > 0 ? " -" + map.getPlayer().getTakenDamage() : map.getPlayer().getTakenDamage() == -1 ? " Dodged" : "");
        playerDamageLabel.setTextFill(map.getPlayer().getTakenDamage() == -1 ? Color.GREEN : Color.RED);
        strengthLabel.setText("" + map.getPlayer().getStrength());
        dodgeLabel.setText("" + (int) (map.getPlayer().getDodgeChance() * 100) + "%");
        if(map.getPlayer().getCurrentEnemy() == null) {
            setEnemyVisible(false);
        } else {
            setEnemyVisible(true);
            Actor enemy = map.getPlayer().getCurrentEnemy();
            enemyLabel.setText("Enemy " + enemy.getTileName().substring(0, 1).toUpperCase(Locale.ROOT) + enemy.getTileName().substring(1));
            enemyHealthNumLabel.setText("" + enemy.getHealth());
            enemyDamageLabel.setText(enemy.getTakenDamage() > 0 ? " -" + enemy.getTakenDamage() : enemy.getTakenDamage() == -1 ? " Dodged" : "");
            enemyDamageLabel.setTextFill(enemy.getTakenDamage() == -1 ? Color.GREEN : Color.RED);
            enemyStrengthNumLabel.setText("" + enemy.getStrength());
            enemyDodgeNumLabel.setText("" + (int) (enemy.getDodgeChance() * 100) + "%");
        }
    }

    private void setEnemyVisible(boolean b) {
        enemyLabel.setVisible(b);
        enemyHealthTextLabel.setVisible(b);
        enemyHealthNumLabel.setVisible(b);
        enemyDamageLabel.setVisible(b);
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

            Label label1= new Label("Do you want to pick up a " + map.getPlayer().getCell().getItem().getTileName() + "?");

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
            layout.getChildren().addAll(label1, pickUpButton, closeButton);
            layout.setAlignment(Pos.CENTER);
            Scene scene1= new Scene(layout, 300, 250);
            popUpWindow.setScene(scene1);
            popUpWindow.showAndWait();
        }
    }
}
