package com.hfych2.brickdestroy.model;

import com.hfych2.brickdestroy.controller.DebugConsole;
import com.hfych2.brickdestroy.view.ViewManager;
import org.junit.Test;

import javax.swing.*;
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


    private int total = 0;
    private int minutes = 0;
    private int seconds = 0;
    String formatSeconds = "00";
    String formatMinutes = "00";

    private final int penaltyScore = 20000;

    private DebugConsole debugConsole = new DebugConsole(GameBoard.createGameBoard(owner));

    GameBoard gameBoard = GameBoard.createGameBoard(owner);

    @Test
    public void createGameBoard() {

      assertNotNull(gameBoard);
    }

    @Test
    public void ballReset() {
        assertEquals(ball.getPosition(),startPoint);
        assertEquals(new Point(startPoint.x-(int)player.getPlayerFace().getBounds().getWidth()/2,
                        startPoint.y),
                new Point(player.getPlayerFace().getBounds().x,
                        player.getPlayerFace().getBounds().y));
        assertEquals(0,ball.getSpeedX());
        assertEquals(0,ball.getSpeedY());
        ball.setSpeed(2,-2);
        assertEquals(2,ball.getSpeedX());
        assertEquals(-2,ball.getSpeedY());
        assertEquals(false,gameBoard.isBallLost());

    }

    @Test
    public void getOwner() {
        assertEquals(owner,gameBoard.getOwner());
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
        Impacts impacts = new Impacts(wall,gameBoard);

        assertNotNull(impacts);
    }

    @Test
    public void move() {
        assertEquals(startPoint,ball.getPosition());
        assertEquals(new Point(startPoint.x-(int)player.getPlayerFace().getBounds().getWidth()/2,
                        startPoint.y),
                new Point(player.getPlayerFace().getBounds().x,
                        player.getPlayerFace().getBounds().y));
    }

    @Test
    public void getBallCount() {
        assertEquals(3,gameBoard.getBallCount());
    }

    @Test
    public void setBallLost() {
        gameBoard.setBallLost(true);
        assertEquals(true,gameBoard.isBallLost());
    }

    @Test
    public void isBallLost() {
        assertEquals(false,gameBoard.isBallLost());
    }

    @Test
    public void ballEnd() {
        assertEquals(false,gameBoard.ballEnd());
    }

    @Test
    public void setBallCount() {
        gameBoard.setBallCount(3);
        assertEquals(3,gameBoard.getBallCount());
    }

    @Test
    public void isShowPauseMenu() {
        gameBoard.setShowPauseMenu(true);
        assertEquals(true,gameBoard.isShowPauseMenu());
    }

    @Test
    public void setShowPauseMenu() {
        assertEquals(false,GameBoard.createGameBoard(owner).isShowPauseMenu());
    }

    @Test
    public void getDefWidth() {
        assertEquals(600,gameBoard.getDefWidth());
    }

    @Test
    public void getDefHeight() {
        assertEquals(450,gameBoard.getDefHeight());
    }

    @Test
    public void resetGameBoard() {
        assertEquals(3,gameBoard.getBallCount());
        assertEquals(new Point(startPoint.x-(int)player.getPlayerFace().getBounds().getWidth()/2,
                startPoint.y),
                new Point(player.getPlayerFace().getBounds().x,
                        player.getPlayerFace().getBounds().y));
        assertEquals(startPoint,ball.getPosition());
    }

    @Test
    public void resetScore() {
        gameBoard.setTotal(0);
        gameBoard.setSeconds(0);
        gameBoard.setMinutes(0);
        assertEquals(0, gameBoard.getTotal());
        assertEquals(0, gameBoard.getMinutes());
        assertEquals(0, gameBoard.getSeconds());

        gameBoard.setFormatMinutes(String.format("%02d", gameBoard.getMinutes()));
        gameBoard.setFormatSeconds(String.format("%02d", gameBoard.getSeconds()));
        assertEquals("00",gameBoard.getFormatMinutes());
        assertEquals("00", gameBoard.getFormatSeconds());
    }

    @Test
    public void getScoreTimer() {

        Timer scoreTimer = new Timer(1000, e -> {

            if (debugConsole.getDebugPanel().isGivePenalty()) {
                total = total + penaltyScore;
            }
            debugConsole.getDebugPanel().setGivePenalty(false);

            total = total + 1000;
            minutes = (total / 60000) % 60;
            seconds = (total / 1000) % 60;

            formatSeconds = String.format("%02d", seconds);
            formatMinutes = String.format("%02d", minutes);

        });
        assertNotNull(scoreTimer);
    }

    @Test
    public void getTotal() {
        assertEquals(total,gameBoard.getTotal());
    }

    @Test
    public void setTotal() {
        gameBoard.setTotal(100);
        assertEquals(100,gameBoard.getTotal());
    }

    @Test
    public void getMinutes() {
        assertEquals(minutes,gameBoard.getTotal());
    }

    @Test
    public void setMinutes() {
        gameBoard.setMinutes(100);
        assertEquals(100,gameBoard.getMinutes());
    }

    @Test
    public void getSeconds() {
        assertEquals(0,gameBoard.getSeconds());
    }

    @Test
    public void setSeconds() {
        gameBoard.setSeconds(100);
        assertEquals(100,gameBoard.getSeconds());
    }

    @Test
    public void getFormatSeconds() {
        assertEquals("00",gameBoard.getFormatSeconds());
    }

    @Test
    public void setFormatSeconds() {
        gameBoard.setFormatSeconds("100");
        assertEquals("100",gameBoard.getFormatSeconds());
    }

    @Test
    public void getFormatMinutes() {
        assertEquals("00",gameBoard.getFormatMinutes());
    }

    @Test
    public void setFormatMinutes() {
        gameBoard.setFormatMinutes("100");
        assertEquals("100",gameBoard.getFormatMinutes());
    }
}