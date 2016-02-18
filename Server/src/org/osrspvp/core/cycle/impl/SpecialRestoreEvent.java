package org.osrspvp.core.cycle.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.model.player.PlayerHandler;

public class SpecialRestoreEvent extends CycleEvent {

    @Override
    public void execute(CycleEventContainer container) {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] == null) {
                continue;
            }
            Client client = (Client) PlayerHandler.players[i];
            if (client.specAmount < 10) {
                client.specAmount += .5;
                if (client.specAmount > 10)
                    client.specAmount = 10;
                client.getItems().addSpecialBar(client.getEquipment()[PlayerConstants.PLAYER_WEAPON]);
            }
        }
    }

}
