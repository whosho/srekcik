package com.performgroup.innovation.kickers.core;

import java.io.Serializable;

public class Player implements Serializable {

    public static final Player NULL = createNullPlayer();

    public int id;
    public String name = "";
    public int teamID;
    public PlayerRole role;

    public Player(int id, String name, PlayerRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public void switchRole() {
        if (role.equals(PlayerRole.ATTACER)) {
            role = PlayerRole.DEFFENDER;
        } else if (role.equals(PlayerRole.DEFFENDER)) {
            role = PlayerRole.ATTACER;
        }
    }

    public void assign(Team team, PlayerRole role) {
        this.teamID = team.ID;
        this.role = role;
    }

    private static Player createNullPlayer() {
        Player player = new Player(-1, "Somebody", PlayerRole.UNDEFINED);
        return player;
    }
}
