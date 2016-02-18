package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Bank 10 Items
 **/
public class Bank10 implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int interfaceId = c.getInStream().readUnsignedWordBigEndian();
        int removeId = c.getInStream().readUnsignedWordA();
        int removeSlot = c.getInStream().readUnsignedWordA();

        switch (interfaceId) {
        case 1688:
            c.getPA().useOperate(removeId);
            break;
        case 3900:
            c.getShops().buyItem(removeId, removeSlot, 5);
            break;

        case 3823:
            c.getShops().sellItem(removeId, removeSlot, 5);
            break;

        case 5064:
            c.getBankHandler().bankItem(removeId, removeSlot, 10);
            break;

        case 5382:
            c.getBankHandler().fromBank(removeId, removeSlot, 10);
            break;
        case 3322:
            if (c.duelStatus > 0) {
                c.getDuel().stakeItem(removeId, removeSlot, 10);
                return;
            }
            c.getTradeHandler().tradeItem(removeId, removeSlot, 10);
            break;

        case 3415:
            if (c.duelStatus > 0) {
                return;
            }
            c.getTradeHandler().fromTrade(removeId, removeSlot, 10);
            break;

        case 6669:
            c.getDuel().fromDuel(removeId, removeSlot, 10);
            break;
        }
    }

}
