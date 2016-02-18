package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.item.UseItem;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.net.packet.PacketType;

public class ItemOnNpc implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int itemId = c.getInStream().readSignedWordA();
        int i = c.getInStream().readSignedWordA();
        int slot = c.getInStream().readSignedWordBigEndian();
        if (i <= 0)
            return;
        int npcId = NPCHandler.npcs[i].npcType;
        if (!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }

        UseItem.ItemonNpc(c, itemId, npcId, slot);
    }
}
