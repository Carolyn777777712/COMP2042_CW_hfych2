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
        title.setTitleColor(new Color(255,101,71));
        setBorder(title);
        setEditable(false);

        setPreferredSize(new Dimension(600,450));

        playerInstructions();
        gameOperations();
        scoreCalculations();
        startGame();

        setBackground(new Color(162,228,201));

    }


    private void bulletStyle(){
        attributes = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributes,"Monospaced");
        StyleConstants.setFontSize(attributes,20);
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

        }catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void scoreCalculations() {
        try {
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u2022 Score Calculations: \n", attributes);
            subBulletsStyle();
            instructions.insertString(instructions.getLength(), "\t \u2022 Break 1 Clay brick   = 1 score\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Break 1 Cement brick = 2 score\n", attributes);
            instructions.insertString(instructions.getLength(), "\t \u2022 Break 1 Steel brick  = 3 score\n", attributes);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void startGame(){
        try{
            bulletStyle();
            instructions.insertString(instructions.getLength(), "\u002A Press 'ENTER' to start playing :)", attributes);
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
