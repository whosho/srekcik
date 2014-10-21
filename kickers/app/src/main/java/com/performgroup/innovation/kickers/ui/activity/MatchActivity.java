package com.performgroup.innovation.kickers.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.Match;
import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.PlayerRole;
import com.performgroup.innovation.kickers.core.Team;
import com.performgroup.innovation.kickers.core.TeamColor;
import com.performgroup.innovation.kickers.event.GameFinishedEvent;
import com.performgroup.innovation.kickers.event.GoalEvent;
import com.performgroup.innovation.kickers.event.MatchFinishedEvent;
import com.performgroup.innovation.kickers.ui.CustomFont;
import com.performgroup.innovation.kickers.ui.customview.BoxPoints;
import com.performgroup.innovation.kickers.ui.customview.PlayerButtonBase;
import com.performgroup.innovation.kickers.ui.customview.PlayerButtonBottom;
import com.performgroup.innovation.kickers.ui.customview.PlayerButtonTop;
import com.performgroup.innovation.kickers.ui.dialog.MatchFinishedDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MatchActivity extends ActionBarActivity {

    BoxPoints blueBoxPoints;
    BoxPoints redBoxPoints;
    PlayerButtonBase tvRedAttackerName;
    PlayerButtonBase tvRedDeffenderName;
    PlayerButtonBase tvBlueAttackerName;
    PlayerButtonBase tvBlueDeffenderName;
    TextView tvScoreRed;
    TextView tvScoreBlue;
    TextView matchInfo;

    @Inject
    GameAPI gameAPI;
    @Inject
    Bus eventBus;

    private Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        KickersApplication.inject(this);

        Typeface font = Typeface.createFromAsset(getAssets(), CustomFont.ORBITRON_BOLD_FONT);

        int maxGoals = gameAPI.getMaxGoals();

        blueBoxPoints = (BoxPoints) findViewById(R.id.bp_blue_points);
        blueBoxPoints.setMaxPoints(maxGoals);

        redBoxPoints = (BoxPoints) findViewById(R.id.bp_red_points);
        redBoxPoints.setMaxPoints(maxGoals);

        tvBlueAttackerName = (PlayerButtonBottom) findViewById(R.id.tv_player_blue_attacker_name);
        tvBlueAttackerName.setListener(clickListener);

        tvBlueDeffenderName = (PlayerButtonBottom) findViewById(R.id.tv_player_blue_deffender_name);
        tvBlueDeffenderName.setListener(clickListener);

        tvRedAttackerName = (PlayerButtonTop) findViewById(R.id.tv_player_red_attacker_name);
        tvRedAttackerName.setListener(clickListener);

        tvRedDeffenderName = (PlayerButtonTop) findViewById(R.id.tv_player_red_deffender_name);
        tvRedDeffenderName.setListener(clickListener);

        tvScoreBlue = (TextView) findViewById(R.id.tv_blues_score);
        tvScoreBlue.setTypeface(font);

        tvScoreRed = (TextView) findViewById(R.id.tv_reds_score);
        tvScoreRed.setTypeface(font);

        ((TextView) findViewById(R.id.tv_score_separator)).setTypeface(font);

        matchInfo = (TextView) findViewById(R.id.tv_match_number);
        matchInfo.setTypeface(font);

        match = gameAPI.getMatch();


        updateMatchInfo();
        updateLineups();
        updateScore(match.score);
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

    public void updateMatchInfo() {
        int matchCount = gameAPI.getMatchesCount();
        int numberOfMatches = gameAPI.getNumberOfMatches();
        String info = getString(R.string.match_info, matchCount, numberOfMatches);
        matchInfo.setText(info);
    }

    public void updateLineups() {
        Team blue = match.lineups.getTeam(TeamColor.BLUE);
        tvBlueAttackerName.setPlayer(blue.getPlayer(PlayerRole.ATTACER));
        tvBlueDeffenderName.setPlayer(blue.getPlayer(PlayerRole.DEFFENDER));

        Team red = match.lineups.getTeam(TeamColor.RED);
        tvRedAttackerName.setPlayer(red.getPlayer(PlayerRole.ATTACER));
        tvRedDeffenderName.setPlayer(red.getPlayer(PlayerRole.DEFFENDER));
    }

    public void updateScore(MatchScore score) {
        int bluePoints = score.bluesPoints;
        int redPoints = score.redsPoints;

        tvScoreBlue.setText(bluePoints + "");
        tvScoreRed.setText(redPoints + "");

        blueBoxPoints.setPoints(bluePoints);
        redBoxPoints.setPoints(redPoints);
    }

    @Subscribe
    public void onGoal(GoalEvent event) {
        updateScore(event.score);
    }

    @Subscribe
    public void onMatchFinished(MatchFinishedEvent event) {
        match = gameAPI.getMatch();

        updateMatchInfo();
        updateLineups();
        updateScore(match.score);

        MatchFinishedDialog dialog = new MatchFinishedDialog();
        dialog.show(getSupportFragmentManager(), "match_finished_dialog");
    }

    @Subscribe
    public void onGameFinished(GameFinishedEvent event) {
        MatchFinishedDialog dialog = new MatchFinishedDialog();
        dialog.show(getSupportFragmentManager(), "match_finished_dialog");
    }

    PlayerButtonTop.OnClickListener clickListener = new PlayerButtonTop.OnClickListener() {

        @Override
        public void onRevertClicked(Player player) {
        }

        @Override
        public void onOwnGoalClicked(Player player) {
            gameAPI.registerOwnGoal(player);
        }

        @Override
        public void onNameClicked(Player player) {
            gameAPI.registerGoal(player);
        }
    };

}
