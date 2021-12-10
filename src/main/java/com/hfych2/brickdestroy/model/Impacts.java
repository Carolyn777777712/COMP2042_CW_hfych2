package com.hfych2.brickdestroy.model;


import java.awt.geom.Point2D;

/**
 * This class is extracted from {@link Wall} of the original code.<br>
 *
 * This class defines the corresponding updates to be performed by the related components
 * when impacts are detected.<br>
 *
 * @author Carolyn
 */
public class Impacts {

    private final Wall wall;
    private GameBoard gameBoard;

    /**
     * Class constructor.
     * @param wall the wall.
     * @param gameBoard the gameBoard.
     */
    public Impacts(Wall wall, GameBoard gameBoard) {
        this.wall = wall;
        this.gameBoard = gameBoard;
    }

    /**
     * Finds the impacts and defines the corresponding updates to be made by the related components.<br>
     * Checks if {@link Impacts#impactWall()} then minus the brickCount by one<br>
     * or if  {@link Impacts#impactBorder()} then call {@link Ball#reverseX()}<br>
     * or if {@link Player#impact(Ball)} then call {@link Ball#reverseY()}<br>
     * or if ball is lost then minus the ballCount by one and
     *      set {@link GameBoard#setBallLost(boolean)} to true.<br>
     */
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

    /**
     * Checks the direction of impact to wall and
     *      call {@link Ball#reverseY()} on {@link Brick#UP_IMPACT} and {@link Brick#DOWN_IMPACT} or <br>
     *      call {@link Ball#reverseX()} on {@link Brick#LEFT_IMPACT} and {@link Brick#RIGHT_IMPACT}<br>
     * Calls {@link Brick#setImpact(Point2D, int)} and
     *      passes in the first parameter as {@link Ball#up} or {@link Ball#down}
     *              or {@link Ball#left} or {@link Ball#right}
     *              depending on the direction of impact found using {@link Brick#findImpact(Ball)}.<br>
     * @return value of {@link Brick#setImpact(Point2D, int)} as long as wall is not completely destroyed
     *          and returns false if wall is completely destroyed.
     */
    private boolean impactWall() {
        for (Brick brick : wall.getLevels().getBricks()) {
                switch (brick.findImpact(gameBoard.getBall())) {
                    //Vertical Impact
                    case Brick.UP_IMPACT:
                        gameBoard.getBall().reverseY();
                        return brick.setImpact(gameBoard.getBall().down, Crack.UP);
                    case Brick.DOWN_IMPACT:
                        gameBoard.getBall().reverseY();
                        return brick.setImpact(gameBoard.getBall().up, Crack.DOWN);

                    //Horizontal Impact
                    case Brick.LEFT_IMPACT:
                        gameBoard.getBall().reverseX();
                        return brick.setImpact(gameBoard.getBall().right, Crack.RIGHT);
                    case Brick.RIGHT_IMPACT:
                        gameBoard.getBall().reverseX();
                        return brick.setImpact(gameBoard.getBall().left, Crack.LEFT);
                }
            }
        return false;
    }

    /**
     * Checks if the ball has impact on the border of the screen using {@link Ball#getPosition()}.<br>
     * @return true if the ball has impact the border and false if not.
     */
    private boolean impactBorder() {
        Point2D p = gameBoard.getBall().getPosition();
        return ((p.getX() < wall.getArea().getX()) || (p.getX() > (wall.getArea().getX() + wall.getArea().getWidth())));
    }
}