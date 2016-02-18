package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
        textSent = textSent.replaceAll("_", " ");
    }
}
