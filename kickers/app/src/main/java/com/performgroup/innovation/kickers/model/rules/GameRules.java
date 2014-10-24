package com.performgroup.innovation.kickers.model.rules;

import com.performgroup.innovation.kickers.model.Game;

public class GameRules {
    private PointLimitMatchRules pointLimitMatchRules;
    public String name;
    private int maxMatchesCount = 5;
    public int maxGoals;
    public boolean shufflePositions;
    public boolean shuffleTeamColors;

    public GameRules(String name, int maxMatches, int maxGoals) {
        this.name = name;
        this.maxMatchesCount = maxMatches;
        this.maxGoals = maxGoals;
        pointLimitMatchRules = new PointLimitMatchRules(maxGoals);
    }

    public boolean isLastMatch(Game game) {
        return game.getMatchesCount() == maxMatchesCount;
    }

    public MatchRules getMatchRules() {
        return pointLimitMatchRules;
    }

    public int getNumberOfMatches() {
        return maxMatchesCount;
    }

}
