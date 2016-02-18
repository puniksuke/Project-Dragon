package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Bank 5 Items
 **/
public class Bank5 implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int interfaceId = c.getInStream().readSignedWordBigEndianA();
        int removeId = c.getInStream().readSignedWordBigEndianA();
        int removeSlot = c.getInStream().readSignedWordBigEndian();

        switch (interfaceId) {

        case 3900:
            c.getShops().buyItem(removeId, removeSlot, 1);
            break;

        case 3823:
            c.getShops().sellItem(removeId, removeSlot, 1);
            break;

        case 5064:
            c.getBankHandler().bankItem(removeId, removeSlot, 5);
            break;

        case 5382:
            c.getBankHandler().fromBank(removeId, removeSlot, 5);
            break;

        case 3322:
            if (c.duelStatus > 0) {
                c.getDuel().stakeItem(removeId, removeSlot, 5);
                return;
            }
            c.getTradeHandler().tradeItem(removeId, removeSlot, 5);
            break;

        case 3415:
            if (c.duelStatus > 0) {
                return;
            }
            c.getTradeHandler().fromTrade(c.getTradeHandler().getOffer()[removeSlot] - 1, removeSlot, 5);
            break;

        case 6669:
            c.getDuel().fromDuel(removeId, removeSlot, 5);
            break;
        }
    }

}
