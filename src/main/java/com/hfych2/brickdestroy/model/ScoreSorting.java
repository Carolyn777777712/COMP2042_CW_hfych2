package com.hfych2.brickdestroy.model;

import java.util.Comparator;

/**
 * This class is used for score sorting functionality on sorting the highScoresList.txt
 */
public class ScoreSorting {

    private String name;
    private int thisScore;
    private int thisLevel;

    /**
     * Class constructor.
     * @param name      the name.
     * @param thisScore the score
     * @param thisLevel the level
     */
    public ScoreSorting (String name, int thisScore, int thisLevel) {
        this.name = name;
        this.thisScore = thisScore;
        this.thisLevel = thisLevel;
    }

    /**
     * Custom comparator to compare and sort the scores of the users in ascending order.
     */
    public static Comparator<ScoreSorting> sortScore = (p1, p2) -> {

        int score1 = p1.getThisScore();
        int score2 = p2.getThisScore();

        return score1-score2;//ascending


    };

    /**
     * String representation of the ArrayList elements.
     * @return String representation of the ArrayList elements.
     */
    @Override
    public String toString() {
        return "[Name: " + name + "\tScore: " + thisScore + "\tLevel: " + thisLevel + "]\n";
    }

    /**
     * Gets the score.
     * @return the score.
     */
    public int getThisScore() {
        return thisScore;
    }

}
