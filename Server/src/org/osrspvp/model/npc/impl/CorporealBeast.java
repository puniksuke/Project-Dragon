package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class CorporealBeast extends SpecialNPC {

    private static int attackStyle = 0;

    @Override
    public void execute(Client client, NPC n) {
        int chance = Misc.random(3);
        if (chance < 3) {
            n.startAnimation(1680);
            attackStyle = chance;
            int offX = (n.getY() - client.getY()) * -1;
            int offY = (n.getX() - client.getX()) * -1;
            if (attackStyle == 0) {
                client.getPA().createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 316, 43, 31,
                    -client.getId() - 1, 76);
            } else if (attackStyle == 1) {
                client.getPA().createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 314, 43, 31,
                    -client.getId() - 1, 76);
            } else if (attackStyle == 2) {
                client.getPA().createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 315, 43, 31,
                    -client.getId() - 1, 76);
            }
        } else {
            n.startAnimation(1682);
            attackStyle = 3;
        }
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int damage = Misc.random(n.maxHit);
                if (attackStyle == 3) {
                    if (attackStyle == 2)
                        damage = Misc.random(20);
                    else if (attackStyle == 1)
                        damage = Misc.random(10);
                    else if (attackStyle == 0)
                        damage = Misc.random(69);
                    if (client.prayerActive[18]) {
                        damage = (int) (attackStyle == 1 ? damage * .50 : 0);
                    }
                    if (Misc.random(client.getCombat().calculateMeleeDefence() + 10) >= Misc.random(n.attack + 10)) {
                        damage = 0;
                    }
                }
                if (attackStyle < 3) {
                    if (client.prayerActive[16]) {
                        damage = 0;
                    }
                    if (Misc.random(client.getCombat().mageDef() + 10) >= Misc.random(n.attack + 10)) {
                        damage = 0;
                    }
                }
                if (damage > client.getLevel()[3]) {
                    damage = client.getLevel()[3];
                }
                client.dealDamage(damage);
                client.handleHitMask(damage);
                client.updateRequired = true;
                client.getPA().refreshSkill(3);
                container.stop();
            }

        }, attackStyle == 3 ? 1 : 3);
    }

}
