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


import java.awt.*;

/**
 * This class defines the player.
 */
public class Player {

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private final int min;
    private final int max;

    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    /**
     * Class constructor.<br>
     * Initialises {@link Player#moveAmount} to zero.<br>
     * Assigns {@link Player#makeRectangle(int, int)} to {@link Player#playerFace}.<br>
     * Uses the fourth parameter to calculate the {@link Player#min} and {@link Player#max}
     * @param ballPoint the ball point.
     * @param width the width of the player
     * @param height the height of the player
     * @param container the area of the gameBoard
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * Makes the rectangle player.
     * @param width the width of the player.
     * @param height the height of the player.
     * @return the rectangle player made.
     *
     * @see Rectangle#Rectangle(Point, Dimension)
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * Flag for checking if the ball impacts the player.
     * @param ball the ball.
     * @return true if impact is detected and false otherwise.
     */
    public boolean impact(Ball ball){
        return playerFace.contains(ball.getPosition()) && playerFace.contains(ball.down) ;
    }

    /**
     * Keeps the player within the area of the gameBoard when player is moved.
     * @see Rectangle#setLocation(Point)
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Moves the player to a certain location point.
     * @param point the point location to move to.
     * @see Rectangle#setLocation(Point)
     */
    public void moveTo(Point point){
        ballPoint.setLocation(point);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * Assigns negative {@link Player#DEF_MOVE_AMOUNT} to {@link Player#moveAmount}
     *      when player is moved left.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * Assigns {@link Player#DEF_MOVE_AMOUNT} to {@link Player#moveAmount}
     *      when player is moved right.
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * Assigns zero to {@link Player#moveAmount} when player is not moved
     */
    public void stop(){
        moveAmount = 0;
    }

    /**
     * Gets the player shape.
     * @return the player shape.
     */
    public Shape getPlayerFace(){
        return  playerFace;
    }


}
