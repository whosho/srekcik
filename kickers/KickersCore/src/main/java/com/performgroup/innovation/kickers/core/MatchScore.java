package com.performgroup.innovation.kickers.core;

import java.util.HashMap;
import java.util.Map;

public class MatchScore {

    private Map<Integer, Integer> points;

    public MatchScore() {
        points = new HashMap<Integer, Integer>();
    }

    public static MatchScore createFor(Match match) {
        MatchScore score = new MatchScore();
        return score;
    }

    public void addPointForTeam(int id) {
        if (!points.containsKey(id)) {
            points.put(id, 0);
        }

        int value = points.get(id);
        points.put(id, ++value);
    }

    public int getWinner() {
        int maxValue = 0;
        int winnerTeamID = Team.NULL.ID;

        for (int teamID : points.keySet()) {
            int teamPoints = points.get(teamID);
            if (teamPoints > maxValue) {
                maxValue = teamPoints;
                winnerTeamID = teamID;
            } else if (teamPoints == maxValue) {
                winnerTeamID = Team.NULL.ID;
            }
        }

        return winnerTeamID;
    }

    public int getPoints(int teamID) {
        if (points.containsKey(teamID)) {
            return points.get(teamID);
        } else {
            return 0;
        }
    }

    public boolean isValueReached(int pointsLimit) {
        for (Integer value : points.values()) {
            if (value >= pointsLimit) return true;
        }
        return false;
    }
}
