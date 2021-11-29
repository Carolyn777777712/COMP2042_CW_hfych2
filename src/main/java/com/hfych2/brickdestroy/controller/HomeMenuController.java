package com.hfych2.brickdestroy.controller;

import com.hfych2.brickdestroy.view.HomeMenu;


import java.awt.*;
import java.awt.event.*;


public class HomeMenuController implements MouseListener, MouseMotionListener{

    private final HomeMenu homeMenu;

    public HomeMenuController(HomeMenu homeMenu) {

        this.homeMenu = homeMenu;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p)) {
            homeMenu.getOwner().enableGameBoard();

        } else if (homeMenu.getInfoButton().contains(p)){
            homeMenu.getOwner().enableView();
        }
        else if (homeMenu.getExitButton().contains(p)) {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p)) {
            homeMenu.setStartClicked(true);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

        } else if (homeMenu.getInfoButton().contains(p)){
            homeMenu.setInfoClicked(true);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);
        }
        else if (homeMenu.getExitButton().contains(p)) {
            homeMenu.setExitClicked(true);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (homeMenu.isStartClicked()) {
            homeMenu.setStartClicked(false);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

        } else if (homeMenu.isInfoClicked()){
            homeMenu.setInfoClicked(false);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);
        }
        else if (homeMenu.isExitClicked()) {
            homeMenu.setExitClicked(false);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

            homeMenu.setStartEntered(true);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

            homeMenu.setInfoEntered(true);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);

            homeMenu.setExitEntered(true);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

            homeMenu.setStartEntered(false);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

            homeMenu.setInfoEntered(false);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);

            homeMenu.setExitEntered(false);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p) || homeMenu.getExitButton().contains(p) ||homeMenu.getInfoButton().contains(p))
            homeMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            homeMenu.setCursor(Cursor.getDefaultCursor());

    }
}