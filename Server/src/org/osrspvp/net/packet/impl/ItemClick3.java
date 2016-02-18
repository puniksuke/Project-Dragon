package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;

/**
 * Item Click 3 Or Alternative Item Option 1
 *
 * @author Ryan / Lmctruck30
 *         <p>
 *         Proper Streams
 */

public class ItemClick3 implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int itemId11 = c.getInStream().readSignedWordBigEndianA();
        int itemId1 = c.getInStream().readSignedWordA();
        int itemId = c.getInStream().readSignedWordA();
        if (!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
        if (ItemDef.forId(itemId).getName().contains("Games necklace")) {
            c.getDH().sendOption5("Corporeal Beast lair (PVP Zone)", "Callisto (Wildy)", "Vet'ion (Wildy)",
                "Chaos Fanatic (Wildy)", "Next Page");
            c.dialogueAction = 30;
            return;
        }
        switch (itemId) {

        case 1712:
            c.getPA().handleGlory(itemId);
            break;

        default:
            if (c.playerRights == 3)
                Misc.println(c.playerName + " - Item3rdOption: " + itemId + " : " + itemId11 + " : " + itemId1);
            break;
        }

    }

}
