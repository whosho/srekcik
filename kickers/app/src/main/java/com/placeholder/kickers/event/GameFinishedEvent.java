package com.placeholder.kickers.event;

import com.placeholder.kickers.model.Game;

public class GameFinishedEvent {
    public Game game;

    public GameFinishedEvent(Game game) {
        this.game = game;
    }
}
