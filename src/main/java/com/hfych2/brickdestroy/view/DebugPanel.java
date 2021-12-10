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


import javax.swing.*;
import java.awt.*;

/**
 * This class has been modified from the original code along with having additions
 *      to become a view class.<br>
 *
 * This class is the view class for the controller
 *      {@link com.hfych2.brickdestroy.controller.DebugConsole}
 *      and for the model {@link com.hfych2.brickdestroy.model.GameBoard}.<br>
 *
 *
 */
public class DebugPanel extends JPanel {


    private static final Color DEF_BKG = Color.WHITE;

    private JButton skipLevelsButton;

    private JButton resetBallsButton;
    private JButton changeBallSpeedButton;

    private JSlider changeBallSpeedSlider;

    private boolean givePenalty;

    /**
     * Class constructor.<br>
     * Implements the factory method.<br>
     * {@link DebugPanel#createDebugPanel()} will be replacing constructor calls.<br>
     *  Calls the {@link DebugPanel#initialize()} method and adds the components to view.
     */
    private DebugPanel(){

       initialize();

        skipLevelsButton = skipLevelsButton();
        resetBallsButton = resetBallsButton();

        changeBallSpeedSlider = changeBallSpeedSlider(1,10);
        changeBallSpeedButton = changeBallSpeedButton();

        this.add(changeBallSpeedSlider);
        this.add(changeBallSpeedButton);

        this.add(resetBallsButton);
        this.add(skipLevelsButton);

    }

    /**
     * Factory method to replace constructor calls.
     * @return new DebugPanel object.
     */
    public static DebugPanel createDebugPanel() {
        return new DebugPanel();
    }

    /**
     * Initialises the view.
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(9,9));
    }

    /**
     * Creates the button for changing ball speed.
     * @return the button for changing ball speed.
     */
    private JButton changeBallSpeedButton(){
        JButton changeBallSpeedButton = new JButton("Reset Ball Speed");

        return changeBallSpeedButton;
    }

    /**
     * Creates the slider for changing ball speed.
     * @param min the minimum value for the slider.
     * @param max the maximum value for the slider.
     * @return the slider for changing ball speed.
     */
    private JSlider changeBallSpeedSlider(int min, int max){
        JSlider changeBallSpeedSlider = new JSlider(min, max);
        changeBallSpeedSlider.setMajorTickSpacing(1);
        changeBallSpeedSlider.setSnapToTicks(true);
        changeBallSpeedSlider.setPaintTicks(true);
        changeBallSpeedSlider.setPaintLabels(true);

        return changeBallSpeedSlider;
    }

    /**
     * Creates the button for skipping levels.
     * @return the button for skipping levels.
     */
   private JButton skipLevelsButton(){
        JButton skipLevelsButton = new JButton("Skip to: ");

        return skipLevelsButton;
    }

    /**
     * Creates the button for reset balls.
     * @return the button for reset balls.
     */
    private JButton resetBallsButton(){
        JButton resetBallsButton = new JButton("Reset Balls");

        return resetBallsButton;
    }

    /**
     * Sets the value for the slider.
     * @param x x value of the slider.
     * @param y y value fo the slider.
     */
    public void setValues(int x,int y){
        changeBallSpeedSlider.setValue(x);
        changeBallSpeedSlider.setValue(y);
    }

    /**
     * Checks if penalty is given.
     * @return true if penalty is given and false otherwise.
     */
    public boolean isGivePenalty() {
        return givePenalty;
    }

    /**
     * Sets if penalty is given.
     * @param givePenalty the value to set to.
     */
    public void setGivePenalty(boolean givePenalty) {
        this.givePenalty = givePenalty;
    }

    /**
     * Gets the skipLevelsButton.
     * @return the skipLevelsButton.
     */
    public JButton getSkipLevelsButton() {
        return skipLevelsButton;
    }

    /**
     * Gets the resetBallsButton.
     * @return the resetBallsButton.
     */
    public JButton getResetBallsButton() {
        return resetBallsButton;
    }

    /**
     * Gets the changeBallSpeedButton.
     * @return the changeBallSpeedButton.
     */
    public JButton getChangeBallSpeedButton() {
        return changeBallSpeedButton;
    }

    /**
     * Gets the changeBallSpeedSlider.
     * @return the changeBallSpeedSlider.
     */
    public JSlider getChangeBallSpeedSlider() {
        return changeBallSpeedSlider;
    }

}
