package com.performgroup.innovation.kickers.application;

import com.performgroup.innovation.kickers.core.Lineups;
import com.performgroup.innovation.kickers.core.Match;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;
import com.performgroup.innovation.kickers.event.GameFinishedEvent;
import com.performgroup.innovation.kickers.event.GameRulesDialogRequestedEvent;
import com.performgroup.innovation.kickers.event.GameStartedEvent;
import com.performgroup.innovation.kickers.event.GoalEvent;
import com.performgroup.innovation.kickers.event.MatchFinishedEvent;
import com.performgroup.innovation.kickers.model.Game;
import com.performgroup.innovation.kickers.model.rules.GameRules;
import com.performgroup.innovation.kickers.statistics.GameResults;
import com.squareup.otto.Bus;

import java.util.List;

public class GameAPI {
    private Match match;
    private Bus bus;
    private Game game;
    private GameResults results;
    private List<GameRules> gameRuleses;
    private GameRules selectedRules;
    private Lineups lineups;

    public GameAPI(Bus bus, List<GameRules> gameRuleses) {
        this.bus = bus;
        this.gameRuleses = gameRuleses;
    }

    public void registerOwnGoal(Player player) {
        match.onOwnGoal(player.color);
        results.registerGoal(player, true);
        bus.post(new GoalEvent(player.color, match.score));
        validateMatchEnd();
    }

    public void registerGoal(Player player) {
        match.onGoal(player.color);
        results.registerGoal(player, false);
        bus.post(new GoalEvent(player.color, match.score));
        validateMatchEnd();
    }

    private void validateMatchEnd() {
        boolean matchFinished = game.isMatchFinished(match.score);
        boolean gameFinished = game.isFinished();
        if (matchFinished) {
            results.registerMatchScore(match.score);
            bus.post(new MatchFinishedEvent(match.score));
            if (gameFinished) {
                bus.post(new GameFinishedEvent(game));
            } else {
                game.nextMatch();
                match = game.getMatch();
            }
        }
    }

    public void startNewGame() {
        game = new Game(selectedRules, lineups);
        match = getMatch();
        results = new GameResults();
        bus.post(new GameStartedEvent());
    }

    public Match getMatch() {
        return game.getMatch();
    }

    public GameResults getResults() {
        return results;
    }

    public String getShortStatisticsText() {
        String result = "WINNER: " + results.getWinner().name() + "\n\n";
        result += "BLUES : " + results.blueWins + "\n";
        result += "REDS : " + results.redsWins + "\n\n";

        result += game.getMatchStats();

        return result;
    }

    public int getMatchesCount() {
        return game.getMatchesCount();
    }

    public int getNumberOfMatches() {
        return game.getNumberOfMatches();
    }

    public int getMaxGoals() {
        return game.getMaxGoals();
    }

    public List<GameRules> getAvailableGameRulesList() {
        return gameRuleses;
    }

    public void setRules(GameRules gameRules) {
        this.selectedRules = gameRules;
    }

    public void setLineups(Lineups lineups) {
        this.lineups = lineups;
    }

    public void openGameRulesDialog() {
        bus.post(new GameRulesDialogRequestedEvent());
    }
}

