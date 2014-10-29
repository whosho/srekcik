package com.performgroup.innovation.kickers.ui.customview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.PlayerRole;
import com.performgroup.innovation.kickers.core.TeamColor;

public class LineupsItem extends CardView {

    private Player player;
    private TeamColor teamColor;
    private TextView playerName;
    private TextView playerRole;

    public LineupsItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.lineups_players_item, this);
        initView();
    }

    public LineupsItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lineups_players_item, this);
        initView();
    }

    public LineupsItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.lineups_players_item, this);

        initView();
    }

    private void initView() {
        playerName = (TextView) findViewById(R.id.tv_player_name);
        playerRole = (TextView) findViewById(R.id.tv_player_role);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.playerName.setText(player.name);
        this.playerRole.setText(player.role.equals(PlayerRole.ATTACER) ? R.string.attack : R.string.defense);
    }

    public Player getPlayer() {
        return player;
    }

}
