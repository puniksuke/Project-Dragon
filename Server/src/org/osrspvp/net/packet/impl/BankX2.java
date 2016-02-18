package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Bank X Items
 **/
public class BankX2 implements PacketType {
    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int Xamount = c.getInStream().readDWord();
        if (Xamount == 0)
            Xamount = 1;
        switch (c.xInterfaceId) {

        case 5064:
            if (c.inTrade) {
                c.sendMessage("You can't bank items while trading!");
                return;
            }
            c.getBankHandler().bankItem(c.playerItems[c.XremoveSlot], c.XremoveSlot, Xamount);
            break;

        case 5382:
            if (c.inTrade) {
                c.sendMessage("You can't store items while trading!");
                return;
            }
            c.getBankHandler().fromBank(c.bankItems[c.XremoveSlot], c.XremoveSlot, Xamount);
            break;

        case 3322:
            c.getTradeHandler().tradeItem(c.playerItems[c.XremoveSlot] - 1, c.XremoveSlot, c.Xamount);
            break;

        case 3415:
            c.getTradeHandler().fromTrade(c.getTradeHandler().getOffer()[c.XremoveSlot] - 1, c.XremoveSlot, c.Xamount);
            break;
        }
    }
}