package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class LevelsTest {

    private final Wall wall = new Wall(new Rectangle(0, 0, 600, 450), 30, 3, 6 / 2);
    private Levels levels = new Levels(wall);
    private ViewManager owner = new ViewManager();
    private GameBoard gameBoard = GameBoard.createGameBoard(owner);

    @Test
    public void nextLevel() {
        assertEquals(31,gameBoard.getWall().getBrickCount());
    }

    @Test
    public void hasLevel() {
        assertEquals(true,gameBoard.getWall().hasLevel());
    }

    @Test
    public void setLevel() {
        levels.setLevel(1);
        assertEquals(1,levels.getLevel());
    }

    @Test
    public void getLevel() {
        assertEquals(0,levels.getLevel());
    }
}