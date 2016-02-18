package org.osrspvp.model.npc;

import org.osrspvp.Server;
import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;

public class ZulrahInstance {

    public static void enterInstance(Client client) {
        int heightLevel = client.getId() * 4;
        client.getPA().movePlayer(2268, 3070, heightLevel);
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                if (client == null || client.heightLevel != heightLevel || client.getLevel()[3] <= 0 || client.isDead) {
                    container.stop();
                    return;
                }
                Server.npcHandler.spawnNpc(client, 2042, 2266, 3073, heightLevel, 0, 500, 41, 500, 300, true, false);
                container.stop();
            }

        }, 3);
    }

}
