package com.performgroup.innovation.kickers.event;

import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;

public class GoalEvent {
    public Player player;
    public MatchScore score;
    public boolean isOwnGoal;

    public GoalEvent(Player player, MatchScore score, boolean isOwnGoal) {
        this.player = player;
        this.score = score;
        this.isOwnGoal = isOwnGoal;
    }
}
