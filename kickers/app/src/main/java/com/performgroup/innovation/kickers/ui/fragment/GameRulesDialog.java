package com.performgroup.innovation.kickers.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.event.GameRulesChoosenEvent;
import com.performgroup.innovation.kickers.model.rules.GameRules;
import com.performgroup.innovation.kickers.ui.adapter.GameRulesAdapter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by Grzegorz.Barski on 2014-10-20.
 */
public class GameRulesDialog extends DialogFragment {

    private GameRulesAdapter rulesAdapter;
    @Inject
    GameAPI gameAPI;
    @Inject
    Bus eventBus;

    public GameRulesDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        KickersApplication.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_game_rules, container);
        ListView rulesList = (ListView) view.findViewById(R.id.lv_game_rules);
        rulesAdapter = new GameRulesAdapter(getActivity(), eventBus);
        rulesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventBus.post(new GameRulesChoosenEvent((GameRules) rulesAdapter.getItem(position)));
                dismiss();
            }
        });

        rulesList.setAdapter(rulesAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rulesAdapter.update(gameAPI.getAvailableGameRulesList());
        super.onViewCreated(view, savedInstanceState);
    }

}
