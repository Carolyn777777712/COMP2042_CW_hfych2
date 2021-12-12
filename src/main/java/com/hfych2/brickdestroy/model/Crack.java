package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This class is extracted from {@link Brick} of the original code.
 *
 * This class specifies how to draw the crack on the brick called.<br>
 */
public class Crack {


    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private final Brick brick;

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    private static Random rnd;


    private GeneralPath crack;

    private int crackDepth;
    private int steps;

    /**
     * Class constructor.<br>
     * Initialises {@link Crack#crack} and {@link Crack#rnd}.
     * @param brick the brick to draw on.
     * @param crackDepth the depth of the crack.
     * @param steps number of lines drawn of the crack.
     */
    public Crack(Brick brick, int crackDepth, int steps){

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
        this.brick = brick;

    }

    /**
     * @return the crack drawn.
     * @see GeneralPath
     */
    public GeneralPath draw(){

        return crack;
    }

    /**
     * Clears the cracks drawn on the brick
     * @see GeneralPath#reset()
     */
    public void reset(){
        crack.reset();
    }

    /**
     * Defines the bounds for the drawing of the crack.
     * @param point the point of impact.
     * @param direction the direction of damage caused by impact
     */
    protected void crackBounds(Point2D point, int direction){
        Rectangle bounds = brick.brickFace.getBounds();

        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();
        Point randomPoint;

        switch(direction){
            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                randomPoint = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,randomPoint);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                randomPoint = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,randomPoint);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                randomPoint = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,randomPoint);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                randomPoint = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,randomPoint);

                break;

        }
    }

    /**
     * Makes the crack.<br>
     * Use {@link GeneralPath#moveTo(double, double)} to the point of impact.<br>
     * The bound is the {@link Crack#crackDepth}.<br>
     * Calls {@link Crack#inMiddle(int, int, int)} to check for each {@link Crack#steps} and if
     *      {@link Crack#inMiddle(int, int, int)} returns true
     *      then assign {@link Crack#jumps(int, double)} value
     *      to the second parameter of {@link GeneralPath#lineTo(double, double)}
     * @param start point of impact.
     * @param end ending point.
     * @see GeneralPath#append(PathIterator, boolean)
     * @see GeneralPath#moveTo(double, double)
     * @see GeneralPath#lineTo(double, double)
     * @see Crack#randomInBounds(int)
     */
    protected void makeCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();


        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps; i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,steps))
                y += jumps(jump,JUMP_PROBABILITY);

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
    }

    /**
     * Generates random int.
     * @param bound the bound.
     * @return random int.
     */
    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    /**
     * Checks if the current step is in the middle
     * @param i     the current step
     * @param steps the total steps
     * @param divisions the divisions
     * @return true if in middle and false if not
     */
    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    /**
     * Calls and return {@link Crack#randomInBounds(int)}
     *      if the random number is larger than the {@link Crack#JUMP_PROBABILITY}
     *      to generate zig-zag pattern of crack drawn.
     * @param bound the bound.
     * @param probability the probability.
     * @return 0 if the random number is smaller than the {@link Crack#JUMP_PROBABILITY} and
     *      returns the value of {@link Crack#randomInBounds(int)} otherwise
     */
    private int jumps(int bound,double probability){
        if(rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

    /**
     * Makes the random point.
     * @param from the starting point.
     * @param to the ending point.
     * @param direction the direction.
     * @return random point.
     */
    private Point makeRandomPoint(Point from, Point to, int direction){
        Point randomPoint = new Point();
        int position;

        switch(direction){
            case HORIZONTAL:
                position = rnd.nextInt(to.x - from.x) + from.x;
                randomPoint.setLocation(position,to.y);
                break;
            case VERTICAL:
                position = rnd.nextInt(to.y - from.y) + from.y;
                randomPoint.setLocation(to.x, position);
                break;
        }
        return randomPoint;
    }

}
