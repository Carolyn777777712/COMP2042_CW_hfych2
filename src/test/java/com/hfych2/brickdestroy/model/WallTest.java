package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class WallTest {

    private Wall wall = new Wall(new Rectangle(0,0,600,450),30,3,6/2);
    private ViewManager owner = new ViewManager();
    private Levels levels = new Levels(wall);

    @Test
    public void isDone() {
        assertEquals(true, wall.isDone());
    }

    @Test
    public void nextLevel() {
        assertEquals(1, levels.getLevel()+1);
    }

    @Test
    public void hasLevel() {
        levels.setLevels(levels.makeLevels(new Rectangle(0,0,600,450),
                30, 3, 6/2));
        levels.setLevel(0);
        assertEquals(true, levels.hasLevel());
    }

    @Test
    public void getLevels() {
        assertEquals(levels, levels);
    }

    @Test
    public void setBrickCount() {
        assertEquals(0,wall.getBrickCount());
    }

    @Test
    public void getBrickCount() {
        assertEquals(0,wall.getBrickCount());
    }

    @Test
    public void getArea() {
        assertEquals(new Rectangle(0,0,600,450),wall.getArea());
    }
}