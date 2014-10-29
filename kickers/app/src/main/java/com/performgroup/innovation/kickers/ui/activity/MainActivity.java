package com.performgroup.innovation.kickers.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.event.GameRulesChoosenEvent;
import com.performgroup.innovation.kickers.event.GameRulesDialogRequestedEvent;
import com.performgroup.innovation.kickers.event.LineupsConfirmedEvent;
import com.performgroup.innovation.kickers.ui.dialog.GameRulesDialog;
import com.performgroup.innovation.kickers.ui.dialog.LineupsDialog;
import com.performgroup.innovation.kickers.ui.fragment.PickPlayerFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MainActivity extends ActionBarActivity {

    @Inject
    Bus eventBus;
    @Inject
    GameAPI gameAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        KickersApplication.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.t_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitle(R.string.pick_players);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_launcher);

        setSupportActionBar(toolbar);

        loadFragmentContainer(new PickPlayerFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.players, menu);

        return true;
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

        openLineupsDialog();
    }


    @Subscribe
    public void onLineupsConfirmedevent(LineupsConfirmedEvent event)
    {
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

    private void loadFragmentContainer(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.commitAllowingStateLoss();
    }

    private void openLineupsDialog() {
        LineupsDialog dialog = new LineupsDialog();
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialog.show(fragmentManager, "lineups_dialog");
    }

}
