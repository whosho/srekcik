package com.performgroup.innovation.kickers.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Grzegorz.Barski on 2014-10-14.
 */
public class Lineups {
    public List<Team> teams;

    public Lineups() {
        teams = new ArrayList<Team>();
        teams.add(new Team(TeamColor.BLUE, 0));
        teams.add(new Team(TeamColor.RED, 1));
    }

    public void shuffleTeamColors() {
        teams.get(0).switchColor();
        teams.get(1).switchColor();
    }

    public void shufflePositions(int i) {
        if (i == 0 || i == 1) {
            teams.get(i).switchRole();
        }
    }

    public static Lineups from(List<Player> playerList) {
        Collections.shuffle(playerList);

        playerList.get(0).assign(TeamColor.BLUE, PlayerRole.DEFFENDER);
        playerList.get(1).assign(TeamColor.BLUE, PlayerRole.ATTACER);
        playerList.get(2).assign(TeamColor.RED, PlayerRole.DEFFENDER);
        playerList.get(3).assign(TeamColor.RED, PlayerRole.ATTACER);

        Lineups lineups = new Lineups();
        lineups.teams.get(0).add(playerList.get(0));
        lineups.teams.get(0).add(playerList.get(1));
        lineups.teams.get(1).add(playerList.get(2));
        lineups.teams.get(1).add(playerList.get(3));

        return lineups;
    }

    public Team getTeam(TeamColor color) {
        for (Team team : teams) {
            if (team.color.equals(color)) return team;
        }
        return Team.NULL;
    }
}
