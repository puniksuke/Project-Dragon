package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Remove Item
 **/
public class RemoveItem implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int interfaceId = c.getInStream().readUnsignedWordA();
        int removeSlot = c.getInStream().readUnsignedWordA();
        int removeId = c.getInStream().readUnsignedWordA();
        switch (interfaceId) {

        case 1688:
            c.getItems().removeItem(removeId, removeSlot);
            break;

        case 5064:
            c.getBankHandler().bankItem(removeId, removeSlot, 1);
            break;

        case 5382:
            c.getBankHandler().fromBank(removeId, removeSlot, 1);
            break;

        case 3900:
            c.getShops().buyFromShopPrice(removeId, removeSlot);
            break;

        case 3823:
            c.getShops().sellToShopPrice(removeId, removeSlot);
            break;

        case 3322:
            if (c.duelStatus > 0) {
                c.getDuel().stakeItem(removeId, removeSlot, 1);
                return;
            }
            c.getTradeHandler().tradeItem(removeId, removeSlot, 1);
            break;

        case 3415:
            if (c.duelStatus > 0) {
                return;
            }
            c.getTradeHandler().fromTrade(removeId, removeSlot, 1);
            break;

        case 6669:
            c.getDuel().fromDuel(removeId, removeSlot, 1);
            break;
        }
    }

}
