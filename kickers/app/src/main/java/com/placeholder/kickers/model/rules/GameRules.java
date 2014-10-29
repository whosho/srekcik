package com.placeholder.kickers.model.rules;

import com.placeholder.kickers.model.Game;

public class GameRules {
    private PointLimitMatchRules pointLimitMatchRules;
    public String name;
    private int maxMatchesCount = 5;
    public int maxGoals;
    public boolean shufflePositions;
    public boolean shuffleTeamColors;

    public GameRules(String name, int maxMatches, int maxGoals, boolean shufflePositions, boolean shuffleTeamsColors) {
        this.name = name;
        this.maxMatchesCount = maxMatches;
        this.maxGoals = maxGoals;
        this.shufflePositions = shufflePositions;
        this.shuffleTeamColors = shuffleTeamsColors;
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
