package org.osrspvp.net.packet.impl;

import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Follow Player
 **/
public class FollowPlayer implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int followPlayer = c.getInStream().readUnsignedWordBigEndian();
        if (PlayerHandler.players[followPlayer] == null) {
            return;
        }
        if (AccountPinManager.hasToTypePin(c)) {
            AccountPinManager.openPinInterface(c);
            return;
        }
        c.followId = followPlayer;
    }
}
