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

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    private RectangularShape ball() {
        return (RectangularShape) ballFace;
    }

    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public void move(){
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double width = ball().getWidth();
        double height = ball().getHeight();

        ball().setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)), width, height);
        setPoints(width, height);

    }

    public void moveTo(Point p){
        center.setLocation(p);

        double width = ball().getWidth();
        double height = ball().getHeight();

        ball().setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)), width, height);
    }

    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }

    public void reverseX(){
        speedX *= -1;
    }

    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

}
