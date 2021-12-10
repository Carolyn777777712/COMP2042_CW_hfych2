package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This class is a newly created class that is not from the original code.<br>
 *
 * This class is brick of type Gold
 * which is broken with a probability as specified as {@link GoldBrick#GOLD_PROBABILITY}
 *
 * @author Carolyn
 */

public class GoldBrick extends Brick{

    private static final String NAME = "Gold Brick";
    private static final Color DEF_INNER = new Color(255, 255, 0);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int GOLD_STRENGTH = 1;
    private static final double GOLD_PROBABILITY = 0.25;

    private Random rnd;
    private Shape brickFace;

    /**
     * Class constructor.<br>
     * Calls the super class constructor and passes in the respective arguments.<br>
     * Calls the {@link Brick#brickFace} and assigns to {@link GoldBrick#brickFace}.<br>
     * Initialises {@link GoldBrick#rnd}.
     * @param point the point of the brick
     * @param size the size of the brick
     */
    public GoldBrick(Point point, Dimension size) {
        super(NAME,point,size,DEF_BORDER,DEF_INNER,GOLD_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
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

    /**
     * Calls the {@link Brick#impact()}
     * if the {@link GoldBrick#rnd} is less than {@link GoldBrick#GOLD_PROBABILITY}
     */
    @Override
    public void impact(){
        if(rnd.nextDouble() < GOLD_PROBABILITY){
            super.impact();
        }
    }

    /**
     * Makes the brick.
     * @param pos the position of the brick.
     * @param size the size of the brick.
     * @return the rectangle brick made.
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

}
