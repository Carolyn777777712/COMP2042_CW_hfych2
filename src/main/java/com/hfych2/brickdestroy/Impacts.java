package com.hfych2.brickdestroy;

import java.awt.geom.Point2D;

public class Impacts {
    private final Wall wall;
    private GameBoard gameBoard;

    public Impacts(Wall wall, GameBoard gameBoard) {
        this.wall = wall;
        this.gameBoard = gameBoard;
    }

    public void findImpacts() {
        if (gameBoard.getPlayer().impact(gameBoard.getBall())) {
            gameBoard.getBall().reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
            */
            wall.setBrickCount(wall.getBrickCount() - 1);
        } else if (impactBorder()) {
            gameBoard.getBall().reverseX();
        } else if (gameBoard.getBall().getPosition().getY() < wall.getArea().getY()) {
            gameBoard.getBall().reverseY();
        } else if (gameBoard.getBall().getPosition().getY() > wall.getArea().getY() + wall.getArea().getHeight()) {
            gameBoard.setBallCount(gameBoard.getBallCount() - 1);
            gameBoard.setBallLost(true);
        }
    }

    boolean impactWall() {
        for (Brick b : wall.getLevels().getBricks()) {
                switch (b.findImpact(gameBoard.getBall())) {
                    //Vertical Impact
                    case Brick.UP_IMPACT:
                        gameBoard.getBall().reverseY();
                        return b.setImpact(gameBoard.getBall().down, Crack.UP);
                    case Brick.DOWN_IMPACT:
                        gameBoard.getBall().reverseY();
                        return b.setImpact(gameBoard.getBall().up, Crack.DOWN);

                    //Horizontal Impact
                    case Brick.LEFT_IMPACT:
                        gameBoard.getBall().reverseX();
                        return b.setImpact(gameBoard.getBall().right, Crack.RIGHT);
                    case Brick.RIGHT_IMPACT:
                        gameBoard.getBall().reverseX();
                        return b.setImpact(gameBoard.getBall().left, Crack.LEFT);
                }
            }
        return false;
    }

    boolean impactBorder() {
        Point2D p = gameBoard.getBall().getPosition();
        return ((p.getX() < wall.getArea().getX()) || (p.getX() > (wall.getArea().getX() + wall.getArea().getWidth())));
    }
}