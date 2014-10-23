package com.performgroup.innovation.kickers.ui.dialog;

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

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.MatchScore;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.core.TeamColor;
import com.performgroup.innovation.kickers.event.MatchResultConfirmedEvent;
import com.performgroup.innovation.kickers.statistics.GameResults;
import com.performgroup.innovation.kickers.statistics.PlayerResults;
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
        TeamColor winner = results.getWinner();

        List<PlayerResults> playerResults = results.getSortedPlayerGoalBalance();
        List<MatchScore> matchScores = results.matchesScore;

        MatchScore score = matchScores.get(matchScores.size() - 1);

        ((TextView) view.findViewById(R.id.tv_red_points)).setText(score.redsPoints + "");
        ((TextView) view.findViewById(R.id.tv_blue_points)).setText(score.bluesPoints + "");

        drawMatchHistory(matchScores, gameAPI.getNumberOfMatches());
        drawPlayerStatistics(playerResults);

        view.findViewById(R.id.b_goto_next_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // some stuff here
                eventBus.post(new MatchResultConfirmedEvent());
                dismiss();
            }
        });
    }

    private void drawMatchHistory(List<MatchScore> matchScores, int numberOfMatches) {
        TableLayout table = (TableLayout) view.findViewById(R.id.tl_match_history);

        TableRow titleRow = new TableRow(context);
        addTableColumn(titleRow, "");
        for (int i = 1; i <= numberOfMatches; i++) {
            addBoldTableColumn(titleRow, i + "");
        }
        addBoldTableColumn(titleRow, getString(R.string.total_matches));
        table.addView(titleRow, new TableLayout.LayoutParams());

        TableRow teamRow1 = new TableRow(context);
        addBoldTableColumn(teamRow1, "team1");
        for (MatchScore matchScore : matchScores) {
            addTableColumn(teamRow1, matchScore.bluesPoints + "");
        }
        table.addView(teamRow1, new TableLayout.LayoutParams());

        TableRow teamRow2 = new TableRow(context);
        addBoldTableColumn(teamRow2, "team2");
        for (MatchScore matchScore : matchScores) {
            addTableColumn(teamRow2, matchScore.redsPoints + "");
        }
        table.addView(teamRow2, new TableLayout.LayoutParams());
    }


    private void drawPlayerStatistics(List<PlayerResults> playerResults) {
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
