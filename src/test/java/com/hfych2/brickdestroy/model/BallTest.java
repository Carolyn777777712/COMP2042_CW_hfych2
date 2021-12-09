package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BallTest {

    ViewManager owner = new ViewManager();
    GameBoard gameBoard = GameBoard.createGameBoard(owner);

    Ball ball = new RubberBall(new Point(300,430));

    @Test
    public void setSpeed() {
        gameBoard.getBall().setSpeed(5,-5);
        assertEquals(5,gameBoard.getBall().getSpeedX());
        assertEquals(-5,gameBoard.getBall().getSpeedY());
    }

    @Test
    public void setXSpeed() {
        gameBoard.getBall().setXSpeed(10);
        assertEquals(10,gameBoard.getBall().getSpeedX());
    }

    @Test
    public void setYSpeed() {
        gameBoard.getBall().setYSpeed(10);
        assertEquals(10,gameBoard.getBall().getSpeedY());
    }

    @Test
    public void getSpeedX() {
        assertEquals(2,gameBoard.getBall().getSpeedX());
    }

    @Test
    public void getSpeedY() {
        assertEquals(-2, gameBoard.getBall().getSpeedY());
    }

    @Test
    public void reverseX() {
        gameBoard.getBall().setXSpeed(10);
        gameBoard.getBall().reverseX();
        assertEquals(-10,gameBoard.getBall().getSpeedX());
    }

    @Test
    public void reverseY() {
        gameBoard.getBall().setYSpeed(10);
        gameBoard.getBall().reverseY();
        assertEquals(-10,gameBoard.getBall().getSpeedY());
    }

    @Test
    public void getBorderColor() {
        assertEquals(ball.getBorderColor(),gameBoard.getBall().getBorderColor());
    }

    @Test
    public void getInnerColor() {
        assertEquals(ball.getInnerColor(),gameBoard.getBall().getInnerColor());
    }

    @Test
    public void getPosition() {
        assertEquals(ball.getPosition(), gameBoard.getBall().getPosition());
    }
}