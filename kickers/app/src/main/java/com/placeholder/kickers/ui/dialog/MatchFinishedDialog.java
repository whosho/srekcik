package com.placeholder.kickers.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.placeholder.kickers.R;
import com.placeholder.kickers.application.GameAPI;
import com.placeholder.kickers.application.KickersApplication;
import com.placeholder.kickers.core.MatchScore;
import com.placeholder.kickers.core.Player;
import com.placeholder.kickers.core.Team;
import com.placeholder.kickers.core.TeamColor;
import com.placeholder.kickers.event.MatchResultConfirmedEvent;
import com.placeholder.kickers.statistics.GameResults;
import com.placeholder.kickers.statistics.PlayerResults;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

public class MatchFinishedDialog extends DialogFragment {

    private View view;
    private GameResults results;
    private Context context;

    @Inject
    GameAPI gameAPI;
    @Inject
    Bus eventBus;

    public MatchFinishedDialog() {
        // should be empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_match_finished, container);
        context = getActivity();

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        KickersApplication.inject(this);
        results = gameAPI.getResults();

        initView();

        return view;
    }

    private void initView() {
        List<MatchScore> matchScores = results.matchesScore;
        MatchScore lastScore = matchScores.get(matchScores.size() - 1);

        int currentMatchNumber = gameAPI.getMatchesCount();
        int numberOfMatches = gameAPI.getNumberOfMatches();

        String title = getString(R.string.match_statistics_title, currentMatchNumber, numberOfMatches);

        Team blueTeam = gameAPI.getTeam(TeamColor.BLUE);
        Team redTeam = gameAPI.getTeam(TeamColor.RED);

        ((TextView) view.findViewById(R.id.tv_match_finished_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_blue_points)).setText(lastScore.getPoints(blueTeam.ID) + "");
        ((TextView) view.findViewById(R.id.tv_red_points)).setText(lastScore.getPoints(redTeam.ID) + "");

        drawMatchHistory(numberOfMatches);
        drawPlayerStatistics();

        view.findViewById(R.id.b_goto_next_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // some stuff here
                eventBus.post(new MatchResultConfirmedEvent());
                dismiss();
            }
        });
    }

    private void drawMatchHistory(int numberOfMatches) {
        List<Team> teams = results.lineups.teams;

        TableLayout table = (TableLayout) view.findViewById(R.id.tl_match_history);

        TableRow titleRow = new TableRow(context);
        addTableColumn(titleRow, "");
        for (int i = 1; i <= numberOfMatches; i++) {
            addBoldTableColumn(titleRow, i + "");
        }
        addBoldTableColumn(titleRow, getString(R.string.matches_won));
        table.addView(titleRow, new TableLayout.LayoutParams());

        for (Team team : teams) {
            TableRow row = new TableRow(context);
            String playerName1 = team.players.get(0).name;
            String playerName2 = team.players.get(1).name;

            addBoldTableColumn(row, playerName1 + " / " + playerName2);

            for (int i = 0; i < numberOfMatches; i++) {
                List<Integer> teamScores = team.scores;
                if (i < teamScores.size()) {
                    addTableColumn(row, teamScores.get(i) + "");
                } else {
                    addTableColumn(row, "");
                }
            }
            addBoldTableColumn(row, team.wins + "");

            table.addView(row, new TableLayout.LayoutParams());
        }

    }


    private void drawPlayerStatistics() {
        List<PlayerResults> playerResults = results.getSortedPlayerGoalBalance();

        TableLayout table = (TableLayout) view.findViewById(R.id.tl_players_statistics);

        TableRow titleRow = new TableRow(context);
        addTableColumn(titleRow, "");
        addBoldTableColumn(titleRow, getString(R.string.goals));
        addBoldTableColumn(titleRow, getString(R.string.own_goals));
        addBoldTableColumn(titleRow, getString(R.string.goals_balance));

        table.addView(titleRow, new TableLayout.LayoutParams());

        for (PlayerResults item : playerResults) {
            Player player = item.player;

            TableRow row = new TableRow(context);
            addBoldTableColumn(row, player.name + "");
            addTableColumn(row, item.totalGoals + "");
            addTableColumn(row, item.totalOwnGoals + "");
            addBoldTableColumn(row, item.goalBalance + "");
            table.addView(row, new TableLayout.LayoutParams());
        }
    }

    private void addTableColumn(TableRow row, String text) {
        TextView column = new TextView(context);
        column.setText(text);
        column.setTextColor(Color.WHITE);
        setCommonPadding(column);
        row.addView(column);
    }

    private void addBoldTableColumn(TableRow row, String text) {
        TextView column = new TextView(context);
        column.setText(text);
        column.setTypeface(null, Typeface.BOLD);
        column.setTextColor(Color.WHITE);
        setCommonPadding(column);
        row.addView(column);
    }

    private void setCommonPadding(TextView textView) {
        textView.setPadding(15, 2, 15, 2);
    }

}
