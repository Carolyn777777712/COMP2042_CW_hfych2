/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
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

import com.hfych2.brickdestroy.controller.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;


public class GameBoard {

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Wall wall;
    private Impacts impacts;

    //new stuff
    Ball ball;
    Player player;
    Point ballPos;

    private GameFrame owner;

    private int ballCount;
    private boolean ballLost;
    private Point startPoint;
    //new stuff

    private boolean showPauseMenu;

    private GameBoard(GameFrame owner) {

        this.owner = owner;

        showPauseMenu = false;

        initialiseGameBoard();

    }

    //new stuff
    public static GameBoard createGameBoard(GameFrame owner) {
        return new GameBoard(owner);//factory method
    }

    private void initialiseGameBoard() {

        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2);

        ballPos = new Point(300, 430);//new
        this.startPoint = ballPos;

        impacts = new Impacts(this.wall, this);

        //initialize the first level
        wall.nextLevel();

        //new
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
        //new
    }

    private void makeBall(Point2D ballPos) {
        ball = new RubberBall(ballPos);
    }

    //new
    public void ballReset() {
        player.moveTo(new Point(300, 430));
        ball.moveTo(new Point(300, 430));
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
        ball.moveTo(new Point(300, 430));
        player.moveTo(new Point(300, 430));//new
    }

    public GameFrame getOwner() {
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

    public void setBallXSpeed(int s) {
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s) {
        ball.setYSpeed(s);
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
    //new

}

