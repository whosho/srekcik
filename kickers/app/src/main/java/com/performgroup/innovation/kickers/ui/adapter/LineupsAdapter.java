package com.performgroup.innovation.kickers.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.Player;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LineupsAdapter extends BaseAdapter {

    private Context context;
    private List<PlayerListItem> items;
    private LayoutInflater inflater;
    private int teamColor;

    public LineupsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<PlayerListItem>();
        teamColor = android.R.color.white;
    }

    public void updateData(List<Player> players) {
        items.clear();
        for (Player player : players) {
            items.add(new PlayerListItem(player));
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

    public void setTeamColor(int teamColor) {
        this.teamColor = teamColor;
    }

    static class ViewHolder {
        TextView name;
    }

}
