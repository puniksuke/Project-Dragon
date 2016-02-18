package org.osrspvp.model.content;

import org.osrspvp.Config;
import org.osrspvp.model.Client;
import org.osrspvp.model.item.Item;
import org.osrspvp.util.cache.defs.ItemDef;

public class BankHandler {

    Client client;

    public BankHandler(Client client) {
        this.client = client;
    }

    public void swapBankItem(int from, int to) {
        int tempI = client.bankItems[from];
        int tempN = client.bankItemsN[from];
        client.bankItems[from] = client.bankItems[to];
        client.bankItemsN[from] = client.bankItemsN[to];
        client.bankItems[to] = tempI;
        client.bankItemsN[to] = tempN;
    }

    public void fromBank(int itemID, int fromSlot, int amount) {
        if (!client.isBanking) {
            return;
        }
        if (amount > 0) {
            if (client.bankItems[fromSlot] > 0) {
                if (!client.takeAsNote) {
                    if (ItemDef.isStackable(client.bankItems[fromSlot] - 1)) {
                        if (client.bankItemsN[fromSlot] > amount) {
                            if (client.getItems().addItem((client.bankItems[fromSlot] - 1), amount)) {
                                client.bankItemsN[fromSlot] -= amount;
                                client.getBankHandler().sendBankReset();
                                client.getItems().resetItems(5064);
                            }
                        } else {
                            if (client.getItems().addItem((client.bankItems[fromSlot] - 1), client.bankItemsN[fromSlot])) {
                                client.bankItems[fromSlot] = 0;
                                client.bankItemsN[fromSlot] = 0;
                                client.getBankHandler().sendBankReset();
                                client.getItems().resetItems(5064);
                            }
                        }
                    } else {
                        while (amount > 0) {
                            if (client.bankItemsN[fromSlot] > 0) {
                                if (client.getItems().addItem((client.bankItems[fromSlot] - 1), 1)) {
                                    client.bankItemsN[fromSlot] += -1;
                                    amount--;
                                } else {
                                    amount = 0;
                                }
                            } else {
                                amount = 0;
                            }
                        }
                        client.getBankHandler().sendBankReset();
                        client.getItems().resetItems(5064);
                    }
                } else if (client.takeAsNote && Item.itemIsNote[client.bankItems[fromSlot]]) {
                    if (client.bankItemsN[fromSlot] > amount) {
                        if (client.getItems().addItem(client.bankItems[fromSlot], amount)) {
                            client.bankItemsN[fromSlot] -= amount;
                            client.getBankHandler().sendBankReset();
                            client.getItems().resetItems(5064);
                        }
                    } else {
                        if (client.getItems().addItem(client.bankItems[fromSlot], client.bankItemsN[fromSlot])) {
                            client.bankItems[fromSlot] = 0;
                            client.bankItemsN[fromSlot] = 0;
                            client.getBankHandler().sendBankReset();
                            client.getItems().resetItems(5064);
                        }
                    }
                } else {
                    client.sendMessage("Item can't be drawn as note.");
                    if (ItemDef.isStackable(client.bankItems[fromSlot] - 1)) {
                        if (client.bankItemsN[fromSlot] > amount) {
                            if (client.getItems().addItem((client.bankItems[fromSlot] - 1), amount)) {
                                client.bankItemsN[fromSlot] -= amount;
                                client.getBankHandler().sendBankReset();
                                client.getItems().resetItems(5064);
                            }
                        } else {
                            if (client.getItems().addItem((client.bankItems[fromSlot] - 1), client.bankItemsN[fromSlot])) {
                                client.bankItems[fromSlot] = 0;
                                client.bankItemsN[fromSlot] = 0;
                                client.getBankHandler().sendBankReset();
                                client.getItems().resetItems(5064);
                            }
                        }
                    } else {
                        while (amount > 0) {
                            if (client.bankItemsN[fromSlot] > 0) {
                                if (client.getItems().addItem((client.bankItems[fromSlot] - 1), 1)) {
                                    client.bankItemsN[fromSlot] += -1;
                                    amount--;
                                } else {
                                    amount = 0;
                                }
                            } else {
                                amount = 0;
                            }
                        }
                        client.getBankHandler().sendBankReset();
                        client.getItems().resetItems(5064);
                    }
                }
            }
        }
    }

