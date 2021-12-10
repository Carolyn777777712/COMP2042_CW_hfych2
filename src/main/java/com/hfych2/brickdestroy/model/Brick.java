package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 * Created by filippo on 04/09/16.
 *
 *
 * Modified by Carolyn Han En Qi since 19/11/2021.
 */

/**
 * This class is the super class for {@link ClayBrick}, {@link CementBrick}
 *      {@link SteelBrick}, {@link GoldBrick}.<br>
 * This class will be the super class for all brick objects which may be extended in the future.<br>
 * This class defines the general behaviour for all brick objects.
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    protected Shape brickFace;

    private String name;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;


    /**
     * Class constructor.<br>
     * Sets the initial value of {@link Brick#broken} to false.<br>
     * Calls the {@link Brick#makeBrickFace(Point, Dimension)} method and
     *      assigns it to {@link Brick#brickFace}.<br>
     * @param name the name of the brick type.
     * @param pos the position of the brick.
     * @param size the size of the brick.
     * @param border the border colour of the brick.
     * @param inner the inner colour of the brick.
     * @param strength the amount of impacts needed to break the brick.
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    /**
     * The base outline to make the bricks.
     * @param pos the position of the brick.
     * @param size the size of the brick.
     * @return the brick made.
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * The base outline to get the brick.
     * @return the brick.
     */
    public abstract Shape getBrick();

    /**
     * Sets impact on the brick by calling the {@link Brick#impact()} until it is broken.
     * @param point the point of impact.
     * @param dir the direction of damage caused by impact.
     * @return false if broken and true if not.
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * Finds the direction of impact.
     * @param ball the ball.
     * @return the impact value.
     * @see Brick#UP_IMPACT
     * @see Brick#DOWN_IMPACT
     * @see Brick#LEFT_IMPACT
     * @see Brick#RIGHT_IMPACT
     */
    public final int findImpact(Ball ball){
        if(broken)
            return 0;
        int impact = 0;
        if(brickFace.contains(ball.right))
            impact = LEFT_IMPACT;
        else if(brickFace.contains(ball.left))
            impact = RIGHT_IMPACT;
        else if(brickFace.contains(ball.up))
            impact = DOWN_IMPACT;
        else if(brickFace.contains(ball.down))
            impact = UP_IMPACT;
        return impact;
    }

    /**
     * Decreases the strength of the brick on impact made and
     *      {@link Brick#broken} is true when strength is zero.
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    /**
     * Repairs the brick by setting {@link Brick#broken} to false and
     *      set {@link Brick#strength} to {@link Brick#fullStrength}.
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * Gets the border colour of the brick.
     * @return the border colour of the brick.
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * Gets the inner colour of the brick.
     * @return the inner colour of the brick.
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Gets the boolean value of the status of the brick.
     * @return true if broken and false if not.
     */
    public final boolean isBroken(){
        return broken;
    }

}





