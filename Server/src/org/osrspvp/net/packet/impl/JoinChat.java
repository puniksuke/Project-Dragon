package org.osrspvp.net.packet.impl;

import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.util.Misc;
import org.osrspvp.world.Clan;

public class JoinChat implements PacketType {

    @Override
    public void processPacket(Client player, int packetType, int packetSize) {
        String owner = Misc.longToPlayerName2(player.getInStream().readQWord()).replaceAll("_", " ");
        if (owner != null && owner.length() > 0) {
            if (player.clan == null) {
                Clan clan = Server.clanManager.getClan(owner);
                if (clan != null) {
                    clan.addMember(player);
                } else if (owner.equalsIgnoreCase(player.playerName)) {
                    Server.clanManager.create(player);
                } else {
                    player.sendMessage(Misc.formatPlayerName(owner) + " has not created a clan yet.");
                }
                player.getPA().refreshSkill(21);
                player.getPA().refreshSkill(22);
                player.getPA().refreshSkill(23);
            }
        }
    }

}
