package com.performgroup.innovation.kickers.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.application.GameAPI;
import com.performgroup.innovation.kickers.application.KickersApplication;
import com.performgroup.innovation.kickers.core.Lineups;
import com.performgroup.innovation.kickers.core.Player;
import com.performgroup.innovation.kickers.event.PickPlayersListUpdated;
import com.performgroup.innovation.kickers.event.PlayerCreatedEvent;
import com.performgroup.innovation.kickers.ui.adapter.AvailablePlayersAdapter;
import com.performgroup.innovation.kickers.ui.adapter.ChosenPlayerAdapter;
import com.performgroup.innovation.kickers.ui.adapter.PlayerListItem;
import com.performgroup.innovation.kickers.ui.dialog.CreatePlayerDialog;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PickPlayerFragment extends Fragment {

    public static final int MINIMAL_PLAYERS_COUNT = 4;
    private View view;
    private AvailablePlayersAdapter availableAdapter;
    private ChosenPlayerAdapter chosenAdapter;
    private List<PlayerListItem> playersListItems;

    @Inject
    Bus eventBus;
    @Inject
    GameAPI gameAPI;
    private Button playButton;
    private TextView waringText;

    public PickPlayerFragment() {
        // should be empty
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        KickersApplication.inject(this);
        view = inflater.inflate(R.layout.fragment_pick_players, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        Context context = getActivity();
        availableAdapter = new AvailablePlayersAdapter(context);
        chosenAdapter = new ChosenPlayerAdapter(context);

        ListView availableListView = (ListView) view.findViewById(R.id.lv_available_players);
        ListView chosenListView = (ListView) view.findViewById(R.id.lv_chosen_players);

        availableListView.setAdapter(availableAdapter);
        chosenListView.setAdapter(chosenAdapter);

        view.findViewById(R.id.b_create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateNewPlayerDialog();
            }
        });

        playButton = (Button) view.findViewById(R.id.b_play);
        playButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameAPI.setLineups(getLineups());
                gameAPI.openGameRulesDialog();
            }
        });

        waringText = (TextView) view.findViewById(R.id.tv_warning);
    }

    private void showCreateNewPlayerDialog() {
        CreatePlayerDialog dialog = new CreatePlayerDialog();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        dialog.show(fragmentManager, "create_player_dialog");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        loadContent();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    private Lineups getLineups() {
        List<Player> playersList = new ArrayList<Player>();

        for (PlayerListItem playerItemView : playersListItems) {
            Player player = playerItemView.player;
            if (playerItemView.isChosen) {
                playersList.add(player);
            }
        }

        return Lineups.from(playersList);
    }

    private void loadContent() {
        List<Player> playerList = gameAPI.getAvailablePlayers();
        updatePlayersList(playerList);
    }

    private void updatePlayersList(List<Player> playerList) {
        playersListItems = new ArrayList<PlayerListItem>();
        for (Player player : playerList) {
            playersListItems.add(new PlayerListItem(player));
        }

        updateAdapters(playersListItems);
    }

    private void updateAdapters(List<PlayerListItem> players) {
        availableAdapter.updateData(players);
        chosenAdapter.updateData(players);
    }

    @Subscribe
    public void onPlayerCreated(PlayerCreatedEvent event)
    {
        updatePlayersList(gameAPI.getAvailablePlayers());
    }

    @Subscribe
    public void onPickPlayersListUpdated(PickPlayersListUpdated event) {
        for (PlayerListItem playerItemView : playersListItems) {
            if (event.playerId == playerItemView.player.id) {
                playerItemView.isChosen = event.isChosen;
            }
        }
        updateAdapters(playersListItems);

        int count = chosenAdapter.getCount();
        int missingPlayersCount = MINIMAL_PLAYERS_COUNT - count;

        playButton.setEnabled(count == MINIMAL_PLAYERS_COUNT);

        if (missingPlayersCount > 0) {
            waringText.setText("Add another " + missingPlayersCount + " player(s)");
        } else if (count == MINIMAL_PLAYERS_COUNT) {
            waringText.setText("Teams ready!");
        } else {
            waringText.setText("Remove " + (count - MINIMAL_PLAYERS_COUNT) + " player(s)");
        }
    }
}
