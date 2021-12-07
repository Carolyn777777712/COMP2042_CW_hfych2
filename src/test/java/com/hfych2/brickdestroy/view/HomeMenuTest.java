package com.hfych2.brickdestroy.view;

import com.hfych2.brickdestroy.controller.HomeMenuController;
import com.hfych2.brickdestroy.model.GameBoard;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class HomeMenuTest {

    private ViewManager owner = new ViewManager();
    private HomeMenu homeMenu = new HomeMenu(owner,new Dimension(600,450));
    private HomeMenuController homeMenuController = new HomeMenuController(homeMenu);

    private Dimension area = new Dimension(600,450);

    private Dimension btnDim = new Dimension(area.width, area.height / 8);
    private Rectangle startButton = new Rectangle(btnDim);
    private Rectangle infoButton = new Rectangle(btnDim);
    private Rectangle exitButton = new Rectangle(btnDim);


    @Test
    public void getOwner() {
        assertEquals(owner, GameBoard.createGameBoard(owner).getOwner());
    }

    @Test
    public void getStartButton() {
        assertEquals(startButton,homeMenu.getStartButton());
    }

    @Test
    public void getInfoButton() {
        assertEquals(infoButton,homeMenu.getInfoButton());
    }

    @Test
    public void getExitButton() {
        assertEquals(exitButton,homeMenu.getExitButton());
    }

    @Test
    public void isStartClicked() {
        assertEquals(false,homeMenu.isStartClicked());
    }

    @Test
    public void isInfoClicked() {
        assertEquals(false, homeMenu.isInfoClicked());
    }

    @Test
    public void isExitClicked() {
        assertEquals(false, homeMenu.isExitClicked());
    }

    @Test
    public void setStartClicked() {
        assertEquals(false,homeMenu.isStartClicked());
    }

    @Test
    public void setInfoClicked() {
        assertEquals(false,homeMenu.isInfoClicked());
    }

    @Test
    public void setExitClicked() {
        assertEquals(false,homeMenu.isExitClicked());
    }

}