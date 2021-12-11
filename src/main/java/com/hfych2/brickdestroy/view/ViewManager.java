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


import com.hfych2.brickdestroy.controller.GameController;
import com.hfych2.brickdestroy.controller.HomeMenuController;
import com.hfych2.brickdestroy.controller.InfoController;
import com.hfych2.brickdestroy.model.GameBoard;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * This class has been modified from the original code
 *      along with having additions to become a view class.<br>
 *
 * This class was the GameFrame class of the original code and is
 *      renamed to provide easier understanding of the current code.<br>
 *
 * This class contains methods to enable different views.
 */
public class ViewManager extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private GameView gameView;
    private GameController gameController;


    private HomeMenu homeMenu;
    private HomeMenuController homeMenuController;

    private InfoView infoView;
    private InfoController infoController;

    private boolean gaming;

    private JScrollPane jScrollPane;


    /**
     * Class constructor.<br>
     *
     * Calls the super class default constructor.<br>
     *
     * Initialises {@link ViewManager#gaming} to false.<br>
     *
     * Initialises {@link ViewManager#gameBoard}, {@link ViewManager#gameView},
     *      {@link ViewManager#gameController}.<br>
     *
     * Initialises {@link ViewManager#homeMenu}, {@link ViewManager#homeMenuController}
     *      and adds {@link ViewManager#homeMenu} to this JFrame
     *      while also adding the listeners
     *          needed for {@link ViewManager#homeMenuController}.<br>
     *
     * Initialises {@link ViewManager#infoView}, {@link ViewManager#infoController},
     *      {@link ViewManager#jScrollPane}
     *      while adding the {@link ViewManager#infoView} to {@link ViewManager#jScrollPane}.
     */
    public ViewManager(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = GameBoard.createGameBoard(this);
        gameView = new GameView();
        gameController = new GameController(gameBoard, gameView);

        homeMenu = new HomeMenu(this,new Dimension(600,450));
        homeMenuController = new HomeMenuController(homeMenu);

        infoView = new InfoView(this);
        infoController = new InfoController(infoView);

        this.add(homeMenu,BorderLayout.CENTER);
        this.addMouseListener(homeMenuController);
        this.addMouseMotionListener(homeMenuController);

        jScrollPane = new JScrollPane(infoView);

    }

    /**
     * To locate the window to the center of the screen.
     * @see Toolkit#getScreenSize()
     * @see JFrame#setLocation(Point)
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    /**
     * Initialises the JFrame view.
     */
    public void initialise(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);//new
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * Enables the homeMenu to be displayed.
     * Calls {@link ViewManager#initialise()}
     */
    public void enableHomeMenu(){
        this.remove(gameView);
        this.remove(infoView);
        this.add(homeMenu,BorderLayout.CENTER);
        homeMenu.repaint();
        this.addMouseListener(homeMenuController);
        this.addMouseMotionListener(homeMenuController);
        initialise();
    }

    /**
     * Enables the gameBoard to be displayed.
     * Calls {@link ViewManager#initialise()}
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.remove(jScrollPane);
        this.add(gameView,BorderLayout.CENTER);
        this.gameView.setPreferredSize(new Dimension(gameBoard.getDefWidth(),gameBoard.getDefHeight()));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(gameController);
        this.addMouseListener(gameController);
        this.addMouseMotionListener(gameController);
        this.setUndecorated(false);
        initialise();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    /**
     * Enables the infoView to be displayed.
     * Calls {@link ViewManager#initialise()}.
     * @see JScrollPane#setVerticalScrollBarPolicy(int)
     * @see JScrollPane#setHorizontalScrollBarPolicy(int)
     */
    public void enableInfoView(){
        this.dispose();
        this.remove(homeMenu);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.setFocusable(true);
        this.addKeyListener(infoController);
        this.setUndecorated(false);
        initialise();

    }

    /**
     * Assigns {@link ViewManager#gaming} to true if focus is gained.
     * @param windowEvent the WindowEvent detected.
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * Calls {@link GameController#onLostFocus()} if focus is lost and
     *      {@link ViewManager#gaming} is true.
     * @param windowEvent the WindowEvent detected.
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameController.onLostFocus();

    }

}
