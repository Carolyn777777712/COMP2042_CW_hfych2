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


public class DebugPanel extends JPanel {


    private static final Color DEF_BKG = Color.WHITE;

    private JButton skipLevelsButton;

    private JButton resetBallsButton;
    private JButton changeBallSpeedButton;

    private JSlider changeBallSpeedSlider;

    private boolean givePenalty;


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

    public static DebugPanel createDebugPanel() {
        return new DebugPanel();
    }


    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(9,9));
    }

    private JButton changeBallSpeedButton(){
        JButton changeBallSpeedButton = new JButton("Reset Ball Speed");

        return changeBallSpeedButton;
    }

    private JSlider changeBallSpeedSlider(int min, int max){
        JSlider changeBallSpeedSlider = new JSlider(min, max);
        changeBallSpeedSlider.setMajorTickSpacing(1);
        changeBallSpeedSlider.setSnapToTicks(true);
        changeBallSpeedSlider.setPaintTicks(true);
        changeBallSpeedSlider.setPaintLabels(true);

        return changeBallSpeedSlider;
    }

   private JButton skipLevelsButton(){
        JButton skipLevelsButton = new JButton("Skip to: ");

        return skipLevelsButton;
    }

    private JButton resetBallsButton(){
        JButton resetBallsButton = new JButton("Reset Balls");

        return resetBallsButton;
    }


    public void setValues(int x,int y){
        changeBallSpeedSlider.setValue(x);
        changeBallSpeedSlider.setValue(y);
    }

    public boolean isGivePenalty() {
        return givePenalty;
    }

    public void setGivePenalty(boolean givePenalty) {
        this.givePenalty = givePenalty;
    }

    public JButton getSkipLevelsButton() {
        return skipLevelsButton;
    }

    public JButton getResetBallsButton() {
        return resetBallsButton;
    }

    public JButton getChangeBallSpeedButton() {
        return changeBallSpeedButton;
    }

    public JSlider getChangeBallSpeedSlider() {
        return changeBallSpeedSlider;
    }

}
