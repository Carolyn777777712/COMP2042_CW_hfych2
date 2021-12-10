package com.hfych2.brickdestroy.model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


/**
 * Created by filippo on 04/09/16.
 *
 *
 * Modified by Carolyn Han En Qi since 19/11/2021.
 */

/**
 * This class is the super class for {@link RubberBall}.<br>
 * This class will be the super class for all ball objects which may be extended in the future.<br>
 * This class defines the general behaviour of all ball objects.<br>
 */

abstract public class Ball {

    protected Point2D up;
    protected Point2D down;
    protected Point2D left;
    protected Point2D right;

    private Shape ballFace;

    private Point2D center;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;


    /**
     * Class constructor.<br>
     * Calls the {@link Ball#makeBall(Point2D, int, int)} and assigns to {@link Ball#ballFace}.<br>
     * Initialises the speed values, {@link Ball#speedX} and {@link Ball#speedY} to a value of zero.<br>
     * Sets the initial location for {@link Ball#up}, {@link Ball#down}, {@link Ball#left} and {@link Ball#right}.<br>
     *
     * @param center the center point location of the ball.
     * @param radiusA the width of the ball.
     * @param radiusB the height of the ball.
     * @param inner the inner colour of the ball.
     * @param border the border colour of the ball.
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * The base outline to make the ball.
     * @param center the center point location of the ball.
     * @param radiusA the width of the ball.
     * @param radiusB the height of the ball.
     * @return the ball shape made.
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * The ball made.
     * @return the ball as a rectangular shape.
     */
    private RectangularShape ball() {
        return (RectangularShape) ballFace;
    }

    /**
     * Sets the location point for {@link Ball#up}, {@link Ball#down}, {@link Ball#left} and {@link Ball#right}.<br>
     * @param width the width of the ball.
     * @param height the height of the ball.
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * Sets the location and size of the ball to the specified rectangular values
     * with use of {@link Ball#ball()} and {@link Ball#setPoints(double, double)}
     * @see RectangularShape#setFrame(double, double, double, double)
     */
    public void move(){
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double width = ball().getWidth();
        double height = ball().getHeight();

        ball().setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)), width, height);
        setPoints(width, height);

    }

    /**
     * To move the {@link Ball#ball()} to a certain point.<br>
     * @param point the desired point of location.
     */
    public void moveTo(Point point){
        center.setLocation(point);

        double width = ball().getWidth();
        double height = ball().getHeight();

        ball().setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)), width, height);
    }

    /**
     * Sets the speed values of the ball.
     * @param x the speed in x direction.
     * @param y the speed in y direction.
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * Sets the x direction speed of the ball.
     * @param s the speed in x direction.
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * Sets the y direction speed of the ball.
     * @param s the speed in y direction.
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * Gets the x direction speed of the ball.
     * @return the x direction speed of the ball.
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * Gets the y direction speed of the ball.
     * @return the y direction speed of the ball.
     */
    public int getSpeedY(){
        return speedY;
    }

    /**
     * Reverse the x direction speed of the ball.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * Reverse the y direction speed of the ball.
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * Gets the border colour of the ball.
     * @return the border colour of the ball.
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Gets the inner colour of the ball.
     * @return the inner colour of the ball.
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Gets the position of the ball.
     * @return the position of the ball.
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * Gets the appearance of the ball.
     * @return the appearance of the ball.
     */
    public Shape getBallFace(){
        return ballFace;
    }

}
