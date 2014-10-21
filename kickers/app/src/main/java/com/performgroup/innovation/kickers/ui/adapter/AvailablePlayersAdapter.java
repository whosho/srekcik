package com.performgroup.innovation.kickers.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.event.PickPlayersListUpdated;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AvailablePlayersAdapter extends BaseAdapter {

    private Context context;
    private List<PlayerItemView> items;

    @Inject
    Bus eventBus;

    public AvailablePlayersAdapter(Context context) {
        this.context = context;
        items = new ArrayList<PlayerItemView>();

        KickersApplication.inject(this);
    }

    public void updateData(List<PlayerItemView> players) {
        items.clear();
        for (PlayerItemView player : players) {
            if (!player.isChosen) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.available_players_item, null);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);

            viewHolder.button = (ImageButton) convertView.findViewById(R.id.ib_add_button);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int playerId = items.get(position).player.id;
                    eventBus.post(new PickPlayersListUpdated(playerId, true));
                }
            });

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
        ImageButton button;
    }

}
