package com.performgroup.innovation.kickers.core;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public static final int MAX_PLAYERS_COUNT = 2;
    public static final Team NULL = createNull();

    public int ID;

    public TeamColor color;
    public List<Player> players;

    public Team(TeamColor color, int ID) {
        this.color = color;
        this.ID = ID;
        players = new ArrayList<Player>();
    }

    public void switchColor() {
        color = TeamColor.getOposite(color);
        for (Player player : players) {
            player.switchColor();
        }
    }

    public void switchRole() {
        for (Player player : players) {
            player.switchRole();
        }
    }

    public void add(Player player) {
        if (players.size() > MAX_PLAYERS_COUNT) return;
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
}
