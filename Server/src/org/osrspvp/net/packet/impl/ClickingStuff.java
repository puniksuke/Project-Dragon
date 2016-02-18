package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Clicking stuff (interfaces)
 **/
public class ClickingStuff implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        if (c.isBanking) {
            c.isBanking = false;
        }
        if (c.isShopping) {
            c.isShopping = false;
        }
        if (c.getTradeHandler().getCurrentTrade() != null) {
            if (c.getTradeHandler().getCurrentTrade().isOpen()) {
                c.getTradeHandler().decline();
            }
        }
        if (c.inDuelArena()) {
            if (c.duelStatus == 6) {
                c.getDuel().claimStakedItems();
            }
            if (c.duelStatus > 4) {
                return;
            }
            Client o = (Client) PlayerHandler.players[c.duelingWith];
            if (o == null) {
                c.getDuel().declineDuel();
                return;
            }
            if (o.duelStatus > 4 || c.duelStatus > 4) {
                return;
            }
            c.getDuel().declineDuel();
            o.getDuel().declineDuel();
        }
    }

}
