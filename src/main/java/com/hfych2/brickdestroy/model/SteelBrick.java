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
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This class is brick of type Steel which is broken with a probability specified as {@link SteelBrick#STEEL_PROBABILITY}.
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    /**
     * Class constructor.<br>
     * Calls the super class constructor and passes in the respective arguments.<br>
     * Calls the {@link Brick#brickFace} and assigns to {@link SteelBrick#brickFace}.<br>
     * Initialises {@link SteelBrick#rnd}.
     * @param point the point of the brick.
     * @param size the size of the brick.
     */
    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }

    /**
     * Calls the {@link Brick#impact()}
     * if the {@link SteelBrick#rnd} is less than {@link SteelBrick#STEEL_PROBABILITY}
     */
    @Override
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

    /**
     * Makes the brick.
     * @param pos the position of the brick.
     * @param size the size of the brick.
     * @return the rectangle brick made
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Gets the brick.
     * @return the brick.
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     *
     * @param point the point of impact.
     * @param dir the direction of damage caused by impact.
     * @return false if broken and true if not.
     * @see Brick#isBroken()
     */
    @Override
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

}
