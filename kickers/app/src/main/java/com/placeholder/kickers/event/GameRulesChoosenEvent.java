package com.placeholder.kickers.event;

import com.placeholder.kickers.model.rules.GameRules;

public class GameRulesChoosenEvent {
    public GameRules selectedGameRules;

    public GameRulesChoosenEvent(GameRules gameRules) {
        this.selectedGameRules = gameRules;
    }
}
