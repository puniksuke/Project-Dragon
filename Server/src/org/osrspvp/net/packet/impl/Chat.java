package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.sanction.SanctionHandler;

/**
 * Chat
 **/
public class Chat implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        if (SanctionHandler.isMuted(c) || SanctionHandler.isIPMuted(c)) {
            if (SanctionHandler.isMuted(c)) {
                c.sendMessage("Your account is temporarily muted.");
                c.sendMessage("You can appeal your mute at www.dragonicpk.com.");
            } else if (SanctionHandler.isIPMuted(c)) {
                c.sendMessage("Your account is permanently muted.");
                c.sendMessage("This mute cannot be appealed.");
            }
            return;
        }
        c.setChatTextEffects(c.getInStream().readUnsignedByteS());
        c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte) (c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
        c.setChatTextUpdateRequired(true);
    }
}
