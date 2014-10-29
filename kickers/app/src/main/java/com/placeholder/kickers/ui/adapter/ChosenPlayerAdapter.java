package com.placeholder.kickers.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.placeholder.kickers.R;
import com.placeholder.kickers.application.KickersApplication;
import com.placeholder.kickers.core.Player;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChosenPlayerAdapter extends BaseAdapter {

    private Context context;
    private List<PlayerListItem> items;
    private LayoutInflater inflater;

    @Inject
    Bus eventBus;

    public ChosenPlayerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<PlayerListItem>();
        KickersApplication.inject(this);
    }

    public void updateData(List<PlayerListItem> players) {
        items.clear();
        for (PlayerListItem player : players) {
            if (player.isChosen) {
                items.add(player);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (items == null ? 0 : items.size());
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chosen_players_item, null);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Player player = items.get(position).player;
        viewHolder.name.setText(player.name);

        return convertView;

    }

    static class ViewHolder {
        TextView name;
    }

}
