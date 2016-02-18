package org.osrspvp.core.cycle.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;

public class RestoreStatsEvent extends CycleEvent {

    @Override
    public void execute(CycleEventContainer container) {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] == null) {
                continue;
            }
            Client client = (Client) PlayerHandler.players[i];
            for (int level = 0; level < client.getLevel().length; level++) {
                if (client.getLevel()[level] < client.getLevelForXP(client.getExperience()[level])) {
                    if (level != 5) { // prayer doesn't restore
                        client.getLevel()[level] += 1;
                        client.getPA().setSkillLevel(level, client.getLevel()[level], client.getExperience()[level]);
                        client.getPA().refreshSkill(level);
                    }
                } else if (client.getLevel()[level] > client.getLevelForXP(client.getExperience()[level])) {
                    client.getLevel()[level] -= 1;
                    client.getPA().setSkillLevel(level, client.getLevel()[level], client.getExperience()[level]);
                    client.getPA().refreshSkill(level);
                }
            }
        }
    }

}
