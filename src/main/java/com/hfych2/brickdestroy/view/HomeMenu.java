/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2021  Carolyn Han En Qi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hfych2.brickdestroy.view;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * {@link com.hfych2.brickdestroy.controller.HomeMenuController} is extracted
 *      from this class of the original code.<br>
 *
 * This class has been modified along with having additions to become a view class.<br>
 *
 * This class is the view class for the
 *      controller {@link com.hfych2.brickdestroy.controller.HomeMenuController}.
 */
public class HomeMenu extends JPanel {

    private ViewManager owner;
    private Dimension area;

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Newest Version";
    private static final String START_TEXT = "START";
    private static final String INFO_TEXT = "INFO";
    private static final String HIGH_SCORE_TEXT = "HIGHSCORES";
    private static final String EXIT_TEXT = "EXIT";

    private static final Color TEXT_COLOR = new Color(255,243,125);
    private static final Color CLICKED_TEXT = Color.BLACK;
    private static final Color ENTERED_TEXT = Color.BLACK;

    private static final Color CLICKED_BUTTON_COLOR = Color.RED;
    private static final Color ENTERED_START_BUTTON_COLOR = new Color(247,129,131);
    private static final Color ENTERED_EXIT_BUTTON_COLOR = new Color(255,179,112);

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle infoButton;
    private Rectangle highScoreButton;
    private Rectangle exitButton;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private boolean startClicked;
    private boolean infoClicked;
    private boolean highScoreClicked;
    private boolean exitClicked;

    private Image backgroundImage;
    private Image scaledBackground;

    private boolean startEntered;
    private boolean infoEntered;
    private boolean highScoreEntered;
    private boolean exitEntered;


    /**
     * Class constructor.<br>
     * Calls {@link HomeMenu#initialise()} and {@link HomeMenu#initialiseHomeMenu(Dimension)}.<br>
     * Sets layout to null.
     * @param owner the JFrame.
     * @param area the area of the HomeMenu.
     */
    public HomeMenu(ViewManager owner, Dimension area){

        this.owner = owner;
        this.area = area;

        initialise();

        initialiseHomeMenu(area);
        setLayout(null);

    }

