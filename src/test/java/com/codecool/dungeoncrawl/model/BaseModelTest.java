package com.codecool.dungeoncrawl.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseModelTest {
    BaseModel model = new BaseModel();
    @Test
    void setId_And_getId_Test(){
        model.setId(2);
        assertEquals(2, model.getId());
    }
}
