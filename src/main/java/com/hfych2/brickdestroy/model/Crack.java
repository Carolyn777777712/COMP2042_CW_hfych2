package com.hfych2.brickdestroy.model;


import com.hfych2.brickdestroy.model.Brick;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;


public class Crack {

    private final Brick brick;

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;


    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;


    private static Random rnd;


    private GeneralPath crack;

    private int crackDepth;
    private int steps;


    public Crack(Brick brick, int crackDepth, int steps){

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
        this.brick = brick;

    }

    public GeneralPath draw(){

        return crack;
    }

    public void reset(){
        crack.reset();
    }

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

    protected void makeCrack(Point start, Point end){

        GeneralPath path = new GeneralPath();


        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for(int i = 1; i < steps;i++){

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,steps))
                y += jumps(jump,JUMP_PROBABILITY);

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);
    }

    private int randomInBounds(int bound){
        int n = (bound * 2) + 1;
        return rnd.nextInt(n) - bound;
    }

    private boolean inMiddle(int i,int steps,int divisions){
        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);
    }

    private int jumps(int bound,double probability){
        if(rnd.nextDouble() > probability)
            return randomInBounds(bound);
        return  0;

    }

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
