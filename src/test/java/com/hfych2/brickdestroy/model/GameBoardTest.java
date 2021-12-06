package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GameBoardTest {


    private ViewManager owner = new ViewManager();
    private Wall wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2);;


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private Point ballPos = new Point(300,430);
    private Point startPoint = new Point(ballPos);

    private Player player = new Player((Point) ballPos.clone(), 150, 10, new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT));
    private Ball ball = new RubberBall(ballPos);


    @Test
    public void createGameBoard() {

      GameBoard gameBoard = GameBoard.createGameBoard(owner);
      assertNotNull(gameBoard);
    }

/*    @Test
    public void ballReset() {
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        assertEquals(ball.getPosition(),startPoint);

    }*/

    @Test
    public void getOwner() {
        assertEquals(owner,GameBoard.createGameBoard(owner).getOwner());
    }

    @Test
    public void getPlayer() {
        assertNotNull(player);
    }

    @Test
    public void getBall() {
        assertNotNull(ball);
    }

    @Test
    public void getWall() {
        assertNotNull(wall);
    }

    @Test
    public void getImpacts() {
        GameBoard gameBoard = GameBoard.createGameBoard(owner);
        Impacts impacts = new Impacts(wall,gameBoard);

        assertNotNull(impacts);
    }

/*    @Test
    public void move() {


    }*/

    @Test
    public void getBallCount() {
        assertEquals(3,GameBoard.createGameBoard(owner).getBallCount());
    }

    @Test
    public void isBallLost() {
        assertEquals(false,GameBoard.createGameBoard(owner).isBallLost());
    }

    @Test
    public void ballEnd() {
        assertEquals(3,GameBoard.createGameBoard(owner).getBallCount());
    }

    @Test
    public void setBallXSpeed() {
        assertEquals(2,GameBoard.createGameBoard(owner).getBall().getSpeedX());

    }

    @Test
    public void setBallYSpeed() {
        assertEquals(-2,GameBoard.createGameBoard(owner).getBall().getSpeedY());
    }

    @Test
    public void setBallLost() {
        assertEquals(false,GameBoard.createGameBoard(owner).isBallLost());
    }

    @Test
    public void setBallCount() {
        assertEquals(3,GameBoard.createGameBoard(owner).getBallCount());
    }

    @Test
    public void isShowPauseMenu() {
        assertEquals(false,GameBoard.createGameBoard(owner).isShowPauseMenu());
    }

    @Test
    public void setShowPauseMenu() {
        assertEquals(false,GameBoard.createGameBoard(owner).isShowPauseMenu());
    }

    @Test
    public void getDefWidth() {
        assertEquals(600,GameBoard.createGameBoard(owner).getDefWidth());
    }

    @Test
    public void getDefHeight() {
        assertEquals(450,GameBoard.createGameBoard(owner).getDefHeight());
    }
}