package com.hfych2.brickdestroy.view;

import com.hfych2.brickdestroy.controller.GameController;
import com.hfych2.brickdestroy.model.GameBoard;
import org.junit.Test;

import static org.junit.Assert.*;

public class DebugPanelTest {

    private ViewManager owner = new ViewManager();
    private GameView gameView = new GameView();

    private GameController gameController = new GameController(GameBoard.createGameBoard(owner),gameView);

    private DebugPanel debugPanel = DebugPanel.createDebugPanel();

    @Test
    public void createDebugPanel() {
        DebugPanel debugPanel = DebugPanel.createDebugPanel();
        assertNotNull(debugPanel);
    }

    @Test
    public void isGivePenalty() {
        assertEquals(false,debugPanel.isGivePenalty());
    }

    @Test
    public void setGivePenalty() {
        debugPanel.setGivePenalty(true);
        assertEquals(true,debugPanel.isGivePenalty());
    }

}