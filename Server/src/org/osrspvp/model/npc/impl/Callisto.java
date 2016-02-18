package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class Callisto extends SpecialNPC {

    private static int attackStyle = 0;

    @Override
    public void execute(Client client, NPC n) {
        n.startAnimation(4925);
        int chance = Misc.random(20);
        if (chance <= 13) {
            int chance1 = Misc.random(1);
            attackStyle = chance1;
        } else {
            attackStyle = 2;
        }
        if (attackStyle == 0) {
            int offX = (n.getY() - client.getY()) * -1;
            int offY = (n.getX() - client.getX()) * -1;
            client.getPA()
                .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 96, 395, 43, 31, -client.getId() - 1,
                    66, 0);
        }
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int damage = Misc.random(n.maxHit);
                if (attackStyle == 1) {
                    if (client.prayerActive[18]) {
                        damage = 0;
                    }
                }
                if (Misc.random(10 + client.getCombat().calculateMeleeDefence()) > Misc.random(n.attack)) {
                    damage = 0;
                }
                if (attackStyle == 2) {
                    damage = 3;
                    callistoRoar(client, n.absX, n.absY);
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

        }, attackStyle == 0 ? 3 : 1);
    }

    private static int coordsX;
    private static int coordsY;

    private static void callistoRoar(Client client, int otherX, int otherY) {
        int x = client.absX - otherX;
        int y = client.absY - otherY;
        client.getCombat().resetPlayerAttack();
        client.attackTimer += 2;
        client.playAnimation(Animation.create(2109));
        coordsX = client.absX;
        coordsY = client.absY;
        if (x > 0) {
            client.setForceMovement(client.localX(), client.localY(), client.localX() + 3, client.localY(), 10, 60, 1);
            coordsX = client.absX + 3;
            coordsY = client.absY;
        } else if (x < 0) {
            client.setForceMovement(client.localX(), client.localY(), client.localX() - 3, client.localY(), 10, 60, -1);
            coordsX = client.absX - 3;
            coordsY = client.absY;
        }
        if (y > 0) {
            client.setForceMovement(client.localX(), client.localY(), client.localX(), client.localY() + 3, 10, 60, 1);
            coordsX = client.absX;
            coordsY = client.absY + 3;
        } else if (y < 0) {
            client.setForceMovement(client.localX(), client.localY(), client.localX(), client.localY() - 3, 10, 60, -1);
            coordsX = client.absX;
            coordsY = client.absY - 3;
        }

        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                client.getPA().movePlayer(coordsX, coordsY, 0);
                container.stop();
            }

        }, 2);
    }

}
