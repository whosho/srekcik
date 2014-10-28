package com.performgroup.innovation.kickers.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            teams.get(i).switchRoles();
        }
    }

    public static Lineups from(List<Player> playerList) {
        Collections.shuffle(playerList);

        Lineups lineups = new Lineups();
        Team first =  lineups.teams.get(0);
        Team second =  lineups.teams.get(1);

        playerList.get(0).assign(first, PlayerRole.DEFFENDER);
        playerList.get(1).assign(first, PlayerRole.ATTACER);
        playerList.get(2).assign(second, PlayerRole.DEFFENDER);
        playerList.get(3).assign(second, PlayerRole.ATTACER);

        first.add(playerList.get(0));
        first.add(playerList.get(1));
        second.add(playerList.get(2));
        second.add(playerList.get(3));

        return lineups;
    }

    public Team getTeam(TeamColor color) {
        for (Team team : teams) {
            if (team.color.equals(color)) return team;
        }
        return Team.NULL;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public int getOponentTeamOf(int teamID) {
        for (Team team: teams)
        {
            if(team.ID!=teamID) return team.ID;
        }

        return Team.NULL.ID;
    }
}
