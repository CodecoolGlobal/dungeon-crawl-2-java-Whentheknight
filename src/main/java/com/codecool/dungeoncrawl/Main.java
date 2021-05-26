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
    GameMap map = MapLoader.loadMap();

    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    GridPane ui = new GridPane();;

    Label playerLabel = new Label("Player");
    Label healthLabel = new Label(), strengthLabel = new Label(), dodgeLabel = new Label(), inventory = new Label();
    Label enemyLabel = new Label(), enemyHealthTextLabel = new Label("Health: "), enemyHealthNumLabel = new Label();
    Label enemyStrengthTextLabel = new Label("Strength: "), enemyStrengthNumLabel = new Label();
    Label enemyDodgeTextLabel = new Label("Dodge Chance: "), enemyDodgeNumLabel = new Label();
    Label playerHealthChangeLabel = new Label(), enemyHealthChangeLabel = new Label();
    Label playerStrengthChangeLabel = new Label(), enemyStrengthChangeLabel = new Label();
    Label playerDodgeChanceChangeLabel = new Label(), enemyDodgeChanceChangeLabel = new Label();
    Label inventoryLabel = new Label("Inventory: ");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        ui.setPrefWidth(200);
//        ui.setPadding(new Insets(10));

        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));

        ui.add(playerLabel, 0, 0);
        playerLabel.setStyle("-fx-font-weight: bold;");
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
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }
                else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);

                } else if(cell.getType() == CellType.CDOOR){
                    if(map.getPlayer().hasKey()){
                            Tiles.drawTile(context, new Cell(map,x,y, CellType.ODOOR), x,y);
                    }
                    else{
                        Tiles.drawTile(context, cell,x,y);
                    }
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
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
//                enemy.getTakenDamage() > 0 ? " -" + enemy.getTakenDamage() : enemy.getTakenDamage() == -1 ? " Dodged" : "");
            enemyHealthChangeLabel.setTextFill(enemy.getHealthChange() >= 0 ? Color.GREEN : Color.RED);
            enemyStrengthNumLabel.setText("" + enemy.getStrength());
            enemyStrengthChangeLabel.setText(enemy.getStrengthChange() > 0 ? " +" + enemy.getStrengthChange() : enemy.getStrengthChange() < 0 ? " " + enemy.getStrengthChange() : "");
            enemyStrengthChangeLabel.setTextFill(enemy.getStrengthChange() >= 0 ? Color.GREEN : Color.RED);

            enemyDodgeNumLabel.setText("" + (int) (enemy.getDodgeChance() * 100) + "%");
            enemyDodgeChanceChangeLabel.setText(enemy.getDodgeChanceChange() > 0 ? " +" + (int)(enemy.getDodgeChanceChange() * 100) + "%" : enemy.getDodgeChanceChange() < 0 ? " " + (int) (enemy.getDodgeChanceChange() * 100) : "");
            enemyDodgeChanceChangeLabel.setTextFill(enemy.getDodgeChanceChange() >= 0 ? Color.GREEN : Color.RED);
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
            Label label1= new Label("Do you want to pick up (a) " + item.getTileName().substring(0, 1).toUpperCase(Locale.ROOT) + item.getTileName().substring(1) + "?");
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
}
