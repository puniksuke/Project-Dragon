package org.osrspvp.core.cycle.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;

public class FollowEvent extends CycleEvent {

    @Override
    public void execute(CycleEventContainer container) {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] == null) {
                continue;
            }
            Client client = (Client) PlayerHandler.players[i];
            if (client.followId > 0) {
                client.getPA().followPlayer();
            }
        }
    }

}
