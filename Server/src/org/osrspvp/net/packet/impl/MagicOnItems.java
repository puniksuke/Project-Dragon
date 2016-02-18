package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Magic on items
 **/
public class MagicOnItems implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        int slot = c.getInStream().readSignedWord();
        int itemId = c.getInStream().readSignedWordA();
        c.getInStream().readSignedWord();
        int spellId = c.getInStream().readSignedWordA();

        c.usingMagic = true;
        c.getPA().magicOnItems(slot, itemId, spellId);
        c.usingMagic = false;

    }

}
