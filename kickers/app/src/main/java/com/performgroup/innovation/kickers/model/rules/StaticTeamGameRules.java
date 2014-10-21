package com.performgroup.innovation.kickers.model.rules;

import com.performgroup.innovation.kickers.model.Game;

public class StaticTeamGameRules implements GameRules {
    private PointLimitMatchRules pointLimitMatchRules;
    private int maxMatchesCount = 5;
    private int maxGoals;

    public StaticTeamGameRules(int maxMatches, int maxGoals) {
        this.maxMatchesCount = maxMatches;
        this.maxGoals = maxGoals;
        pointLimitMatchRules = new PointLimitMatchRules(maxGoals);
    }

    @Override
    public boolean isLastMatch(Game game) {
        return game.getMatchesCount() == maxMatchesCount;
    }

    @Override
    public boolean shufflePositions() {
        return true;
    }

    @Override
    public boolean shuffleTeamColors() {
        return true;
    }

    @Override
    public MatchRules getMatchRules() {
        return pointLimitMatchRules;
    }

    @Override
    public int getNumberOfMatches() {
        return maxMatchesCount;
    }

    @Override
    public int getMaxGoals() {
        return maxGoals;
    }

    @Override
    public String getName() {
        return "Mixed teams, " + maxMatchesCount + " matches to " + maxGoals + " goals";
    }
}
