package com.performgroup.innovation.kickers.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;

public class PlayerButton extends TextView implements View.OnClickListener {

    private Player player;
    private TeamColor teamColor;
    private OnClickListener listener;

    public interface OnClickListener {
        void onPlayerClick(Player player, TeamColor teamColor);
    }

    public PlayerButton(Context context) {
        super(context);
    }

    public PlayerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onPlayerClick(player, teamColor);
    }

    public void setPlayerClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.teamColor = player.color;
        setText(player.name);
    }

    public Player getPlayer() {
        return player;
    }

}
