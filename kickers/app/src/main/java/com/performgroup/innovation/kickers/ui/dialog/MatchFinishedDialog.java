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
import com.performgroup.innovation.kickers.statistics.GameResults;
import com.performgroup.innovation.kickers.statistics.PlayerResults;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MatchFinishedDialog extends DialogFragment {

    private View view;
    private GameResults results;

    @Inject
    GameAPI gameAPI;

    public MatchFinishedDialog() {
        // should be empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_match_finished, container);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        KickersApplication.inject(this);
        results = gameAPI.getResults();

        initView();

        return view;
    }

    private void initView() {
        TeamColor winner = results.getWinner();

        Map<Integer, PlayerResults> playerStatistics = results.playerStatistics;
        List<MatchScore> matchScores = results.matchesScore;

        MatchScore score = matchScores.get(matchScores.size() - 1);

        String title = (winner == TeamColor.BLUE ? getString(R.string.blue_team_wins) : getString(R.string.red_team_wins));

        ((TextView) view.findViewById(R.id.tv_match_finished_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_red_points)).setText(score.redsPoints + "");
        ((TextView) view.findViewById(R.id.tv_blue_points)).setText(score.bluesPoints + "");

        drawMatchResults(playerStatistics);

        view.findViewById(R.id.b_goto_next_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // some stuff here
                dismiss();
            }
        });
    }

    private void drawMatchResults(Map<Integer, PlayerResults> playerStatistics) {
        TableLayout matchResultsTable = (TableLayout) view.findViewById(R.id.tl_match_history);

        TableRow matchesIndex = getIndexRow();
        matchResultsTable.addView(matchesIndex, new TableLayout.LayoutParams());

        for (Map.Entry<Integer, PlayerResults> entry : playerStatistics.entrySet()) {
            TableRow playerRow = getPlayerRow(entry.getValue());
            matchResultsTable.addView(playerRow, new TableLayout.LayoutParams());
        }
    }

    private TableRow getIndexRow() {
        Context context = getActivity();
        TableRow row = new TableRow(context);

        TextView emptyColumn = new TextView(context);
        emptyColumn.setText("");
        row.addView(emptyColumn);

        TextView goalTitleColumn = new TextView(context);
        setCommonPadding(goalTitleColumn);
        goalTitleColumn.setText(getString(R.string.goals));
        goalTitleColumn.setTypeface(null, Typeface.BOLD);
        row.addView(goalTitleColumn);

        TextView ownGoalTitleColumn = new TextView(context);
        setCommonPadding(ownGoalTitleColumn);
        ownGoalTitleColumn.setText(getString(R.string.own_goals));
        ownGoalTitleColumn.setTypeface(null, Typeface.BOLD);
        row.addView(ownGoalTitleColumn);

        return row;
    }

    private TableRow getPlayerRow(PlayerResults playerResults) {
        Context context = getActivity();
        TableRow row = new TableRow(context);

        Player player = playerResults.player;
        int colorId = (player.color == TeamColor.BLUE ? R.color.blue_team_color : R.color.red_team_color);

        TextView teamColumn = new TextView(context);
        teamColumn.setText(player.name + "");
        teamColumn.setTextColor(Color.WHITE);
        teamColumn.setBackgroundResource(colorId);
        setCommonPadding(teamColumn);
        row.addView(teamColumn);

        int goals = playerResults.goalsAsAttacker + playerResults.goalsAsDefender;
        int ownGoals = playerResults.ownGoalsAsAttacker + playerResults.ownGoalsAsDefender;

        TextView goalsColumn = new TextView(context);
        goalsColumn.setText(goals + "");
        goalsColumn.setTextColor(Color.BLACK);
        setCommonPadding(goalsColumn);
        row.addView(goalsColumn);

        TextView onwGoalsColumn = new TextView(context);
        onwGoalsColumn.setText(ownGoals + "");
        onwGoalsColumn.setTextColor(Color.BLACK);
        setCommonPadding(onwGoalsColumn);
        row.addView(onwGoalsColumn);

        return row;
    }

    private void setCommonPadding(TextView textView) {
        textView.setPadding(15, 5, 15, 5);
    }

}
