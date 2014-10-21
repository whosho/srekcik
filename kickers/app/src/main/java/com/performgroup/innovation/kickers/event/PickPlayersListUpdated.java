package com.performgroup.innovation.kickers.event;


public class PickPlayersListUpdated {

    public int playerId;
    public boolean isChosen;

    public PickPlayersListUpdated(int playerId, boolean isChosen) {
        this.playerId = playerId;
        this.isChosen = isChosen;
    }

}
