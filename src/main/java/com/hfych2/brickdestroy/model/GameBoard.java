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
import com.hfych2.brickdestroy.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;


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

      public void resetScore() {

          scoreTimer.stop();
          setTotal(0);
          setMinutes(0);
          setSeconds(0);

          setFormatMinutes(String.format("%02d", getMinutes()));
          setFormatSeconds(String.format("%02d", getSeconds()));
      }

    public static GameBoard createGameBoard(ViewManager owner) {
        return new GameBoard(owner);//factory method
    }

    private void initialiseGameBoard() {

        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2);

        ballPos = new Point(300, 430);//new
        this.startPoint = new Point(ballPos);

        impacts = new Impacts(this.wall, this);

        //initialize the first level
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

    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

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

    public void resetGameBoard() {
        ballCount = 3;
        ball.moveTo(startPoint);
        player.moveTo(startPoint);
    }

    public ViewManager getOwner() {
        return owner;
    }

    public Player getPlayer() {
        return player;
    }

    public Ball getBall() {
        return ball;
    }

    public Wall getWall() {
        return wall;
    }

    public DebugConsole getDebugConsole() {
        return debugConsole;
    }

    public Impacts getImpacts() {
        return impacts;
    }

    public void move() {
        player.move();
        ball.move();
    }

    public int getBallCount() {
        return ballCount;
    }

    public boolean isBallLost() {
        return ballLost;
    }

    public boolean ballEnd() {
        return ballCount == 0;
    }

    public void setBallLost(boolean ballLost) {
        this.ballLost = ballLost;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    public static int getDefWidth() {
        return DEF_WIDTH;
    }

    public static int getDefHeight() {
        return DEF_HEIGHT;
    }

    public Timer getScoreTimer() {
        return scoreTimer;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getFormatSeconds() {
        return formatSeconds;
    }

    public void setFormatSeconds(String formatSeconds) {
        this.formatSeconds = formatSeconds;
    }

    public String getFormatMinutes() {
        return formatMinutes;
    }

    public void setFormatMinutes(String formatMinutes) {
        this.formatMinutes = formatMinutes;
    }

}

