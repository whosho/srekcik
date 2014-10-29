package com.placeholder.kickers.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.placeholder.kickers.R;
import com.placeholder.kickers.application.GameAPI;
import com.placeholder.kickers.application.KickersApplication;
import com.placeholder.kickers.event.GameRulesChoosenEvent;
import com.placeholder.kickers.model.rules.GameRules;
import com.placeholder.kickers.ui.adapter.GameRulesAdapter;
import com.squareup.otto.Bus;

import javax.inject.Inject;

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

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

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
