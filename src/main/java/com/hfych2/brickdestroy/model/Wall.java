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
 * {@link Levels}, {@link Impacts} is extracted from this class,
 * whereas anything else unrelated to a wall of bricks is moved into {@link GameBoard}.<br>
 *
 * This class is the wall of bricks in all levels.
 */
public class Wall {

    private final Levels levels = new Levels(this);

    private Rectangle area;
    private int brickCount;

    /**
     * Class constructor.<br>
     * Calls the {@link Levels#setLevels(Brick[][])},
     *      {@link Levels#makeLevels(Rectangle, int, int, double)} and
     *      {@link Levels#setLevel(int)} to initialise level making.<br>
     * Assigns the first parameter to {@link Wall#area}
     *
     * @param drawArea the area of the gameBoard.
     * @param brickCount the total number of bricks.
     * @param lineCount the total number of lines of bricks.
     * @param brickDimensionRatio the ratio of the brick size.
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){

        levels.setLevels(levels.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        levels.setLevel(0);

        area = drawArea;

    }

    /**
     * Resets the wall by calling {@link Brick#repair()}
     *      for the length {@link Levels#getBricks()}.<br>
     * Assigns the length of {@link Levels#getBricks()} to {@link Wall#brickCount}
     */
    public void wallReset(){
        for(Brick brick : levels.getBricks())
            brick.repair();
        brickCount = levels.getBricks().length;

    }

    /**
     * Checks if {@link Wall#brickCount} is equal to zero.
     * @return true if {@link Wall#brickCount} is equal to zero and false otherwise
     */
    public boolean isDone(){

        return brickCount == 0;
    }

    /**
     * Calls {@link Levels#nextLevel()} to proceed to the next level.
     */
    public void nextLevel(){

        levels.nextLevel();
    }

    /**
     * Checks if there is another level by calling {@link Levels#hasLevel()}
     * @return true if there is another level and false otherwise.
     */
    public boolean hasLevel(){

        return levels.hasLevel();
    }

    /**
     * Gets the levels.
     * @return the levels.
     */
    public Levels getLevels() {
        return levels;
    }

    /**
     * Sets the brickCount.
     * @param brickCount the brickCount to set to.
     */
    public void setBrickCount(int brickCount) {

        this.brickCount = brickCount;
    }

    /**
     * Gets the brickCount.
     * @return the brickCount.
     */
    public int getBrickCount(){

        return brickCount;
    }

    /**
     * Gets the area of the gameBoard.
     * @return the area of the gameBoard.
     */
    public Rectangle getArea() {
        return area;
    }

}
