package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int interfaceId = c.inStream.readSignedWordBigEndianA();
        boolean insertMode = c.inStream.readSignedByteC() == 1;
        int from = c.inStream.readSignedWordBigEndianA();
        int to = c.inStream.readSignedWordBigEndian();
        c.getItems().moveItems(from, to, interfaceId, insertMode);
    }
}