    /**
     * Initialises the HomeMenu.
     */
    private void initialise(){

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
     * Initialises the elements of the HomeMenu.<br>
     * @param area the area of the HomeMenu.
     * @see ImageIO
     * @see Image#getScaledInstance(int, int, int)
     */
    private void initialiseHomeMenu(Dimension area){

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension((int) (area.width /1.85), area.height / 8);
        startButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        highScoreButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);


        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,50);
        creditsFont = new Font("Monospaced",Font.BOLD,20);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);

        try {
            backgroundImage = ImageIO.read(this.getClass().getResource("/images/brickWall.jpg"));
            scaledBackground = backgroundImage.getScaledInstance(area.width, area.height, Image.SCALE_SMOOTH);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Paints the view by calling {@link HomeMenu#drawMenu(Graphics2D)}.
     * @param g the Graphics.
     */
    public void paint(Graphics g){

        drawMenu((Graphics2D)g);
    }

    /**
     * Draws the HomeMenu.<br>
     * Calls {@link HomeMenu#drawText(Graphics2D)}
     *      and {@link HomeMenu#drawButton(Graphics2D)}.<br>
     * Background image is drawn here.
     * @param g2d the Graphics2D
     * @see Graphics2D#drawImage(Image, int, int, ImageObserver)
     */
    public void drawMenu(Graphics2D g2d){

        g2d.drawImage(scaledBackground,0,0,null);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * Draws the text in the HomeMenu using the {@link Graphics2D#drawString(String, int, int)}.
     * @param g2d the Graphics2D
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 8);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX+20,sY);


    }

    /**
     * Draws the rectangle buttons in the HomeMenu.
     * @param g2d the Graphics2D
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D startRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D infoRect = buttonFont.getStringBounds(INFO_TEXT,frc);
        Rectangle2D highScoreRect = buttonFont.getStringBounds(HIGH_SCORE_TEXT,frc);
        Rectangle2D exitRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        //startButton
        int x = (int) ((menuFace.width - startButton.width) /1.2);
        int y =(int) ((menuFace.height - startButton.height) * 0.4);

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - startRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - startRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.fill(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
        }
        else if(startEntered){
            g2d.setColor(ENTERED_START_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.fill(startButton);
            g2d.setColor(ENTERED_TEXT);
            g2d.drawString(START_TEXT,x,y);

        }
        else{
            g2d.setColor(TEXT_COLOR);
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        //infoButton
        x = startButton.x;
        y = startButton.y;

        y *= 1.4;

        infoButton.setLocation(x,y);

        x = (int)(infoButton.getWidth() - infoRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - infoRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (startButton.height * 0.9);

        g2d.setFont(buttonFont);

        if(infoClicked){
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.fill(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
        }
        else if(infoEntered){
            g2d.draw(infoButton);
            g2d.setColor(TEXT_COLOR);
            g2d.fill(infoButton);
            g2d.setColor(ENTERED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);

        }
        else{
            g2d.setColor(TEXT_COLOR);
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }

        x = infoButton.x;
        y = infoButton.y;

        y *= 1.4;

        highScoreButton.setLocation(x,y);

        x = (int)(highScoreButton.getWidth() - highScoreRect.getWidth()) / 2;
        y = (int)(highScoreButton.getHeight() - highScoreRect.getHeight()) / 2;

        x += highScoreButton.x;
        y += highScoreButton.y + (infoButton.height * 0.9);

        g2d.setFont(buttonFont);

        if(highScoreClicked){
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(highScoreButton);
            g2d.fill(highScoreButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(HIGH_SCORE_TEXT,x,y);
        }
        else if(highScoreEntered){
            g2d.draw(highScoreButton);
            g2d.setColor(TEXT_COLOR);
            g2d.fill(highScoreButton);
            g2d.setColor(ENTERED_TEXT);
            g2d.drawString(HIGH_SCORE_TEXT,x,y);

        }
        else{
            g2d.setColor(TEXT_COLOR);
            g2d.draw(highScoreButton);
            g2d.drawString(HIGH_SCORE_TEXT,x,y);
        }

        //exitButton
        x = highScoreButton.x;
        y = highScoreButton.y;

        y *= 1.25;

        exitButton.setLocation(x,y);


        x = (int)(exitButton.getWidth() - exitRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - exitRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (highScoreButton.height * 0.9);

        if(exitClicked){
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.fill(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
        }
        else if(exitEntered){
            g2d.setColor(ENTERED_EXIT_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.fill(exitButton);
            g2d.setColor(ENTERED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);

        }
        else{
            g2d.setColor(TEXT_COLOR);
            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);
        }
    }

    /**
     * Gets the JFrame.
     * @return the JFrame.
     */
    public ViewManager getOwner() {
        return owner;
    }

    /**
     * Gets the rectangle startButton.
     * @return the rectangle startButton.
     */
    public Rectangle getStartButton() {
        return startButton;
    }

    /**
     * Gets the rectangle infoButton.
     * @return the rectangle infoButton.
     */
    public Rectangle getInfoButton(){
        return infoButton;
    }

    /**
     * Gets the rectangle highScoreButton.
     * @return the rectangle highScoreButton.
     */
    public Rectangle getHighScoreButton(){
        return highScoreButton;
    }

    /**
     * Gets the rectangle exitButton.
     * @return the rectangle exitButton.
     */
    public Rectangle getExitButton() {
        return exitButton;
    }

    /**
     * Flag for rectangle startButton is clicked.
     * @return true if rectangle startButton is clicked and false otherwise.
     */
    public boolean isStartClicked() {
        return startClicked;
    }

    /**
     * Flag for rectangle infoButton is clicked.
     * @return true if rectangle infoButton is clicked and false otherwise.
     */
    public boolean isInfoClicked(){
        return infoClicked;
    }

    /**
     * Flag for rectangle highScoreButton is clicked.
     * @return true if rectangle highScoreButton is clicked and false otherwise.
     */
    public boolean isHighScoreClicked(){
        return highScoreClicked;
    }

    /**
     * Flag for rectangle exitButton is clicked.
     * @return true if rectangle exitButton is clicked and false otherwise.
     */
    public boolean isExitClicked() {
        return exitClicked;
    }

    /**
     * Sets the flag for if rectangle startButton is clicked.
     * @param startClicked true if rectangle startButton is clicked and false otherwise.
     */
    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }

    /**
     * Sets the flag for if rectangle infoButton is clicked.
     * @param infoClicked true if rectangle infoButton is clicked and false otherwise.
     */
    public void setInfoClicked(boolean infoClicked){
        this.infoClicked = infoClicked;
    }

    /**
     * Sets the flag for if rectangle highScoreButton is clicked.
     * @param highScoreClicked true if rectangle highScoreButton is clicked and false otherwise.
     */
    public void setHighScoreClicked(boolean highScoreClicked){
        this.highScoreClicked = highScoreClicked;
    }

    /**
     * Sets the flag for if rectangle exitButton is clicked.
     * @param exitClicked true if rectangle exitButton is clicked and false otherwise.
     */
    public void setExitClicked(boolean exitClicked) {
        this.exitClicked = exitClicked;
    }

    /**
     * Sets the flag for if mouse entered the HomeMenu.
     * @param startEntered true if mouse entered the HomeMenu.
     */
    public void setStartEntered(boolean startEntered){
        this.startEntered = startEntered;
    }

    /**
     * Sets the flag for if mouse entered the HomeMenu.
     * @param infoEntered true if mouse entered the HomeMenu.
     */
    public void setInfoEntered(boolean infoEntered){
        this.infoEntered = infoEntered;
    }

    /**
     * Sets the flag for if mouse entered the HomeMenu.
     * @param highScoreEntered true if mouse entered the HomeMenu.
     */
    public void setHighScoreEntered(boolean highScoreEntered){
        this.highScoreEntered = highScoreEntered;
    }

    /**
     * Sets the flag for if mouse entered the HomeMenu.
     * @param exitEntered true if mouse entered the HomeMenu.
     */
    public void setExitEntered(boolean exitEntered){
        this.exitEntered = exitEntered;
    }

}
