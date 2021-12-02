package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.model.PlayerInfo;
import com.hfych2.brickdestroy.view.GameView;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.Collator;
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
    //private final int rewardScore = -20000;

    private ArrayList<String> userName = new ArrayList<String>();
    private String newUser;
    private int save;
    private String highScoreUserName = "";
    private ArrayList<Integer> scores = new ArrayList<Integer>();
    private PlayerInfo playerInfo;


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

/*              if(debugConsole.getDebugPanel().isGiveReward()){
                  total = total + rewardScore;
              }
              debugConsole.getDebugPanel().setGiveReward(false);*/

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

        playerInfo = new PlayerInfo(userName, scores);
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


        Integer maxScore = Collections.min(playerInfo.getScore());
        Integer maxScoreIndex = playerInfo.getScore().indexOf(maxScore);
        highScoreUserName = playerInfo.getUserName().get(maxScoreIndex);

        int maxMinutes = 0;
        int maxSeconds = 0;

        maxMinutes = (maxScore / 60000) % 60;
        maxSeconds = (maxScore / 1000) % 60;

        String formatMaxSeconds;
        String formatMaxMinutes;

        formatMaxSeconds = String.format("%02d", maxSeconds);
        formatMaxMinutes = String.format("%02d", maxMinutes);

        System.out.println("Highest score is" + highScoreUserName + ", with a score of " + formatMaxMinutes + ":" + formatMaxSeconds);

        System.out.println(Arrays.toString(playerInfo.getUserName().toArray()));
        JOptionPane.showMessageDialog(gameView, "This is your score " + formatMinutes + ":" + formatSeconds, "Score Pop Up Message", JOptionPane.INFORMATION_MESSAGE);

/*

        try{
            File highScoreFile = new File("highScore.ser");
            if(!highScoreFile.exists())
                highScoreFile.createNewFile();

            FileOutputStream writeTo = new FileOutputStream("highScore.ser",true);

            ObjectOutputStream writeThis = new ObjectOutputStream(writeTo);


            writeThis.writeObject(playerInfo);
            writeThis.flush();
            writeThis.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

        PlayerInfo playerInfo2 = null;
        try{
            FileInputStream readOf = new FileInputStream("highScore.ser");
            ObjectInputStream readThis = new ObjectInputStream(readOf);

            playerInfo2 = (PlayerInfo)readThis.readObject();
            readThis.close();
            System.out.println(playerInfo2.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
*/
        File highScoreFile = new File("highScore.txt");
        String line = "";

        try {
            FileWriter fileWriter = new FileWriter(highScoreFile,true);
            Writer writer = new BufferedWriter(fileWriter);
            int size = playerInfo.getUserName().size();
            for (int i = 0; i < size; i++) {

                writer.write(playerInfo.nameToString());
                writer.write(playerInfo.scoreToString());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
                    if(!reader.ready()){
                        throw new IOException();
                    }

                while(true){
                    try {
                        if (!((line = reader.readLine())!=null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //playerInfo.getUserName().add(line);
                }
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}