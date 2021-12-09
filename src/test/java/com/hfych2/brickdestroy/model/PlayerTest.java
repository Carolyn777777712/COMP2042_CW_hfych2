package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PlayerTest {

    ViewManager owner = new ViewManager();
    GameBoard gameBoard = GameBoard.createGameBoard(owner);
    Player player = new Player((Point) gameBoard.getBall().getPosition().clone(), 150, 10,
            new Rectangle(0, 0,gameBoard.getDefWidth(), gameBoard.getDefHeight()));

    private static final int DEF_MOVE_AMOUNT = 5;

    private Point ballPoint = (Point) gameBoard.getBall().getPosition().clone();
    private int moveAmount = 0;
    private Rectangle container = new Rectangle(0, 0,gameBoard.getDefWidth(), gameBoard.getDefHeight());
    private int width  = 150;
    private int height = 10;


    private Rectangle playerFace = makeRectangle(width, height);
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    @Test
    public void impact() {
        assertEquals(true,
                player.getPlayerFace().
                contains(gameBoard.getBall().getPosition())
                &&
                player.getPlayerFace().contains(gameBoard.getBall().down));
    }

    @Test
    public void move() {
        double x = ballPoint.getX() + moveAmount;
        assertEquals(new Point((int)x,(int)ballPoint.getY()),
                gameBoard.getBall().getPosition());

        assertEquals(new Point(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y),
                new Point(gameBoard.getPlayer().getPlayerFace().getBounds().x,
                        gameBoard.getPlayer().getPlayerFace().getBounds().y));
    }

    @Test
    public void moveTo() {
        assertEquals(new Point(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y),
                new Point(gameBoard.getPlayer().getPlayerFace().getBounds().x,
                        gameBoard.getPlayer().getPlayerFace().getBounds().y));
        assertEquals(ballPoint.getLocation(),gameBoard.getBall().getPosition());
    }

    @Test
    public void moveLeft() {
        assertEquals(-DEF_MOVE_AMOUNT,moveAmount=-DEF_MOVE_AMOUNT);
    }

    @Test
    public void moveRight() {
        assertEquals(DEF_MOVE_AMOUNT,moveAmount=DEF_MOVE_AMOUNT);
    }

    @Test
    public void stop() {
        assertEquals(0,moveAmount);
    }

    @Test
    public void getPlayerFace() {
        assertEquals(makeRectangle(width,height),player.getPlayerFace());
    }
}