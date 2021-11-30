package com.hfych2.brickdestroy.view;

import com.hfych2.brickdestroy.controller.GameController;
import com.hfych2.brickdestroy.model.Ball;
import com.hfych2.brickdestroy.model.Brick;
import com.hfych2.brickdestroy.model.GameBoard;
import com.hfych2.brickdestroy.model.Player;


import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;


public class GameView extends JPanel {

    private GameBoard gameBoard;


    private static final Color BG_COLOR = Color.WHITE;


    private Font menuFont;
    private static final int TEXT_SIZE = 30;

    private static final String PAUSE = "Pause Menu";
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";

    private Rectangle continueButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle exitButtonRect;

    private static final Color MENU_COLOR = new Color(0, 255, 0);

    private String message;
    private int strLen;

    private String scoreMessage;


    public GameView(){
        initialise();
    }

    void initialise()
    {
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        message = "";
        scoreMessage = "";
        strLen = 0;

    }

    public void updateView(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message, 250, 225);
        g2d.drawString(scoreMessage,225,250);

        drawBall(gameBoard.getBall(), g2d);

        for (Brick b : gameBoard.getWall().getLevels().getBricks())
            if (!b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(gameBoard.getPlayer(), g2d);

        if (gameBoard.isShowPauseMenu())
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameBoard.getDefWidth(), gameBoard.getDefHeight());

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if (strLen == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = (this.getHeight() / 10);

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if (continueButtonRect == null) {
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x, y - continueButtonRect.height);
        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if (restartButtonRect == null) {
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x, y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if (exitButtonRect == null) {
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x, y - exitButtonRect.height);
        }

        g2d.drawString(EXIT, x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setScoreMessage(String scoreMessage) {
        this.scoreMessage = scoreMessage;
    }


    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }
}