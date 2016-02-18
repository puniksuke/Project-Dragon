package org.osrspvp.core.cycle.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;

public class InterfaceTextEvent extends CycleEvent {

    @Override
    public void execute(CycleEventContainer container) {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] == null) {
                continue;
            }
            Client client = (Client) PlayerHandler.players[i];
            if (!PlayerHandler.players[i].inSafeZone()) {
                int modY = PlayerHandler.players[i].absY > 6400 ? PlayerHandler.players[i].absY - 6400 :
                    PlayerHandler.players[i].absY;
                PlayerHandler.players[i].wildLevel = (((modY - 3520) / 8) + 1);
                if (PlayerHandler.players[i].wildLevel < 1)
                    PlayerHandler.players[i].wildLevel = 1;
                client.getPA().sendFrame36(600, 0);
                client.getPA().walkableInterface(28034);
                client.getPA().sendFrame126(client.drawLevels(), 28036);
                if (client.inWild()) {
                    client.getPA().sendFrame126("Level: " + client.wildLevel, 28038);
                } else {
                    client.getPA().sendFrame126("", 28038);
                }
                client.getPA().sendOption("Attack", 1);
            } else if (client.inSafeZone()) {
                if (client.inDuelArena() && client.duelStatus != 5) {
                    client.getPA().sendOption("Challenge", 1);
                } else if (client.inDuelArena() && client.duelStatus > 4) {
                    client.getPA().sendOption("Attack", 1);
                } else {
                    client.getPA().sendOption("null", 1);
                }
                client.getPA().sendFrame126(client.drawLevels(), 28036);
                client.getPA().sendFrame126("", 28038);
                client.getPA().sendFrame36(600, 1);
            } else {
                client.getPA().sendFrame99(0);
                client.getPA().walkableInterface(-1);
                client.getPA().sendOption("Null", 1);
            }
        }
    }

}
