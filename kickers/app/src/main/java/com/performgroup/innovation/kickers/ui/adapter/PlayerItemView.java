package com.performgroup.innovation.kickers.ui.adapter;

import com.performgroup.innovation.kickers.core.Player;

public class PlayerItemView {

    public Player player;
    public boolean isChosen;

    public PlayerItemView(Player player) {
        this(player, false);
    }

    public PlayerItemView(Player player, boolean isChosen) {
        this.player = player;
        this.isChosen = isChosen;
    }

}
