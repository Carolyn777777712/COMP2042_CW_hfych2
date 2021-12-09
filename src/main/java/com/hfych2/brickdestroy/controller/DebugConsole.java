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
import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.view.DebugPanel;
import com.hfych2.brickdestroy.view.GameView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * This class represents the DebugConsole
 * that is shown when user presses 'ALT','F1' and 'SHIFT'.
 *
 * This class extends JDialog and implements WindowListener.
 *
 * It is the controller class for the DebugPanel.
 *
 * @author Carolyn
 *
 */
public class DebugConsole extends JDialog implements WindowListener{


    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameBoard gameBoard;
    private GameView gameView;

    private int clicks = 2;

    /**
     * Class constructor.
     *
     * Calls the initialise(); resetBalls(); skipLevels(); changeSpeed(); methods
     * Initialises the DebugPanel using the factory method and add to DebugConsole
     *
     * @param gameBoard the gameBoard that initialises the debugConsole
     */
    public DebugConsole(GameBoard gameBoard){

        this.owner = gameBoard.getOwner();
        this.gameBoard = gameBoard;

        initialise();

        debugPanel = DebugPanel.createDebugPanel();
        this.add(debugPanel,BorderLayout.CENTER);

        this.pack();

        resetBalls();
        skipLevels();
        changeSpeed();
    }

    private void initialise(){
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

    /**
     * Gets the debugPanel initialised in constructor.
     * @return debugPanel initialised in constructor.
     */
    public DebugPanel getDebugPanel() {
        return debugPanel;
    }

    /**
     * Defines the action when the "Reset Ball Speed" button in debugPanel is pressed.
     *
     */
    public void changeSpeed(){
        debugPanel.getChangeBallSpeedButton().addActionListener(e -> {
            double overallSpeed;

            overallSpeed = debugPanel.getChangeBallSpeedSlider().getValue();

            debugPanel.getChangeBallSpeedButton().setText("Set ball speed to: "+ String.valueOf(overallSpeed));

            gameBoard.getBall().setXSpeed(debugPanel.getChangeBallSpeedSlider().getValue());
            gameBoard.getBall().setYSpeed(debugPanel.getChangeBallSpeedSlider().getValue());

            debugPanel.setGivePenalty(true);
        });
    }

    /**
     * Defines the action when "Skip to: " button in debugPanel is pressed.
     *
     */
    public void skipLevels(){
        debugPanel.getSkipLevelsButton().addActionListener(e -> {
            debugPanel.getSkipLevelsButton().setText("Skip to: " + "level  " + clicks);
            clicks++;
            gameBoard.getWall().nextLevel();
            gameBoard.ballReset();
            gameBoard.resetScore();

            if(!gameBoard.getWall().hasLevel()){
                debugPanel.getSkipLevelsButton().setText("Last Level");
                debugPanel.getSkipLevelsButton().setEnabled(false);
            }
        });
    }

    /**
     * Defines the action when "Reset Balls" button in debugPanel is pressed.
     *
     */
    public void resetBalls(){
        debugPanel.getResetBallsButton().addActionListener(e -> {
                gameBoard.ballReset();
                gameBoard.resetGameBoard();

                debugPanel.setGivePenalty(true);
        });
    }

    /**
     * Gets the gameView to call the repaint(); method
     * @param gameView gameView to call repaint(); method
     */
    public void updateView(GameView gameView){
        this.gameView = gameView;
    }

    /**
     * Calls the repaint(); method using gameView to update the gameView upon
     * window closing event is detected
     * @param windowEvent the window event detected
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameView.repaint();
    }

    /**
     * Calls the setLocation(); method and sets the values for ball speed using the debugPanel
     * @param windowEvent the window event detected
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball ball = gameBoard.getBall();
        debugPanel.setValues(ball.getSpeedX(),ball.getSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {
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
    public void windowOpened(WindowEvent windowEvent) {

    }

}
