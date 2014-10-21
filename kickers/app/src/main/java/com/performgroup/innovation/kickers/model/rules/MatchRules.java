package com.performgroup.innovation.kickers.model.rules;

import com.performgroup.innovation.kickers.core.MatchScore;

public interface MatchRules {
    boolean isFinish(MatchScore score);
}
