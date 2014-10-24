package com.performgroup.innovation.kickers.application;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.performgroup.innovation.kickers.core.Lineups;
import com.performgroup.innovation.kickers.core.Match;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.PlayerRole;
import com.performgroup.innovation.kickers.core.TeamColor;
import com.performgroup.innovation.kickers.event.GameFinishedEvent;
import com.performgroup.innovation.kickers.event.GameRulesDialogRequestedEvent;
import com.performgroup.innovation.kickers.event.GameStartedEvent;
import com.performgroup.innovation.kickers.event.GoalEvent;
import com.performgroup.innovation.kickers.event.MatchFinishedEvent;
import com.performgroup.innovation.kickers.event.MatchStartedEvent;
import com.performgroup.innovation.kickers.event.PlayerCreatedEvent;
import com.performgroup.innovation.kickers.model.Game;
import com.performgroup.innovation.kickers.model.PlayersList;
import com.performgroup.innovation.kickers.model.rules.GameRules;
import com.performgroup.innovation.kickers.statistics.GameResults;
import com.squareup.otto.Bus;

import java.util.List;

public class GameAPI {
    public static final String TAG_PLAYERS_LIST = "players_list";
    private Match match;
    private Bus bus;
    private Game game;
    private GameResults results;
    private Gson gson;
    private Context context;
    private List<GameRules> gameRuleses;
    private GameRules selectedRules;
    private Lineups lineups;
    private PlayersList playersList;


    public GameAPI(Bus bus, Gson gson, Context context, List<GameRules> gameRuleses) {
        this.bus = bus;
        this.gson = gson;
        this.context = context;
        this.gameRuleses = gameRuleses;
        this.playersList = new PlayersList();
    }

    public void registerOwnGoal(Player player) {
        match.onOwnGoal(player.color);
        results.registerGoal(player, true);
        bus.post(new GoalEvent(player.color, match.score, true));
        validateMatchEnd();
    }

    public void registerGoal(Player player) {
        match.onGoal(player.color);
        results.registerGoal(player, false);
        bus.post(new GoalEvent(player.color, match.score, false));
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
            }
        }
    }

    public void startNewGame() {
        game = new Game(selectedRules, lineups);
        match = game.getMatch();
        results = new GameResults();
        bus.post(new GameStartedEvent());
    }

    public void startNextMatch() {
        game.nextMatch();
        match = game.getMatch();
        startMatch();
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

    public void loadPlayersList() {
        SharedPreferences preferences = context.getSharedPreferences("kickers", Context.MODE_PRIVATE);
        String plyersListFile = preferences.getString(TAG_PLAYERS_LIST, "");
        if (plyersListFile.isEmpty()) {
            playersList = new PlayersList();
        } else {
            playersList = gson.fromJson(plyersListFile, PlayersList.class);
        }
    }

    private void savePlayersList(PlayersList playersList) {
        SharedPreferences preferences = context.getSharedPreferences("kickers", Context.MODE_PRIVATE);
        preferences.edit().putString(TAG_PLAYERS_LIST, gson.toJson(playersList)).commit();
    }

    public void createPlayer(String playerName) {
        Player player = new Player(playersList.players.size(), playerName, TeamColor.UNDEFINED, PlayerRole.UNDEFINED);
        playersList.players.add(player);
        savePlayersList(playersList);
        bus.post(new PlayerCreatedEvent());
    }

    public List<Player> getAvailablePlayers() {
        if (playersList.players.isEmpty()) {
            loadPlayersList();
        }
        return playersList.players;
    }

    public boolean isFinished() {
        return game.isFinished();
    }

    public void startMatch() {
        if (!game.isStarted()) {
            match = game.start();
        }
        bus.post(new MatchStartedEvent(match));
    }
}

