package com.hfych2.brickdestroy;


import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;


/**
 * Created by filippo on 04/09/16.
 *
 */


abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

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

    public void move(){
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = ball().getWidth();
        double h = ball().getHeight();

        ball().setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = ball();
    }

    public void moveTo(Point p){
        center.setLocation(p);

        double w = ball().getWidth();
        double h = ball().getHeight();

        ball().setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = ball();
    }

    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
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
