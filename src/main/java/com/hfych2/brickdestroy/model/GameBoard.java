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
package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.controller.DebugConsole;
import com.hfych2.brickdestroy.view.DebugPanel;
import com.hfych2.brickdestroy.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Both {@link com.hfych2.brickdestroy.view.GameView} and {@link com.hfych2.brickdestroy.controller.GameController}
 *      are extracted from this class of the original code.<br>
 *
 * This class has been modified from the original code along with having additions
 *      to become a model class.<br>
 *
 * This class is the model class for the view {@link com.hfych2.brickdestroy.view.GameView}
 * and the controller {@link com.hfych2.brickdestroy.controller.GameController}
 *
 * @author Carolyn
 */
public class GameBoard {

    private Ball ball;
    private Player player;
    private ViewManager owner;
    private Wall wall;
    private Impacts impacts;
    private DebugConsole debugConsole;

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Point ballPos;
    private Point startPoint;

    private int ballCount;
    private boolean ballLost;

    private boolean showPauseMenu;

    private Timer scoreTimer;
    private int total = 0;
    private int minutes = 0;
    private int seconds = 0;
    private String formatSeconds = "00";
    private String formatMinutes = "00";

    private final int penaltyScore = 20000;

    /**
     * Class constructor.<br>
     * Implements the factory method.<br>
     * {@link GameBoard#createGameBoard(ViewManager)} will be replacing constructor calls.<br>
     * Calls {@link GameBoard#initialiseGameBoard()}.<br>
     * Initialises {@link GameBoard#scoreTimer} with a delay of 1000ms and
     *      check if {@link DebugPanel#isGivePenalty()}
     *      and calculates the score to be output to screen.<br>
     * Initialises {@link GameBoard#debugConsole}.
     * @param owner the JFrame
     * @see Timer
     */
    private GameBoard(ViewManager owner) {

        this.owner = owner;

        showPauseMenu = false;

        initialiseGameBoard();
        scoreTimer = new Timer(1000, e -> {

            if (debugConsole.getDebugPanel().isGivePenalty()) {
                total = total + penaltyScore;
            }
            debugConsole.getDebugPanel().setGivePenalty(false);

            total = total + 1000;
            minutes = (total / 60000) % 60;
            seconds = (total / 1000) % 60;

            formatSeconds = String.format("%02d", seconds);
            formatMinutes = String.format("%02d", minutes);

        });

        debugConsole = new DebugConsole(this);

    }

    /**
     * Resets the score by using {@link GameBoard#setTotal(int)},
     *      {@link GameBoard#setMinutes(int)},
     *      {@link GameBoard#setSeconds(int)} to set to zero,
     *      and stops the {@link GameBoard#scoreTimer}
     */
      public void resetScore() {

          scoreTimer.stop();
          setTotal(0);
          setMinutes(0);
          setSeconds(0);

          setFormatMinutes(String.format("%02d", getMinutes()));
          setFormatSeconds(String.format("%02d", getSeconds()));
      }

    /**
     * Factory method to replace constructor calls
     * @param owner the JFrame
     * @return new GameBoard object
     */
    public static GameBoard createGameBoard(ViewManager owner) {
        return new GameBoard(owner);//factory method
    }

    /**
     * Initialises {@link GameBoard#wall}, {@link GameBoard#impacts}, {@link GameBoard#player}.<br>
     * Calls {@link GameBoard#makeBall(Point2D)} and sets the speed values.<br>
     * Sets {@link GameBoard#ballCount} to 3 and {@link GameBoard#ballLost} to false.<br>
     * Uses {@link GameBoard#wall} to call {@link Wall#nextLevel()} to initialise the first level.<br>
     */
    private void initialiseGameBoard() {

        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2);

        ballPos = new Point(300, 430);
        this.startPoint = new Point(ballPos);

        impacts = new Impacts(this.wall, this);

        //initialise the first level
        wall.nextLevel();

