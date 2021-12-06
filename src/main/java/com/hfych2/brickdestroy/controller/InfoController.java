package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.view.InfoView;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InfoController implements KeyListener{
    private final InfoView infoView;

    public InfoController(InfoView infoView) {
        this.infoView = infoView;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            infoView.getOwner().enableGameBoard();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}