package com.performgroup.innovation.kickers.service;

import android.content.Context;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoundService {

    private Map<Integer, Integer> sounds;
    SoundPool player;
    private Context context;

    public SoundService(SoundPool player, Context context) {
        this.player = player;
        this.context = context;
        sounds = new HashMap<Integer, Integer>();
    }

    public void registerSound(int resourceID) {
        sounds.put(resourceID, player.load(context, resourceID, 1));
    }

    public void cleanSoundsBuffer() {
        Set<Integer> soundsIDs = sounds.keySet();
        for (Integer soundID : soundsIDs) {
            player.stop(soundID);
            player.unload(soundID);
        }
        sounds.clear();
    }

    public void play(int soundResourceID) throws UnregisteredSoundException {
        if (sounds.containsKey(soundResourceID)) {
            Integer soundID = sounds.get(soundResourceID);
            player.play(soundID, 1.0f, 1.0f, 1, 0, 1f);
        }else
        {
            throw new UnregisteredSoundException();
        }
    }

    private class UnregisteredSoundException extends RuntimeException {

    }
}