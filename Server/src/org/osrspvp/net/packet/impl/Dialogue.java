package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {

        if (c.nextChat > 0) {
            c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
        } else {
            c.getDH().sendDialogues(0, -1);
        }

    }

}
