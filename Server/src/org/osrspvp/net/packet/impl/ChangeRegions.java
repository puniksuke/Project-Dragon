package org.osrspvp.net.packet.impl;

import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        // Server.objectHandler.updateObjects(c);
        Server.itemHandler.reloadItems(c);
        Server.objectManager.loadObjects(c);
        c.getPA().castleWarsObjects();
        c.getPA().removeObjects();
        //c.getPA().Deletewalls(c);
        c.saveFile = true;

        if (c.skullTimer > 0) {
            c.isSkulled = true;
            c.headIconPk = 0;
            c.getPA().requestUpdates();
        }

    }

}
