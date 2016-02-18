package org.osrspvp.model.content;

import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.Misc;

public class RewardHandler {

    public static void giveWealth(Client client, int totalCoins, Client otherClient) {
        if (client.connectedFrom.equalsIgnoreCase(otherClient.connectedFrom) && client.playerRights < 3) {
            client.sendMessage("You don't get rewards for killing someone on your own network.");
            return;
        }
        if (client.getLastKilledConnection().equalsIgnoreCase(otherClient.connectedFrom) && client.playerRights < 3) {
            client.sendMessage("You have recently killed @blu@" + otherClient.playerName + "@bla@...");
            client.sendMessage("...Please kill another person to receive rewards again.");
            return;
        }
        client.setLastKilledConnection(otherClient.connectedFrom);
        client.killStreak++;
        otherClient.killStreak = 0;
        otherClient.bounty = 0;
        if (client.killStreak > 2) {
            client.bounty = 100000 * client.killStreak;
            client.getPA().globalYell(
                "@cr6@[@blu@STREAK@bla@]@red@" + client.playerName + "@bla@ is on a kill streak, and have killed @blu@" + client.killStreak + "@bla@ people!");
            client.getPA().globalYell(
                "@cr6@[@blu@STREAK@bla@]Current Bounty; @blu@" + Misc.formatNumbers(client.bounty) + "@bla@ coins.");
        }
        int pointAmount = 1;
        totalCoins = totalCoins + otherClient.bounty;
        if (totalCoins > 500000) {
            if (RankHandler.isPremium(client.playerName)) {
                if (otherClient.bounty > 0) {
                    totalCoins = 600000 + otherClient.bounty;
                } else {
                    totalCoins = 600000;
                }
            } else if (RankHandler.isSuperPremium(client.playerName)) {
                if (otherClient.bounty > 0) {
                    totalCoins = 700000 + otherClient.bounty;
                } else {
                    totalCoins = 700000;
                }
            } else if (RankHandler.isExtremePremium(client.playerName)) {
                if (otherClient.bounty > 0) {
                    totalCoins = 800000 + otherClient.bounty;
                } else {
                    totalCoins = 800000;
                }
            } else {
                if (otherClient.bounty > 0) {
                    totalCoins = 500000 + otherClient.bounty;
                } else {
                    totalCoins = 500000;
                }
            }
        }
        if (client.inWild() && client.wildLevel >= 20) {
            if (RankHandler.isPremium(client.playerName)) {
                pointAmount = 6;
            } else if (RankHandler.isSuperPremium(client.playerName)) {
                pointAmount = 7;
            } else if (RankHandler.isExtremePremium(client.playerName)) {
                pointAmount = 8;
            } else {
                pointAmount = 5;
            }
        } else if (client.inWild() && client.wildLevel < 20) {
            if (RankHandler.isPremium(client.playerName)) {
                pointAmount = 4;
            } else if (RankHandler.isSuperPremium(client.playerName)) {
                pointAmount = 5;
            } else if (RankHandler.isExtremePremium(client.playerName)) {
                pointAmount = 6;
            } else {
                pointAmount = 3;
            }
        } else if (!client.inWild() && !client.inSafeZone()) {
            if (RankHandler.isPremium(client.playerName)) {
                pointAmount = 2;
            } else if (RankHandler.isSuperPremium(client.playerName)) {
                pointAmount = 3;
            } else if (RankHandler.isExtremePremium(client.playerName)) {
                pointAmount = 4;
            } else {
                pointAmount = 1;
            }
        }
        if (client.getItems().freeSlots() > 1) {
            client.getItems().addItem(4067, pointAmount);
            client.getItems().addItem(995, totalCoins);
        } else {
            client.sendMessage("You didn't have enough space in your inventory...");
            client.sendMessage("So @blu@" + Misc.formatNumbers(totalCoins) + "@bla@ coins were dropped on the floor.");
            String msg = pointAmount > 1 ? "tickets..." : "Ticket...";
            client.sendMessage("Along with @blu@" + pointAmount + "@bla@ PVP " + msg);
            Server.itemHandler
                .createGroundItem(client, 995, otherClient.getX(), otherClient.getY(), totalCoins, otherClient.killerId);
            Server.itemHandler
                .createGroundItem(client, 4067, otherClient.getX(), otherClient.getY(), pointAmount, otherClient.killerId);
        }
    }
}
