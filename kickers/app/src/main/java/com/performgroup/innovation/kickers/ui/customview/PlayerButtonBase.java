package com.performgroup.innovation.kickers.ui.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;
import com.performgroup.innovation.kickers.ui.CustomFont;

public class PlayerButtonBase extends LinearLayout {

    protected Player player;
    protected TeamColor teamColor;

    protected LayoutInflater inflater;
    protected ImageView btnRevert;
    protected ImageView btnOwnGoal;
    protected TextView playerName;
    protected Context context;
    protected OnClickListener listener;

    public PlayerButtonBase(Context context) {
        super(context);
    }

    public PlayerButtonBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerButtonBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    protected void initButtons(View view) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), CustomFont.ORBITRON_BOLD_FONT);

        btnRevert = (ImageView) view.findViewById(R.id.btn_player_revert);
        btnRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRevertClicked();
            }
        });

        btnOwnGoal = (ImageView) view.findViewById(R.id.btn_player_own_goal);
        btnOwnGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOwnGoalClicked();
            }
        });

        playerName = (TextView) view.findViewById(R.id.tv_player_name);
        playerName.setTypeface(font);
        playerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNameClicked();
            }
        });
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.playerName.setText(player.name);
        this.teamColor = player.color;
    }

    public void onRevertClicked() {
        if (listener == null) return;
        listener.onRevertClicked(player);
    }

    public void onNameClicked() {
        if (listener == null) return;
        listener.onNameClicked(player);
    }

    public void onOwnGoalClicked() {
        if (listener == null) return;
        listener.onOwnGoalClicked(player);
    }

    public interface OnClickListener {
        void onRevertClicked(Player player);

        void onOwnGoalClicked(Player player);

        void onNameClicked(Player player);

    }

}
