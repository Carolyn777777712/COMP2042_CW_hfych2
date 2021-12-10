package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.view.InfoView;
import com.hfych2.brickdestroy.view.ViewManager;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is the controller class for {@link InfoView}
 * Defines the action of key pressed when InfoView is in display.<br>
 *
 * @author Carolyn
 */
public class InfoController implements KeyListener{
    private final InfoView infoView;

    /**
     * Class constructor.
      * @param infoView the view
     */
    public InfoController(InfoView infoView) {
        this.infoView = infoView;
    }

    /**
     * Calls the {@link ViewManager#enableGameBoard()} when 'ENTER' key is pressed.<br>
     * @param keyEvent the KeyEvent detected
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
            infoView.getOwner().enableGameBoard();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
}