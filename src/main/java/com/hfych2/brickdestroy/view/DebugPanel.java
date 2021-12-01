/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
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


import com.hfych2.brickdestroy.controller.GameController;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DebugPanel extends JPanel {


    private GameController gameController;

    private int clicks = 2;
    private JButton skipLevelsButton;


    private static final Color DEF_BKG = Color.WHITE;

    private JButton resetBallsButton;
    private JButton changeBallSpeedButton;

    private JSlider changeBallSpeedSlider;


    private DebugPanel(GameController gameController){

        this.gameController = gameController;

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

    public static DebugPanel createDebugPanel(GameController gameController) {
        return new DebugPanel(gameController);
    }


    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(9,9));
    }
    private JButton changeBallSpeedButton(){
        JButton changeBallSpeedButton = new JButton("Reset Ball Speed");
        changeBallSpeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double overallSpeed;

                overallSpeed = changeBallSpeedSlider.getValue();

                changeBallSpeedButton.setText("Set ball speed to: "+ String.valueOf(overallSpeed));

                gameController.getGameBoard().setBallXSpeed(changeBallSpeedSlider.getValue());
                gameController.getGameBoard().setBallYSpeed(changeBallSpeedSlider.getValue());

            }
        });
        return changeBallSpeedButton;
    }

    private JSlider changeBallSpeedSlider(int min, int max){
        JSlider changeBallSpeedSlider = new JSlider(min, max);
        changeBallSpeedSlider.setMajorTickSpacing(1);
        changeBallSpeedSlider.setSnapToTicks(true);
        changeBallSpeedSlider.setPaintTicks(true);
        changeBallSpeedSlider.setPaintLabels(true);
/*        changeBallSpeedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameController.getGameBoard().setBallXSpeed(changeBallSpeedSlider.getValue());
                gameController.getGameBoard().setBallYSpeed(changeBallSpeedSlider.getValue());
            }
        });*/

        return changeBallSpeedSlider;
    }

   private JButton skipLevelsButton(){
        JButton skipLevelsButton = new JButton("Skip to: ");
        skipLevelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skipLevelsButton.setText("Skip to: " + "level  " + clicks);
                clicks++;
                gameController.getGameBoard().getWall().nextLevel();
                gameController.getGameBoard().ballReset();
                gameController.resetScore();

                if(!gameController.getGameBoard().getWall().hasLevel()){
                    skipLevelsButton.setText("Last Level");
                    skipLevelsButton.setEnabled(false);
                }
            }
        });
        return skipLevelsButton;
    }
    private JButton resetBallsButton(){
        JButton resetBallsButton = new JButton("Reset Balls");
        resetBallsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.getGameBoard().ballReset();
                gameController.getGameBoard().resetGameBoard();
            }
        });
        return resetBallsButton;
    }


    public void setValues(int x,int y){
        changeBallSpeedSlider.setValue(x);
        changeBallSpeedSlider.setValue(y);
    }

}
