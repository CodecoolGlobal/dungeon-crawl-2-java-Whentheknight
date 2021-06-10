package com.codecool.dungeoncrawl.logic.actortest;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.Bone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void attackChangesEnemyHealth() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.setDodgeChance(0);
        int initialHealth = skeleton.getHealth();
        player.attack(skeleton);
        assertEquals(initialHealth - player.getStrength(), skeleton.getHealth());
    }

    @Test
    void attackDoesNotChangeEnemyHealthIfDodged() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.setDodgeChance(1);
        int initialHealth = skeleton.getHealth();
        player.attack(skeleton);
        assertEquals(initialHealth, skeleton.getHealth());

    }

    @Test
    void attackChangesEnemyHasDodged() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.setDodgeChance(0);
        player.setDodgeChance(1);
        player.attack(skeleton);
        skeleton.attack(player);
        assertFalse(skeleton.getHasDodged());
        assertTrue(player.getHasDodged());
    }

    @Test
    void attackChangesEnemyHealthChange() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.setDodgeChance(0);
        player.setDodgeChance(1);
        player.attack(skeleton);
        skeleton.attack(player);
        assertEquals(player.getStrength() * -1, skeleton.getHealthChange());
        assertEquals(0, player.getHealthChange());
    }

    @Test
    void MonsterDropsItemWhenKilled() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        skeleton.setDropChance(1);
        skeleton.setDodgeChance(0);
        skeleton.setHealth(1);
        player.attack(skeleton);
        assertEquals(gameMap.getCell(2, 1).getItem().getClass(), Bone.class);
    }


}