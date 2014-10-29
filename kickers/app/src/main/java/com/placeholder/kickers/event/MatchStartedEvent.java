package com.placeholder.kickers.event;

import com.placeholder.kickers.core.Match;

/**
 * Created by Grzegorz.Barski on 2014-10-23.
 */
public class MatchStartedEvent {
    public Match newMatch;

    public MatchStartedEvent(Match newMatch) {
        this.newMatch = newMatch;
    }
}
