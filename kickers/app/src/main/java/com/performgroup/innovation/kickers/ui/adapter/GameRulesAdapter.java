package com.performgroup.innovation.kickers.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.event.GameRulesChoosenEvent;
import com.performgroup.innovation.kickers.model.rules.GameRules;
import com.squareup.otto.Bus;

import java.util.List;

public class GameRulesAdapter extends BaseAdapter {

    private List<GameRules> rulesList;
    private Context context;
    private Bus eventBus;
    private LayoutInflater layoutInflater;

    public GameRulesAdapter(Context context, Bus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rulesList == null ? 0 : rulesList.size();
    }

    @Override
    public Object getItem(int position) {
        return rulesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GameRulesItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.rules_item, null);
            viewHolder = new GameRulesItemViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GameRulesItemViewHolder) convertView.getTag();
        }

        viewHolder.ruleName.setText(rulesList.get(position).getName());
        return convertView;

    }

    public void update(List<GameRules> availableGameRulesList) {
        this.rulesList = availableGameRulesList;
        notifyDataSetChanged();
    }

    private class GameRulesItemViewHolder {
        public TextView ruleName;
        public ImageView icon;

        public GameRulesItemViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.iv_rule_icon);
            ruleName = (TextView) view.findViewById(R.id.tv_rule_name);
        }
    }
}
