package com.hfych2.brickdestroy;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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

        } else if (homeMenu.getMenuButton().contains(p)) {
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

        } else if (homeMenu.getMenuButton().contains(p)) {
            homeMenu.setMenuClicked(true);
            homeMenu.repaint(homeMenu.getMenuButton().x, homeMenu.getMenuButton().y, homeMenu.getMenuButton().width + 1, homeMenu.getMenuButton().height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (homeMenu.isStartClicked()) {
            homeMenu.setStartClicked(false);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);
        } else if (homeMenu.isMenuClicked()) {
            homeMenu.setMenuClicked(false);
            homeMenu.repaint(homeMenu.getMenuButton().x, homeMenu.getMenuButton().y, homeMenu.getMenuButton().width + 1, homeMenu.getMenuButton().height + 1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p) || homeMenu.getMenuButton().contains(p))
            homeMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            homeMenu.setCursor(Cursor.getDefaultCursor());

    }
}