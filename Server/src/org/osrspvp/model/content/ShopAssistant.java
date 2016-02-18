package org.osrspvp.model.content;

import org.osrspvp.Config;
import org.osrspvp.model.Client;
import org.osrspvp.model.item.Item;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;
import org.osrspvp.world.ItemHandler;
import org.osrspvp.world.ShopHandler;

public class ShopAssistant {

    private Client c;

    public ShopAssistant(Client client) {
        this.c = client;
    }

    public boolean isAlchable(int itemID) {
        for (int i = 0; i < ShopHandler.ShopName.length; i++) {
            if (itemID == (ShopHandler.ShopItems[i][i] - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean shopSellsItem(int itemID) {
        for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
            if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shops
     **/

    public void openShop(int ShopID) {
        c.getItems().resetItems(3823);
        resetShop(ShopID);
        c.isShopping = true;
        c.myShopId = ShopID;
        c.getPA().sendFrame248(3824, 3822);
        c.getPA().sendFrame126(ShopHandler.ShopName[ShopID], 3901);
    }

    public void updatePlayerShop() {
        for (int i = 1; i < Config.MAX_PLAYERS; i++) {
            if (PlayerHandler.players[i] != null) {
                if (PlayerHandler.players[i].isShopping == true && PlayerHandler.players[i].myShopId == c.myShopId && i != c.playerId) {
                    PlayerHandler.players[i].updateShop = true;
                }
            }
        }
    }

    public void updateshop(int i) {
        resetShop(i);
    }

    public void resetShop(int ShopID) {
        int TotalItems = 0;
        for (int i = 0; i < ShopHandler.MaxShopItems; i++) {
            if (ShopHandler.ShopItems[ShopID][i] > 0) {
                TotalItems++;
            }
        }
        if (TotalItems > ShopHandler.MaxShopItems) {
            TotalItems = ShopHandler.MaxShopItems;
        }
        c.getOutStream().createFrameVarSizeWord(53);
        c.getOutStream().writeWord(3900);
        c.getOutStream().writeWord(TotalItems);
        int TotalCount = 0;
        for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
            if (ShopHandler.ShopItems[ShopID][i] > 0 || i <= ShopHandler.ShopItemsStandard[ShopID]) {
                if (ShopHandler.ShopItemsN[ShopID][i] > 254) {
                    c.getOutStream().writeByte(255);
                    c.getOutStream().writeDWord_v2(0);
                } else {
                    c.getOutStream().writeByte(ShopHandler.ShopItemsN[ShopID][i]);
                }
                if (ShopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT || ShopHandler.ShopItems[ShopID][i] < 0) {
                    ShopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
                }
                c.getOutStream().writeWordBigEndianA(ShopHandler.ShopItems[ShopID][i]);
                TotalCount++;
            }
            if (TotalCount > TotalItems) {
                break;
            }
        }
        c.getOutStream().endFrameVarSizeWord();
        c.flushOutStream();
    }

    public double getItemShopValue(int ItemID, int Type, int fromSlot) {
        double ShopValue = 1;
        double TotPrice = 0;
        for (int i = 0; i < Config.ITEM_LIMIT; i++) {
            if (ItemHandler.ItemList[i] != null) {
                if (ItemHandler.ItemList[i].itemId == ItemID) {
                    ShopValue = ItemHandler.ItemList[i].ShopValue;
                }
            }
        }

        TotPrice = ShopValue;

        if (ShopHandler.ShopBModifier[c.myShopId] == 1) {
            TotPrice *= 1;
            TotPrice *= 1;
            if (Type == 1) {
                TotPrice *= 1;
            }
        } else if (Type == 1) {
            TotPrice *= 1;
        }
        return TotPrice;
    }

    public int getItemShopValue(int itemId) {
        for (int i = 0; i < Config.ITEM_LIMIT; i++) {
            if (ItemHandler.ItemList[i] != null) {
                if (itemId > 0)
                    itemId = Item.itemIsNote[itemId] ? itemId - 1 : itemId;
                if (ItemHandler.ItemList[i].itemId == itemId) {
                    return (int) ItemHandler.ItemList[i].ShopValue;
                }
            }
        }
        return 0;
    }

    /**
     * buy item from shop (Shop Price)
     **/

    public void buyFromShopPrice(int removeId, int removeSlot) {
        int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0, removeSlot));
        ShopValue *= 1.15;
        String ShopAdd = "";
        if (c.myShopId == 6 || c.myShopId == 10) {
            c.sendMessage(c.getItems().getItemName(removeId) + ": costs @blu@" + Misc
                .formatNumbers(getSpecialItemValue(removeId)) + "@bla@ coins.");
            return;
        }
        if (c.myShopId == 7) {
            c.sendMessage(c.getItems().getItemName(removeId) + ": costs @blu@" + Misc
                .formatNumbers(getSpecialItemValue(removeId)) + "@bla@ PVP Points.");
            return;
        }
        if (c.myShopId == 8) {
            c.sendMessage(c.getItems().getItemName(removeId) + ": costs @blu@" + Misc
                .formatNumbers(getSpecialItemValue(removeId)) + "@bla@ Donator Points.");
            return;
        }
        if (ShopValue >= 1000 && ShopValue < 1000000) {
            ShopAdd = " (" + (ShopValue / 1000) + "K)";
        } else if (ShopValue >= 1000000) {
            ShopAdd = " (" + (ShopValue / 1000000) + " million)";
        }
        c.sendMessage(ItemDef.forId(Item.itemIsNote[removeId] ? removeId - 1 : removeId).getName() + ": is @blu@free@bla@.");
    }

    public int getSpecialItemValue(int id) {
        if (c.myShopId == 7) {
            if (id >= 6731 && id <= 6737) {
                return 25;
            }
            if (id == 12954 || id == 13124) {
                return 35;
            }
            if (id == 10548) {
                return 40;
            }
            if (id == 10551) {
                return 50;
            }
            if (id >= 11663 && id <= 11665 || id == 8842) {
                return 55;
            }
            if (id == 6570) {
                return 65;
            }
            if (id == 8839 || id == 8840) {
                return 35;
            }
            if (id >= 12757 && id <= 12763 || id == 12849) {
                return 20;
            }
            return 0;
        }
        if (c.myShopId == 8) {
            if (id == 6199) {
                return 1;
            }
            if (id == 12821) {
                return 15;
            }
            if (id == 12817) {
                return 16;
            }
            if (id == 12825) {
                return 18;
            }
            if (id == 13205) {
                return 12;
            }
            if (id == 12006) {
                return 23;
            }
            if (id == 12807) {
                return 10;
            }
            if (id == 12926) {
                return 26;
            }
            if (id == 11785) {
                return 20;
            }
            if (id == 11802) {
                return 1925;
            }
            if (id == 13197) {
                return 25;
            }
            if (id == 12931) {
                return 8;
            }
            if (id == 13199) {
                return 7;
            }
            if (id == 13211) {
                return 9;
            }
            if (id == 13213) {
                return 10;
            }
            if (id == 13215) {
                return 11;
            }
            if (id == 12426) {
                return 12;
            }
            if (id == 12424) {
                return 21;
            }
            if (id == 12422) {
                return 20;
            }
            if (id == 10352) {
                return 28;
            }
            if (id == 10350) {
                return 25;
            }
            if (id == 10348) {
                return 30;
            }
            if (id == 10346) {
                return 30;
            }
            if (id == 10344) {
                return 30;
            }
            if (id == 10342) {
                return 20;
            }
            if (id == 10338) {
                return 30;
            }
            if (id == 10340) {
                return 30;
            }
            if (id == 10336) {
                return 30;
            }
            if (id == 10334) {
                return 20;
            }
            if (id == 10330) {
                return 30;
            }
            if (id == 10332) {
                return 30;
            }
            if (id == 12437) {
                return 12;
            }
            if (id == 11832) {
                return 24;
            }
            if (id == 11834) {
                return 24;
            }
            if (id == 11836) {
                return 20;
            }
            if (id == 11826) {
                return 19;
            }
            if (id == 11828) {
                return 19;
            }
            if (id == 11830) {
                return 19;
            }
            if (id == 1050) {
                return 13;
            }
            if (id == 11802) {
                return 45;
            }
            if (id == 1038) {
                return 17;
            }
            if (id == 12954 || id == 13124) {
                return 35;
            }
            if (id == 10548) {
                return 40;
            }
            if (id == 10551) {
                return 50;
            }
            if (id >= 11663 && id <= 11665 || id == 8842) {
                return 55;
            }
            if (id == 6570) {
                return 65;
            }
            if (id == 11802) {
                return 45;
            }
            if (id == 1038) {
                return 23;
            }
            if (id == 1040) {
                return 23;
            }
            if (id == 1042) {
                return 22;
            }
            if (id == 1044) {
                return 22;
            }
            if (id == 1046) {
                return 22;
            }
            if (id == 1048) {
                return 21;
            }
            if (id == 11862) {
                return 24;
            }
            if (id == 11863) {
                return 28;
            }
            if (id == 1055) {
                return 19;
            }
            if (id == 1057) {
                return 19;
            }
            if (id == 1053) {
                return 19;
            }
            if (id == 8839 || id == 8840) {
                return 35;
            }
            if (id == 12757 || id == 12763 || id == 12849) {
                return 20;
            }
            return 0;
        } 
        {
if (c.myShopId == 11) {
            if (id == 6199) {
                return 1;
            }
            if (id == 12821) {
                return 15;
            }
            if (id == 12817) {
                return 16;
            }
            if (id == 12825) {
                return 18;
            }
            if (id == 13205) {
                return 12;
            }
            if (id == 12006) {
                return 23;
            }
            if (id == 12807) {
                return 10;
            }
            if (id == 12926) {
                return 26;
            }
            if (id == 11785) {
                return 20;
            }
            if (id == 11802) {
                return 1925;
            }
            if (id == 13197) {
                return 25;
            }
            if (id == 12931) {
                return 8;
            }
            if (id == 13199) {
                return 7;
            }
            if (id == 13211) {
                return 9;
            }
            if (id == 13213) {
                return 10;
            }
            if (id == 13215) {
                return 11;
            }
            if (id == 12426) {
                return 12;
            }
            if (id == 12424) {
                return 21;
            }
            if (id == 12422) {
                return 20;
            }
            if (id == 10352) {
                return 28;
            }
            if (id == 10350) {
                return 25;
            }
            if (id == 10348) {
                return 30;
            }
            if (id == 10346) {
                return 30;
            }
            if (id == 10344) {
                return 30;
            }
            if (id == 10342) {
                return 20;
            }
            if (id == 10338) {
                return 30;
            }
            if (id == 10340) {
                return 30;
            }
            if (id == 10336) {
                return 30;
            }
            if (id == 10334) {
                return 20;
            }
            if (id == 10330) {
                return 30;
            }
            if (id == 10332) {
                return 30;
            }
            if (id == 12437) {
                return 12;
            }
            if (id == 11832) {
                return 24;
            }
            if (id == 11834) {
                return 24;
            }
            if (id == 11836) {
                return 20;
            }
            if (id == 11826) {
                return 19;
            }
            if (id == 11828) {
                return 19;
            }
            if (id == 11830) {
                return 19;
            }
            if (id == 1050) {
                return 13;
            }
            if (id == 11802) {
                return 45;
            }
            if (id == 1038) {
                return 17;
            }
            if (id == 12954 || id == 13124) {
                return 35;
            }
            if (id == 10548) {
                return 40;
            }
            if (id == 10551) {
                return 50;
            }
            if (id >= 11663 && id <= 11665 || id == 8842) {
                return 55;
            }
            if (id == 6570) {
                return 65;
            }
            if (id == 11802) {
                return 45;
            }
            if (id == 1038) {
                return 23;
            }
            if (id == 1040) {
                return 23;
            }
            if (id == 1042) {
                return 22;
            }
            if (id == 1044) {
                return 22;
            }
            if (id == 1046) {
                return 22;
            }
            if (id == 1048) {
                return 21;
            }
            if (id == 11862) {
                return 24;
            }
            if (id == 11863) {
                return 28;
            }
            if (id == 1055) {
                return 19;
            }
            if (id == 1057) {
                return 19;
            }
            if (id == 1053) {
                return 19;
            }
            if (id == 8839 || id == 8840) {
                return 35;
            }
            if (id == 12757 || id == 12763 || id == 12849) {
                return 20;
            }
            return 0;
        }
        }
        {
        if (c.myShopId == 10) {
            if (id == 8013) {
                return 100000;
            }
        }
        switch (id) {
        case 12954:
            return 2000000;

        case 12809:
            return 2600000;

        case 11770:
        case 11771:
        case 11772:
        case 11773:
            return 2500000;

        case 12931:
            return 20000000;

        case 12006:
            return 3000000;

        case 12853:
            return 2500000;

        case 12926:
            return 10000000;

        case 13199:
        case 13197:
            return 25000000;

        default:
            return c.getShops().getItemShopValue(id);
        }
        }
    }

    /**
     * Sell item to shop (Shop Price)
     **/
    public void sellToShopPrice(int removeId, int removeSlot) {
        for (int i : Config.ITEM_SELLABLE) {
            if (i == removeId) {
                c.sendMessage("You can't sell " + c.getItems().getItemName(removeId).toLowerCase() + ".");
                return;
            }
        }
        boolean IsIn = false;
        if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
            for (int j = 0; j <= ShopHandler.ShopItemsStandard[c.myShopId]; j++) {
                if (removeId == (ShopHandler.ShopItems[c.myShopId][j] - 1)) {
                    IsIn = true;
                    break;
                }
            }
        } else {
            IsIn = true;
        }
        if (IsIn == false) {
            c.sendMessage("You can't sell " + c.getItems().getItemName(removeId).toLowerCase() + " to this store.");
        } else {
            int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1, removeSlot));
            String ShopAdd = "";
            if (ShopValue >= 1000 && ShopValue < 1000000) {
                ShopAdd = " (" + (ShopValue / 1000) + "K)";
            } else if (ShopValue >= 1000000) {
                ShopAdd = " (" + (ShopValue / 1000000) + " million)";
            }
            if (c.myShopId == 10 || c.myShopId == 6) {
                c.sendMessage(c.getItems().getItemName(removeId) + ": shop will buy for @blu@" + Misc
                    .formatNumbers((int) (c.getShops().getSpecialItemValue(removeId) * .50)) + "@bla@ coins.");
                return;
            }
            c.sendMessage(c.getItems().getItemName(removeId) + ": shop will buy for " + ShopValue + " coins" + ShopAdd);
        }
    }

    public boolean sellItem(int itemID, int fromSlot, int amount) {
        if (!c.isShopping)
            return false;
        if (c.myShopId == 8)
        		return false;
        if (c.myShopId == 11)
    		return false;
        if (c.myShopId == 7)
    		return false;
        for (int i : Config.ITEM_SELLABLE) {
            if (i == itemID) {
                c.sendMessage("You can't sell " + c.getItems().getItemName(itemID).toLowerCase() + ".");
                return false;
            }
        }
        if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
            if (ShopHandler.ShopSModifier[c.myShopId] > 1) {
                boolean IsIn = false;
                for (int i = 0; i <= ShopHandler.ShopItemsStandard[c.myShopId]; i++) {
                    if (itemID == (ShopHandler.ShopItems[c.myShopId][i] - 1)) {
                        IsIn = true;
                        break;
                    }
                }
                if (IsIn == false) {
                    c.sendMessage("You can't sell " + c.getItems().getItemName(itemID).toLowerCase() + " to this store.");
                    return false;
                }
            }

            if (amount > c.playerItemsN[fromSlot] && (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
                amount = c.playerItemsN[fromSlot];
            } else if (amount > c.getItems().getItemAmount(
                itemID) && Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false && Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
                amount = c.getItems().getItemAmount(itemID);
            }
            // double ShopValue;
            // double TotPrice;
            int TotPrice2 = 0;
            // int Overstock;
            for (int i = amount; i > 0; i--) {
                TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1, fromSlot));
                if (c.getItems().freeSlots() > 0 || c.getItems().playerHasItem(995)) {
                    if (Item.itemIsNote[itemID] == false) {
                        c.getItems().deleteItem(itemID, c.getItems().getItemSlot(itemID), 1);
                    } else {
                        c.getItems().deleteItem(itemID, fromSlot, 1);
                    }
                    if (c.myShopId == 6 || c.myShopId == 10 || c.myShopId == 8 || c.myShopId == 11) {
                        if (c.myShopId == 6 || c.myShopId == 10) {
                            c.getItems().addItem(995, (int) (c.getShops().getSpecialItemValue(itemID) * .50D));
                        }
                    }
                } else {
                    c.sendMessage("You don't have enough space in your inventory.");
                    break;
                }
            }
            c.getItems().resetItems(3823);
            resetShop(c.myShopId);
            updatePlayerShop();
            return true;
        }
        return true;
    }

    public boolean addShopItem(int itemID, int amount) {
        boolean Added = false;
        if (amount <= 0) {
            return false;
        }
        if (Item.itemIsNote[itemID] == true) {
            itemID = c.getItems().getUnnotedItem(itemID);
        }
        for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
            if ((ShopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
                ShopHandler.ShopItemsN[c.myShopId][i] += amount;
                Added = true;
            }
        }
        if (Added == false) {
            for (int i = 0; i < ShopHandler.ShopItems.length; i++) {
                if (ShopHandler.ShopItems[c.myShopId][i] == 0) {
                    ShopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
                    ShopHandler.ShopItemsN[c.myShopId][i] = amount;
                    ShopHandler.ShopItemsDelay[c.myShopId][i] = 0;
                    break;
                }
            }
        }
        return true;
    }

    public boolean buyItem(int itemID, int fromSlot, int amount) {
        if (!shopSellsItem(itemID) || !c.isShopping)
            return false;
        if (amount > 0) {
            if (amount > ShopHandler.ShopItemsN[c.myShopId][fromSlot]) {
                amount = ShopHandler.ShopItemsN[c.myShopId][fromSlot];
            }
            // double ShopValue;
            // double TotPrice;
            int TotPrice2 = 0;
            // int Overstock;
            int Slot = 0;
            int Slot1 = 0;// Tokkul
            if (c.myShopId == 6 || c.myShopId == 8 || c.myShopId == 7 || c.myShopId == 10) {
                c.getShops().handleOtherShop(itemID);
                return false;
            }
            for (int i = amount; i > 0; i--) {
                if (c.getItems().freeSlots() > 0) {
                    c.getItems().addItem(itemID, 1);
                } else {
                    c.sendMessage("You don't have enough space in your inventory.");
                    break;
                }
            }
            c.getItems().resetItems(3823);
            resetShop(c.myShopId);
            updatePlayerShop();
            return true;
        }
        return false;
    }

    public void handleOtherShop(int itemID) {
        if (c.myShopId == 6 || c.myShopId == 10) {
            if (c.getItems().getItemAmount(995) >= getSpecialItemValue(itemID)) {
                if (c.getItems().freeSlots() > 0) {
                    c.getItems().deleteItem(995, c.getItems().getItemSlot(995), getSpecialItemValue(itemID));
                    c.getItems().addItem(itemID, 1);
                    c.getItems().resetItems(3823);
                }
            } else {
                c.sendMessage("You do not have enough coins to buy this item.");
            }
        }
        if (c.myShopId == 7) {
            if (c.pkPoints >= getSpecialItemValue(itemID)) {
                if (c.getItems().freeSlots() > 0) {
                    c.pkPoints -= getSpecialItemValue(itemID);
                    c.getItems().addItem(itemID, 1);
                    c.getItems().resetItems(3823);
                }
            } else {
                c.sendMessage("You do not have enough PVP Points to buy this item.");
            }
        }
	if (c.myShopId == 8) {
            if (c.donatorPoints >= getSpecialItemValue(itemID)) {
                if (c.getItems().freeSlots() > 0) {
                    c.donatorPoints -= getSpecialItemValue(itemID);
                    c.getItems().addItem(itemID, 1);
                    c.getItems().resetItems(3823);
                }
            } else {
                c.sendMessage("You do not have enough Donator Points to buy this item.");
            }
        }
    }

    public void openSkillCape() {
        int capes = get99Count();
        if (capes > 1)
            capes = 1;
        else
            capes = 0;
        c.myShopId = 14;
        setupSkillCapes(capes, get99Count());
    }

    /*
     * public int[][] skillCapes =
     * {{0,9747,4319,2679},{1,2683,4329,2685},{2,2680
     * ,4359,2682},{3,2701,4341,2703
     * },{4,2686,4351,2688},{5,2689,4347,2691},{6,2692,4343,2691},
     * {7,2737,4325,2733
     * },{8,2734,4353,2736},{9,2716,4337,2718},{10,2728,4335,2730
     * },{11,2695,4321,2697},{12,2713,4327,2715},{13,2725,4357,2727},
     * {14,2722,4345
     * ,2724},{15,2707,4339,2709},{16,2704,4317,2706},{17,2710,4361,
     * 2712},{18,2719,4355,2721},{19,2737,4331,2739},{20,2698,4333,2700}};
     */
    public int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801, 9807, 9783, 9798, 9804, 9780, 9795, 9792,
        9774, 9771, 9777, 9786, 9810, 9765 };

    public int get99Count() {
        int count = 0;
        for (int j = 0; j < c.getLevel().length; j++) {
            if (c.getLevelForXP(c.getExperience()[j]) >= 99) {
                count++;
            }
        }
        return count;
    }

    public void setupSkillCapes(int capes, int capes2) {
        c.getItems().resetItems(3823);
        c.isShopping = true;
        c.myShopId = 14;
        c.getPA().sendFrame248(3824, 3822);
        c.getPA().sendFrame126("Skillcape Shop", 3901);

        int TotalItems = 0;
        TotalItems = capes2;
        if (TotalItems > ShopHandler.MaxShopItems) {
            TotalItems = ShopHandler.MaxShopItems;
        }
        c.getOutStream().createFrameVarSizeWord(53);
        c.getOutStream().writeWord(3900);
        c.getOutStream().writeWord(TotalItems);
        for (int i = 0; i < 21; i++) {
            if (c.getLevelForXP(c.getExperience()[i]) < 99)
                continue;
            c.getOutStream().writeByte(1);
            c.getOutStream().writeWordBigEndianA(skillCapes[i] + 2);
        }
        c.getOutStream().endFrameVarSizeWord();
        c.flushOutStream();
    }

    public void skillBuy(int item) {
        int nn = get99Count();
        if (nn > 1)
            nn = 1;
        else
            nn = 0;
        for (int j = 0; j < skillCapes.length; j++) {
            if (skillCapes[j] == item || skillCapes[j] + 1 == item) {
                if (c.getItems().freeSlots() > 1) {
                    if (c.getItems().playerHasItem(995, 99000)) {
                        if (c.getLevelForXP(c.getExperience()[j]) >= 99) {
                            c.getItems().deleteItem(995, c.getItems().getItemSlot(995), 99000);
                            c.getItems().addItem(skillCapes[j] + nn, 1);
                            c.getItems().addItem(skillCapes[j] + 2, 1);
                        } else {
                            c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
                        }
                    } else {
                        c.sendMessage("You need 99k to buy this item.");
                    }
                } else {
                    c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
                }
            }
        }
        c.getItems().resetItems(3823);
    }

    public void openVoid() {
    }

    public void buyVoid(int item) {
    }

}
