package org.osrspvp.model.content;

import org.osrspvp.model.Client;
import org.osrspvp.util.cache.defs.ItemDef;

public class ItemCombining {

    private static final int[][] COMBINE_DATA = { { 11818, 11820, 11794 }, { 11818, 11822, 11796 }, { 11820, 11822, 11800 },
        { 11818, 11800, 11798 }, { 11822, 11794, 11798 }, { 11820, 11796, 11798 }, { 11810, 11798, 11802 },
        { 11812, 11798, 11804 }, { 11814, 11798, 11806 }, { 11816, 11798, 11808 }, { 12004, 4151, 12006 },
        { 1540, 11286, 11283 }, { 12833, 12829, 12831 }, { 12831, 12819, 12817 }, { 12831, 12823, 12821 },
        { 12831, 12827, 12825 } };

    public static void combineItems(Client client, int itemUsed, int useWith) {
        for (int i = 0; i < COMBINE_DATA.length; i++) {
            if (itemUsed == COMBINE_DATA[i][0] && useWith == COMBINE_DATA[i][1] || useWith == COMBINE_DATA[i][0] && itemUsed == COMBINE_DATA[i][1]) {
                if (!client.getItems().playerHasItem(COMBINE_DATA[i][0], 1) || !client.getItems()
                    .playerHasItem(COMBINE_DATA[i][1], 1)) {
                    return;
                }
                int item0 = COMBINE_DATA[i][0];
                int item1 = COMBINE_DATA[i][1];
                client.getItems().deleteItem(item0, client.getItems().getItemSlot(item0), 1);
                client.getItems().deleteItem(item1, client.getItems().getItemSlot(item1), 1);
                int item2 = COMBINE_DATA[i][2];
                client.getItems().addItem(item2, 1);
                client.getDH().sendItemChat2(ItemDef.forId(item2).getName(),
                    "You combine a @blu@" + ItemDef.forId(item0).getName() + " @bla@with a @blu@" + ItemDef.forId(item1)
                        .getName() + "@bla@,", "and form a @blu@" + ItemDef.forId(item2).getName() + "@bla@.", item2, 250);
                client.nextChat = 0;
                return;
            }
        }
    }

}
