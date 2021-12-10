package com.hfych2.brickdestroy.model;


import java.util.ArrayList;

/**
 * This class is a newly created class that is not from the original code.<br>
 *
 * This class is to temporarily store user information in Arraylists
 *      before writing to highScoresList.txt
 */
public class User {

    private ArrayList <String> userName;
    private ArrayList <Integer> score;
    private ArrayList <Integer> currentLevel;

    /**
     * Class constructor.
     * @param userName the name of the user.
     * @param score the score of the user.
     * @param currentLevel the current level completed.
     */
    public User(ArrayList <String> userName, ArrayList <Integer> score, ArrayList<Integer> currentLevel){
        this.userName = userName;
        this.score = score;
        this.currentLevel = currentLevel;
    }

    /**
     * Converts the newest element added to {@link User#userName} to String.
     * @return the label and the value of the newest element of the ArrayList.
     */
    public String nameToString(){
        return "Name: "+userName.get(userName.size()-1)+"\n";
    }

    /**
     * Converts the newest element added to {@link User#score} to String.
     * @return the label and the value of the newest element of the ArrayList.
     */
    public String scoreToString(){
        return "Score: "+score.get(score.size()-1)+"\n";
    }

    /**
     * Converts the newest element added to {@link User#currentLevel} to String.
     * @return the label and the value of the newest element of the ArrayList.
     */
    public String currentLevelToString(){
        return "Level: "+currentLevel.get(currentLevel.size()-1)+"\n";
    }

    /**
     * Gets the userName.
     * @return the userName.
     */
    public ArrayList<String> getUserName() {
        return userName;
    }

    /**
     * Gets the score.
     * @return the score.
     */
    public ArrayList<Integer> getScore() {
        return score;
    }

    /**
     * Gets the currentLevel.
     * @return the currentLevel
     */
    public ArrayList<Integer> getCurrentLevel(){
        return currentLevel;
    }


}
