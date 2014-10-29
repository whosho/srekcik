package com.placeholder.kickers.model.rules;

import com.placeholder.kickers.core.MatchScore;

public interface MatchRules {
    boolean isFinish(MatchScore score);
}
