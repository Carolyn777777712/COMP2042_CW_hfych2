package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.Point;


/**
 * Created by filippo on 04/09/16.
 *
 *
 * Modified by Carolyn Han En Qi since 19/11/2021.
 */

/**
 * This class is brick of type Clay with strength of 1.<br>
 *
 */

public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    /**
     * Class constructor.<br>
     * Class the super class constructor and passes in the respective arguments.
     * @param point the point of the brick
     * @param size the size of the brick
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
     * Gets the brick
     * @return the brick
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }

}
