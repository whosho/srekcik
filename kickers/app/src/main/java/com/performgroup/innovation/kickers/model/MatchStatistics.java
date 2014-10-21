package com.performgroup.innovation.kickers.model;

import com.performgroup.innovation.kickers.core.TeamColor;

import java.io.Serializable;
import java.util.List;

public class MatchStatistics implements Serializable {

    public int numberOfMatches;

    public TeamColor teamColor;
    public String blueTeamPoints;
    public String redTeamPoints;

    public List<Integer> blueStats;
    public List<Integer> redStats;

}
