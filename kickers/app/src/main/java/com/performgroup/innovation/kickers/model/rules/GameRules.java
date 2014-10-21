package com.performgroup.innovation.kickers.model.rules;

import com.performgroup.innovation.kickers.model.Game;

public interface GameRules {

    boolean isLastMatch(Game game);

    boolean shufflePositions();

    boolean shuffleTeamColors();

    MatchRules getMatchRules();

    int getNumberOfMatches();

    public int getMaxGoals();

    String getName();

}
