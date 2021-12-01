package com.hfych2.brickdestroy.model;


import java.util.ArrayList;


public class PlayerInfo {

    private ArrayList <String> userName;
    private ArrayList <Integer> score;

    public ArrayList<String> getUserName() {
        return userName;
    }


    public ArrayList<Integer> getScore() {
        return score;
    }


    public PlayerInfo(ArrayList <String> userName, ArrayList <Integer> score){
        this.userName = userName;
        this.score = score;
    }


}
