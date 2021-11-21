package com.hfych2.brickdestroy;

import java.awt.geom.Point2D;

public class Impacts {
    private final Wall wall;

    public Impacts(Wall wall) {
        this.wall = wall;
    }

    public void findImpacts() {
        if (wall.getPlayer().impact(wall.getBall())) {
            wall.getBall().reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            wall.setBrickCount(wall.getBrickCount() - 1);
        } else if (impactBorder()) {
            wall.getBall().reverseX();
        } else if (wall.getBall().getPosition().getY() < wall.getArea().getY()) {
            wall.getBall().reverseY();
        } else if (wall.getBall().getPosition().getY() > wall.getArea().getY() + wall.getArea().getHeight()) {
            wall.setBallCount(wall.getBallCount() - 1);
            wall.setBallLost(true);
        }
    }

    boolean impactWall() {
        for (Brick b : wall.getLevels().getBricks()) {
                switch (b.findImpact(wall.getBall())) {
                    //Vertical Impact
                    case Brick.UP_IMPACT:
                        wall.getBall().reverseY();
                        return b.setImpact(wall.getBall().down, Crack.UP);
                    case Brick.DOWN_IMPACT:
                        wall.getBall().reverseY();
                        return b.setImpact(wall.getBall().up, Crack.DOWN);

                    //Horizontal Impact
                    case Brick.LEFT_IMPACT:
                        wall.getBall().reverseX();
                        return b.setImpact(wall.getBall().right, Crack.RIGHT);
                    case Brick.RIGHT_IMPACT:
                        wall.getBall().reverseX();
                        return b.setImpact(wall.getBall().left, Crack.LEFT);
                }
            }
        return false;
    }

    boolean impactBorder() {
        Point2D p = wall.getBall().getPosition();
        return ((p.getX() < wall.getArea().getX()) || (p.getX() > (wall.getArea().getX() + wall.getArea().getWidth())));
    }
}