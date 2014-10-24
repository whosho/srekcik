package com.performgroup.innovation.kickers.application;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.performgroup.innovation.kickers.R;
import com.performgroup.innovation.kickers.model.rules.GameRules;
import com.performgroup.innovation.kickers.service.SoundService;
import com.performgroup.innovation.kickers.ui.activity.MainActivity;
import com.performgroup.innovation.kickers.ui.activity.MatchActivity;
import com.performgroup.innovation.kickers.ui.adapter.AvailablePlayersAdapter;
import com.performgroup.innovation.kickers.ui.adapter.ChosenPlayerAdapter;
import com.performgroup.innovation.kickers.ui.dialog.CreatePlayerDialog;
import com.performgroup.innovation.kickers.ui.dialog.GameRulesDialog;
import com.performgroup.innovation.kickers.ui.dialog.GoalDialog;
import com.performgroup.innovation.kickers.ui.dialog.MatchFinishedDialog;
import com.performgroup.innovation.kickers.ui.fragment.PickPlayerFragment;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true,
        complete = false,
        injects = {
                MainActivity.class,
                MatchActivity.class,
                PickPlayerFragment.class,
                AvailablePlayersAdapter.class,
                ChosenPlayerAdapter.class,
                GameAPI.class,
                PickPlayerFragment.class,
                MatchFinishedDialog.class,
                GameRulesDialog.class,
                CreatePlayerDialog.class,
                GoalDialog.class
        }
)
public class BaseModule {

    Context context;

    public BaseModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public GameAPI provideMatchApi(Bus bus, Gson gson) {
        List<GameRules> gameRuleses = defineGameRules();
        GameAPI gameAPI = new GameAPI(bus, gson, context, gameRuleses);
        return gameAPI;
    }

    @Singleton
    @Provides
    public SoundService provideSoundService() {
        SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        return new SoundService(soundPool, context);
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    private List<GameRules> defineGameRules() {
        List<GameRules> gameRuleses = new ArrayList<GameRules>();
        gameRuleses.add(new GameRules(getString(R.string.game_name_exhibition_match) + " 5 po 5", 5, 5));
        gameRuleses.add(new GameRules(getString(R.string.game_name_exhibition_match) + " 5 do 8", 5, 8));
        //    gameRuleses.add(new GameRules(getString(R.string.game_name_three_wins), 5, 8));
        //  gameRuleses.add(new GameRules(getString(R.string.game_name_sparring), 5, 8));
        return gameRuleses;
    }

    private String getString(int stringID) {
        return context.getString(stringID);
    }

    @Singleton
    @Provides
    public Bus provideBus() {
        return new Bus();
    }


}


