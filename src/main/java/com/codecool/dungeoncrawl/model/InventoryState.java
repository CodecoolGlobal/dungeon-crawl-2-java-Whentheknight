package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class InventoryState extends BaseModel {
    private int playerId;
    private List<Item> inventory;

    public InventoryState(List<Item> inventory, int playerId) {
        this.playerId = playerId;
        this.inventory = inventory;
    }

    public int getPlayerId() {
        return playerId;
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
