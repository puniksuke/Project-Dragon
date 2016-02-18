package org.osrspvp.net.packet;

import org.osrspvp.model.Client;

public interface SubPacketType {
	public void processSubPacket(Client c, int packetType, int packetSize);
}
