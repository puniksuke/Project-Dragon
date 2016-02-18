package org.osrspvp.net.packet;

import org.osrspvp.model.Client;

public interface PacketType {
    public void processPacket(Client c, int packetType, int packetSize);
}
