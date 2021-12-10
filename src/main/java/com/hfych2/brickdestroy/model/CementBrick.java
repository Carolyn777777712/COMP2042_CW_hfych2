package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This class is a brick of type Cement and the only brick type with strength of 2.<br>
 * This class is the only brick type that draw cracks on impact for this version.<br>
 * @see Crack
 */
public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    /**
     * Class constructor.<br>
     * The crack is initialised here.<br>
     * Calls the {@link Brick#brickFace} and assigns to {@link CementBrick#brickFace}.
     * @param point the point of the brick.
     * @param size the size of the brick.
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(this,DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * Updates the brick by drawing the crack pattern.
     * @see GeneralPath#append(Shape, boolean)
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath path = crack.draw();
            path.append(super.brickFace,false);
            brickFace = path;
        }
    }

    /**
     * Repairs the brick by calling {@link Brick#repair()}, {@link Crack#reset()}
     *      and assigns {@link Brick#brickFace} to {@link CementBrick#brickFace}
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }

    /**
     * Overrides the {@link Brick#makeBrickFace(Point, Dimension)}.
     * @param pos the position of the brick.
     * @param size the size of the brick.
     * @return the rectangle brick made.
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * Overrides the {@link Brick#setImpact(Point2D, int)}.<br>
     * Draws the crack onto the brick if not broken
     *      by calling {@link Crack#crackBounds(Point2D, int)}
     *      and {@link CementBrick#updateBrick()}
     * @param point the point of impact.
     * @param dir the direction of damage caused by impact.
     * @return false if broken and true if not.
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.crackBounds(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * Overrides the {@link Brick#getBrick()}.<br>
     * Gets the brick.
     * @return the brick.
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

}
