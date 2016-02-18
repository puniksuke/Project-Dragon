package org.osrspvp.model.item;

import org.osrspvp.Config;
import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.model.content.RewardHandler;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;
import org.osrspvp.world.ItemHandler;

public class ItemAssistant {

	private Client c;

	public ItemAssistant(Client client) {
		this.c = client;
	}

	/**
	 * Items
	 **/

	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 },
			{ 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 }, { 4720, 4896 },
			{ 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 },
			{ 4734, 4938 }, { 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 },
			{ 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 }, { 4745, 4956 },
			{ 4747, 4926 }, { 4749, 4968 }, { 4751, 4794 }, { 4753, 4980 },
			{ 4755, 4986 }, { 4757, 4992 }, { 4759, 4998 } };

	public void resetItems(int WriteFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(WriteFrame);
			c.getOutStream().writeWord(c.playerItems.length);
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.playerItemsN[i]);
				}
				c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public int getItemCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (c.playerItems[j] == itemID + 1) {
				count += c.playerItemsN[j];
			}
		}
		return count;
	}

	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < c.playerBonus.length; i++) {
			if (c.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + c.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -"
						+ java.lang.Math.abs(c.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			c.getPA().sendFrame126(send, (1675 + i + offset));
		}

	}

	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (Item.itemIsNote[itemID + 1]) {
				if (itemID + 2 == c.playerItems[j])
					count += c.playerItemsN[j];
			}
			if (!Item.itemIsNote[itemID + 1]) {
				if (itemID + 1 == c.playerItems[j]) {
					count += c.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] == itemID + 1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public void sendItemsKept() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(6963);
			c.getOutStream().writeWord(c.itemKeptId.length);
			for (int i = 0; i < c.itemKeptId.length; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(1);
				} else {
					c.getOutStream().writeByte(1);
				}
				if (c.itemKeptId[i] > 0) {
					c.getOutStream().writeWordBigEndianA(c.itemKeptId[i] + 1);
				} else {
					c.getOutStream().writeWordBigEndianA(0);
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	/**
	 * Item kept on death
	 **/

	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 > 0) {
				int inventoryItemValue = c.getShops().getItemShopValue(
						c.playerItems[i] - 1);
				if (inventoryItemValue > value && (!c.invSlot[i])) {
					value = inventoryItemValue;
					item = c.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < c.getEquipment().length; i1++) {
			if (c.getEquipment()[i1] > 0) {
				int equipmentItemValue = c.getShops().getItemShopValue(
						c.getEquipment()[i1]);
				if (equipmentItemValue > value && (!c.equipSlot[i1])) {
					value = equipmentItemValue;
					item = c.getEquipment()[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			c.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(c.playerItems[slotId] - 1,
						getItemSlot(c.playerItems[slotId] - 1), 1);
			}
		} else {
			c.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		c.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death
	 **/

	public void resetKeepItems() {
		for (int i = 0; i < c.itemKeptId.length; i++) {
			c.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < c.invSlot.length; i1++) {
			c.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < c.equipSlot.length; i2++) {
			c.equipSlot[i2] = false;
		}
	}

	/**
	 * delete all items
	 **/

	public void deleteAllItems() {
		for (int i1 = 0; i1 < c.getEquipment().length; i1++) {
			deleteEquipment(c.getEquipment()[i1], i1);
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			deleteItem(c.playerItems[i] - 1, getItemSlot(c.playerItems[i] - 1),
					c.playerItemsN[i]);
		}
	}

	/**
	 * Drop all items for your killer
	 **/

	public void dropAllItems() {
		Client o = (Client) PlayerHandler.players[c.killerId];
		int totalWealth = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(c.playerItems[i] - 1)) {
					totalWealth += c.getShops().getItemShopValue(
							c.playerItems[i] - 1);
					Server.itemHandler.createGroundItem(o,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.killerId);
				} else {
					if (specialCase(c.playerItems[i] - 1))
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(),
								getUntradePrice(c.playerItems[i] - 1),
								c.killerId);
					Server.itemHandler.createGroundItem(c,
							c.playerItems[i] - 1, c.getX(), c.getY(),
							c.playerItemsN[i], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerItems[i] - 1,
						c.getX(), c.getY(), c.playerItemsN[i], c.playerId);
			}
		}
		for (int e = 0; e < c.getEquipment().length; e++) {
			if (o != null) {
				if (tradeable(c.getEquipment()[e])) {
					totalWealth += c.getShops().getItemShopValue(
							c.getEquipment()[e]);
					Server.itemHandler.createGroundItem(o, c.getEquipment()[e],
							c.getX(), c.getY(), c.playerEquipmentN[e],
							c.killerId);
				} else {
					if (specialCase(c.getEquipment()[e]))
						Server.itemHandler.createGroundItem(o, 995, c.getX(),
								c.getY(), getUntradePrice(c.getEquipment()[e]),
								c.killerId);
					Server.itemHandler.createGroundItem(c, c.getEquipment()[e],
							c.getX(), c.getY(), c.playerEquipmentN[e],
							c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.getEquipment()[e],
						c.getX(), c.getY(), c.playerEquipmentN[e], c.playerId);
			}
		}
		if (o != null) {
			System.out.println("total wealth: " + totalWealth);
			RewardHandler.giveWealth(o, totalWealth, c);
			Server.itemHandler.createGroundItem(o, 526, c.getX(), c.getY(), 1,
					c.killerId);
		}
	}

	public int getUntradePrice(int item) {
		switch (item) {
		case 2518:
		case 2524:
		case 2526:
			return 100000;
		case 2520:
		case 2522:
			return 150000;
		}
		return 0;
	}

	public boolean specialCase(int itemId) {
		switch (itemId) {
		case 2518:
		case 2520:
		case 2522:
		case 2524:
		case 2526:
			return true;
		}
		return false;
	}

	public void handleSpecialPickup(int itemId) {
		// c.sendMessage("My " + getItemName(itemId) +
		// " has been recovered. I should talk to the void knights to get it back.");
		// c.getItems().addToVoidList(itemId);
	}

	public void addToVoidList(int itemId) {
		switch (itemId) {
		case 2518:
			c.voidStatus[0]++;
			break;
		case 2520:
			c.voidStatus[1]++;
			break;
		case 2522:
			c.voidStatus[2]++;
			break;
		case 2524:
			c.voidStatus[3]++;
			break;
		case 2526:
			c.voidStatus[4]++;
			break;
		}
	}

	public boolean tradeable(int itemId) {
		for (int j = 0; j < Config.ITEM_TRADEABLE.length; j++) {
			if (itemId == Config.ITEM_TRADEABLE[j])
				return false;
		}
		return true;
	}

	/**
	 * Add Item
	 **/
	public boolean addItem(int item, int amount) {
		// synchronized(c) {
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((((freeSlots() >= 1) || playerHasItem(item, 1)) && Item.itemStackable[item])
				|| ((freeSlots() > 0) && !Item.itemStackable[item])) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if ((c.playerItems[i] == (item + 1))
						&& Item.itemStackable[item] && (c.playerItems[i] > 0)) {
					c.playerItems[i] = (item + 1);
					if (((c.playerItemsN[i] + amount) < Config.MAXITEM_AMOUNT)
							&& ((c.playerItemsN[i] + amount) > -1)) {
						c.playerItemsN[i] += amount;
					} else {
						c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
					}
					if (c.getOutStream() != null && c != null) {
						c.getOutStream().createFrameVarSizeWord(34);
						c.getOutStream().writeWord(3214);
						c.getOutStream().writeByte(i);
						c.getOutStream().writeWord(c.playerItems[i]);
						if (c.playerItemsN[i] > 254) {
							c.getOutStream().writeByte(255);
							c.getOutStream().writeDWord(c.playerItemsN[i]);
						} else {
							c.getOutStream().writeByte(c.playerItemsN[i]);
						}
						c.getOutStream().endFrameVarSizeWord();
						c.flushOutStream();
					}
					resetItems(3214);
					i = 30;
					return true;
				}
			}
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] <= 0) {
					c.playerItems[i] = item + 1;
					if ((amount < Config.MAXITEM_AMOUNT) && (amount > -1)) {
						c.playerItemsN[i] = 1;
						if (amount > 1) {
							c.getItems().addItem(item, amount - 1);
							return true;
						}
					} else {
						c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
					}
					resetItems(3214);
					i = 30;
					return true;
				}
			}
			return false;
		} else {
			resetItems(3214);
			c.sendMessage("Not enough space in your inventory.");
			return false;
		}
	}

	public boolean addItem(int item, int itemSlot, int amount) {
		// synchronized(c) {
		if (amount < 1) {
			amount = 1;
		}
		if (item <= 0) {
			return false;
		}
		if ((((freeSlots() >= 1) || playerHasItem(item, 1)) && ItemDef
				.isStackable(item))
				|| ((freeSlots() > 0) && !ItemDef.isStackable(item))) {
			if ((c.playerItems[itemSlot] == (item + 1))
					&& ItemDef.isStackable(item)
					&& (c.playerItems[itemSlot] > 0)) {
				c.playerItems[itemSlot] = (item + 1);
				if (((c.playerItemsN[itemSlot] + amount) < Config.MAXITEM_AMOUNT)
						&& ((c.playerItemsN[itemSlot] + amount) > -1)) {
					c.playerItemsN[itemSlot] += amount;
				} else {
					c.playerItemsN[itemSlot] = Config.MAXITEM_AMOUNT;
				}
				if (c.getOutStream() != null && c != null) {
					c.getOutStream().createFrameVarSizeWord(34);
					c.getOutStream().writeWord(3214);
					c.getOutStream().writeByte(itemSlot);
					c.getOutStream().writeWord(c.playerItems[itemSlot]);
					if (c.playerItemsN[itemSlot] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord(c.playerItemsN[itemSlot]);
					} else {
						c.getOutStream().writeByte(c.playerItemsN[itemSlot]);
					}
					c.getOutStream().endFrameVarSizeWord();
					c.flushOutStream();
				}
				return true;
			}
			if (c.playerItems[itemSlot] <= 0) {
				c.playerItems[itemSlot] = item + 1;
				if ((amount < Config.MAXITEM_AMOUNT) && (amount > -1)) {
					c.playerItemsN[itemSlot] = 1;
					if (amount > 1) {
						c.getItems().addItem(item, itemSlot, amount - 1);
						return true;
					}
				} else {
					c.playerItemsN[itemSlot] = Config.MAXITEM_AMOUNT;
				}
				resetItems(3214);
				return true;
			}
			return false;
		} else {
			resetItems(3214);
			c.sendMessage("Not enough space in your inventory.");
			return false;
		}
	}

	public String itemType(int item) {
		for (int i = 0; i < Item.capes.length; i++) {
			if (item == Item.capes[i])
				return "cape";
		}
		for (int i = 0; i < Item.hats.length; i++) {
			if (item == Item.hats[i])
				return "hat";
		}
		for (int i = 0; i < Item.boots.length; i++) {
			if (item == Item.boots[i])
				return "boots";
		}
		for (int i = 0; i < Item.gloves.length; i++) {
			if (item == Item.gloves[i])
				return "gloves";
		}
		for (int i = 0; i < Item.shields.length; i++) {
			if (item == Item.shields[i])
				return "shield";
		}
		for (int i = 0; i < Item.amulets.length; i++) {
			if (item == Item.amulets[i])
				return "amulet";
		}
		for (int i = 0; i < Item.arrows.length; i++) {
			if (item == Item.arrows[i])
				return "arrows";
		}
		for (int i = 0; i < Item.rings.length; i++) {
			if (item == Item.rings[i])
				return "ring";
		}
		for (int i = 0; i < Item.body.length; i++) {
			if (item == Item.body[i])
				return "body";
		}
		for (int i = 0; i < Item.legs.length; i++) {
			if (item == Item.legs[i])
				return "legs";
		}
		return "weapon";
	}

	/**
	 * Bonuses
	 **/

	public final String[] BONUS_NAMES = { "Stab", "Slash", "Crush", "Magic",
			"Range", "Stab", "Slash", "Crush", "Magic", "Range", "Strength",
			"Prayer" };

	public void resetBonus() {
		for (int i = 0; i < c.playerBonus.length; i++) {
			c.playerBonus[i] = 0;
		}
	}

	public void getBonus() {
		for (int i = 0; i < c.getEquipment().length; i++) {
			if (c.getEquipment()[i] > -1) {
				for (int j = 0; j < Config.ITEM_LIMIT; j++) {
					if (ItemHandler.ItemList[j] != null) {
						if (ItemHandler.ItemList[j].itemId == c
								.getEquipment()[i]) {
							for (int k = 0; k < c.playerBonus.length; k++) {
								c.playerBonus[k] += ItemHandler.ItemList[j].Bonuses[k];
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Wear Item
	 **/

	public void sendWeapon(int Weapon, String WeaponName) {
		String WeaponName2 = WeaponName.replaceAll("Bronze", "");
		WeaponName2 = WeaponName2.replaceAll("Iron", "");
		WeaponName2 = WeaponName2.replaceAll("Steel", "");
		WeaponName2 = WeaponName2.replaceAll("Black", "");
		WeaponName2 = WeaponName2.replaceAll("Mithril", "");
		WeaponName2 = WeaponName2.replaceAll("Adamant", "");
		WeaponName2 = WeaponName2.replaceAll("Rune", "");
		WeaponName2 = WeaponName2.replaceAll("Granite", "");
		WeaponName2 = WeaponName2.replaceAll("Dragon", "");
		WeaponName2 = WeaponName2.replaceAll("Drag", "");
		WeaponName2 = WeaponName2.replaceAll("Crystal", "");
		WeaponName2 = WeaponName2.trim();
		if (WeaponName.equals("Unarmed")) {
			c.setSidebarInterface(0, 5855); // punch, kick, block
			c.getPA().sendFrame126(WeaponName, 5857);
		} else if (WeaponName2.startsWith("halberd")
				|| WeaponName2.endsWith("of the dead")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (WeaponName.endsWith("whip")
				|| WeaponName.endsWith("tentacle")) {
			c.setSidebarInterface(0, 12290); // flick, lash, deflect
			c.getPA().sendFrame246(12291, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 12293);
		} else if (WeaponName.endsWith("bow") || WeaponName.endsWith("10")
				|| WeaponName.endsWith("full")
				|| WeaponName.startsWith("seercull")) {
			c.setSidebarInterface(0, 1764); // accurate, rapid, longrange
			c.getPA().sendFrame246(1765, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 1767);
		} else if (WeaponName.startsWith("Staff")
				|| WeaponName.endsWith("staff") || WeaponName.endsWith("wand")) {
			c.setSidebarInterface(0, 328); // spike, impale, smash, block
			c.getPA().sendFrame246(329, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 331);
		} else if (WeaponName2.startsWith("dart")
				|| WeaponName2.startsWith("knife")
				|| WeaponName2.startsWith("javelin")
				|| WeaponName.equalsIgnoreCase("toktz-xil-ul")
				|| WeaponName.contains("blowpipe")) {
			c.setSidebarInterface(0, 4446); // accurate, rapid, longrange
			c.getPA().sendFrame246(4447, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4449);
		} else if (WeaponName2.contains("dagger")
				|| WeaponName2.contains("sword")) {
			c.setSidebarInterface(0, 2276); // stab, lunge, slash, block
			c.getPA().sendFrame246(2277, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2279);
		} else if (WeaponName2.startsWith("pickaxe")) {
			c.setSidebarInterface(0, 5570); // spike, impale, smash, block
			c.getPA().sendFrame246(5571, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 5573);
		} else if (WeaponName2.startsWith("axe")
				|| WeaponName2.startsWith("battleaxe")
				|| WeaponName.endsWith("axe")) {
			c.setSidebarInterface(0, 1698); // chop, hack, smash, block
			c.getPA().sendFrame246(1699, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 1701);
		} else if (WeaponName2.startsWith("Scythe")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("spear")) {
			c.setSidebarInterface(0, 4679); // lunge, swipe, pound, block
			c.getPA().sendFrame246(4680, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4682);
		} else if (WeaponName2.toLowerCase().contains("mace")) {
			c.setSidebarInterface(0, 3796);
			c.getPA().sendFrame246(3797, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 3799);

		} else if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 4153) {
			c.setSidebarInterface(0, 425); // war hamer equip.
			c.getPA().sendFrame246(426, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 428);
		} else {
			c.setSidebarInterface(0, 2423); // chop, slash, lunge, block
			c.getPA().sendFrame246(2424, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2426);
		}

	}

	/**
	 * Weapon Requirements
	 **/

	public void getRequirements(int itemId) {
		c.attackLevelReq = c.defenceLevelReq = c.strengthLevelReq = c.prayerLevelReq = c.rangeLevelReq = c.magicLevelReq = 0;
		String name = ItemDef.forId(itemId).getName().toLowerCase();
		if (name.contains("defender")) {
			if (name.contains("steel")) {
				c.defenceLevelReq = 5;
			}
			if (name.contains("black")) {
				c.defenceLevelReq = 10;
			}
			if (name.contains("mithril")) {
				c.defenceLevelReq = 20;
			}
			if (name.contains("adamant")) {
				c.defenceLevelReq = 30;
			}
			if (name.contains("rune")) {
				c.defenceLevelReq = 40;
			}
			if (name.contains("dragon")) {
				c.defenceLevelReq = 60;
			}
		}
		if (name.contains("steel")) {
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 5;
			}
			if (name.contains("scimitar") || name.contains("dagger")
					|| name.contains("sword")) {
				c.attackLevelReq = 5;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 5;
			}
		}
		if (name.contains("black")) {
			if (name.contains("d'hide body")) {
				c.defenceLevelReq = 40;
				c.rangeLevelReq = 70;
			}
			if (name.contains("d'hide chaps") || name.contains("d'hide vamb")) {
				c.rangeLevelReq = 70;
			}
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 10;
			}
			if (name.contains("scimitar") || name.contains("dagger")
					|| name.contains("sword")) {
				c.attackLevelReq = 10;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 10;
			}
		}
		if (name.contains("mithril")) {
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 20;
			}
			if (name.contains("scimitar") || name.contains("dagger")
					|| name.contains("sword")) {
				c.attackLevelReq = 20;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 20;
			}
		}
		if (name.contains("adamant")) {
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 30;
			}
			if (name.contains("scimitar") || name.contains("dagger")
					|| name.contains("sword")) {
				c.attackLevelReq = 30;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 30;
			}
		}
		if (name.contains("rune")) {
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 40;
			}
			if (name.contains("scimitar") || name.contains("dagger")
					|| name.contains("sword")) {
				c.attackLevelReq = 40;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 40;
			}
		}
		if (name.contains("dragon")) {
			if (name.contains("plate") || name.contains("chain")
					|| name.contains("boots") || name.contains("helm")
					|| name.contains("shield")) {
				c.defenceLevelReq = 60;
			}
			if (name.contains("scimitar") || name.contains("dagger") || name.contains("claws")
					|| name.contains("sword")) {
				c.attackLevelReq = 60;
			}
			if (name.contains("knife") || name.contains("thrownaxe")
					|| name.contains("javelin")) {
				c.rangeLevelReq = 60;
			}
		}
		if (name.contains("initiate")) {
			c.defenceLevelReq = 20;
			c.prayerLevelReq = 10;
		}
		if (name.contains("green")) {
			if (name.contains("d'hide")) {
				if (name.contains("d'hide body")) {
					c.rangeLevelReq = 40;
					c.defenceLevelReq = 40;
				} else {
					c.rangeLevelReq = 40;
				}
			}
		}
		if (name.contains("blue")) {
			if (name.contains("d'hide")) {
				if (name.contains("body")) {
					c.rangeLevelReq = 50;
					c.defenceLevelReq = 40;
				} else {
					c.rangeLevelReq = 50;
				}
			}
		}
		if (name.contains("red")) {
			if (name.contains("d'hide")) {
				if (name.contains("body")) {
					c.rangeLevelReq = 60;
					c.defenceLevelReq = 40;
				} else {
					c.rangeLevelReq = 60;
				}
			}
		}
		if (name.contains("saradomin") || name.contains("zamorak")) {
			if (name.contains("d'hide")) {
				if (name.contains("body")) {
					c.rangeLevelReq = 70;
					c.defenceLevelReq = 40;
				} else {
					c.rangeLevelReq = 70;
				}
			}
		}
		if (name.contains("crossbow")) {
			if (name.contains("rune")) {
				c.rangeLevelReq = 61;
			}
			if (name.contains("karil's") || name.contains("armadyl")) {
				c.rangeLevelReq = 70;
			}
		}
		if (name.contains("dharok's") || name.contains("torag's")
				|| name.contains("guthan's") || name.contains("verac's")) {
			if (name.contains("plate") || name.contains("helm")
					|| name.contains("chain") || name.contains("brassard")) {
				c.defenceLevelReq = 70;
			}
			if (name.contains("greataxe") || name.contains("hammers")
					|| name.contains("warspear") || name.contains("flail")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			}
		}
		if (name.contains("karil's")) {
			if (name.contains("leathertop") || name.contains("leatherskirt")) {
				c.rangeLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (name.contains("ahrim's")) {
			if (name.contains("robe") || name.contains("hood")) {
				c.magicLevelReq = 70;
				c.defenceLevelReq = 70;
			}
			if (name.contains("staff")) {
				c.magicLevelReq = 70;
				c.attackLevelReq = 70;
			}
		}
		if (name.contains("dragonfire")) {
			c.defenceLevelReq = 75;
		}
		if (name.contains("godsword")) {
			c.attackLevelReq = 75;
		}
		if (name.contains("bandos")) {
			if (name.contains("chest") || name.contains("tasset")
					|| name.contains("boots")) {
				c.defenceLevelReq = 65;
			}
		}
		if (name.contains("armadyl")) {
			if (name.contains("helmet") || name.contains("chestplate")
					|| name.contains("chainskirt")) {
				c.defenceLevelReq = 70;
				c.rangeLevelReq = 70;
			}
		}
		if (name.equals("granite maul")) {
			c.attackLevelReq = 50;
			c.strengthLevelReq = 50;
		}
		if (name.equals("tzhaar-ket-em")) {
			c.attackLevelReq = 60;
		}
		if (name.equals("tzhaar-ket-om")) {
			c.strengthLevelReq = 60;
		}
		if (name.contains("mystic")) {
			if (name.contains("hat") || name.contains("robe top")
					|| name.contains("robe bottom") || name.contains("gloves")
					|| name.contains("boots")) {
				c.magicLevelReq = 40;
				c.defenceLevelReq = 20;
			}
		}
		if (name.contains("splitbark")) {
			if (name.contains("helm") || name.contains("body")
					|| name.contains("legs") || name.contains("gauntlets")
					|| name.contains("boots")) {
				c.magicLevelReq = 40;
				c.defenceLevelReq = 40;
			}
		}
		if (name.equalsIgnoreCase("magic shortbow")) {
			c.rangeLevelReq = 50;
		}
		if (name.equals("toktz-xil-ul") || name.equals("dark bow")) {
			c.rangeLevelReq = 60;
		}
		if (name.contains("snakeskin")) {
			c.rangeLevelReq = 30;
			c.defenceLevelReq = 30;
		}
		if (name.contains("infinity")) {
			c.magicLevelReq = 50;
			c.defenceLevelReq = 25;
		}
		if (name.equals("master wand") || name.contains("mage's book")) {
			c.magicLevelReq = 60;
		}
		if (name.equals("ancient staff")) {
			c.magicLevelReq = 50;
			c.defenceLevelReq = 50;
		}
	}

	/**
	 * two handed weapon check
	 **/
	public boolean is2handed(String itemName, int itemId) {
		if (itemName.contains("ahrim") || itemName.contains("karil")
				|| itemName.contains("verac") || itemName.contains("guthan")
				|| itemName.contains("dharok") || itemName.contains("torag")
				|| itemName.contains("halberd")
				|| itemName.contains("blowpipe")) {
			return true;
		}
		if (itemName.contains("longbow") || itemName.contains("shortbow")
				|| itemName.contains("ark bow")) {
			return true;
		}
		if (itemName.contains("crystal")) {
			return true;
		}
		if (itemName.contains("godsword")
				|| itemName.contains("aradomin sword")
				|| itemName.contains("2h") || itemName.contains("spear")
				|| itemName.contains("blessed sword")) {
			return true;
		}
		switch (itemId) {
		case 6724: // seercull
		case 11730:
		case 4153:
		case 6528:
		case 13217:
			return true;
		}
		return false;
	}

	/**
	 * Weapons special bar, adds the spec bars to weapons that require them and
	 * removes the spec bars from weapons which don't require them
	 **/

	public void addSpecialBar(int weapon) {
		switch (weapon) {

		case 4151: // whip
		case 12006:
			c.getPA().sendFrame171(0, 12323);
			specialAmount(weapon, c.specAmount, 12335);
			break;

		case 859: // magic bows
		case 861:
		case 11235:
		case 11785:
			c.getPA().sendFrame171(0, 7549);
			specialAmount(weapon, c.specAmount, 7561);
			break;

		case 4587: // dscimmy
			c.getPA().sendFrame171(0, 7599);
			specialAmount(weapon, c.specAmount, 7611);
			break;

		case 3204: // d hally
		case 13092:
		case 11791:
			c.getPA().sendFrame171(0, 8493);
			specialAmount(weapon, c.specAmount, 8505);
			break;

		case 1377: // d battleaxe
			c.getPA().sendFrame171(0, 7499);
			specialAmount(weapon, c.specAmount, 7511);
			break;

		case 4153: // gmaul
			c.getPA().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;

		case 1249: // dspear
			c.getPA().sendFrame171(0, 7674);
			specialAmount(weapon, c.specAmount, 7686);
			break;

		case 1215:// dragon dagger
		case 1231:
		case 5680:
		case 5698:
		case 1305: // dragon long
		case 11802:
		case 11698:
		case 11700:
		case 11730:
		case 11696:
		case 13205:
		c.getPA().sendFrame171(0, 7574);
		specialAmount(weapon, c.specAmount, 7586);
		break;

		case 13217: //dragon claws
		c.getPA().sendFrame171(0, 7800);
		specialAmount(weapon, c.specAmount, 7812);
		break;

		case 1434: // dragon mace
			c.getPA().sendFrame171(0, 7624);
			specialAmount(weapon, c.specAmount, 7636);
			break;

		default:
			c.getPA().sendFrame171(1, 7624); // mace interface
			c.getPA().sendFrame171(1, 7474); // hammer, gmaul
			c.getPA().sendFrame171(1, 7499); // axe
			c.getPA().sendFrame171(1, 7549); // bow interface
			c.getPA().sendFrame171(1, 7574); // sword interface
			c.getPA().sendFrame171(1, 7599); // scimmy sword interface, for most
			// swords
			c.getPA().sendFrame171(1, 8493);
			c.getPA().sendFrame171(1, 12323); // whip interface
			break;
		}
	}

	/**
	 * Specials bar filling amount
	 **/

	public void specialAmount(int weapon, double specAmount, int barId) {
		c.specBarId = barId;
		c.getPA().sendFrame70(specAmount >= 10 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 9 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 8 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 7 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 6 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 5 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 4 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 3 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 2 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 1 ? 500 : 0, 0, (--barId));
		updateSpecialBar();
		sendWeapon(weapon, getItemName(weapon));
	}

	/**
	 * Special attack text and what to highlight or blackout
	 **/

	public void updateSpecialBar() {
		if (c.usingSpecial) {
			c.getPA()
					.sendFrame126(
							""
									+ (c.specAmount >= 2 ? "@yel@S P"
											: "@bla@S P")
									+ ""
									+ (c.specAmount >= 3 ? "@yel@ E"
											: "@bla@ E")
									+ ""
									+ (c.specAmount >= 4 ? "@yel@ C I"
											: "@bla@ C I")
									+ ""
									+ (c.specAmount >= 5 ? "@yel@ A L"
											: "@bla@ A L")
									+ ""
									+ (c.specAmount >= 6 ? "@yel@  A"
											: "@bla@  A")
									+ ""
									+ (c.specAmount >= 7 ? "@yel@ T T"
											: "@bla@ T T")
									+ ""
									+ (c.specAmount >= 8 ? "@yel@ A"
											: "@bla@ A")
									+ ""
									+ (c.specAmount >= 9 ? "@yel@ C"
											: "@bla@ C")
									+ ""
									+ (c.specAmount >= 10 ? "@yel@ K"
											: "@bla@ K"), c.specBarId);
		} else {
			c.getPA().sendFrame126("@bla@S P E C I A L  A T T A C K",
					c.specBarId);
		}
	}

	/**
	 * Wear Item
	 **/
	public boolean wearItem(int wearID, int slot) {
		if (!c.getItems().playerHasItem(wearID, 1, slot)) {
			// add a method here for logging cheaters(If you want)
			return false;
		}
		// synchronized(c) {
		int targetSlot = 0;
		boolean canWearItem = true;
		if (c.playerItems[slot] == (wearID + 1)) {
			getRequirements(wearID);
			String name = ItemDef.forId(wearID).name.toLowerCase();
			if (wearID == 542 || wearID == 1033 || wearID == 6108) {
				targetSlot = PlayerConstants.PLAYER_LEGS;
			} else if (wearID == 1035 || wearID == 6107) {
				targetSlot = PlayerConstants.PLAYER_BODY;
			} else if (name.contains("helm") || name.contains("coif")
					|| name.contains("hat") || name.contains("mask")
					|| name.contains("hood") || name.equals("afro")
					|| name.contains("headband") || name.contains("sallet")
					|| name.contains("crown") || name.contains("mitre")) {
				targetSlot = PlayerConstants.PLAYER_HAT;
			} else if (name.contains("legs") || name.contains("tasset")
					|| name.contains("bottom") || name.contains("robeskirt")
					|| name.contains("skirt") || name.contains("chaps")
					|| name.contains("knight robe") || name.contains("cuisse")
					|| name.contains("pantaloons") || name.contains("trouser")) {
				targetSlot = PlayerConstants.PLAYER_LEGS;
			} else if (name.contains("shield") || name.contains("defender")
					|| name.contains("defender") || name.contains("ward")
					|| name.contains("book")) {
				targetSlot = PlayerConstants.PLAYER_SHIELD;
			} else if (name.contains("body") || name.contains("torso")
					|| name.contains("top") || name.contains("brassard")
					|| name.contains("hauberk") || name.contains("jacket")
					|| name.contains("tunic") || name.contains("chest")
					|| name.contains("domin d'hide")
					|| name.contains("orak d'hide")
					|| name.contains("wizard robe")) {
				targetSlot = PlayerConstants.PLAYER_BODY;
			} else if (name.contains("cloak") || name.contains("cape")
					|| name.contains("accumulator")
					|| name.contains("attractor")) {
				targetSlot = PlayerConstants.PLAYER_CAPE;
			} else if (name.contains("boots")) {
				targetSlot = PlayerConstants.PLAYER_FEET;
			} else if (name.contains("glove") || name.contains("bracelet")
					|| name.contains("vamb") || name.contains("bracers")) {
				targetSlot = PlayerConstants.PLAYER_GLOVES;
			} else if (wearID == 7462) {
				targetSlot = PlayerConstants.PLAYER_GLOVES;
			} else if (name.contains("arrow") || name.contains("bolt")) {
				targetSlot = PlayerConstants.PLAYER_ARROWS;
			} else if (name.contains("ring")) {
				targetSlot = PlayerConstants.PLAYER_RING;
			} else if (name.contains("amulet") || name.contains("necklace")
					|| name.contains("stole")) {
				targetSlot = PlayerConstants.PLAYER_AMULET;
			} else {
				targetSlot = 3;
			}
			if (wearID == 12006 && c.getHits()[0] <= 0) {
				c.getHits()[0] = 5000;
			}
			if (c.duelRule[11] && targetSlot == 0) {
				c.sendMessage("Wearing hats has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[12] && targetSlot == 1) {
				c.sendMessage("Wearing capes has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[13] && targetSlot == 2) {
				c.sendMessage("Wearing amulets has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[14] && targetSlot == 3) {
				c.sendMessage("Wielding weapons has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[15] && targetSlot == 4) {
				c.sendMessage("Wearing bodies has been disabled in this duel!");
				return false;
			}
			if ((c.duelRule[16] && targetSlot == 5)
					|| (c.duelRule[16] && is2handed(getItemName(wearID)
							.toLowerCase(), wearID))) {
				c.sendMessage("Wearing shield has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[17] && targetSlot == 7) {
				c.sendMessage("Wearing legs has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[18] && targetSlot == 9) {
				c.sendMessage("Wearing gloves has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[19] && targetSlot == 10) {
				c.sendMessage("Wearing boots has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[20] && targetSlot == 12) {
				c.sendMessage("Wearing rings has been disabled in this duel!");
				return false;
			}
			if (c.duelRule[21] && targetSlot == 13) {
				c.sendMessage("Wearing arrows has been disabled in this duel!");
				return false;
			}

			if (targetSlot == 10 || targetSlot == 7 || targetSlot == 5
					|| targetSlot == 4 || targetSlot == 0 || targetSlot == 9
					|| targetSlot == 10) {
				if (c.prayerLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[5]) < c.prayerLevelReq) {
						c.sendMessage("You need a prayer level of "
								+ c.prayerLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (c.defenceLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[1]) < c.defenceLevelReq) {
						c.sendMessage("You need a defence level of "
								+ c.defenceLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (c.rangeLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[4]) < c.rangeLevelReq) {
						c.sendMessage("You need a range level of "
								+ c.rangeLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
				if (c.magicLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[6]) < c.magicLevelReq) {
						c.sendMessage("You need a magic level of "
								+ c.magicLevelReq + " to wear this item.");
						canWearItem = false;
					}
				}
			}
			if (targetSlot == 3) {
				if (c.strengthLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[2]) < c.strengthLevelReq) {
						c.sendMessage("You need a strength level of "
								+ c.strengthLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
				if (c.attackLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[0]) < c.attackLevelReq) {
						c.sendMessage("You need an attack level of "
								+ c.attackLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
				if (c.rangeLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[4]) < c.rangeLevelReq) {
						c.sendMessage("You need a range level of "
								+ c.rangeLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
				if (c.magicLevelReq > 0) {
					if (c.getPA().getLevelForXP(c.getExperience()[6]) < c.magicLevelReq) {
						c.sendMessage("You need a magic level of "
								+ c.magicLevelReq + " to wield this weapon.");
						canWearItem = false;
					}
				}
			}

			if (!canWearItem) {
				return false;
			}

			int wearAmount = c.playerItemsN[slot];
			if (wearAmount < 1) {
				return false;
			}

			if (targetSlot == PlayerConstants.PLAYER_WEAPON) {
				c.autocasting = false;
				c.autocastId = 0;
				c.getPA().sendFrame36(108, 0);
			}

			if (slot >= 0 && wearID >= 0) {
				int toEquip = c.playerItems[slot];
				int toEquipN = c.playerItemsN[slot];
				int toRemove = c.getEquipment()[targetSlot];
				int toRemoveN = c.playerEquipmentN[targetSlot];
				boolean stackable = false;
				if (getItemName(toRemove).contains("javelin")
						|| getItemName(toRemove).contains("dart")
						|| getItemName(toRemove).contains("knife")
						|| getItemName(toRemove).contains("bolt")
						|| getItemName(toRemove).contains("arrow")
						|| getItemName(toRemove).contains("Bolt")
						|| getItemName(toRemove).contains("bolts")
						|| getItemName(toRemove).contains("thrownaxe")
						|| getItemName(toRemove).contains("throwing"))
					stackable = true;
				else
					stackable = false;
				if (toEquip == toRemove + 1 && ItemDef.isStackable(toRemove)) {
					deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
					c.playerEquipmentN[targetSlot] += toEquipN;
				} else if (targetSlot != 5 && targetSlot != 3) {
					if (playerHasItem(toRemove, 1) && stackable == true) {
						c.playerItems[slot] = 0;// c.playerItems[slot] =
						// toRemove + 1;
						c.playerItemsN[slot] = 0;// c.playerItemsN[slot] =
						// toRemoveN;
						if (toRemove > 0 && toRemoveN > 0)// c.getEquipment()[targetSlot]
							// = toEquip - 1;
							addItem(toRemove, toRemoveN);// c.playerEquipmentN[targetSlot]
						// = toEquipN;
					} else {
						c.playerItems[slot] = toRemove + 1;
						c.playerItemsN[slot] = toRemoveN;
					}
					c.getEquipment()[targetSlot] = toEquip - 1;
					c.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 5) {
					boolean wearing2h = is2handed(
							getItemName(c.getEquipment()[3]).toLowerCase(),
							c.getEquipment()[3]);
					boolean wearingShield = c.getEquipment()[PlayerConstants.PLAYER_SHIELD] > 0;
					if (wearing2h) {
						toRemove = c.getEquipment()[3];
						toRemoveN = c.playerEquipmentN[3];
						c.getEquipment()[3] = -1;
						c.playerEquipmentN[3] = 0;
						updateSlot(3);
					}
					c.playerItems[slot] = toRemove + 1;
					c.playerItemsN[slot] = toRemoveN;
					c.getEquipment()[targetSlot] = toEquip - 1;
					c.playerEquipmentN[targetSlot] = toEquipN;
				} else if (targetSlot == 3) {
					boolean is2h = is2handed(getItemName(wearID).toLowerCase(),
							wearID);
					boolean wearingShield = c.getEquipment()[PlayerConstants.PLAYER_SHIELD] > 0;
					boolean wearingWeapon = c.getEquipment()[3] > 0;
					if (is2h) {
						if (wearingShield && wearingWeapon) {
							if (freeSlots() > 0) {
								if (playerHasItem(toRemove, 1)
										&& stackable == true) {
									c.playerItems[slot] = 0;// c.playerItems[slot]
									// = toRemove + 1;
									c.playerItemsN[slot] = 0;// c.playerItemsN[slot]
									// = toRemoveN;
									if (toRemove > 0 && toRemoveN > 0)// c.getEquipment()[targetSlot]
										// =
										// toEquip
										// - 1;
										addItem(toRemove, toRemoveN);// c.playerEquipmentN[targetSlot]
									// =
									// toEquipN;
								} else {
									c.playerItems[slot] = toRemove + 1;
									c.playerItemsN[slot] = toRemoveN;
								}
								c.getEquipment()[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
								removeItem(
										c.getEquipment()[PlayerConstants.PLAYER_SHIELD],
										PlayerConstants.PLAYER_SHIELD);
							} else {
								c.sendMessage("You do not have enough inventory space to do this.");
								return false;
							}
						} else if (wearingShield && !wearingWeapon) {
							c.playerItems[slot] = c.getEquipment()[PlayerConstants.PLAYER_SHIELD] + 1;
							c.playerItemsN[slot] = c.playerEquipmentN[PlayerConstants.PLAYER_SHIELD];
							c.getEquipment()[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
							c.getEquipment()[PlayerConstants.PLAYER_SHIELD] = -1;
							c.playerEquipmentN[PlayerConstants.PLAYER_SHIELD] = 0;
							updateSlot(PlayerConstants.PLAYER_SHIELD);
						} else {
							if (playerHasItem(toRemove, 1) && stackable == true) {
								c.playerItems[slot] = 0;// c.playerItems[slot] =
								// toRemove + 1;
								c.playerItemsN[slot] = 0;// c.playerItemsN[slot]
								// = toRemoveN;
								if (toRemove > 0 && toRemoveN > 0)// c.getEquipment()[targetSlot]
									// = toEquip
									// - 1;
									addItem(toRemove, toRemoveN);// c.playerEquipmentN[targetSlot]
								// =
								// toEquipN;
							} else {
								c.playerItems[slot] = toRemove + 1;
								c.playerItemsN[slot] = toRemoveN;
							}
							c.getEquipment()[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
						}
					} else {
						if (playerHasItem(toRemove, 1) && stackable == true) {
							c.playerItems[slot] = 0;// c.playerItems[slot] =
							// toRemove + 1;
							c.playerItemsN[slot] = 0;// c.playerItemsN[slot] =
							// toRemoveN;
							if (toRemove > 0 && toRemoveN > 0)// c.getEquipment()[targetSlot]
								// = toEquip -
								// 1;
								addItem(toRemove, toRemoveN);// c.playerEquipmentN[targetSlot]
							// = toEquipN;
						} else {
							c.playerItems[slot] = toRemove + 1;
							c.playerItemsN[slot] = toRemoveN;
						}
						c.getEquipment()[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					}
				}
			}
			if (targetSlot == 3) {
				c.usingSpecial = false;
				addSpecialBar(wearID);
			}
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(targetSlot);
				c.getOutStream().writeWord(wearID + 1);

				if (c.playerEquipmentN[targetSlot] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[targetSlot]);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[targetSlot]);
				}

				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
			sendWeapon(c.getEquipment()[3], getItemName(c.getEquipment()[3]));
			resetBonus();
			getBonus();
			writeBonus();
			c.getCombat().getPlayerAnimIndex();
			c.getPA().requestUpdates();
			return true;
		} else {
			return false;
		}
	}

	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		// synchronized (c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(targetSlot);
			c.getOutStream().writeWord(wearID + 1);

			if (wearAmount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(wearAmount);
			} else {
				c.getOutStream().writeByte(wearAmount);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.getEquipment()[targetSlot] = wearID;
			c.playerEquipmentN[targetSlot] = wearAmount;
			c.getItems().sendWeapon(
					c.getEquipment()[PlayerConstants.PLAYER_WEAPON],
					c.getItems().getItemName(
							c.getEquipment()[PlayerConstants.PLAYER_WEAPON]));
			c.getItems().resetBonus();
			c.getItems().getBonus();
			c.getItems().writeBonus();
			c.getCombat().getPlayerAnimIndex();
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
		// }
	}

	public void updateSlot(int slot) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(slot);
			c.getOutStream().writeWord(c.getEquipment()[slot] + 1);
			if (c.playerEquipmentN[slot] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(c.playerEquipmentN[slot]);
			} else {
				c.getOutStream().writeByte(c.playerEquipmentN[slot]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}

	}

	/**
	 * Remove Item
	 **/
	public void removeItem(int wearID, int slot) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (c.getEquipment()[slot] > -1) {
				if (addItem(c.getEquipment()[slot], c.playerEquipmentN[slot])) {
					c.getEquipment()[slot] = -1;
					c.playerEquipmentN[slot] = 0;
					sendWeapon(
							c.getEquipment()[PlayerConstants.PLAYER_WEAPON],
							getItemName(c.getEquipment()[PlayerConstants.PLAYER_WEAPON]));
					resetBonus();
					getBonus();
					writeBonus();
					c.getCombat().getPlayerAnimIndex();
					c.getOutStream().createFrame(34);
					c.getOutStream().writeWord(6);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(slot);
					c.getOutStream().writeWord(0);
					c.getOutStream().writeByte(0);
					c.flushOutStream();
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				}
			}
		}
	}

	/**
	 * BANK
	 */

	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (c.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (c.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							c.bankItems[j - spots] = c.bankItems[j];
							c.bankItemsN[j - spots] = c.bankItemsN[j];
							stop = true;
							c.bankItems[j] = 0;
							c.bankItemsN[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter)
			c.disconnected = true;
	}

	public void resetBank() {
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(5382); // bank
		c.getOutStream().writeWord(Config.BANK_SIZE);
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItemsN[i] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(c.bankItemsN[i]);
			} else {
				c.getOutStream().writeByte(c.bankItemsN[i]);
			}
			if (c.bankItemsN[i] < 1) {
				c.bankItems[i] = 0;
			}
			if (c.bankItems[i] > Config.ITEM_LIMIT || c.bankItems[i] < 0) {
				c.bankItems[i] = Config.ITEM_LIMIT;
			}
			c.getOutStream().writeWordBigEndianA(c.bankItems[i]);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public void resetTempItems() {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > -1) {
				itemCount = i;
			}
		}
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(5064);
		c.getOutStream().writeWord(itemCount + 1);
		for (int i = 0; i < itemCount + 1; i++) {
			if (c.playerItemsN[i] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
			} else {
				c.getOutStream().writeByte(c.playerItemsN[i]);
			}
			if (c.playerItems[i] > Config.ITEM_LIMIT || c.playerItems[i] < 0) {
				c.playerItems[i] = Config.ITEM_LIMIT;
			}
			c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				tempAmount += c.playerItemsN[i];
			}
		}
		return tempAmount;
	}

	public boolean isStackable(int itemID) {
		return Item.itemStackable[itemID];
	}

	/**
	 * Update Equip tab
	 **/

	public void setEquipment(int wearID, int amount, int targetSlot) {
		// synchronized(c) {
		c.getOutStream().createFrameVarSizeWord(34);
		c.getOutStream().writeWord(1688);
		c.getOutStream().writeByte(targetSlot);
		c.getOutStream().writeWord(wearID + 1);
		if (amount > 254) {
			c.getOutStream().writeByte(255);
			c.getOutStream().writeDWord(amount);
		} else {
			c.getOutStream().writeByte(amount);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
		c.getEquipment()[targetSlot] = wearID;
		c.playerEquipmentN[targetSlot] = amount;
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	/**
	 * Move Items
	 **/

	public void moveItems(int from, int to, int moveWindow, boolean insertMode) {
		if (moveWindow == 3214) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
			c.getItems().resetItems(3214);
		}
		if (moveWindow == 5382 && from >= 0 && to >= 0
				&& from < Config.BANK_SIZE && to < Config.BANK_SIZE) {
			if (insertMode) {
				int tempFrom = from;
				for (int tempTo = to; tempFrom != tempTo;) {
					if (tempFrom > tempTo) {
						c.getBankHandler().swapBankItem(tempFrom, tempFrom - 1);
						tempFrom--;
					} else if (tempFrom < tempTo) {
						c.getBankHandler().swapBankItem(tempFrom, tempFrom + 1);
						tempFrom++;
					}
				}
			} else {
				c.getBankHandler().swapBankItem(from, to);
			}
		}
		if (moveWindow == 5382) {
			c.getItems().resetBank();
		}
		if (moveWindow == 5064) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
			c.getItems().resetItems(3214);
		}
	}

	/**
	 * delete Item
	 **/

	public void deleteEquipment(int i, int j) {
		// synchronized(c) {
		if (PlayerHandler.players[c.playerId] == null) {
			return;
		}
		if (i < 0) {
			return;
		}

		c.getEquipment()[j] = -1;
		c.playerEquipmentN[j] = c.playerEquipmentN[j] - 1;
		c.getOutStream().createFrame(34);
		c.getOutStream().writeWord(6);
		c.getOutStream().writeWord(1688);
		c.getOutStream().writeByte(j);
		c.getOutStream().writeWord(0);
		c.getOutStream().writeByte(0);
		getBonus();
		if (j == PlayerConstants.PLAYER_WEAPON) {
			sendWeapon(-1, "Unarmed");
		}
		resetBonus();
		getBonus();
		writeBonus();
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void deleteItem(int id, int amount) {
		if (id <= 0)
			return;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (amount <= 0)
				break;
			if (c.playerItems[j] == id + 1) {
				c.playerItems[j] = 0;
				c.playerItemsN[j] = 0;
				amount--;
			}
		}
		resetItems(3214);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (c.playerItems[slot] == (id + 1)) {
			if (c.playerItemsN[slot] > amount) {
				c.playerItemsN[slot] -= amount;
			} else {
				c.playerItemsN[slot] = 0;
				c.playerItems[slot] = 0;
			}
			resetItems(3214);
		}
	}

	public void deleteItem2(int id, int amount) {
		int am = amount;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (am == 0) {
				break;
			}
			if (c.playerItems[i] == (id + 1)) {
				if (c.playerItemsN[i] > amount) {
					c.playerItemsN[i] -= amount;
					break;
				} else {
					c.playerItems[i] = 0;
					c.playerItemsN[i] = 0;
					am--;
				}
			}
		}
		resetItems(3214);
	}

	/**
	 * Delete Arrows
	 **/
	public void deleteArrow() {
		// synchronized(c) {
		if (c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 10499
				&& Misc.random(5) != 1
				&& c.getEquipment()[PlayerConstants.PLAYER_ARROWS] != 4740)
			return;
		if (c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] == 1) {
			c.getItems().deleteEquipment(
					c.getEquipment()[PlayerConstants.PLAYER_ARROWS],
					PlayerConstants.PLAYER_ARROWS);
		}
		if (c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] != 0) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(PlayerConstants.PLAYER_ARROWS);
			c.getOutStream().writeWord(
					c.getEquipment()[PlayerConstants.PLAYER_ARROWS] + 1);
			if (c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] - 1 > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(
						c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] - 1);
			} else {
				c.getOutStream().writeByte(
						c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] - 1);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipmentN[PlayerConstants.PLAYER_ARROWS] -= 1;
		}
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void deleteEquipment() {
		// synchronized(c) {
		if (c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] == 1) {
			c.getItems().deleteEquipment(
					c.getEquipment()[PlayerConstants.PLAYER_WEAPON],
					PlayerConstants.PLAYER_WEAPON);
		}
		if (c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] != 0) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(PlayerConstants.PLAYER_WEAPON);
			c.getOutStream().writeWord(
					c.getEquipment()[PlayerConstants.PLAYER_WEAPON] + 1);
			if (c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] - 1 > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(
						c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] - 1);
			} else {
				c.getOutStream().writeByte(
						c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] - 1);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipmentN[PlayerConstants.PLAYER_WEAPON] -= 1;
		}
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	/**
	 * Dropping Arrows
	 **/

	public void dropArrowNpc() {
		if (c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 10499)
			return;
		int enemyX = NPCHandler.npcs[c.oldNpcIndex].getX();
		int enemyY = NPCHandler.npcs[c.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.rangeItemUsed,
						enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	public void dropArrowPlayer() {
		int enemyX = PlayerHandler.players[c.oldPlayerIndex].getX();
		int enemyY = PlayerHandler.players[c.oldPlayerIndex].getY();
		if (c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 10499)
			return;
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.rangeItemUsed, enemyX,
					enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.rangeItemUsed,
						enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX,
						enemyY, amount + 1, c.getId());
			}
		}
	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		resetItems(3214);
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if (((items[i] - 1) == id) && (amounts[i] > 0)) {
				return i;
			}
		}
		return -1;
	}

	public String getItemName(int ItemID) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (ItemHandler.ItemList[i] != null) {
				if (ItemHandler.ItemList[i].itemId == ItemID) {
					return ItemHandler.ItemList[i].itemName;
				}
			}
		}
		return "Unarmed";
	}

	public int getItemId(String itemName) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (ItemHandler.ItemList[i] != null) {
				if (ItemHandler.ItemList[i].itemName
						.equalsIgnoreCase(itemName)) {
					return ItemHandler.ItemList[i].itemId;
				}
			}
		}
		return -1;
	}

	public int getItemSlot(int ItemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				return i;
			}
		}
		return -1;
	}

	public void removeEquipment(int slot) {
		boolean godArmourChanged = false;

		if (c.getOutStream() == null || c == null) {
			return;
		}
		if (c.getEquipment()[slot] < 0) {
			return;
		}
		c.getEquipment()[slot] = -1;
		c.playerEquipmentN[slot] = 0;
		sendWeapon(c.getEquipment()[PlayerConstants.PLAYER_WEAPON],
				getItemName(c.getEquipment()[PlayerConstants.PLAYER_WEAPON]));
		writeBonus();
		c.getCombat().getPlayerAnimIndex();
		c.getOutStream().createFrame(34);
		c.getOutStream().writeWord(6);
		c.getOutStream().writeWord(1688);
		c.getOutStream().writeByte(slot);
		c.getOutStream().writeWord(0);
		c.getOutStream().writeByte(0);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
		if (godArmourChanged
				&& c.getLevel()[3] > c.getPA().getLevelForXP(
						c.getExperience()[3])) {
			c.getLevel()[3] = c.getPA().getLevelForXP(c.getExperience()[3]);
			c.getPA().refreshSkill(3);
		}
	}

	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int getItemAmount(int ItemID) {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				itemCount += c.playerItemsN[i];
			}
		}
		return itemCount;
	}

	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (c.playerItems[slot] == (itemID)) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] == itemID) {
					if (c.playerItemsN[i] >= amt) {
						return true;
					} else {
						found++;
					}
				}
			}
			if (found >= amt) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean playerHasItem(int itemID) {
		itemID++;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID)
				return true;
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				if (c.playerItemsN[i] >= amt) {
					return true;
				} else {
					found++;
				}
			}
		}
		if (found >= amt) {
			return true;
		}
		return false;
	}

	public int getUnnotedItem(int ItemID) {
		int NewID = ItemID - 1;
		String NotedName = "";
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (ItemHandler.ItemList[i] != null) {
				if (ItemHandler.ItemList[i].itemId == ItemID) {
					NotedName = ItemHandler.ItemList[i].itemName;
				}
			}
		}
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (ItemHandler.ItemList[i] != null) {
				if (ItemHandler.ItemList[i].itemName == NotedName) {
					if (ItemHandler.ItemList[i].itemDescription
							.startsWith("Swap this note at any bank for a") == false) {
						NewID = ItemHandler.ItemList[i].itemId;
						break;
					}
				}
			}
		}
		return NewID;
	}

	/**
	 * Drop Item
	 **/

	public void createGroundItem(int itemID, int itemX, int itemY,
			int itemAmount) {
		c.getOutStream().createFrame(85);
		c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
		c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
		c.getOutStream().createFrame(44);
		c.getOutStream().writeWordBigEndianA(itemID);
		c.getOutStream().writeWord(itemAmount);
		c.getOutStream().writeByte(0);
		c.flushOutStream();
	}

	/**
	 * Pickup Item
	 **/

	public void removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
		if (c == null) {
			return;
		}
		c.getOutStream().createFrame(85);
		c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
		c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
		c.getOutStream().createFrame(156);
		c.getOutStream().writeByteS(0);
		c.getOutStream().writeWord(itemID);
		c.flushOutStream();
	}

	public boolean ownsCape() {
		if (c.getItems().playerHasItem(2412, 1)
				|| c.getItems().playerHasItem(2413, 1)
				|| c.getItems().playerHasItem(2414, 1))
			return true;
		for (int j = 0; j < Config.BANK_SIZE; j++) {
			if (c.bankItems[j] == 2412 || c.bankItems[j] == 2413
					|| c.bankItems[j] == 2414)
				return true;
		}
		if (c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 2413
				|| c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 2414
				|| c.getEquipment()[PlayerConstants.PLAYER_CAPE] == 2415)
			return true;
		return false;
	}

	public boolean hasAllShards() {
		return playerHasItem(11712, 1) && playerHasItem(11712, 1)
				&& playerHasItem(11714, 1);
	}

	public void makeBlade() {
		deleteItem(11710, 1);
		deleteItem(11712, 1);
		deleteItem(11714, 1);
		addItem(11690, 1);
		c.sendMessage("You combine the shards to make a blade.");
	}

	public void makeGodsword(int i) {
		if (playerHasItem(11690) && playerHasItem(i)) {
			deleteItem(11690, 1);
			deleteItem(i, 1);
			addItem(i - 8, 1);
			c.sendMessage("You combine the hilt and the blade to make a godsword.");
		}
	}

	public boolean isHilt(int i) {
		return i >= 11702 && i <= 11708 && i % 2 == 0;
	}

}