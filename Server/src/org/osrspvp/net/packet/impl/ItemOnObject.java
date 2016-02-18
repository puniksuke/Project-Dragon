package org.osrspvp.net.packet.impl;

/**
 * @author Ryan / Lmctruck30
 */

import org.osrspvp.model.Client;
import org.osrspvp.model.item.UseItem;
import org.osrspvp.net.packet.PacketType;

public class ItemOnObject implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        /*
         * a = ? b = ?
		 */

        c.getInStream().readUnsignedWord();
        int objectId = c.getInStream().readSignedWordBigEndian();
        int objectY = c.getInStream().readSignedWordBigEndianA();
        c.getInStream().readUnsignedWord();
        int objectX = c.getInStream().readSignedWordBigEndianA();
        int itemId = c.getInStream().readUnsignedWord();
        if (!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
        UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);

    }

}
