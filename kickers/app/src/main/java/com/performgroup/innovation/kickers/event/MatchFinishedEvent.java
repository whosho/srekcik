package com.performgroup.innovation.kickers.event;

import com.performgroup.innovation.kickers.core.MatchScore;

public class MatchFinishedEvent {
    public MatchScore score;

    public MatchFinishedEvent(MatchScore score) {
        this.score = score;
    }
}
