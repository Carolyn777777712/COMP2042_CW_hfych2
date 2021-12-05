package com.hfych2.brickdestroy.view;


import com.hfych2.brickdestroy.controller.GameFrame;


import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;


public class InfoView extends JTextPane implements KeyListener{

    private static final String titleText = "Instructions";

    private static Document instructions;
    private static SimpleAttributeSet attributes;
    private MatteBorder border;

    private GameFrame owner;


    public InfoView(GameFrame owner) {

        this.owner = owner;

        instructions = this.getStyledDocument();

        border = new MatteBorder(5,7,7,7,new Color(255,101,71));

        TitledBorder title = BorderFactory.createTitledBorder(border,titleText);
        title.setTitleFont(new Font("Monospaced", Font.BOLD,15));
        title.setTitleColor(new Color(148,77,255));
        setBorder(title);
        setEditable(false);

        setPreferredSize(new Dimension(850,450));

        playerInstructions();
        gameOperations();
        debugConsole();
        scoreCalculations();
        penaltyCalculations();
        scoreSaving();
        savingConditions();
        caution();
        startGame();

        setBackground(new Color(162,228,201));
    }


    private void bulletStyle(){
        attributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributes,"Monospaced");
        StyleConstants.setFontSize(attributes,17);
        StyleConstants.setLineSpacing(attributes,2);
        StyleConstants.setAlignment(attributes,1);
        StyleConstants.setBold(attributes,true);
    }

    private void subBulletsStyle(){
        attributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributes,"Monospaced");
        StyleConstants.setFontSize(attributes,15);
        StyleConstants.setLineSpacing(attributes,2);
        StyleConstants.setAlignment(attributes,1);
        StyleConstants.setBold(attributes,false);
    }

    private void playerInstructions(){
        try {
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 To Move Player: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Press 'A' key to move player left\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Press 'D' key to move player right\n", attributes);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void gameOperations(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 To Start or Pause game: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Press 'SPACEBAR' to start or pause the game\n", attributes);
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 To Access Pause Menu: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Press 'ESC' to enter Pause Menu\n", attributes);
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 To Access Debug Console: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Hold 'ALT' and 'SHIFT' and 'F1'\n", attributes);
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 When 'Focus Lost' is shown: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Press 'SPACEBAR' to continue to game\n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void debugConsole(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 How to Use Debug Console: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Use the Slider to change to preferred ball speed\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"Reset Ball Speed\" must the pressed to change the speed\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \" Reset Balls\" button resets speed and ball count to default\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"Skip to: \" button skips to the next level on click \n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void scoreCalculations() {
        try {
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Scores: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Score is the time taken to complete the level\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Score is only shown/taken account when a level is completed\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"Save Score Pop Up\" will be shown after a level is completed\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"Score Pop Up\" shows the score of the current level\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The best score is the minimum time taken to complete a level\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"Best Score Pop Up\" shows the all time best score across all levels\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Each use of the penalties results in an addition of 20 seconds to score\n", attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void penaltyCalculations(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Penalties: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Changing ball speed in Debug Console\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Reset balls in Debug Console\n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void scoreSaving(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Score Saving: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Scores are saved in a text file: highScoresList.txt\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Scores of each level that meet the conditions are saved into file\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Scores saved in the file are unprocessed scores (in milliseconds,ms)\n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void savingConditions(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Score Saving to highScoresList.txt Conditions: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 The \"YES\" option is chosen in the \"Save Score Pop Up\" \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 1 is completed in less than or equal to 2 minutes time   (120000 ms) \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 2 is completed in less than or equal to 2.5 minutes time (150000 ms) \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 3 is completed in less than or equal to 3 minutes time   (180000 ms) \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 4 is completed in less than or equal to 3 minutes time   (180000 ms) \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 5 is completed in less than or equal to 4 minutes time   (240000 ms) \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Level 6 is completed in less than or equal to 4 minutes time   (240000 ms) \n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void caution(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Caution: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Completion of last level and score saving will bring back to HomeMenu \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 But, \"Focus Lost\" will be shown after choosing \"START\" in the HomeMenu \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Please press 'SPACEBAR' to regain focus to the screen \n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 And, select \"NO\" option for \"Save Score Pop Up\" to proceed with the game \n", attributes);
        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void startGame(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u002A \u002A Press 'ENTER' to start playing :)", attributes);
        }catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            owner.enableGameBoard();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
