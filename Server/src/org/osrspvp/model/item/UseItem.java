package org.osrspvp.model.item;

import org.osrspvp.model.Client;
import org.osrspvp.model.content.ItemCombining;
import org.osrspvp.model.content.ItemSets;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;

/**
 * @author Ryan / Lmctruck30
 */

public class UseItem {

    public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
        if (!c.getItems().playerHasItem(itemId, 1))
            return;
        if (objectID == 11744) {
            if (ItemDef.forId(itemId).getName().toLowerCase().contains("armour set")) {
                ItemSets.exchange(c, itemId);
                return;
            }
            ItemSets.exchangeBack(c, itemId);
        }
        switch (objectID) {

        default:
            if (c.playerRights == 3)
                Misc.println("Player At Object id: " + objectID + " with Item id: " + itemId);
            break;
        }

    }

    public static void ItemonItem(Client c, int itemUsed, int useWith) {
        ItemCombining.combineItems(c, itemUsed, useWith);
        switch (itemUsed) {

        default:
            if (c.playerRights == 3)
                Misc.println("Player used Item id: " + itemUsed + " with Item id: " + useWith);
            break;
        }
    }

    public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
        switch (itemId) {

        default:
            if (c.playerRights == 3)
                Misc.println("Player used Item id: " + itemId + " with Npc id: " + npcId + " With Slot : " + slot);
            break;
        }

    }

}
