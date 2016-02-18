package org.osrspvp.model.player;

import org.osrspvp.model.Client;
import org.osrspvp.sanction.StarterHandler;

public class NewPlayer {

    private static final int REWARD_ITEMS[][] = { { 995, 1000000, 500000 } };

    public static void newPlayer(Client c) {
        if (c.addStarter) {
            c.getPA().showInterface(3559);
            c.canChangeAppearance = true;
            if (!StarterHandler.hasRecieved1stStarter(c.connectedFrom) && !StarterHandler
                .hasRecieved2ndStarter(c.connectedFrom)) {
                for (int i = 0; i < REWARD_ITEMS.length; i++) {
                    c.getItems().addItem(REWARD_ITEMS[i][0], REWARD_ITEMS[i][1]);
                }
                StarterHandler.addIpToStarterList1(c.connectedFrom);
                StarterHandler.addIpToStarter1(c.connectedFrom);
            }
            if (StarterHandler.hasRecieved1stStarter(c.connectedFrom) && !StarterHandler
                .hasRecieved2ndStarter(c.connectedFrom)) {
                for (int i = 0; i < REWARD_ITEMS.length; i++) {
                    c.getItems().addItem(REWARD_ITEMS[i][0], REWARD_ITEMS[i][2]);
                }
                StarterHandler.addIpToStarterList2(c.connectedFrom);
                StarterHandler.addIpToStarter2(c.connectedFrom);
            } else {
                c.sendMessage("You have already recieved all your starter kits.");
            }
            for (int i = 0; i < 7; i++) {
                c.getLevel()[i] = 99;
                c.getExperience()[i] = c.getPA().getXPForLevel(100);
                c.getPA().refreshSkill(i);
            }
            c.combatLevel = PlayerConstants.getCombatLevel(c);
            c.getPA().requestUpdates();
        }
    }
}