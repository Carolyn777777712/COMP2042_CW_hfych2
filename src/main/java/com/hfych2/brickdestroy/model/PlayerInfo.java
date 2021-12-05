package com.hfych2.brickdestroy.model;


import java.util.ArrayList;


public class PlayerInfo {

    private ArrayList <String> userName;
    private ArrayList <Integer> score;
    private ArrayList <Integer> currentLevel;


    public PlayerInfo(ArrayList <String> userName, ArrayList <Integer> score, ArrayList<Integer> currentLevel){
        this.userName = userName;
        this.score = score;
        this.currentLevel = currentLevel;
    }

    public String nameToString(){
        return "Name: "+userName.get(userName.size()-1)+"\n";
    }

    public String scoreToString(){
        return "Score: "+score.get(score.size()-1)+"\n";
    }

    public String currentLevelToString(){
        return "Level: "+currentLevel.get(currentLevel.size()-1)+"\n";
    }

    public ArrayList<String> getUserName() {
        return userName;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public ArrayList<Integer> getCurrentLevel(){
        return currentLevel;
    }


}
