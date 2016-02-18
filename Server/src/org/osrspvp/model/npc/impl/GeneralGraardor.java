package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class GeneralGraardor extends SpecialNPC {

    private static int attackStyle = 0;

    @Override
    public void execute(Client client, NPC n) {
        int random = Misc.random(10);
        if (random <= 5) {
            n.startAnimation(7018);
            attackStyle = 0;
        } else {
            n.startAnimation(7021);
            int offX = (n.getY() - client.getY()) * -1;
            int offY = (n.getX() - client.getX()) * -1;
            client.getPA()
                .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 1202, 1, 0, -client.getId() - 1,
                    76, 0);
            attackStyle = 1;
        }
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int damage = attackStyle == 0 ? Misc.random(n.maxHit) : Misc.random(35);
                if (attackStyle == 0) {
                    if (client.prayerActive[18]) {
                        damage = 0;
                    }
                    if (Misc.random(client.getCombat().calculateMeleeDefence() + 10) > Misc.random(n.attack + 10)) {
                        damage = 0;
                    }
                } else if (attackStyle == 1) {
                    if (client.prayerActive[17]) {
                        damage = 0;
                    }
                    if (Misc.random(client.getCombat().calculateRangeDefence() + 10) > Misc.random(n.attack + 10)) {
                        damage = 0;
                    }
                }
                if (damage > client.getLevel()[3])
                    damage = client.getLevel()[3];
                client.dealDamage(damage);
                client.handleHitMask(damage);
                client.getPA().refreshSkill(3);
                client.updateRequired = true;
                container.stop();
            }

        }, attackStyle == 0 ? 1 : 2);
    }

}
