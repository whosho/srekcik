package com.placeholder.kickers.model;

import com.placeholder.kickers.core.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Grzegorz.Barski on 2014-10-22.
 */
public class PlayersList {

    public List<Player> players;

    public PlayersList() {
        players = new ArrayList<Player>();
    }

    public int getFreeID()
    {
        int freeID = 0;
        for(Player player: players)
        {
            if(player.id==freeID)
            {
                freeID++;
            }
        }

        return freeID;
    }
}
