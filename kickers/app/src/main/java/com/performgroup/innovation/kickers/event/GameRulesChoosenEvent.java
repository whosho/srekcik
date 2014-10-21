package com.performgroup.innovation.kickers.event;

import com.performgroup.innovation.kickers.model.rules.GameRules;

public class GameRulesChoosenEvent {
    public GameRules selectedGameRules;

    public GameRulesChoosenEvent(GameRules gameRules) {
        this.selectedGameRules = gameRules;
    }
}
