package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.item.GameItem;
import org.osrspvp.model.item.Item;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.util.cache.defs.ItemDef;

/**
 * Bank All Items
 **/
public class BankAll implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int removeSlot = c.getInStream().readUnsignedWordA();
        int interfaceId = c.getInStream().readUnsignedWord();
        int removeId = c.getInStream().readUnsignedWordA();

        switch (interfaceId) {
        case 3900:
            c.getShops().buyItem(removeId, removeSlot, 10);
            break;

        case 3823:
            c.getShops().sellItem(removeId, removeSlot, 10);
            break;

        case 5064:
            if (c.duelStatus > 0) {
                if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
                    c.getDuel().stakeItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
                } else {
                    c.getDuel().stakeItem(removeId, removeSlot, c.getItems().itemAmount(c.playerItems[removeSlot]));
                }
                return;
            }
            if (ItemDef.isStackable(removeId)) {
                c.getBankHandler().bankItem(removeId, removeSlot, c.playerItemsN[removeSlot]);
            } else {
                c.getBankHandler()
                    .bankItem(c.playerItems[removeSlot], removeSlot, c.getItems().itemAmount(c.playerItems[removeSlot]));
            }
            break;

        case 5382:
            c.getBankHandler().fromBank(removeId, removeSlot, c.bankItemsN[removeSlot]);
            break;

        case 3322:
            if (c.duelStatus > 0) {
                return;
            }
            if (ItemDef.isStackable(removeId)) {
                c.getTradeHandler().tradeItem(c.playerItems[removeSlot] - 1, removeSlot, c.playerItemsN[removeSlot]);
            } else {
                c.getTradeHandler().tradeItem(removeId, removeSlot, 28);
            }
            break;

        case 6669:
            if (Item.itemStackable[removeId] || Item.itemIsNote[removeId]) {
                for (GameItem item : c.getDuel().stakedItems) {
                    if (item.id == removeId) {
                        c.getDuel().fromDuel(removeId, removeSlot, c.getDuel().stakedItems.get(removeSlot).amount);
                    }
                }

            } else {
                c.getDuel().fromDuel(removeId, removeSlot, 28);
            }
            break;

        case 3415:
            c.getTradeHandler().fromTrade(removeId, removeSlot, c.getTradeHandler().getOfferN()[removeSlot]);
            break;

        }
    }
}
