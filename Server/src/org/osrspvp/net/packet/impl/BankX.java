package org.osrspvp.net.packet.impl;

import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.net.packet.PacketType;

public class BankX implements PacketType {

    public static final int PART1 = 135;
    public static final int PART2 = 208;
    public int XremoveSlot, XinterfaceID, XremoveID, Xamount;

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        System.out.println("packetType: " + packetType);
        if (packetType == PART1) {
            c.XremoveSlot = c.getInStream().readSignedWordBigEndian();
            c.XinterfaceID = c.getInStream().readUnsignedWordA();
            c.XremoveID = c.getInStream().readSignedWordBigEndian();
            if (c.XinterfaceID == 3900) {
                c.getShops().buyItem(c.XremoveID, c.XremoveSlot, 50);// buy 20
                c.XremoveSlot = -1;
                c.XinterfaceID = -1;
                c.XremoveID = -1;
                c.Xamount = -1;
                return;
            }
            c.getOutStream().createFrame(27);
        }

        if (packetType == PART2) {
            c.Xamount = c.getInStream().readDWord();
            if (c.Xamount <= 0)
                return;
            if (c.Xamount > Integer.MAX_VALUE)
                return;
            if (c.skillToChange > -1) {
                boolean canChangeSkill = true;
                if (c.duelStatus > 0) {
                    c.sendMessage("You can't do this right now.");
                    return;
                }
                if (!c.inSafeZone()) {
                    c.sendMessage("Please run to safety first.");
                    canChangeSkill = false;
                }
                if (c.getPA().getInventoryAmount() > 0) {
                    c.sendMessage("Please bank all the items in your inventory.");
                    canChangeSkill = false;
                }
                if (c.getPA().getWearingAmount() > 0) {
                    c.sendMessage("Please bank all your equipment first.");
                    canChangeSkill = false;
                }
                if (!canChangeSkill) {
                    c.getPA().removeAllWindows();
                    return;
                }
                if (c.Xamount > 99) {
                    c.Xamount = 99;
                }
                if (c.skillToChange == 3 && c.Xamount < 10) {
                    c.Xamount = 10;
                }
                c.getLevel()[c.skillToChange] = c.Xamount;
                c.getExperience()[c.skillToChange] = c.getPA().getXPForLevel(c.Xamount + 1);
                c.getPA().refreshSkill(c.skillToChange);
                c.combatLevel = PlayerConstants.getCombatLevel(c);
                c.skillToChange = -1;
                c.getPA().requestUpdates();
                c.getPA().removeAllWindows();
                return;
            }
            switch (c.XinterfaceID) {
            case 5064:
                c.getBankHandler().bankItem(c.playerItems[c.XremoveSlot], c.XremoveSlot, c.Xamount);
                break;

            case 5382:
                c.getBankHandler().fromBank(c.bankItems[c.XremoveSlot], c.XremoveSlot, c.Xamount);
                break;

            case 3322:
                if (c.duelStatus > 0) {
                    c.getDuel().stakeItem(c.playerItems[c.XremoveSlot] - 1, c.XremoveSlot, c.Xamount);
                    return;
                }
                c.getTradeHandler().tradeItem(c.playerItems[c.XremoveSlot] - 1, c.XremoveSlot, c.Xamount);
                break;

            case 3415:
                if (c.duelStatus > 0) {
                    return;
                }
                c.getTradeHandler().fromTrade(c.getTradeHandler().getOffer()[c.XremoveSlot] - 1, c.XremoveSlot, c.Xamount);
                break;

            case 6669:
                c.getDuel().fromDuel(c.XremoveID, c.XremoveSlot, c.Xamount);
                break;
            }
            c.XremoveSlot = -1;
            c.XinterfaceID = -1;
            c.XremoveID = -1;
            c.Xamount = -1;
        }
    }

}
