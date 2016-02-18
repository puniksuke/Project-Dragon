package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.Graphic;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class TzTokJad extends SpecialNPC {

    public static int attackStyle = 0;

    @Override
    public void execute(Client client, NPC n) {
        attackStyle = 1 + Misc.random(1);
        if (attackStyle == 0) {
            n.startAnimation(2655);
        } else if (attackStyle == 1) {
            n.startAnimation(2652);
            client.playGraphic(Graphic.create(451, 60, 0));
        } else if (attackStyle == 2) {
            int offX = (n.getY() - client.getY()) * -1;
            int offY = (n.getX() - client.getX()) * -1;
            n.startAnimation(2656);
            client.getPA()
                .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 116, 448, 43, 31, -client.getId() - 1,
                    86);
            client.playGraphic(Graphic.create(157, 80, 100));
        }
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int damage = Misc.random(99);
                if (attackStyle == 0) {
                    if (10 + Misc.random(client.getCombat().calculateMeleeDefence()) > Misc.random(n.attack)) {
                        damage = 0;
                    }
                    if (client.prayerActive[18]) {
                        damage = 0;
                    }
                }
                if (attackStyle == 1) {
                    if (10 + Misc.random(client.getCombat().calculateRangeDefence()) > Misc.random(n.attack)) {
                        damage = 0;
                    }
                    if (client.prayerActive[17]) {
                        damage = 0;
                    }
                }
                if (attackStyle == 2) {
                    if (10 + Misc.random(client.getCombat().mageDef()) > Misc.random(n.attack)) {
                        damage = 0;
                    }
                    if (client.prayerActive[16]) { // protect from magic
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

        }, attackStyle == 0 ? 1 : 3);
    }

}
