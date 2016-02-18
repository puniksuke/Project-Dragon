package org.osrspvp.model.content;

import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerConstants;

public class SpawnTab {

    public static int[] packageItems;

    public static void handleActionButtons(Client client, int actionButtonId) {
        if (client.inDuelArena()) {
            client.sendMessage("You can't do this here.");
            return;
        }
        if (!client.inSafeZone()) {
            client.sendMessage("Please move to a safe location first.");
            return;
        }
        boolean canSpawn = true;
        switch (actionButtonId) {
        case 109197:
            packageItems = new int[] { 386, 11937, 3145 };
            if (client.getItems().freeSlots() < packageItems.length) {
                client.sendMessage(
                    "You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
                return;
            }
            for (int i = 0; i < packageItems.length; i++) {
                client.getItems().addItem(packageItems[i], 1000);
            }
            break;

        case 109204:
            packageItems = new int[] { 2441, 2437, 2443, 2445, 3041, 6686, 3025 };
            if (client.getItems().freeSlots() < packageItems.length) {
                client.sendMessage(
                    "You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
                return;
            }
            for (int i = 0; i < packageItems.length; i++) {
                client.getItems().addItem(packageItems[i], 1000);
            }
            break;

        case 109210:
            packageItems = new int[] { 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 9075 };
            if (client.getItems().freeSlots() < packageItems.length) {
                client.sendMessage(
                    "You need atleast @blu@" + packageItems.length + "@bla@ available inventory slots to do this.");
                return;
            }
            for (int i = 0; i < packageItems.length; i++) {
                client.getItems().addItem(packageItems[i], 5000);
            }
            break;

        case 109216:
            canSpawn = true;
            if (client.getPA().getWearingAmount() > 0) {
                client.sendMessage("Please bank all your equipped items.");
                canSpawn = false;
            }
            if (client.getPA().getInventoryAmount() > 0) {
                client.sendMessage("Please bank all the items in your inventory.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[0]) < 60) {
                client.sendMessage("You need a attack level of 60 to spawn this set.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[4]) < 70) {
                client.sendMessage("You need a ranged level of 70 to spawn this set.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[6]) < 50) {
                client.sendMessage("You need a magic level of 50 to spawn this set.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[1]) < 55) {
                client.sendMessage("You need a defence level of 55 to spawn this set.");
                canSpawn = false;
            }
            if (!canSpawn) {
                return;
            }
            int[] capes = { 2414, 2413, 2412 };
            int[] chest = { 4091, 4101, 4111 };
            int[] legs = { 4093, 4103, 4113 };
            client.getItems().wearItem(10828, 1, PlayerConstants.PLAYER_HAT);
            client.getItems().wearItem(1712, 1, PlayerConstants.PLAYER_AMULET);
            client.getItems().wearItem(4675, 1, PlayerConstants.PLAYER_WEAPON);
            client.getItems().wearItem(3840, 1, PlayerConstants.PLAYER_SHIELD);
            client.getItems().wearItem(capes[(int) (Math.random() * capes.length)], 1, PlayerConstants.PLAYER_CAPE);
            client.getItems().wearItem(chest[(int) (Math.random() * chest.length)], 1, PlayerConstants.PLAYER_BODY);
            client.getItems().wearItem(legs[(int) (Math.random() * legs.length)], 1, PlayerConstants.PLAYER_LEGS);
            client.getItems().wearItem(2550, 1, PlayerConstants.PLAYER_RING);
            client.getItems().wearItem(4097, 1, PlayerConstants.PLAYER_FEET);
            client.getItems().wearItem(7462, 1, PlayerConstants.PLAYER_GLOVES);
            client.getItems().addItem(4587, 1);
            client.getItems().addItem(1127, 1);
            client.getItems().addItem(3105, 1);
            client.getItems().addItem(5698, 1);
            client.getItems().addItem(8850, 1);
            client.getItems().addItem(1079, 1);
            client.getItems().addItem(2503, 1);
            client.getItems().addItem(385, 13);
            client.getItems().addItem(555, 1000);
            client.getItems().addItem(565, 1000);
            client.getItems().addItem(560, 1000);
            client.getItems().addItem(2440, 1);
            client.getItems().addItem(2436, 1);
            client.getItems().addItem(3024, 1);
            client.getItems().addItem(6685, 2);
            client.getCombat().getPlayerAnimIndex();
            client.getPA().requestUpdates();
            break;

        case 109222:
            canSpawn = true;
            if (client.getPA().getWearingAmount() > 0) {
                client.sendMessage("Please bank all your equipped items.");
                canSpawn = false;
            }
            if (client.getPA().getInventoryAmount() > 0) {
                client.sendMessage("Please bank all the items in your inventory.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[0]) < 60) {
                client.sendMessage("You need a attack level of 60 to spawn this set.");
                canSpawn = false;
            }
            if (client.getPA().getLevelForXP(client.getExperience()[1]) < 55) {
                client.sendMessage("You need a defence level of 55 to spawn this set.");
                canSpawn = false;
            }
            if (!canSpawn) {
                return;
            }
            client.getItems().wearItem(10828, 1, PlayerConstants.PLAYER_HAT);
            client.getItems().wearItem(1725, 1, PlayerConstants.PLAYER_AMULET);
            client.getItems().wearItem(4587, 1, PlayerConstants.PLAYER_WEAPON);
            client.getItems().wearItem(8850, 1, PlayerConstants.PLAYER_SHIELD);
            int[] cape = { 4315, 4317, 4319, 4321, 4323, 4325 };
            client.getItems().wearItem(cape[(int) (Math.random() * cape.length)], 1, PlayerConstants.PLAYER_CAPE);
            client.getItems().wearItem(1127, 1, PlayerConstants.PLAYER_BODY);
            client.getItems().wearItem(1079, 1, PlayerConstants.PLAYER_LEGS);
            client.getItems().wearItem(2550, 1, PlayerConstants.PLAYER_RING);
            client.getItems().wearItem(3105, 1, PlayerConstants.PLAYER_FEET);
            client.getItems().wearItem(7462, 1, PlayerConstants.PLAYER_GLOVES);
            client.getItems().addItem(5698, 1);
            client.getItems().addItem(2440, 1);
            client.getItems().addItem(2436, 1);
            client.getItems().addItem(3024, 1);
            client.getItems().addItem(557, 1000);
            client.getItems().addItem(560, 1000);
            client.getItems().addItem(9075, 1000);
            client.getItems().addItem(385, 21);
            client.getCombat().getPlayerAnimIndex();
            client.getPA().requestUpdates();
            break;

        case 109228:
            canSpawn = true;
            if (client.getPA().getWearingAmount() > 0) {
                client.sendMessage("Please bank all your equipped items.");
                canSpawn = false;
            }
            if (client.getPA().getInventoryAmount() > 0) {
                client.sendMessage("Please bank all the items in your inventory.");
                canSpawn = false;
            }
            if (!canSpawn) {
                return;
            }
            int[] capes1 = { 2414, 2413, 2412 };
            client.getItems().wearItem(6109, 1, PlayerConstants.PLAYER_HAT);
            client.getItems().wearItem(1712, 1, PlayerConstants.PLAYER_AMULET);
            client.getItems().wearItem(4675, 1, PlayerConstants.PLAYER_WEAPON);
            client.getItems().wearItem(9244, 500, PlayerConstants.PLAYER_ARROWS);
            client.getItems().wearItem(3840, 1, PlayerConstants.PLAYER_SHIELD);
            client.getItems().wearItem(capes1[(int) (Math.random() * capes1.length)], 1, PlayerConstants.PLAYER_CAPE);
            client.getItems().wearItem(6107, 1, PlayerConstants.PLAYER_BODY);
            client.getItems().wearItem(6108, 1, PlayerConstants.PLAYER_LEGS);
            client.getItems().wearItem(2550, 1, PlayerConstants.PLAYER_RING);
            client.getItems().wearItem(6106, 1, PlayerConstants.PLAYER_FEET);
            client.getItems().wearItem(6110, 1, PlayerConstants.PLAYER_GLOVES);
            client.getItems().addItem(9185, 1);
            client.getItems().addItem(10499, 1);
            client.getItems().addItem(2497, 1);
            client.getItems().addItem(4587, 1);
            client.getItems().addItem(5698, 1);
            client.getItems().addItem(2440, 1);
            client.getItems().addItem(2436, 1);
            client.getItems().addItem(2444, 1);
            client.getItems().addItem(2434, 1);
            client.getItems().addItem(385, 16);
            client.getItems().addItem(555, 1000);
            client.getItems().addItem(560, 1000);
            client.getItems().addItem(565, 1000);
            break;
        }
    }

}
