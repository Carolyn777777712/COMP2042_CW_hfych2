package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.view.GameView;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameController implements KeyListener, MouseListener, MouseMotionListener{

    private GameBoard gameBoard;
    private GameView gameView;

    private DebugConsole debugConsole;

    private Timer gameTimer;
    private int total = 0;
    private int minutes = 0;
    private int seconds = 0;

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameView getGameView() {
        return gameView;
    }


    private Timer scoreTimer;
    private String formatSeconds;
    private String formatMinutes;


    public GameController(GameBoard gameBoard, GameView gameView) {

        this.gameBoard = gameBoard;
        this.gameView = gameView;

        gameView.updateView(this.gameBoard);

          scoreTimer = new Timer(1000, e -> {
                                                    total = total + 1000;
                                                    minutes = (total/60000) % 60;
                                                    seconds = (total/1000) % 60;

                                                    formatSeconds = String.format("%02d",seconds);
                                                    formatMinutes = String.format("%02d",minutes);

                                                    gameView.setScoreMessage("Time Taken (mm:ss) "
                                                            + formatMinutes + ":" + formatSeconds);
        });

        debugConsole = new DebugConsole(this);

        gameTimer = new Timer(10,e -> gameCycle());
    }

    public void resetScore(){

        scoreTimer.stop();
        total = 0;
        minutes = 0;
        seconds = 0;

        formatSeconds = String.format("%02d",seconds);
        formatMinutes = String.format("%02d",minutes);

        gameView.setScoreMessage("Time Taken(mm:ss) "+ formatMinutes + ":" + formatSeconds);
    }

    private void gameCycle() {

        gameBoard.move();
        gameBoard.getImpacts().findImpacts();

        gameView.setMessage(String.format("Bricks: %d Balls %d", gameBoard.getWall().getBrickCount(), gameBoard.getBallCount()));

        if (gameBoard.isBallLost()) {
            if (gameBoard.ballEnd()) {
                gameBoard.getWall().wallReset();
                gameBoard.resetGameBoard();
                gameView.setMessage("Game over");
            }
            gameBoard.ballReset();
            gameTimer.stop();
        } else if (gameBoard.getWall().isDone()) {
            if (gameBoard.getWall().hasLevel()) {
                gameView.setMessage("Go to Next Level");
                gameTimer.stop();
                resetScore();
                gameBoard.ballReset();
                gameBoard.getWall().wallReset();
                gameBoard.resetGameBoard();
                gameBoard.getWall().nextLevel();
            } else {
                gameView.setMessage("ALL WALLS DESTROYED");
                gameTimer.stop();
            }
        }

        gameView.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                gameBoard.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                gameBoard.getPlayer().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                gameBoard.setShowPauseMenu(!gameBoard.isShowPauseMenu());
                gameView.repaint();
                scoreTimer.stop();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!gameBoard.isShowPauseMenu()) {
                    if (gameTimer.isRunning()) {
                        gameTimer.stop();
                    } else {
                        gameTimer.start();
                    }
                    if (scoreTimer.isRunning()) {
                        scoreTimer.stop();
                    } else {
                        scoreTimer.start();
                    }
                }
                else {
                    if (scoreTimer.isRunning()) {
                        scoreTimer.stop();
                    } else {
                        scoreTimer.start();
                    }
                }

                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown()) {
                    debugConsole.setVisible(true);
                    scoreTimer.stop();
                }
            default:
                gameBoard.getPlayer().stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        gameBoard.getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!gameBoard.isShowPauseMenu())
            return;
        if (gameView.getContinueButtonRect().contains(p)) {
            gameBoard.setShowPauseMenu(false);
            gameView.repaint();
        } else if (gameView.getRestartButtonRect().contains(p)) {
            gameView.setMessage("Restarting Game...");
            gameBoard.ballReset();//new
            gameBoard.getWall().wallReset();
            gameBoard.resetGameBoard();//new
            resetScore();
            gameBoard.setShowPauseMenu(false);
            gameView.repaint();
        } else if (gameView.getExitButtonRect().contains(p)) {
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (gameView.getExitButtonRect() != null && gameBoard.isShowPauseMenu()) {
            if (gameView.getExitButtonRect().contains(p) || gameView.getContinueButtonRect().contains(p) || gameView.getRestartButtonRect().contains(p))
                gameView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                gameView.setCursor(Cursor.getDefaultCursor());
        } else {
            gameView.setCursor(Cursor.getDefaultCursor());
        }
    }

    public void onLostFocus() {
        gameTimer.stop();
        scoreTimer.stop();
        gameView.setMessage("Focus Lost");
        gameView.repaint();
    }
}