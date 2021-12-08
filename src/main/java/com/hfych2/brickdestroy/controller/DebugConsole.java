/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2021  Carolyn Han En Qi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.model.Ball;
import com.hfych2.brickdestroy.view.DebugPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameController gameController;

    private int clicks = 2;


    public DebugConsole(GameController gameController){

        this.owner = gameController.getGameBoard().getOwner();
        this.gameController = gameController;

        initialize();

        debugPanel = DebugPanel.createDebugPanel();
        this.add(debugPanel,BorderLayout.CENTER);

        this.pack();

        resetBalls();
        skipLevels();
        changeSpeed();
    }

    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }


    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    public DebugPanel getDebugPanel() {
        return debugPanel;
    }

    public void changeSpeed(){
        debugPanel.getChangeBallSpeedButton().addActionListener(e -> {
            double overallSpeed;

            overallSpeed = debugPanel.getChangeBallSpeedSlider().getValue();

            debugPanel.getChangeBallSpeedButton().setText("Set ball speed to: "+ String.valueOf(overallSpeed));

            gameController.getGameBoard().getBall().setXSpeed(debugPanel.getChangeBallSpeedSlider().getValue());
            gameController.getGameBoard().getBall().setYSpeed(debugPanel.getChangeBallSpeedSlider().getValue());

            debugPanel.setGivePenalty(true);
        });
    }

    public void skipLevels(){
        debugPanel.getSkipLevelsButton().addActionListener(e -> {
            debugPanel.getSkipLevelsButton().setText("Skip to: " + "level  " + clicks);
            clicks++;
            gameController.getGameBoard().getWall().nextLevel();
            gameController.getGameBoard().ballReset();
            gameController.resetScore();

            if(!gameController.getGameBoard().getWall().hasLevel()){
                debugPanel.getSkipLevelsButton().setText("Last Level");
                debugPanel.getSkipLevelsButton().setEnabled(false);
            }
        });
    }

    public void resetBalls(){
        debugPanel.getResetBallsButton().addActionListener(e -> {
                gameController.getGameBoard().ballReset();
                gameController.getGameBoard().resetGameBoard();

                debugPanel.setGivePenalty(true);
        });
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameController.getGameView().repaint();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball ball = gameController.getGameBoard().getBall();
        debugPanel.setValues(ball.getSpeedX(),ball.getSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

}
