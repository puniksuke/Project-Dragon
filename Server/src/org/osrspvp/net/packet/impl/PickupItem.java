package org.osrspvp.net.packet.impl;

import org.osrspvp.Server;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Pickup Item
 **/
public class PickupItem implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.pItemY = c.getInStream().readSignedWordBigEndian();
        c.pItemId = c.getInStream().readUnsignedWord();
        c.pItemX = c.getInStream().readSignedWordBigEndian();
        if (Math.abs(c.getX() - c.pItemX) > 25 || Math.abs(c.getY() - c.pItemY) > 25) {
            c.resetWalkingQueue();
            return;
        }
        if (AccountPinManager.hasToTypePin(c)) {
            AccountPinManager.openPinInterface(c);
            return;
        }
        if (!Server.itemHandler.itemExists(c.pItemId, c.pItemX, c.pItemY)) {
            return;
        }
        c.getCombat().resetPlayerAttack();
        if (c.getX() == c.pItemX && c.getY() == c.pItemY) {
            Server.itemHandler.removeGroundItem(c, c.pItemId, c.pItemX, c.pItemY, true);
        } else {
            c.walkingToItem = true;
        }

    }

}
