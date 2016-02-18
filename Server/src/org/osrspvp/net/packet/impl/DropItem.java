package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.Server;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.PetHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int itemId = c.getInStream().readUnsignedWordA();
        c.getInStream().readUnsignedByte();
        c.getInStream().readUnsignedByte();
        int slot = c.getInStream().readUnsignedWordA();

        if (!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }

        if (System.currentTimeMillis() - c.alchDelay < 1800) {
            return;
        }

        if (c.getLevel()[3] <= 0 || c.isDead) {
            return;
        }
        if (AccountPinManager.hasToTypePin(c)) {
            AccountPinManager.openPinInterface(c);
            return;
        }
        if (c.getTradeHandler().getCurrentTrade() != null) {
            if (c.getTradeHandler().getCurrentTrade().isOpen()) {
                c.getTradeHandler().decline();
            }
        }
        PetHandler.dropPet(c, slot, itemId);
        int[] pets = { 11995 };
        for (int id : pets) {
            if (id == itemId) {
                return;
            }
        }
        boolean droppable = true;
        for (int i : Config.UNDROPPABLE_ITEMS) {
            if (i == itemId) {
                droppable = false;
                break;
            }
        }
        if (c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
            if (droppable) {
                c.getCombat().resetPlayerAttack();
                Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
                c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
            } else {
                c.sendMessage("This items cannot be dropped.");
            }
        }

    }
}
