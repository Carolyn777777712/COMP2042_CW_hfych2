package com.hfych2.brickdestroy.model;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class LevelsTest {

    private final Wall wall = new Wall(new Rectangle(0, 0, 600, 450), 30, 3, 6 / 2);
    private Levels levels = new Levels(wall);

/*    @Test
    public void makeLevels() {
    }

    @Test
    public void nextLevel() {
    }

    @Test
    public void hasLevel() {
    }

    @Test
    public void getBricks() {
    }

    @Test
    public void setLevels() {
    }*/

    @Test
    public void setLevel() {
        assertEquals(0,levels.getLevel());
    }

    @Test
    public void getLevel() {
        assertEquals(0,levels.getLevel());
    }
}