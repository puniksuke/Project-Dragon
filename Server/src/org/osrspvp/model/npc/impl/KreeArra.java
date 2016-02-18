package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.region.Region;

public class KreeArra extends SpecialNPC {

    private static int attackStyle = 0, projectileId;

    @Override
    public void execute(Client client, NPC n) {
        n.startAnimation(6980);
        int attack = Misc.random(2);
        if (attack == 0) {
            projectileId = 1199;
            attackStyle = 0;
        } else if (attack == 1) {
            attackStyle = 1;
            projectileId = 1198;
        } else {
            projectileId = 1200;
            attackStyle = 2;
        }
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            Client players = (Client) PlayerHandler.players[i];
            if (players == null)
                continue;
            if (Misc.doubleDistanceBetween(players, n) < 16 && Region.canAttack(n, players)) {
                int offX = (n.getY() - players.getY()) * -1;
                int offY = (n.getX() - players.getX()) * -1;
                players.getPA().createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, projectileId, 0, 0,
                    -players.getId() - 1, 76, 0);
                CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

                    @Override
                    public void execute(CycleEventContainer container) {
                        int damage = Misc.random(n.maxHit);
                        if (attackStyle == 0) {
                            damage = Misc.random(26);
                            if (players.prayerActive[18]) {
                                damage = 0;
                            }
                            if (Misc.random(10 + players.getCombat().calculateMeleeDefence()) > Misc.random(n.attack + 10)) {
                                damage = 0;
                            }
                        }
                        if (attackStyle == 1) {
                            damage = Misc.random(71);
                            if (players.prayerActive[17]) {
                                damage = 0;
                            }
                            if (Misc.random(10 + players.getCombat().calculateRangeDefence()) > Misc.random(n.attack + 10)) {
                                damage = 0;
                            }
                        }
                        if (attackStyle == 2) {
                            damage = Misc.random(21);
                            if (players.prayerActive[16]) {
                                damage = 0;
                            }
                            if (Misc.random(10 + players.getCombat().mageDef()) > Misc.random(n.attack + 10)) {
                                damage = 0;
                            }
                        }
                        if (damage > players.getLevel()[3]) {
                            damage = players.getLevel()[3];
                        }
                        players.dealDamage(damage);
                        players.handleHitMask(damage);
                        players.getPA().refreshSkill(3);
                        players.updateRequired = true;
                        container.stop();
                    }

                }, 3);
            }
        }
    }

}
