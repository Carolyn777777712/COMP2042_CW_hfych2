package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.model.PlayerInfo;
import com.hfych2.brickdestroy.view.GameView;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;


public class GameController implements KeyListener, MouseListener, MouseMotionListener {

    private GameBoard gameBoard;
    private GameView gameView;

    private DebugConsole debugConsole;

    private Timer gameTimer;
    private int total = 0;
    private int minutes = 0;
    private int seconds = 0;

    private final int penaltyScore = 20000;

    private ArrayList<String> userName = new ArrayList<String>();
    private String newUser;
    private int save;
    private String bestScoreUserName = "";
    private int bestScoreLevel = 0;
    private ArrayList<Integer> score = new ArrayList<Integer>();
    private PlayerInfo playerInfo;
    private ArrayList<Integer> currentLevel = new ArrayList<Integer>();


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

            if (debugConsole.getDebugPanel().isGivePenalty()) {
                total = total + penaltyScore;
            }
            debugConsole.getDebugPanel().setGivePenalty(false);

            total = total + 1000;
            minutes = (total / 60000) % 60;
            seconds = (total / 1000) % 60;

            formatSeconds = String.format("%02d", seconds);
            formatMinutes = String.format("%02d", minutes);

            gameView.setScoreMessage("Time Taken (mm:ss) "
                    + formatMinutes + ":" + formatSeconds);
        });

        debugConsole = new DebugConsole(this);

        gameTimer = new Timer(10, e -> gameCycle());

        playerInfo = new PlayerInfo(userName, score, currentLevel);
    }

    public void resetScore() {

        scoreTimer.stop();
        total = 0;
        minutes = 0;
        seconds = 0;

        formatSeconds = String.format("%02d", seconds);
        formatMinutes = String.format("%02d", minutes);

        gameView.setScoreMessage("Time Taken(mm:ss) " + formatMinutes + ":" + formatSeconds);
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
                gameView.setScoreMessage("");

                total = 0;
                minutes = 0;
                seconds = 0;
            }
            gameBoard.ballReset();
            gameTimer.stop();
            scoreTimer.stop();
        } else if (gameBoard.getWall().isDone()) {
            if (gameBoard.getWall().hasLevel()) {
                gameView.setMessage("Go to Next Level");
                save = JOptionPane.showConfirmDialog(
                        gameBoard.getOwner(),
                        "Would you like to save the current score?",
                        "Save Score",
                        JOptionPane.YES_NO_OPTION);
                gameTimer.stop();
                if (save == 0) {
                    scoreSaving();
/*                    resetScore();
                    gameBoard.ballReset();
                    gameBoard.getWall().wallReset();
                    gameBoard.resetGameBoard();*/
                }
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

    private void scoreSaving() {
        newUser = JOptionPane.showInputDialog(gameView, "What is your name?", "Save Score", JOptionPane.INFORMATION_MESSAGE);

        playerInfo.getUserName().add(newUser);
        playerInfo.getScore().add(total);
        playerInfo.getCurrentLevel().add(gameBoard.getWall().getLevels().getLevel());

        JOptionPane.showMessageDialog(gameView, "This is your score "
                + formatMinutes + ":" + formatSeconds
                + " for level: " + currentLevel.get(currentLevel.size() - 1),
                "Score Pop Up Message", JOptionPane.INFORMATION_MESSAGE);
        //Pop up for score of current level

        File scoresFile = new File("scores.txt");

        try {
            FileWriter fileWriter = new FileWriter(scoresFile, true);
            Writer writer = new BufferedWriter(fileWriter);
            if (!playerInfo.getUserName().isEmpty()) {

                writer.write(playerInfo.nameToString());
                writer.write(playerInfo.scoreToString());
                writer.write(playerInfo.currentLevelToString());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }//writes every level of each round (each run of the game)

        ArrayList<String> name = new ArrayList<>();
        ArrayList<Integer> thisLevel = new ArrayList<>();
        ArrayList<Integer> thisScore = new ArrayList<>();
        String[] splitParts;

        try {
            Scanner scanner = new Scanner(scoresFile);

                while (scanner.hasNextLine()) {
                    String wholeLine = scanner.nextLine();
                    splitParts = wholeLine.split(": ");
                    String label = splitParts[0];
                    String value = splitParts[1];

                    if (label.equals("Name"))
                        name.add(value);
                    else if (label.equals("Score"))
                        thisScore.add(Integer.parseInt(value));
                    else
                        thisLevel.add(Integer.parseInt(value));

                }
            Integer minScore = Collections.min(thisScore);
            Integer minScoreIndex = thisScore.indexOf(minScore);
            bestScoreUserName = name.get(minScoreIndex);
            bestScoreLevel = thisLevel.get(minScoreIndex);

            int minMinutes = 0;
            int minSeconds = 0;

            minMinutes = (minScore / 60000) % 60;
            minSeconds = (minScore / 1000) % 60;

            String formatMinSeconds;
            String formatMinMinutes;

            formatMinSeconds = String.format("%02d", minSeconds);
            formatMinMinutes = String.format("%02d", minMinutes);

            JOptionPane.showMessageDialog(gameView, "The all time best score is " + bestScoreUserName
                    + " with a score of " + formatMinMinutes
                    + ":" + formatMinSeconds + " for level: "
                    + bestScoreLevel, "Best Score Pop Up", JOptionPane.INFORMATION_MESSAGE);
            //Pop up all time best score across all levels

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}