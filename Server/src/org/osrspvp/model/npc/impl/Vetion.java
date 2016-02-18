package org.osrspvp.model.npc.impl;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;

import java.util.Random;

public class Vetion extends SpecialNPC {

    public static final int DEATH_ANIMATION = 5509;

    private static final int EARTH_QUAKE_ANIM = 5507;

    private static final int SPECIAL_ANIMATION = 5499;

    private static final int SPECIAL_PROJECTILE_ID = 280;
    private static final int SPECIAL_END_GRAPHIC = 281;

    private static Random r = new Random();

    public static int attackStyle = 1;

    private static int spotX = 0;
    private static int spotY = 0;

    private static int spotX1 = 0;
    private static int spotY1 = 0;

    private static int spotX2 = 0;
    private static int spotY2 = 0;

    @Override
    public void execute(Client client, NPC n) {
        int vet = r.nextInt(20);
        if (vet <= 15) {
            n.startAnimation(SPECIAL_ANIMATION);
            spotX = client.getX();
            spotY = client.getY();
            int endY = (n.getX() - spotX) * -1;
            int endX = (n.getY() - spotY) * -1;
            client.getPA().createPlayersStillGfx(SPECIAL_END_GRAPHIC, spotX, spotY, 0, 100);
            client.getPA()
                .createPlayersProjectile(n.getX(), n.getY(), endX, endY, 50, 110, SPECIAL_PROJECTILE_ID, 46, 31, 0, 80, 5);
            int x = client.absX - n.absX;
            int y = client.absY - n.absY;
            if (x > 0) {
                spotX1 = client.getX() + 1 + Misc.random(3);
                spotY1 = client.getY();
                spotX2 = client.getX() - 1 - Misc.random(3);
                spotY2 = client.getY();
            } else if (x < 0) {
                spotX1 = client.getX() - 1 - Misc.random(3);
                spotY1 = client.getY();
                spotX2 = client.getX() + 1 + Misc.random(3);
                spotY2 = client.getY();
            }
            if (y > 0) {
                spotX1 = client.getX();
                spotY1 = client.getY() + 1 + Misc.random(3);
                spotX2 = client.getX();
                spotY2 = client.getY() - 1 - Misc.random(3);
            } else if (y < 0) {
                spotX1 = client.getX();
                spotY1 = client.getY() - 1 - Misc.random(3);
                spotX2 = client.getX();
                spotY2 = client.getY() + 1 + Misc.random(3);
            }
            int endY1 = (n.getX() - spotX1) * -1;
            int endX1 = (n.getY() - spotY1) * -1;
            int endY2 = (n.getX() - spotX2) * -1;
            int endX2 = (n.getY() - spotY2) * -1;
            client.getPA().createPlayersStillGfx(SPECIAL_END_GRAPHIC, spotX1, spotY1, 0, 100);
            client.getPA()
                .createPlayersProjectile(n.getX(), n.getY(), endX1, endY1, 50, 110, SPECIAL_PROJECTILE_ID, 46, 31, 0, 80, 5);
            client.getPA().createPlayersStillGfx(SPECIAL_END_GRAPHIC, spotX2, spotY2, 0, 100);
            client.getPA()
                .createPlayersProjectile(n.getX(), n.getY(), endX2, endY2, 50, 110, SPECIAL_PROJECTILE_ID, 46, 31, 0, 80, 5);
            attackStyle = 1;
        } else {
            n.startAnimation(EARTH_QUAKE_ANIM);
            attackStyle = 2;
        }
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {
            @Override
            public void execute(CycleEventContainer container) {
                if (client == null || client.getLevel()[3] <= 0 || client.isDead) {
                    container.stop();
                    return;
                }
                int damage = r.nextInt(44);
                if (attackStyle == 1) {
                    for (int i = 0; i < PlayerHandler.players.length; i++) {
                        if (PlayerHandler.players[i] == null) {
                            continue;
                        }
                        if (PlayerHandler.players[i].getX() == spotX && PlayerHandler.players[i]
                            .getY() == spotY || PlayerHandler.players[i].getX() == spotX1 && PlayerHandler.players[i]
                            .getY() == spotY1 || PlayerHandler.players[i].getX() == spotX2 && PlayerHandler.players[i]
                            .getY() == spotY2) {
                            PlayerHandler.players[i].dealDamage(damage);
                            PlayerHandler.players[i].handleHitMask(damage);
                            ((Client) PlayerHandler.players[i]).getPA().refreshSkill(3);
                            PlayerHandler.players[i].updateRequired = true;
                        }
                    }
                    container.stop();
                    return;
                }
                if (attackStyle == 2) {
                    for (int i = 0; i < PlayerHandler.players.length; i++) {
                        if (PlayerHandler.players[i] == null) {
                            continue;
                        }
                        if (PlayerHandler.players[i].distanceToPoint(n.getX(), n.getY()) <= 4) {
                            ((Client) PlayerHandler.players[i])
                                .sendMessage("Vet'ion pummels the ground sending a shattering earthquake wave through you.");
                            PlayerHandler.players[i].dealDamage(damage);
                            PlayerHandler.players[i].handleHitMask(damage);
                            ((Client) PlayerHandler.players[i]).getPA().refreshSkill(3);
                            PlayerHandler.players[i].updateRequired = true;
                        }
                    }
                    container.stop();
                    return;
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

        }, attackStyle == 0 ? 2 : 4);
    }

}
