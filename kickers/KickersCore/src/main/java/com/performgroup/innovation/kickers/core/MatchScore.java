package com.performgroup.innovation.kickers.core;

public class MatchScore {
    public int redsPoints;
    public int bluesPoints;



    public TeamColor getWinner() {
        if (redsPoints > bluesPoints) {
            return TeamColor.RED;
        } else {
            if (redsPoints < bluesPoints) {
                return TeamColor.BLUE;
            } else {
                return TeamColor.UNDEFINED;
            }
        }
    }
}
