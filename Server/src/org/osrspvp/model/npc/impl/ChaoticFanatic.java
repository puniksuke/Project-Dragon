package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.util.Misc;

public class ChaoticFanatic extends SpecialNPC {

    @Override
    public void execute(Client client, NPC n) {
        int attack = Misc.random(10);
        int offX = (n.getY() - client.getY()) * -1;
        int offY = (n.getX() - client.getX()) * -1;
        n.startAnimation(811);
        if (attack <= 5) {
            client.getPA()
                .createPlayersProjectile(n.getX(), n.getY(), offX, offY, 50, 60, 554, 43, 31, -client.getId() - 1, 30);
            client.getPA()
                .createPlayersProjectile(n.getX(), n.getY(), offX, offY, 50, 80, 554, 43, 31, -client.getId() - 1, 50);
            CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

                @Override
                public void execute(CycleEventContainer container) {
                    int damage = Misc.random(31);
                    if (client.prayerActive[16])
                        damage = 0;
                    if (Misc.random(client.getCombat().mageDef()) > Misc.random(n.defence) && damage > 0) {
                        damage = 0;
                    }
                    if (damage > client.getLevel()[3])
                        damage = client.getLevel()[3];
                    client.dealDamage(damage);
                    client.handleHitMask(damage);
                    client.updateRequired = true;
                    client.getPA().refreshSkill(3);
                    container.stop();
                }

            }, 2);
        } else {
            int x = client.absX;
            int y = client.absY;
            int offX1 = (n.absY - y) * -1;
            int offY1 = (n.absX - x) * -1;
            client.getPA().createPlayersProjectile(n.getX(), n.getY(), offX1, offY1, 50, 78, 551, 43, 31, 0, 33);

            client.getPA().createPlayersStillGfx(157, x, y, 0, 90);

            int x1 = client.absX + 2;
            int y1 = client.absY;
            int offX2 = (n.absY - y1) * -1;
            int offY2 = (n.absX - x1) * -1;
            client.getPA().createPlayersProjectile(n.getX(), n.getY(), offX2, offY2, 50, 78, 551, 43, 0, 0, 33);
            client.getPA().createPlayersStillGfx(157, x1, y1, 0, 90);
            CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

                @Override
                public void execute(CycleEventContainer container) {
                    if (client.absX == x && client.absY == y || client.absX == x1 && client.absY == y1) {
                        int damage = Misc.random(31);
                        if (damage > client.getLevel()[3])
                            damage = client.getLevel()[3];
                        client.dealDamage(damage);
                        client.handleHitMask(damage);
                        client.updateRequired = true;
                        client.getPA().refreshSkill(3);
                    }
                    container.stop();
                }

            }, 2);
        }
    }

}
