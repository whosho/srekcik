package com.performgroup.innovation.kickers.statistics;

import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameResults {

    public int redsWins;
    public int blueWins;
    public List<MatchScore> matchesScore;
    public Map<Integer, PlayerResults> playerStatistics;

    public GameResults() {
        matchesScore = new ArrayList<MatchScore>();
        playerStatistics = new HashMap<Integer, PlayerResults>();
        matchesScore = new ArrayList<MatchScore>();
    }

    public void registerGoal(Player player, boolean isOwnGoal) {
        if (!playerStatistics.containsKey(player.id)) {
            playerStatistics.put(player.id, PlayerResults.createFor(player));
        }

        PlayerResults results = playerStatistics.get(player.id);
        results.addGoal(player.role, isOwnGoal);
    }

    public void registerMatchScore(MatchScore matchScore) {
        this.matchesScore.add(matchScore);
        if (matchScore.getWinner().equals(TeamColor.BLUE)) blueWins++;
        if (matchScore.getWinner().equals(TeamColor.RED)) redsWins++;
    }

    public TeamColor getWinner() {
        if (redsWins > blueWins) {
            return TeamColor.RED;
        } else {
            if (redsWins < blueWins) {
                return TeamColor.BLUE;
            } else {
                return TeamColor.UNDEFINED;
            }
        }
    }

    public List<PlayerResults> getSortedPlayerGoalBalance() {
        List<PlayerResults> playerResults = new ArrayList<PlayerResults>();
        for (Map.Entry<Integer, PlayerResults> entry : playerStatistics.entrySet()) {
            playerResults.add(entry.getValue());
        }

        Collections.sort(playerResults, new Comparator<PlayerResults>() {
            public int compare(PlayerResults playerResults1, PlayerResults playerResults2) {
                Integer goalBalance1 = playerResults1.goalBalance;
                Integer goalBalance2 = playerResults2.goalBalance;

                return goalBalance2.compareTo(goalBalance1);
            }
        });

        return playerResults;
    }

}