    public boolean bankItem(int itemID, int fromSlot, int amount) {
        if (!client.isBanking) {
            return false;
        }
        if (client.playerItemsN[fromSlot] <= 0) {
            return false;
        }
        if (!Item.itemIsNote[client.playerItems[fromSlot] - 1]) {
            if (client.playerItems[fromSlot] <= 0) {
                return false;
            }
            if (ItemDef.isStackable(client.playerItems[fromSlot] - 1) || client.playerItemsN[fromSlot] > 1) {
                int toBankSlot = 0;
                boolean alreadyInBank = false;
                for (int i = 0; i < Config.BANK_SIZE; i++) {
                    if (client.bankItems[i] == client.playerItems[fromSlot]) {
                        if (client.playerItemsN[fromSlot] < amount)
                            amount = client.playerItemsN[fromSlot];
                        alreadyInBank = true;
                        toBankSlot = i;
                        i = Config.BANK_SIZE + 1;
                    }
                }

                if (!alreadyInBank && client.getBankHandler().freeBankSlots() > 0) {
                    for (int i = 0; i < Config.BANK_SIZE; i++) {
                        if (client.bankItems[i] <= 0) {
                            toBankSlot = i;
                            i = Config.BANK_SIZE + 1;
                        }
                    }
                    client.bankItems[toBankSlot] = client.playerItems[fromSlot];
                    if (client.playerItemsN[fromSlot] < amount) {
                        amount = client.playerItemsN[fromSlot];
                    }
                    if ((client.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT && (client.bankItemsN[toBankSlot] + amount) > -1) {
                        client.bankItemsN[toBankSlot] += amount;
                    } else {
                        client.sendMessage("Bank full!");
                        return false;
                    }
                    client.getItems().deleteItem((client.playerItems[fromSlot] - 1), fromSlot, amount);
                    client.updateBankItems = true;
                    return true;
                } else if (alreadyInBank) {
                    if ((client.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT && (client.bankItemsN[toBankSlot] + amount) > -1) {
                        client.bankItemsN[toBankSlot] += amount;
                    } else {
                        client.sendMessage("Bank full!");
                        return false;
                    }
                    client.getItems().deleteItem((client.playerItems[fromSlot] - 1), fromSlot, amount);
                    client.updateBankItems = true;
                    return true;
                } else {
                    client.sendMessage("Bank full!");
                    return false;
                }
            } else {
                itemID = client.playerItems[fromSlot];
                int toBankSlot = 0;
                boolean alreadyInBank = false;
                for (int i = 0; i < Config.BANK_SIZE; i++) {
                    if (client.bankItems[i] == client.playerItems[fromSlot]) {
                        alreadyInBank = true;
                        toBankSlot = i;
                        i = Config.BANK_SIZE + 1;
                    }
                }
                if (!alreadyInBank && client.getBankHandler().freeBankSlots() > 0) {
                    for (int i = 0; i < Config.BANK_SIZE; i++) {
                        if (client.bankItems[i] <= 0) {
                            toBankSlot = i;
                            i = Config.BANK_SIZE + 1;
                        }
                    }
                    int firstPossibleSlot = 0;
                    boolean itemExists = false;
                    while (amount > 0) {
                        itemExists = false;
                        for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
                            if ((client.playerItems[i]) == itemID) {
                                firstPossibleSlot = i;
                                itemExists = true;
                                i = 30;
                            }
                        }
                        if (itemExists) {
                            client.bankItems[toBankSlot] = client.playerItems[firstPossibleSlot];
                            client.bankItemsN[toBankSlot] += 1;
                            client.getItems().deleteItem((client.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
                            amount--;
                        } else {
                            amount = 0;
                        }
                    }
                    client.updateBankItems = true;
                    return true;
                } else if (alreadyInBank) {
                    int firstPossibleSlot = 0;
                    boolean itemExists = false;
                    while (amount > 0) {
                        itemExists = false;
                        for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
                            if ((client.playerItems[i]) == itemID) {
                                firstPossibleSlot = i;
                                itemExists = true;
                                i = 30;
                            }
                        }
                        if (itemExists) {
                            client.bankItemsN[toBankSlot] += 1;
                            client.getItems().deleteItem((client.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
                            amount--;
                        } else {
                            amount = 0;
                        }
                    }
                    client.updateBankItems = true;
                    return true;
                } else {
                    client.sendMessage("Bank full!");
                    return false;
                }
            }
        } else if (Item.itemIsNote[client.playerItems[fromSlot] - 1] && !Item.itemIsNote[client.playerItems[fromSlot] - 2]) {
            if (client.playerItems[fromSlot] <= 0) {
                return false;
            }
            if (ItemDef.isStackable(client.playerItems[fromSlot] - 1) || client.playerItemsN[fromSlot] > 1) {
                int toBankSlot = 0;
                boolean alreadyInBank = false;
                for (int i = 0; i < Config.BANK_SIZE; i++) {
                    if (client.bankItems[i] == (client.playerItems[fromSlot] - 1)) {
                        if (client.playerItemsN[fromSlot] < amount)
                            amount = client.playerItemsN[fromSlot];
                        alreadyInBank = true;
                        toBankSlot = i;
                        i = Config.BANK_SIZE + 1;
                    }
                }

                if (!alreadyInBank && client.getBankHandler().freeBankSlots() > 0) {
                    for (int i = 0; i < Config.BANK_SIZE; i++) {
                        if (client.bankItems[i] <= 0) {
                            toBankSlot = i;
                            i = Config.BANK_SIZE + 1;
                        }
                    }
                    client.bankItems[toBankSlot] = (client.playerItems[fromSlot] - 1);
                    if (client.playerItemsN[fromSlot] < amount) {
                        amount = client.playerItemsN[fromSlot];
                    }
                    if ((client.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT && (client.bankItemsN[toBankSlot] + amount) > -1) {
                        client.bankItemsN[toBankSlot] += amount;
                    } else {
                        return false;
                    }
                    client.getItems().deleteItem((client.playerItems[fromSlot] - 1), fromSlot, amount);
                    client.updateBankItems = true;
                    return true;
                } else if (alreadyInBank) {
                    if ((client.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT && (client.bankItemsN[toBankSlot] + amount) > -1) {
                        client.bankItemsN[toBankSlot] += amount;
                    } else {
                        return false;
                    }
                    client.getItems().deleteItem((client.playerItems[fromSlot] - 1), fromSlot, amount);
                    client.updateBankItems = true;
                    return true;
                } else {
                    client.sendMessage("Bank full!");
                    return false;
                }
            } else {
                itemID = client.playerItems[fromSlot];
                int toBankSlot = 0;
                boolean alreadyInBank = false;
                for (int i = 0; i < Config.BANK_SIZE; i++) {
                    if (client.bankItems[i] == (client.playerItems[fromSlot] - 1)) {
                        alreadyInBank = true;
                        toBankSlot = i;
                        i = Config.BANK_SIZE + 1;
                    }
                }
                if (!alreadyInBank && client.getBankHandler().freeBankSlots() > 0) {
                    for (int i = 0; i < Config.BANK_SIZE; i++) {
                        if (client.bankItems[i] <= 0) {
                            toBankSlot = i;
                            i = Config.BANK_SIZE + 1;
                        }
                    }
                    int firstPossibleSlot = 0;
                    boolean itemExists = false;
                    while (amount > 0) {
                        itemExists = false;
                        for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
                            if ((client.playerItems[i]) == itemID) {
                                firstPossibleSlot = i;
                                itemExists = true;
                                i = 30;
                            }
                        }
                        if (itemExists) {
                            client.bankItems[toBankSlot] = (client.playerItems[firstPossibleSlot] - 1);
                            client.bankItemsN[toBankSlot] += 1;
                            client.getItems().deleteItem((client.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
                            amount--;
                        } else {
                            amount = 0;
                        }
                    }
                    client.updateBankItems = true;
                    return true;
                } else if (alreadyInBank) {
                    int firstPossibleSlot = 0;
                    boolean itemExists = false;
                    while (amount > 0) {
                        itemExists = false;
                        for (int i = firstPossibleSlot; i < client.playerItems.length; i++) {
                            if ((client.playerItems[i]) == itemID) {
                                firstPossibleSlot = i;
                                itemExists = true;
                                i = 30;
                            }
                        }
                        if (itemExists) {
                            client.bankItemsN[toBankSlot] += 1;
                            client.getItems().deleteItem((client.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
                            amount--;
                        } else {
                            amount = 0;
                        }
                    }
                    client.updateBankItems = true;
                    return true;
                } else {
                    client.sendMessage("Bank full!");
                    return false;
                }
            }
        } else {
            client.sendMessage("Item not supported " + (client.playerItems[fromSlot] - 1));
            return false;
        }
    }

    public void sendBankReset() {
        client.outStream.createFrameVarSizeWord(53);
        client.outStream.writeWord(5382); // bank
        client.outStream.writeWord(Config.BANK_SIZE);
        for (int i = 0; i < Config.BANK_SIZE; i++) {
            if (client.bankItemsN[i] > 254) {
                client.outStream.writeByte(255);
                client.outStream.writeDWord_v2(client.bankItemsN[i]);
            } else {
                client.outStream.writeByte(client.bankItemsN[i]); // amount
            }
            if (client.bankItemsN[i] < 1)
                client.bankItems[i] = 0;
            if (client.bankItems[i] > Config.ITEM_LIMIT || client.bankItems[i] < 0) {
                client.bankItems[i] = Config.ITEM_LIMIT;
            }
            client.outStream.writeWordBigEndianA(client.bankItems[i]); // itemID
        }
        client.outStream.endFrameVarSizeWord();
        client.flushOutStream();
    }

    public void sendReplacementOfTempItem() {
        int itemCount = 0;
        for (int i = 0; i < client.playerItems.length; i++) {
            if (client.playerItems[i] > -1) {
                itemCount = i;
            }
        }
        client.outStream.createFrameVarSizeWord(53);
        client.outStream.writeWord(5064); // inventory
        client.outStream.writeWord(itemCount + 1); // number of items
        for (int i = 0; i < itemCount + 1; i++) {
            if (client.playerItemsN[i] > 254) {
                client.outStream.writeByte(255);
                client.outStream.writeDWord_v2(client.playerItemsN[i]);
            } else {
                client.outStream.writeByte(client.playerItemsN[i]);
            }
            if (client.playerItems[i] > Config.ITEM_LIMIT || client.playerItems[i] < 0) {
                client.playerItems[i] = Config.ITEM_LIMIT;
            }
            client.outStream.writeWordBigEndianA(client.playerItems[i]); // item
        }

        client.outStream.endFrameVarSizeWord();
        client.flushOutStream();
    }

    public int freeBankSlots() {
        int freeS = 0;
        for (int i = 0; i < Config.BANK_SIZE; i++) {
            if (client.bankItems[i] <= 0) {
                freeS++;
            }
        }
        return freeS;
    }

}
