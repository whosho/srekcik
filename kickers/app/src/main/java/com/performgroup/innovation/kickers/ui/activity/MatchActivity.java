package com.performgroup.innovation.kickers.ui.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
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
import com.performgroup.innovation.kickers.event.MatchResultConfirmedEvent;
import com.performgroup.innovation.kickers.event.MatchStartedEvent;
import com.performgroup.innovation.kickers.service.SoundService;
import com.performgroup.innovation.kickers.ui.customview.BoxPoints;
import com.performgroup.innovation.kickers.ui.customview.PlayerButton;
import com.performgroup.innovation.kickers.ui.dialog.GoalDialog;
import com.performgroup.innovation.kickers.ui.dialog.MatchFinishedDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MatchActivity extends ActionBarActivity {

    BoxPoints blueBoxPoints;
    BoxPoints redBoxPoints;

    PlayerButton tvRedAttackerName;
    PlayerButton tvRedDefenderName;
    PlayerButton tvBlueAttackerName;
    PlayerButton tvBlueDefenderName;

    TextView tvScoreRed;
    TextView tvScoreBlue;
    TextView matchInfo;

    @Inject
    GameAPI gameAPI;
    @Inject
    Bus eventBus;
    @Inject
    SoundService player;

    private Match match;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        KickersApplication.inject(this);

        int maxGoals = gameAPI.getMaxGoals();

        blueBoxPoints = (BoxPoints) findViewById(R.id.bp_blue_points);
        blueBoxPoints.setMaxPoints(maxGoals);

        redBoxPoints = (BoxPoints) findViewById(R.id.bp_red_points);
        redBoxPoints.setMaxPoints(maxGoals);

        tvBlueAttackerName = (PlayerButton) findViewById(R.id.tv_player_blue_attacker_name);
        tvBlueAttackerName.setPlayerClickListener(clickListener);

        tvBlueDefenderName = (PlayerButton) findViewById(R.id.tv_player_blue_deffender_name);
        tvBlueDefenderName.setPlayerClickListener(clickListener);

        tvRedAttackerName = (PlayerButton) findViewById(R.id.tv_player_red_attacker_name);
        tvRedAttackerName.setPlayerClickListener(clickListener);

        tvRedDefenderName = (PlayerButton) findViewById(R.id.tv_player_red_deffender_name);
        tvRedDefenderName.setPlayerClickListener(clickListener);

        tvScoreBlue = (TextView) findViewById(R.id.tv_blues_score);
        tvScoreRed = (TextView) findViewById(R.id.tv_reds_score);

        matchInfo = (TextView) findViewById(R.id.tv_match_number);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);

        player.registerSound(R.raw.crowd_quiet);
        player.registerSound(R.raw.ding);
        player.registerSound(R.raw.point);
        player.registerSound(R.raw.tada);
        player.registerSound(R.raw.applause);
        player.registerSound(R.raw.boooo);

        gameAPI.startMatch();
    }

    @Override
    protected void onPause() {
        eventBus.unregister(this);
        player.cleanSoundsBuffer();
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
        tvBlueDefenderName.setPlayer(blue.getPlayer(PlayerRole.DEFFENDER));

        Team red = match.lineups.getTeam(TeamColor.RED);
        tvRedAttackerName.setPlayer(red.getPlayer(PlayerRole.ATTACER));
        tvRedDefenderName.setPlayer(red.getPlayer(PlayerRole.DEFFENDER));
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
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100);
        }

        player.play(event.isOwnGoal ? R.raw.boooo : R.raw.applause);

    }

    @Subscribe
    public void onMatchFinished(MatchFinishedEvent event) {
        MatchFinishedDialog dialog = new MatchFinishedDialog();
        dialog.show(getSupportFragmentManager(), "match_finished_dialog");

        updateMatchInfo();
        updateLineups();
        updateScore(match.score);
        player.stop(R.raw.crowd_quiet);
    }

    @Subscribe
    public void onMatchResultConfirmedEvent(MatchResultConfirmedEvent event) {
        if (gameAPI.isFinished()) {
            finish();
        } else {
            gameAPI.startNextMatch();
        }
    }

    @Subscribe
    public void onMatchStartedEvent(MatchStartedEvent event) {
        match = event.newMatch;
        updateMatchInfo();
        updateLineups();
        updateScore(match.score);
        player.play(R.raw.crowd_quiet, true);
    }


    @Subscribe
    public void onGameFinished(GameFinishedEvent event) {
        MatchFinishedDialog dialog = new MatchFinishedDialog();
        dialog.show(getSupportFragmentManager(), "match_finished_dialog");
    }

    PlayerButton.OnClickListener clickListener = new PlayerButton.OnClickListener() {

        @Override
        public void onPlayerClick(Player teamPlayer, TeamColor teamColor) {
            player.play(R.raw.point);
            openGoalDialog(teamPlayer, teamColor);
        }

    };

    private void openGoalDialog(Player player, TeamColor teamColor) {
        GoalDialog dialog = GoalDialog.createInstance(player);
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialog.show(fragmentManager, "goal_dialog");
    }

}
