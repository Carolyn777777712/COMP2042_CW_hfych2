package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.model.PlayerInfo;
import com.hfych2.brickdestroy.model.ScoreSorting;
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
    private PlayerInfo playerInfo;

    private Timer gameTimer;

    private ArrayList<String> userName = new ArrayList<String>();
    private ArrayList<Integer> score = new ArrayList<Integer>();
    private ArrayList<Integer> currentLevel = new ArrayList<Integer>();
    private String newUser;
    private int save;
    private String bestScoreUserName = "";
    private int bestScoreLevel = 0;


    public GameController(GameBoard gameBoard, GameView gameView) {

        this.gameBoard = gameBoard;
        this.gameView = gameView;

        gameView.updateView(this.gameBoard);

        gameBoard.getDebugConsole().updateView(gameView);

        gameTimer = new Timer(10, e -> gameCycle());

        playerInfo = new PlayerInfo(userName, score, currentLevel);
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

                gameBoard.resetScore();
            }
            gameBoard.ballReset();
            gameBoard.getDebugConsole().getDebugPanel().setGivePenalty(true);
            gameTimer.stop();
            gameBoard.getScoreTimer().stop();
        } else if (gameBoard.getWall().isDone()) {
            if (gameBoard.getWall().hasLevel()) {
                gameView.setMessage("Go to Next Level");
                save = JOptionPane.showConfirmDialog(
                        gameBoard.getOwner(),
                        "Would you like to save the current score?",
                        "Save Score Pop Up",
                        JOptionPane.YES_NO_OPTION);
                gameTimer.stop();
                if (save == 0) {
                    scoreSaving();
                }
                gameBoard.resetScore();
                gameBoard.ballReset();
                gameBoard.getWall().wallReset();
                gameBoard.resetGameBoard();
                gameBoard.getWall().nextLevel();
            } else {
                gameView.setMessage("ALL WALLS DESTROYED");
                save = JOptionPane.showConfirmDialog(
                        gameBoard.getOwner(),
                        "Would you like to save the current score?",
                        "Save Score Pop Up",
                        JOptionPane.YES_NO_OPTION);
                gameTimer.stop();
                if (save == 0)
                    scoreSaving();
                gameBoard.getOwner().enableHomeMenu();
                gameBoard.resetScore();
                gameBoard.getWall().getLevels().setLevel(0);
                gameBoard.ballReset();
                gameBoard.resetGameBoard();
            }
        }

        gameView.repaint();
    }

    private void scoreSaving() {

        newUser = JOptionPane.showInputDialog(gameView, "What is your name?",
                "Save Score", JOptionPane.INFORMATION_MESSAGE);

        playerInfo.getUserName().add(newUser);
        playerInfo.getScore().add(gameBoard.getTotal());
        playerInfo.getCurrentLevel().add(gameBoard.getWall().getLevels().getLevel());

        JOptionPane.showMessageDialog(gameView, "This is your score "
                        + gameBoard.getFormatMinutes() + ":" + gameBoard.getFormatSeconds()
                        + " for level: " + currentLevel.get(currentLevel.size() - 1),
                "Score Pop Up Message", JOptionPane.INFORMATION_MESSAGE);
        //Pop up for score of current level

        scoreConditions();

        File scoresFile = new File("highScoresList.txt");

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

            int minMinutes;
            int minSeconds;

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

            ArrayList<ScoreSorting> scoreSorting = new ArrayList<>();

            for(int i = 0; i < name.size(); i++)
                scoreSorting.add(new ScoreSorting(name.get(i),thisScore.get(i),thisLevel.get(i)));

            Collections.sort(scoreSorting,ScoreSorting.sortScore);

            File sortedScoresFile = new File("sortedHighScoresList.txt");

            try {
                FileWriter fileWriter = new FileWriter(sortedScoresFile);
                Writer writer = new BufferedWriter(fileWriter);

                if(!scoreSorting.isEmpty())
                    writer.write(scoreSorting.toString());

                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }//writes every level of each round (each run of the game)

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void scoreConditions(){
        if(currentLevel.get(currentLevel.size()-1) == 1)
        {
            if(score.get(score.size()-1) <= 1200000){
                JOptionPane.showMessageDialog(gameView,
                        "Your score has been successfully saved to the highScoresList",
                        "Score Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
            } else {
                playerInfo.getUserName().remove(playerInfo.getUserName().size()-1);
                playerInfo.getScore().remove(playerInfo.getScore().size()-1);
                playerInfo.getCurrentLevel().remove(playerInfo.getCurrentLevel().size()-1);
                JOptionPane.showMessageDialog(gameView,
                        "Sorry, time taken for this level needs to be less than or equal to 2 minutes to be saved into highScoreList",
                        "Unable to Save Score", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(currentLevel.get(currentLevel.size()-1) == 2)
        {
            if(score.get(score.size()-1) <= 150000){
                JOptionPane.showMessageDialog(gameView,
                        "Your score has been successfully saved to the highScoresList",
                        "Score Saved Successfully", JOptionPane.INFORMATION_MESSAGE);

            } else {
                playerInfo.getUserName().remove(playerInfo.getUserName().size()-1);
                playerInfo.getScore().remove(playerInfo.getScore().size()-1);
                playerInfo.getCurrentLevel().remove(playerInfo.getCurrentLevel().size()-1);
                JOptionPane.showMessageDialog(gameView,
                        "Sorry, time taken for this level needs to be less than or equal to 2.5 minutes to be saved into highScoreList",
                        "Unable to Save Score", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if((currentLevel.get(currentLevel.size()-1) == 3) || (currentLevel.get(currentLevel.size()-1) == 4))
        {
            if(score.get(score.size()-1) <= 180000){
                JOptionPane.showMessageDialog(gameView,
                        "Your score has been successfully saved to the highScoresList",
                        "Score Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
            } else {
                playerInfo.getUserName().remove(playerInfo.getUserName().size()-1);
                playerInfo.getScore().remove(playerInfo.getScore().size()-1);
                playerInfo.getCurrentLevel().remove(playerInfo.getCurrentLevel().size()-1);
                JOptionPane.showMessageDialog(gameView,
                        "Sorry, time taken for this level needs to be less than or equal to 3 minutes to be saved into highScoreList",
                        "Unable to Save Score", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if((currentLevel.get(currentLevel.size()-1) == 5) || (currentLevel.get(currentLevel.size()-1) == 6))
        {
            if(score.get(score.size()-1) <= 240000){
                JOptionPane.showMessageDialog(gameView,
                        "Your score has been successfully saved to the highScoresList",
                        "Score Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
            } else {
                playerInfo.getUserName().remove(playerInfo.getUserName().size()-1);
                playerInfo.getScore().remove(playerInfo.getScore().size()-1);
                playerInfo.getCurrentLevel().remove(playerInfo.getCurrentLevel().size()-1);
                JOptionPane.showMessageDialog(gameView,
                        "Sorry, time taken for this level needs to be less than or equal to 4 minutes to be saved into highScoreList",
                        "Unable to Save Score", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void onLostFocus() {
        gameTimer.stop();
        gameBoard.getScoreTimer().stop();
        gameView.setMessage("Focus Lost");
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
                gameBoard.getScoreTimer().stop();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!gameBoard.isShowPauseMenu()) {
                    if (gameTimer.isRunning()) {
                        gameTimer.stop();
                    } else {
                        gameTimer.start();
                    }
                    if (gameBoard.getScoreTimer().isRunning()) {
                        gameBoard.getScoreTimer().stop();
                    } else {
                        gameBoard.getScoreTimer().start();
                    }
                }
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown()) {
                    gameBoard.getDebugConsole().setVisible(true);
                    gameBoard.getScoreTimer().stop();
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
            gameBoard.resetScore();
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

}