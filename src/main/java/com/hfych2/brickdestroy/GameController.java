package com.hfych2.brickdestroy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameController implements KeyListener, MouseListener, MouseMotionListener{
    private GameBoard gameBoard;
    private GameView gameView;

    private DebugConsole debugConsole;

    private Timer gameTimer;

    public GameController(GameBoard gameBoard, GameView gameView) {
        this.gameBoard = gameBoard;
        this.gameView = gameView;

        debugConsole = new DebugConsole(gameBoard.getOwner(), gameBoard.getWall(), gameBoard, gameView);

        gameTimer = new Timer(10,e -> gameCycle());
    }

    void gameCycle() {

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
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!gameBoard.isShowPauseMenu())
                    if (gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
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
        gameView.setMessage("Focus Lost");
        gameView.repaint();
    }
}