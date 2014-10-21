package com.performgroup.innovation.kickers.model.rules;

import com.performgroup.innovation.kickers.core.MatchScore;

public class PointLimitMatchRules implements MatchRules {

    private int pointsLimit;

    public PointLimitMatchRules(int pointsLimit) {
        this.pointsLimit = pointsLimit;
    }

    @Override
    public boolean isFinish(MatchScore score) {
        return score.bluesPoints >= pointsLimit
                || score.redsPoints >= pointsLimit;
    }
}
