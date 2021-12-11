package com.hfych2.brickdestroy.controller;


import com.hfych2.brickdestroy.view.HomeMenu;
import com.hfych2.brickdestroy.view.ViewManager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is extracted from {@link HomeMenu} of the original code
 *      and modified along with having additions.<br>
 *
 * This class is the controller class for {@link HomeMenu}.<br>
 * This class defines the actions to be performed when HomeMenu options are clicked.<br>
 *
 * It calls the {@link ViewManager#enableGameBoard()}.<br>
 * It reads and displays sortedHighScoresList.txt
 *      {@link HomeMenuController#readSortedHighScores()}<br>
 *      {@link HomeMenuController#displaySortedHighScores()}.<br>
 * It calls the {@link ViewManager#enableInfoView()}.<br>
 * It terminates the process successfully {@link System#exit(int)}.<br>
 *
 * @author Carolyn
 *
 */

public class HomeMenuController implements MouseListener, MouseMotionListener{

    private final HomeMenu homeMenu;

    private ArrayList<String> wholeString = new ArrayList<String>();

    private JScrollPane jScrollPane;
    private JTextArea jTextArea;
    private int i = 1;

    /**
     * Class constructor.
     * @param homeMenu the view
     */
    public HomeMenuController(HomeMenu homeMenu) {

        this.homeMenu = homeMenu;
    }

    /**
     * Reads the sortedHighScoresList.txt and stores its contents in an ArrayList of type String<br>
     * @see Scanner
     * @see ArrayList
     */
    private void readSortedHighScores(){
        try {
            File sortedHighScoresFile = new File("sortedHighScoresList.txt");
            Scanner sortedScanner = new Scanner(sortedHighScoresFile);

            while (sortedScanner.hasNextLine()) {
                String wholeLine = sortedScanner.nextLine();

                wholeString.add(wholeLine);

            }

            sortedScanner.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Displays the sortedHighScoresList.txt contents into a JTextArea with a JScrollPane.<br>
     * Adds a JButton to the JTextArea to proceed to the "INFO" page
     *      with {@link ViewManager#enableInfoView()}.<br>
     * Calls the {@link HomeMenuController#readSortedHighScores()}
     * @see JTextArea
     * @see JScrollPane
     * @see JButton
     * @see ViewManager#dispose()
     * @see ViewManager#remove(Component)
     * @see JTextArea#append(String)
     * @see ViewManager#initialise()
     *
     */
    private void displaySortedHighScores(){
        if(i==1) {
            readSortedHighScores();
            i++;
        }

        jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Monospaced",Font.BOLD,17));
        jTextArea.setBackground(new Color(255,153,255));
        jTextArea.setVisible(true);
        jTextArea.setPreferredSize(new Dimension(700, 450));
        jTextArea.setEditable(false);
        jTextArea.setFocusable(true);

        jTextArea.append("\t\tSORTED HIGHSCORES LIST\n\n");

        for(int j = 0; j < wholeString.size(); j++)
        {
            jTextArea.append(wholeString.get(j));
            if(j==0)
                jTextArea.append("->CHAMPION");
            jTextArea.append("\n");
        }

        JButton jButton = new JButton("INFO");
        jButton.setBackground(Color.cyan);
        jButton.setBounds(600,250,100,100);

        jButton.addActionListener(e -> {
            homeMenu.getOwner().dispose();
            homeMenu.getOwner().remove(jScrollPane);
            homeMenu.getOwner().enableInfoView();

        });
        jButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jButton.setBackground(Color.YELLOW);
                jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                jButton.setBackground(Color.CYAN);
            }
        });
        jTextArea.add(jButton);

        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        homeMenu.getOwner().remove(homeMenu);
        homeMenu.getOwner().add(jScrollPane,BorderLayout.CENTER);
        homeMenu.getOwner().initialise();
    }

    /**
     * Displays different views based on option chosen in HomeMenu.<br>
     * {@link HomeMenu#getStartButton()} {@link ViewManager#enableGameBoard()}<br>
     * {@link HomeMenu#getInfoButton()}  {@link ViewManager#enableInfoView()}<br>
     * {@link HomeMenu#getHighScoreButton()} {@link HomeMenuController#displaySortedHighScores()}<br>
     * {@link HomeMenu#getExitButton()}  {@link System#exit(int)}<br>
     *
     * @see HomeMenu#getOwner()
     * @see ViewManager#remove(Component)
     *
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p)) {
            homeMenu.getOwner().enableGameBoard();
        }

        else if (homeMenu.getInfoButton().contains(p)){
            homeMenu.getOwner().enableInfoView();
        }

        else if (homeMenu.getHighScoreButton().contains(p)){
            homeMenu.getOwner().dispose();
            homeMenu.getOwner().remove(homeMenu);
            displaySortedHighScores();
        }

        else if (homeMenu.getExitButton().contains(p)) {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }

    /**
     * Paints a different view of the options in HomeMenu on pressed and sets the boolean setters below to true.<br>
     * @see HomeMenu#setStartClicked(boolean)
     * @see HomeMenu#setInfoClicked(boolean)
     * @see HomeMenu#setHighScoreClicked(boolean)
     * @see HomeMenu#setExitClicked(boolean)
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p)) {
            homeMenu.setStartClicked(true);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

        }

        else if (homeMenu.getInfoButton().contains(p)){
            homeMenu.setInfoClicked(true);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);
        }

        else if (homeMenu.getHighScoreButton().contains(p)){
            homeMenu.setHighScoreClicked(true);
            homeMenu.repaint(homeMenu.getHighScoreButton().x, homeMenu.getHighScoreButton().y, homeMenu.getHighScoreButton().width + 1, homeMenu.getHighScoreButton().height);
        }

        else if (homeMenu.getExitButton().contains(p)) {
            homeMenu.setExitClicked(true);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);
        }
    }

    /**
     * Paints the default view of the options in HomeMenu on released and sets the boolean setters below to false.<br>
     * @see HomeMenu#setStartClicked(boolean)
     * @see HomeMenu#setInfoClicked(boolean)
     * @see HomeMenu#setHighScoreClicked(boolean)
     * @see HomeMenu#setExitClicked(boolean)
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (homeMenu.isStartClicked()) {
            homeMenu.setStartClicked(false);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

        }

        else if (homeMenu.isInfoClicked()){
            homeMenu.setInfoClicked(false);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);
        }

        else if (homeMenu.isHighScoreClicked()){
            homeMenu.setHighScoreClicked(false);
            homeMenu.repaint(homeMenu.getHighScoreButton().x, homeMenu.getHighScoreButton().y, homeMenu.getHighScoreButton().width + 1, homeMenu.getHighScoreButton().height + 1);
        }

        else if (homeMenu.isExitClicked()) {
            homeMenu.setExitClicked(false);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);
        }
    }

    /**
     * Paints a different view of the options in HomeMenu on entered and sets the boolean setters below to true.<br>
     * @see HomeMenu#setStartEntered(boolean)
     * @see HomeMenu#setInfoEntered(boolean)
     * @see HomeMenu#setHighScoreEntered(boolean)
     * @see HomeMenu#setExitEntered(boolean)
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

            homeMenu.setStartEntered(true);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

            homeMenu.setInfoEntered(true);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);

            homeMenu.setHighScoreEntered(true);
            homeMenu.repaint(homeMenu.getHighScoreButton().x, homeMenu.getHighScoreButton().y, homeMenu.getHighScoreButton().width + 1, homeMenu.getHighScoreButton().height +1);

            homeMenu.setExitEntered(true);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);

    }

    /**
     * Paints the default view of the options in HomeMenu on exited and sets the boolean setters below to false.<br>
     * @see HomeMenu#setStartEntered(boolean)
     * @see HomeMenu#setInfoEntered(boolean)
     * @see HomeMenu#setHighScoreEntered(boolean)
     * @see HomeMenu#setExitEntered(boolean)
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

            homeMenu.setStartEntered(false);
            homeMenu.repaint(homeMenu.getStartButton().x, homeMenu.getStartButton().y, homeMenu.getStartButton().width + 1, homeMenu.getStartButton().height + 1);

            homeMenu.setInfoEntered(false);
            homeMenu.repaint(homeMenu.getInfoButton().x, homeMenu.getInfoButton().y, homeMenu.getInfoButton().width + 1, homeMenu.getInfoButton().height + 1);

            homeMenu.setHighScoreEntered(false);
        homeMenu.repaint(homeMenu.getHighScoreButton().x, homeMenu.getHighScoreButton().y, homeMenu.getHighScoreButton().width + 1, homeMenu.getHighScoreButton().height + 1);

            homeMenu.setExitEntered(false);
            homeMenu.repaint(homeMenu.getExitButton().x, homeMenu.getExitButton().y, homeMenu.getExitButton().width + 1, homeMenu.getExitButton().height + 1);

    }

    /**
     * Changes the cursor when mouse is moved into the outline area of the options in the HomeMenu.<br>
     * @see Cursor#getPredefinedCursor(int)
     * @param mouseEvent the MouseEvent detected
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (homeMenu.getStartButton().contains(p) || homeMenu.getExitButton().contains(p) ||
                homeMenu.getInfoButton().contains(p) || homeMenu.getHighScoreButton().contains(p))
            homeMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            homeMenu.setCursor(Cursor.getDefaultCursor());

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }
}