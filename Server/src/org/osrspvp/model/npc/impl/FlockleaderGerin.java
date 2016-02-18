package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class FlockleaderGerin extends SpecialNPC {

    @Override
    public void execute(Client client, NPC n) {
        n.startAnimation(6956);
        int offX = (n.getY() - client.getY()) * -1;
        int offY = (n.getX() - client.getX()) * -1;
        client.getPA()
            .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 1192, 66, 31, -client.getId() - 1, 76,
                0);
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int damage = Misc.random(n.maxHit);
                if (client.prayerActive[17]) {
                    damage = 0;
                }
                if (Misc.random(10 + client.getCombat().calculateRangeDefence()) > Misc.random(n.attack + 10)) {
                    damage = 0;
                }
                if (damage > client.getLevel()[3]) {
                    damage = client.getLevel()[3];
                }
                client.dealDamage(damage);
                client.handleHitMask(damage);
                client.getPA().refreshSkill(3);
                client.updateRequired = true;
                container.stop();
            }

        }, 3);
    }

}
