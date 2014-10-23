package com.performgroup.innovation.kickers.model;

import com.performgroup.innovation.kickers.core.Lineups;
import com.performgroup.innovation.kickers.core.Match;
import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.model.rules.GameRules;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private boolean isStarted;
    GameRules rules;
    List<Match> matches;

    public Game(GameRules rules, Lineups initialiLineups) {
        this.rules = rules;
        matches = new ArrayList<Match>();
        matches.add(new Match(initialiLineups, matches.size()));
        isStarted = false;
    }

    public void nextMatch() {
        Match currentMatch = getMatch();
        Lineups lineups = currentMatch.lineups;

        if (rules.shuffleTeamColors()) {
            lineups.shuffleTeamColors();
        }

        if (rules.shufflePositions()) {
            lineups.shufflePositions(((int) (Math.random() * 2) % 2));
        }

        Match newMatch = new Match(lineups, matches.size());
        matches.add(newMatch);
    }

    public Match getMatch() {
        return matches.get(matches.size() - 1);
    }

    public boolean isMatchFinished(MatchScore score) {
        return rules.getMatchRules().isFinish(score);
    }

    public boolean isFinished() {
        return rules.isLastMatch(this);
    }

    public int getMatchesCount() {
        return matches.size();
    }

    public int getNumberOfMatches() {
        return rules.getNumberOfMatches();
    }

    public int getMaxGoals() {
        return rules.getMaxGoals();
    }

    public List<Integer> getBlueMatchStats() {
        List<Integer> blueStats = new ArrayList<Integer>();
        for (Match match : matches) {
            blueStats.add(match.score.bluesPoints);

        }
        return blueStats;
    }

    public List<Integer> getRedMatchStats() {
        List<Integer> redStats = new ArrayList<Integer>();
        for (Match match : matches) {
            redStats.add(match.score.redsPoints);

        }
        return redStats;
    }

    public String getMatchStats() {
        String stats = "";
        for (Match match : matches) {
            stats += "Match " + match.ID + ") " + match.score.redsPoints + " : " + match.score.bluesPoints + "\n";
        }

        return stats;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public Match start() {
        isStarted = true;
        return getMatch();
    }
}
