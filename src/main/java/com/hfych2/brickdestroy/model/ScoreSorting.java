package com.hfych2.brickdestroy.model;

import java.util.Comparator;

public class ScoreSorting {

    private String name;
    private int thisScore;
    private int thisLevel;

    public ScoreSorting (String name, int thisScore, int thisLevel) {
        this.name = name;
        this.thisScore = thisScore;
        this.thisLevel = thisLevel;
    }

    public static Comparator<ScoreSorting> sortScore = new Comparator<ScoreSorting>() {

        public int compare(ScoreSorting p1, ScoreSorting p2) {

            int score1 = p1.getThisScore();
            int score2 = p2.getThisScore();

            return score1-score2;//ascending


        }};
    @Override
    public String toString() {
        return "[Name: " + name + "\tScore: " + thisScore + "\tLevel: " + thisLevel + "]\n";
    }

    public int getThisScore() {
        return thisScore;
    }

}
