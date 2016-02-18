package org.osrspvp.model.content;

import org.osrspvp.model.Client;
import org.osrspvp.util.cache.defs.ItemDef;

import java.util.HashMap;

public class ItemSets {

    public static void exchangeBack(Client client, int itemId) {
        ItemTable2 table = ItemTable2.forId(itemId);
        if (table == null) {
            // NOT A BARROWS ITEM
            return;
        }
        for (int i = 0; i < table.getItems().length; i++) {
            if (!client.getItems().playerHasItem(table.getItems()[i])) {
                client.sendMessage("You need the full set to do this.");
                return;
            }
        }
        for (int i = 0; i < table.getItems().length; i++) {
            client.getItems().deleteItem(table.getItems()[i], client.getItems().getItemSlot(table.getItems()[i]), 1);
        }
        client.getItems().addItem(table.getSetId(), 1);
        client.sendMessage(
            "You combine the armour pieces into a @blu@" + client.getItems().getItemName(table.getSetId()) + "@bla@.");
    }

    public static void exchange(Client client, int itemId) {
        ItemTable table = ItemTable.forId(itemId);
        if (table == null) {
            // SET DOES NOT EXIST ON ITEM TABLE
            return;
        }

        if (client.getItems().freeSlots() < table.getItems().length) {
            client.sendMessage("You need @blu@" + table.getItems().length + " @bla@available inventory slots to do this.");
            return;
        }

        client.getItems().deleteItem(itemId, client.getItems().getItemSlot(itemId), 1);

        for (int index = 0; index < table.getItems().length; index++) {
            client.getItems().addItem(table.getItems()[index], table.getAmounts()[index]);
        }

        client.sendMessage("You exchange the " + ItemDef.forId(itemId).getName() + ".");
    }

    private enum ItemTable2 {

        DHAROKS(new int[] { 4716, 4718, 4720, 4722 }, 12877), GUTHANS(new int[] { 4724, 4726, 4728, 4730 }, 12873), VERAC(
            new int[] { 4753, 4755, 4757, 4759 }, 12875), TORAG(new int[] { 4745, 4747, 4749, 4751 }, 12879), AHRIMS(
            new int[] { 4708, 4710, 4712, 4714 }, 12881), KARIL(new int[] { 4732, 4734, 4736, 4738 }, 12883);

        private ItemTable2(int[] items, int items2) {
            this.setId = items2;
            this.items = items;
        }

        private int setId;
        private int[] items;

        public int[] getItems() {
            return this.items;
        }

        public int getSetId() {
            return this.setId;
        }

        private static HashMap<Integer, ItemTable2> itemMap = new HashMap<Integer, ItemTable2>();

        public static ItemTable2 forId(int itemId) {
            return itemMap.get(itemId);
        }

        static {
            for (ItemTable2 t : ItemTable2.values()) {
                for (int i = 0; i < t.getItems().length; i++) {
                    itemMap.put(t.getItems()[i], t);
                }
            }
        }
    }

    private enum ItemTable {

        DHAROKS_ARMOUR_SET(12877, new int[] { 4716, 4718, 4720, 4722 }, new int[] { 1, 1, 1, 1 }), GUTHANS_ARMOUR_SET(12873,
            new int[] { 4724, 4726, 4728, 4730 }, new int[] { 1, 1, 1, 1 }), VERACS_ARMOUR_SET(12875,
            new int[] { 4753, 4755, 4757, 4759 }, new int[] { 1, 1, 1, 1 }), TORAGS_ARMOUR_SET(12879,
            new int[] { 4745, 4747, 4749, 4751 }, new int[] { 1, 1, 1, 1 }), AHRIMS_ARMOUR_SET(12881,
            new int[] { 4708, 4710, 4712, 4714 }, new int[] { 1, 1, 1, 1 }), KARILS_ARMOUR_SET(12883,
            new int[] { 4732, 4734, 4736, 4738, 4740 }, new int[] { 1, 1, 1, 1, 500 });

        private ItemTable(int setId, int[] items, int[] amounts) {
            this.setId = setId;
            this.items = items;
            this.amounts = amounts;
        }

        private int setId;
        private int[] items;
        private int[] amounts;

        public int[] getItems() {
            return this.items;
        }

        public int[] getAmounts() {
            return this.amounts;
        }

        private static HashMap<Integer, ItemTable> itemMap = new HashMap<Integer, ItemTable>();

        public static ItemTable forId(int itemId) {
            return itemMap.get(itemId);
        }

        static {
            for (ItemTable t : ItemTable.values()) {
                itemMap.put(t.setId, t);
            }
        }
    }

}
