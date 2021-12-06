package com.hfych2.brickdestroy.view;

import com.hfych2.brickdestroy.controller.GameController;
import com.hfych2.brickdestroy.model.GameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class DebugPanelTest {

    private ViewManager owner = new ViewManager();
    private GameView gameView = new GameView();

    private GameController gameController = new GameController(GameBoard.createGameBoard(owner),gameView);

    @Test
    public void createDebugPanel() {
        DebugPanel debugPanel = DebugPanel.createDebugPanel(gameController);
        assertNotNull(debugPanel);
    }

    @Test
    public void isGivePenalty() {
        assertEquals(false,DebugPanel.createDebugPanel(gameController).isGivePenalty());
    }

    @Test
    public void setGivePenalty() {
        assertEquals(false,DebugPanel.createDebugPanel(gameController).isGivePenalty());
    }
}