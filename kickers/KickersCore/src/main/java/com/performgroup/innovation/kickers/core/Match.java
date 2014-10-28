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

    public void onGoal(int teamID) {
        score.addPointForTeam(teamID);
    }
}
