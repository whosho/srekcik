package com.performgroup.innovation.kickers.event;

import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;

public class GoalEvent {
    public TeamColor teamColor;
    public Player player;
    public MatchScore score;

    public GoalEvent(TeamColor teamColor, MatchScore score) {
        this.teamColor = teamColor;
        this.score = score;
    }
}
