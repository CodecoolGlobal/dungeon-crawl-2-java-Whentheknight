package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryState;

import java.util.List;

public interface InventoryDao {
    void add(InventoryState inventory);
    void update(InventoryState inventory);
    InventoryState get(int id);
    List<InventoryState> getAll();
}
