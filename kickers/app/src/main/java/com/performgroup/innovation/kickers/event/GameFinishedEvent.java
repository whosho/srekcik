package com.performgroup.innovation.kickers.event;

import com.performgroup.innovation.kickers.model.Game;

public class GameFinishedEvent {
    public Game game;

    public GameFinishedEvent(Game game) {
        this.game = game;
    }
}
