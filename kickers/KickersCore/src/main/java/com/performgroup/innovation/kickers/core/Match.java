package com.performgroup.innovation.kickers.core;

public class Match {
    public int ID;
    public Lineups lineups;
    public MatchScore score;

    public Match(Lineups lineups, int ID) {
        this.lineups = lineups;
        this.ID = ID;
        this.score = new MatchScore();
    }

    public void onGoal(TeamColor color) {
        if (color.equals(TeamColor.BLUE)) {
            score.bluesPoints++;
        }
        if (color.equals(TeamColor.RED)) {
            score.redsPoints++;
        }
    }

    public void onOwnGoal(TeamColor color) {
        if (color.equals(TeamColor.RED)) {
            score.bluesPoints++;
        }
        if (color.equals(TeamColor.BLUE)) {
            score.redsPoints++;
        }
    }
}
