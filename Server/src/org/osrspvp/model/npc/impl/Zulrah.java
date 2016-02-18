package org.osrspvp.model.npc.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.model.npc.SpecialNPC;

public class Zulrah extends SpecialNPC {

    public static int[][] newCoords = { { 2256, 3073 }, { 2266, 3073 }, { 2266, 3061 }, { 2276, 3073 } };

    public static int[] npcIds = { 2042, 2043, 2044 };

    private static int lastX = 2266;
    private static int lastY = 3073;

    @Override
    public void execute(Client client, NPC n) {
        if (n.respawnTime <= 3)
            return;
        int offX = (n.getY() - client.getY()) * -1;
        int offY = (n.getX() - client.getX()) * -1;
        if (n.npcType == 2042) {
            n.startAnimation(5069);
            client.getPA()
                .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 1044, 53, 43, -client.getId() - 1,
                    76);
        }
        if (n.npcType == 2044) {
            n.startAnimation(5069);
            client.getPA()
                .createPlayersProjectile(n.getX() + 1, n.getY() + 1, offX, offY, 50, 106, 1046, 53, 43, -client.getId() - 1,
                    76);
        }
        if (n.npcType == 2043) {
            n.startAnimation(5807);
        }
    }
}
