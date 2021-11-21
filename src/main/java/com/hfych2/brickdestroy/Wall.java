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
package com.hfych2.brickdestroy;


import java.awt.*;
import java.awt.geom.Point2D;


public class Wall {

    final Levels levels = new Levels(this);
    private final Impacts impacts = new Impacts(this);

    private Rectangle area;

    Ball ball;
    Player player;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;


    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels.setLevels(levels.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        levels.setLevel(0);

        ballCount = 3;
        ballLost = false;


        makeBall(ballPos);
        int speedX,speedY;
        do{
            speedX = 2;
        }while(speedX == 0);
        do{
            speedY = -2;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    public void move(){
        player.move();
        ball.move();
    }

    public void findImpacts(){
        impacts.findImpacts();
    }


    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    public boolean isBallLost(){
        return ballLost;
    }

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX,speedY;
        do{
            speedX = 2;
        }while(speedX == 0);
        do{
            speedY = -2;
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : levels.getBricks())
            b.repair();
        brickCount = levels.getBricks().length;
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        levels.nextLevel();
    }

    public boolean hasLevel(){
        return levels.hasLevel();
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }

    public void setBallLost(boolean ballLost) {
        this.ballLost = ballLost;
    }

    public Player getPlayer() {
        return player;
    }

    public Ball getBall() {
        return ball;
    }

    public Levels getLevels() {
        return levels;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }
}
