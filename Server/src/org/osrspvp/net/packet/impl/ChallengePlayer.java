package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        switch (packetType) {
        case 128:
            int answerPlayer = c.getInStream().readUnsignedWord();
            if (PlayerHandler.players[answerPlayer] == null) {
                return;
            }
            break;
        }
    }
}
