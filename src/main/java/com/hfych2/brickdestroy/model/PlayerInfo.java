package com.hfych2.brickdestroy.model;


import java.util.ArrayList;


public class PlayerInfo {

    private ArrayList <String> userName;
    private ArrayList <Integer> score;


    public PlayerInfo(ArrayList <String> userName, ArrayList <Integer> score){
        this.userName = userName;
        this.score = score;
    }

/*    @Override
    public String toString(){
        return "Name:"+userName+", \tScore "+score+"\n";
    }*/

    public String nameToString(){
        return "Name: "+userName;
    }

    public String scoreToString(){
        return "\tScore: "+score+"\n";
    }

    public ArrayList<String> getUserName() {
        return userName;
    }


    public ArrayList<Integer> getScore() {
        return score;
    }


}