        ballCount = 3;
        ballLost = false;
        makeBall(ballPos);
        int speedX, speedY;
        do {
            speedX = 2;
        } while (speedX == 0);
        do {
            speedY = -2;
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
        player = new Player((Point) ballPos.clone(), 150, 10, new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT));

    }

    /**
     * Makes the ball of type {@link RubberBall}
     * @param ballPos the center position of the ball.
     */
    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

    /**
     * Resets the ball and player position to the starting point.<br>
     * Resets the speed values of the ball to the default values.<br>
     * Resets {@link GameBoard#ballLost} to false.
     * @see Ball#setSpeed(int, int)
     * @see Player#moveTo(Point)
     * @see Ball#moveTo(Point)
     */
    public void ballReset() {
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX, speedY;
        do {
            speedX = 2;
        } while (speedX == 0);
        do {
            speedY = -2;
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
        ballLost = false;
    }

    /**
     * Resets the gameBoard elements excluding the current wall to the default view.<br>
     * Resets {@link GameBoard#ballCount} to 3.<br>
     * Resets the ball and player position to the starting point.<br>
     * @see Player#moveTo(Point)
     * @see Ball#moveTo(Point)
     */
    public void resetGameBoard() {
        ballCount = 3;
        ball.moveTo(startPoint);
        player.moveTo(startPoint);
    }

    /**
     * Gets the JFrame
     * @return the JFrame
     */
    public ViewManager getOwner() {
        return owner;
    }

    /**
     * Gets the player.
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the ball.
     * @return the ball.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * Gets the wall.
     * @return the wall.
     */
    public Wall getWall() {
        return wall;
    }

    /**
     * Gets the debugConsole.
     * @return the debug Console.
     */
    public DebugConsole getDebugConsole() {
        return debugConsole;
    }

    /**
     * Gets the impacts.
     * @return the impacts.
     */
    public Impacts getImpacts() {
        return impacts;
    }

    /**
     * Calls {@link Player#move()} and {@link Ball#move()}
     */
    public void move() {
        player.move();
        ball.move();
    }

    /**
     * Gets the ball count.
     * @return the ball count.
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * Flag for ball lost.
     * @return true if lost and false if not.
     */
    public boolean isBallLost() {
        return ballLost;
    }

    /**
     * Flag for all balls lost.
     * @return true if {@link GameBoard#ballCount} == 0 and false if not
     */
    public boolean ballEnd() {
        return ballCount == 0;
    }

    /**
     * Sets the flag for ball lost.
     * @param ballLost true if ball lost and false if not
     */
    public void setBallLost(boolean ballLost) {
        this.ballLost = ballLost;
    }

    /**
     * Sets the ball count.
     * @param ballCount number of balls.
     */
    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    /**
     * Flag for showing PauseMenu.
     * @return true if PauseMenu is currently displayed and false if not.
     */
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    /**
     * Sets the flag for showing PauseMenu.
     * @param showPauseMenu true if PauseMenu is currently displayed and false if not.
     */
    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    /**
     * Gets the {@link GameBoard#DEF_WIDTH}
     * @return the {@link GameBoard#DEF_WIDTH}
     */
    public static int getDefWidth() {
        return DEF_WIDTH;
    }

    /**
     * Gets the {@link GameBoard#DEF_HEIGHT}
     * @return the {@link GameBoard#DEF_HEIGHT}
     */
    public static int getDefHeight() {
        return DEF_HEIGHT;
    }

    /**
     * Gets the {@link GameBoard#scoreTimer}
     * @return the {@link GameBoard#scoreTimer}
     */
    public Timer getScoreTimer() {
        return scoreTimer;
    }

    /**
     * Gets the total time taken to complete the level in milliseconds.
     * @return the total time taken to complete the level in milliseconds.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the total time taken to complete the level in milliseconds.
     * @param total the total time taken to complete the level in milliseconds.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Gets the total in milliseconds to minutes.
     * @return the total in milliseconds to minutes.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets the total in milliseconds to minutes.
     * @param minutes the total in milliseconds to minutes.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Gets the total in milliseconds to seconds.
     * @return the total in milliseconds to seconds.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Sets the total in milliseconds to seconds
     * @param seconds the total in milliseconds to seconds.
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Gets the formatted string of seconds.
     * @return the formatted string of seconds.
     */
    public String getFormatSeconds() {
        return formatSeconds;
    }

    /**
     * Sets the formatted string of seconds.
     * @param formatSeconds the formatted string of seconds.
     */
    public void setFormatSeconds(String formatSeconds) {
        this.formatSeconds = formatSeconds;
    }

    /**
     * Gets the formatted string of minutes.
     * @return the formatted string of minutes.
     */
    public String getFormatMinutes() {
        return formatMinutes;
    }

    /**
     * Gets the formatted string of minutes.
     * @param formatMinutes the formatted string of minutes.
     */
    public void setFormatMinutes(String formatMinutes) {
        this.formatMinutes = formatMinutes;
    }

}

