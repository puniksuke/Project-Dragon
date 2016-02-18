package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Trading
 */
public class TradeAction implements PacketType {

    public static final int REQUEST = 73, ANSWER = 139;

    @Override
    public void processPacket(Client client, int packetType, int packetSize) {
        if (AccountPinManager.hasToTypePin(client)) {
            AccountPinManager.openPinInterface(client);
            return;
        }
        if (client.isBanking || client.isShopping || client.duelStatus > 0) {
            client.sendMessage("You are far too busy right now.");
            return;
        }
        if (packetType == REQUEST) {
            int trade = client.inStream.readSignedWordBigEndian();
            if (trade < 0 || trade >= Config.MAX_PLAYERS)
                return;
            if (!client.goodDistance(client.getX(), client.getY(), PlayerHandler.players[trade].getX(),
                PlayerHandler.players[trade].getY(), 1)) {
                client.sendMessage("Please move closer to the person you are trying to trade.");
                return;
            }
            if (PlayerHandler.players[trade] != null) {
                Client c = (Client) PlayerHandler.players[trade];
                client.getTradeHandler().requestTrade(c);
            }
            client.println_debug("Trade Request to: " + trade);
        } else if (packetType == ANSWER) {
            int trade = client.inStream.readSignedWordBigEndian();

            if (trade < 0 || trade >= Config.MAX_PLAYERS)
                return;
            if (!client.goodDistance(client.getX(), client.getY(), PlayerHandler.players[trade].getX(),
                PlayerHandler.players[trade].getY(), 1)) {
                client.sendMessage("Please move closer to the person you are trying to trade.");
                return;
            }
            if (PlayerHandler.players[trade] != null) {
                Client c = (Client) PlayerHandler.players[trade];
                client.getTradeHandler().answerTrade(c);
            }
            client.println_debug("Trade Answer to: " + trade);
        }
    }

}
