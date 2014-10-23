package com.performgroup.innovation.kickers.core;

import java.io.Serializable;

public class Player implements Serializable {

    public static final Player NULL = createNullPlayer();

    public int id;
    public String name = "";
    public TeamColor color;
    public PlayerRole role;

    public Player(int id, String name, TeamColor color, PlayerRole role) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.role = role;
    }

    private static Player createNullPlayer() {
        Player player = new Player(-1, "Somebody", TeamColor.UNDEFINED, PlayerRole.UNDEFINED);
        return player;
    }

    public void switchRole() {
        if (role.equals(PlayerRole.ATTACER)) {
            role = PlayerRole.DEFFENDER;
        } else if (role.equals(PlayerRole.DEFFENDER)) {
            role = PlayerRole.ATTACER;
        }
    }

    public void switchColor() {
        color = TeamColor.getOposite(color);
    }

    public void assign(TeamColor color, PlayerRole role) {
        this.color = color;
        this.role = role;
    }
}
