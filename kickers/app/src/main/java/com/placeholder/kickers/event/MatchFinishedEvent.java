package com.placeholder.kickers.event;

import com.placeholder.kickers.core.MatchScore;

public class MatchFinishedEvent {
    public MatchScore score;

    public MatchFinishedEvent(MatchScore score) {
        this.score = score;
    }
}
