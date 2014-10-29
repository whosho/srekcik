package com.placeholder.kickers.event;

import com.placeholder.kickers.core.MatchScore;
import com.placeholder.kickers.core.Player;

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
