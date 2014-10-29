package com.placeholder.kickers.core;

import java.util.ArrayList;
import java.util.List;

public class Team {
    protected static final int MAX_PLAYERS_COUNT = 2;
    public static final Team NULL = createNull();
    public static final String EMPTY_STRING = "";

    public int ID;
    public TeamColor color;
    public List<Player> players;
    public List<Integer> scores;
    public int wins;

    public Team(TeamColor color, int ID) {
        this.color = color;
        this.ID = ID;
        players = new ArrayList<Player>();
        scores = new ArrayList<Integer>();
    }

    public void switchColor() {
        color = TeamColor.getOposite(color);
    }

    public void switchRoles() {
        for (Player player : players) {
            player.switchRole();
        }
    }

    public void add(Player player) {
        if (players.size() > MAX_PLAYERS_COUNT) {
            throw new IllegalStateException("Team may contain only " + MAX_PLAYERS_COUNT + " players.");
        }
        players.add(player);
    }

    public Player getPlayer(PlayerRole role) {
        for (Player player : players) {
            if (player.role.equals(role)) return player;
        }
        return Player.NULL;
    }

    private static Team createNull() {
        return new Team(TeamColor.UNDEFINED, -1);
    }

    public String getName() {
        if (players.size() == 2) {
            return players.get(0).name + " & " + players.get(1).name;
        } else {
            return EMPTY_STRING;
        }
    }
}
