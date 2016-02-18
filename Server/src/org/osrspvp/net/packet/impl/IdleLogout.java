package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

public class IdleLogout implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        // if (!c.playerName.equalsIgnoreCase("Sanity"))
        // c.logout();
    }
}