package com.performgroup.innovation.kickers.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.event.GameRulesChoosenEvent;
import com.performgroup.innovation.kickers.event.GameRulesDialogRequestedEvent;
import com.performgroup.innovation.kickers.ui.fragment.GameRulesDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class PlayersActivity extends ActionBarActivity {

    @Inject
    Bus eventBus;
    @Inject
    GameAPI gameAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        KickersApplication.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        eventBus.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onGameRulesChoosenEvent(GameRulesChoosenEvent event) {
        gameAPI.setRules(event.selectedGameRules);
        gameAPI.startNewGame();
        openMatchActivity();
    }

    @Subscribe
    public void onGameRulesDialogRequestedEvent(GameRulesDialogRequestedEvent event) {
        openDialog(new GameRulesDialog(), "GameRulesDialog");
    }

    private void openDialog(DialogFragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, tag);
    }

    private void openMatchActivity() {
        Intent intent = new Intent(this, MatchActivity.class);
        startActivity(intent);
    }
}
