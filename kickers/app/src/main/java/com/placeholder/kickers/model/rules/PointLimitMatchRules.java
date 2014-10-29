package com.placeholder.kickers.model.rules;

import com.placeholder.kickers.core.MatchScore;

public class PointLimitMatchRules implements MatchRules {

    private int pointsLimit;

    public PointLimitMatchRules(int pointsLimit) {
        this.pointsLimit = pointsLimit;
    }

    @Override
    public boolean isFinish(MatchScore score) {
        return score.isValueReached(pointsLimit);
    }
}
