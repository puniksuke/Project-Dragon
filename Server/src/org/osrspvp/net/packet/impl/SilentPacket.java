package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {

    }
}
