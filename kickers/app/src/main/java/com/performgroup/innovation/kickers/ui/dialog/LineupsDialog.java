package com.performgroup.innovation.kickers.ui.dialog;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.Lineups;
import com.performgroup.innovation.kickers.core.PlayerRole;
import com.performgroup.innovation.kickers.event.LineupsConfirmedEvent;
import com.performgroup.innovation.kickers.service.SoundService;
import com.performgroup.innovation.kickers.ui.customview.LineupsItem;
import com.squareup.otto.Bus;

import javax.inject.Inject;

public class LineupsDialog extends DialogFragment {

    private View view;
    private Context context;

    @Inject
    GameAPI gameAPI;

    @Inject
    Bus eventBus;

    @Inject
    SoundService player;


    private LineupsItem teamOneAttack;
    private LineupsItem teamOneDefense;
    private LineupsItem teamTwoAttack;
    private LineupsItem teamTwoDefense;

    AsyncTask<Void, Integer, Integer> task;

    public LineupsDialog() {
        // should be empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_lineups, container);
        context = getActivity();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        KickersApplication.inject(this);
        initView();

        task = new AsyncTask<Void, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                for (int player = 0; player < 4; player++) {
                    delay();
                    publishProgress(player);
                }

                return 0;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                player.play(R.raw.ding);

                Integer playerNumber = values[0];
                if (playerNumber == 0) {
                    teamOneAttack.setVisibility(View.VISIBLE);
                } else if (playerNumber == 1) {
                    teamOneDefense.setVisibility(View.VISIBLE);
                } else if (playerNumber == 2) {
                    teamTwoAttack.setVisibility(View.VISIBLE);
                } else if (playerNumber == 3) {
                    teamTwoDefense.setVisibility(View.VISIBLE);
                }
            }

        };

        return view;
    }

    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        ((TextView) view.findViewById(R.id.dialog_header)).setText(R.string.lineups);
        teamOneAttack = (LineupsItem) view.findViewById(R.id.team_one_attack);
        teamOneDefense = (LineupsItem) view.findViewById(R.id.team_one_defense);
        teamTwoAttack = (LineupsItem) view.findViewById(R.id.team_two_attack);
        teamTwoDefense = (LineupsItem) view.findViewById(R.id.team_two_defense);
        view.findViewById(R.id.dialog_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // some stuff here
                eventBus.post(new LineupsConfirmedEvent());
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Lineups lineups = gameAPI.getLineups();

        player.registerSound(R.raw.ding);

        teamOneAttack.setVisibility(View.GONE);
        teamOneDefense.setVisibility(View.GONE);
        teamTwoAttack.setVisibility(View.GONE);
        teamTwoDefense.setVisibility(View.GONE);

        teamOneAttack.setPlayer(lineups.teams.get(0).getPlayer(PlayerRole.ATTACER));
        teamOneDefense.setPlayer(lineups.teams.get(0).getPlayer(PlayerRole.DEFFENDER));
        teamTwoAttack.setPlayer(lineups.teams.get(1).getPlayer(PlayerRole.ATTACER));
        teamTwoDefense.setPlayer(lineups.teams.get(1).getPlayer(PlayerRole.DEFFENDER));

        task.execute();

    }

    @Override
    public void onPause() {
        task.cancel(true);
        player.cleanSoundsBuffer();
        super.onPause();
    }
}
