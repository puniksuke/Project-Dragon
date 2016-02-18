package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.Graphic;
import org.osrspvp.model.content.FlowerGame;
import org.osrspvp.model.content.SpawnTab;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.mysql.MYSQLController;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.net.packet.SubPacketType;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.Misc;

/**
 * Clicking most buttons
 **/
public class ActionButtonPacket implements SubPacketType {

	@Override
	public void processSubPacket(Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0,
				packetSize);
		if (AccountPinManager.hasToTypePin(c)) {
			AccountPinManager.openPinInterface(c);
			return;
		}
		if (c.isDead || c.getLevel()[3] <= 0) {
			return;
		}
		if (c.playerName.equalsIgnoreCase("tyler")
				|| c.playerName.equalsIgnoreCase("dennis")) {
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		}
		String teleportType = c.playerMagicBook == 1 ? "ancient" : "modern";
		switch (actionButtonId) {

		case 33206:
			c.outStream.createFrame(27);
			c.skillToChange = 0;
			break;

		case 33212:
			c.outStream.createFrame(27);
			c.skillToChange = 1;
			break;

		case 33209:
			c.outStream.createFrame(27);
			c.skillToChange = 2;
			break;

		case 33207:
			c.outStream.createFrame(27);
			c.skillToChange = 3;
			break;

		case 33215:
			c.outStream.createFrame(27);
			c.skillToChange = 4;
			break;

		case 33218:
			c.outStream.createFrame(27);
			c.skillToChange = 5;
			break;

		case 33221:
			c.outStream.createFrame(27);
			c.skillToChange = 6;
			break;

		case 89227:
			if (!c.isBanking) {
				return;
			}
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] > 0) {
					int itemId = c.playerItems[i];
					int slot = c.getItems().getItemSlot(itemId - 1);
					int itemAmount = c.playerItemsN[i];
					c.getBankHandler().bankItem(itemId, slot, itemAmount);
				}
			}
			break;

		case 108006:
			c.getPA().sendFrame126("OSRS-PVP - Item's Kept on Death", 17103);
			c.StartBestItemScan(c);
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0,
						c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1,
						c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2,
						c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0 && c.prayerActive[10])
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1,
							c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.getEquipment()[EQUIP] > 0
						&& !(c.getEquipment()[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.getEquipment()[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.getEquipment()[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.getEquipment()[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.getEquipment()[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.getEquipment()[EQUIP] > 0
						&& (c.getEquipment()[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.getEquipment()[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.getEquipment()[EQUIP] > 0
						&& (c.getEquipment()[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.getEquipment()[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.getEquipment()[EQUIP] > 0
						&& (c.getEquipment()[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.getEquipment()[EQUIP],
							c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.getEquipment()[EQUIP] > 0
						&& (c.getEquipment()[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1
						&& c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.getEquipment()[EQUIP],
							c.EquipStatus, c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}
			c.ResetKeepItems();
			c.getPA().showInterface(17100);
			break;

		case 9167:
			if (c.dialogueAction == 21) {
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

		case 9168:
			if (c.dialogueAction == 21) {
				c.setSidebarInterface(6, 12855);
				c.playerMagicBook = 1;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

		case 9169:
			if (c.dialogueAction == 21) {
				c.setSidebarInterface(6, 29999);
				c.playerMagicBook = 2;
				c.autocasting = false;
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

		case 62165:
			c.getPA().sendFrame126("www.dragonicpk.com/community/", 12000);
			break;

		case 62166:
			c.getPA().sendFrame126("www.dragonicpk.com/store/", 12000);
			break;

		case 62167:
			c.getPA().sendFrame126("www.dragonicpk.com/vote/", 12000);
			break;

		case 62168:
			c.getPA().sendFrame126("www.dragonicpk.com/itemlist/", 12000);
			break;

		case 109197:
		case 109204:
		case 109210:
		case 109216:
		case 109222:
		case 109228:
			SpawnTab.handleActionButtons(c, actionButtonId);
			break;

		/**
		 * Teleports
		 */
		case 109157: // edgeville
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(Config.LUMBY_X + Misc.random(2),
					Config.LUMBY_Y + Misc.random(2), 0, teleportType);
			break;

		case 109159: // falador
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(2945 + Misc.random(2),
					3370 + Misc.random(2), 0, teleportType);
			break;

		case 109161: // east dragons
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(3339, 3659, 0, teleportType);
			break;

		case 109163: // east dragons
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(Config.MAGEBANK_X + Misc.random(2),
					Config.MAGEBANK_Y + Misc.random(2), 0, teleportType);
			break;

		case 118098:
			c.getPA().castVeng();
			break;
		// crafting + fletching interface:
		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		// 1st tele option
		case 9190:
			if (c.dialogueAction == 30) {
				c.getPA().startTeleport(2975, 4384, 2, teleportType);
			}
			if (c.dialogueAction == 32) {
				c.getDH().sendOption4("Armadyl", "Bandos", "Saradomin",
						"Zamorak");
				c.dialogueAction = 31;
				return;
			}
			c.getPA().removeAllWindows();
			break;
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		// 2nd tele option
		case 9191:
			if (c.dialogueAction == 30) {
				c.getPA().startTeleport(3309, 3847, 0, teleportType);
			}
			if (c.dialogueAction == 32) {
				c.getPA().startTeleport(2439, 5169, 0, teleportType);
			}
			c.getPA().removeAllWindows();
			break;
		// 3rd tele option

		case 9192:
			if (c.dialogueAction == 30) {
				c.getPA().startTeleport(3222, 3788, 0, teleportType);
			}
			c.getPA().removeAllWindows();
			break;
		// 4th tele option
		case 9193:
			if (c.dialogueAction == 30) {
				c.getPA().startTeleport(2984, 3856, 0, teleportType);
			}
			c.getPA().removeAllWindows();
			break;
		// 5th tele option
		case 9194:
			if (c.dialogueAction == 30) {
				c.getDH().sendOption5("Godwars Chambers (Safe)", "Fight Caves",
						"", "", "");
				c.dialogueAction = 32;
				return;
			}
			c.getPA().removeAllWindows();
			break;

		case 58253:
			// c.getPA().showInterface(15106);
			c.getItems().writeBonus();
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;

		case 9178:
			if (c.dialogueAction == 31) {
				c.getPA().startTeleport(2925, 5331, 2, teleportType);
			}
			c.getPA().removeAllWindows();
			break;

		case 9179:
			if (c.dialogueAction == 31) {
				c.getPA().startTeleport(2864, 5354, 2, teleportType);
			}
			c.getPA().removeAllWindows();
			break;

		case 9180:
			if (c.dialogueAction == 31) {
				c.sendMessage("This chamber is not available right now.");
			}
			c.getPA().removeAllWindows();
			break;

		case 9181:
			if (c.dialogueAction == 31) {
				c.sendMessage("This chamber is not available right now.");
			}
			c.getPA().removeAllWindows();
			break;

		case 1093:
		case 1094:
		case 1097:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 4675)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient staff.");
				} else if (c.playerMagicBook == 0) {
					if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 4170) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;

		case 9157:
			if (c.wizardIndex > 0) {
				c.getDH().sendOption5("Corporeal Beast lair (PVP Zone)",
						"Callisto (Wildy)", "Vet'ion (Wildy)",
						"Chaos Fanatic (Wildy)", "Next Page");
				c.dialogueAction = 30;
				return;
			} else if (c.dialogueAction == 26) {
				c.getShops().openShop(6);
				c.dialogueAction = 0;
				return;
			} else if (c.dialogueAction == 20) {
				c.getDH().sendOption2("PK Exchange 1", "Pk Exchange 2");
				c.dialogueAction = 26;
				return;
			} else if (c.dialogueAction == 22) {
				FlowerGame.pickupFlower(c, c.lastFlowerPlanted);
			} else if (c.dialogueAction == 30) {
				c.getPA().sendFrame126("www.dragonicpk.com/store/", 12000);
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

		case 9158:
			if (c.dialogueAction == 26) {
				c.getShops().openShop(10);
				c.dialogueAction = 0;
				return;
			}
			if (c.dialogueAction == 20) {
				c.getShops().openShop(7);
				c.dialogueAction = 0;
				return;
			}
			if (c.dialogueAction == 25) {
				c.outStream.createFrame(27);
				return;
			}
			if (c.dialogueAction == 30) {
				MYSQLController.getStore().claim(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
			} else {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}
			break;

		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
			// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			if (c.getEquipment()[3] == 11791) {
				if (c.getCombat().checkSpecAmount(c.getEquipment()[3])) {
					c.playAnimation(Animation.create(7083));
					c.playGraphic(Graphic.create(1228, 0, 400));
					c.stofdDelay = System.currentTimeMillis();
					c.getItems().updateSpecialBar();
				} else {
					c.sendMessage("You have run out of special power.");
				}
			} else {
				c.specBarId = 8505;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			}
			break;

		case 29038:
			c.specBarId = 7486;
			c.getCombat().handleGmaulPlayer();
			c.getItems().updateSpecialBar();
			break;

		case 29063:
			if (c.getCombat().checkSpecAmount(
					c.getEquipment()[PlayerConstants.PLAYER_WEAPON])) {
				c.playGraphic(Graphic.create(246, 0, 0));
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.playAnimation(Animation.create(1056));
				c.getLevel()[2] = c.getLevelForXP(c.getExperience()[2])
						+ (c.getLevelForXP(c.getExperience()[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.playGraphic(Graphic.create(c.MAGIC_SPELLS[48][3], 0, 100));
			c.playAnimation(Animation.create(c.MAGIC_SPELLS[48][2]));
			c.usingMagic = false;
			break;

		case 28164: // item kept on death
			break;

		case 152:
			c.isRunning2 = !c.isRunning2;
			int frame = c.isRunning2 == true ? 1 : 0;
			c.getPA().sendFrame36(173, frame);
			break;

		case 9154:
			c.logout();
			break;

		/**
		 * Duel arena
		 */
		case 26065: // no forfeit
		case 26040:
			c.duelSlot = -1;
			c.getDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getDuel().selectRule(7);
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getDuel().selectRule(8);
			break;

		case 2158: // fun weapons
		case 2157:
			c.duelSlot = -1;
			c.getDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getDuel().selectRule(21);
			break;

		case 26018:
			Client o = (Client) PlayerHandler.players[c.duelingWith];
			if (o == null) {
				c.getDuel().declineDuel();
				return;
			}
			if (!c.inDuelArena() || !o.inDuelArena()) {
				c.getDuel().declineDuel();
				o.getDuel().declineDuel();
				return;
			}
			if (System.currentTimeMillis() - c.lastRuleSelected <= 5000) {
				c.sendMessage("A new rule has been selected, please wait 5 seconds before accepting.");
				return;
			}
			if (c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if (c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if (o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}

			if (c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getDuel().confirmDuel();
				o.getDuel().confirmDuel();
			}
			break;

		case 25120:
			if (c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) PlayerHandler.players[c.duelingWith];
			if (o1 == null) {
				c.getDuel().declineDuel();
				return;
			}
			if (!c.inDuelArena() || !o1.inDuelArena()) {
				c.getDuel().declineDuel();
				o1.getDuel().declineDuel();
				return;
			}
			c.duelStatus = 4;
			if (o1.duelStatus == 4 && c.duelStatus == 4) {
				c.getDuel().startDuel();
				o1.getDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
			break;

		case 13092:
			c.getTradeHandler().acceptStage1();
			break;

		case 13218:
			c.getTradeHandler().acceptStage2();
			break;

		case 21010:
			c.takeAsNote = true;
			break;

		case 21011:
			c.takeAsNote = false;
			break;

		// home teleports
		case 4171:
		case 50056:
		case 117048:
			String type = c.playerMagicBook == 1 ? "ancient" : "modern";
			c.getPA().startTeleport(3086 + Misc.random(2),
					3499 + Misc.random(2), 0, type);
			break;

		case 4140:
			c.getPA().startTeleport(Config.VARROCK_X, Config.VARROCK_Y, 0,
					"modern");
			break;

		case 4143:
			c.getPA()
					.startTeleport(Config.LUMBY_X, Config.LUMBY_Y, 0, "modern");
			break;

		case 4146:
			c.getPA().startTeleport(Config.FALADOR_X, Config.FALADOR_Y, 0,
					"modern");
			break;

		case 4150:
			c.getPA().startTeleport(Config.CAMELOT_X, Config.CAMELOT_Y, 0,
					"modern");
			break;

		case 6004:
			c.getPA().startTeleport(Config.ARDOUGNE_X, Config.ARDOUGNE_Y, 0,
					"modern");
			break;

		case 6005:
			c.getPA().startTeleport(Config.WATCHTOWER_X, Config.WATCHTOWER_Y,
					0, "modern");
			break;

		case 29031:
			c.getPA().startTeleport(Config.TROLLHEIM_X, Config.TROLLHEIM_Y, 0,
					"modern");
			break;

		case 50235:
			c.getPA().startTeleport(Config.PADDEWWA_X, Config.PADDEWWA_Y, 0,
					"ancient");
			break;

		case 50245:
			c.getPA().startTeleport(Config.SENNTISTEN_X, Config.SENNTISTEN_Y,
					0, "ancient");
			break;

		case 50253:
			c.getPA().startTeleport(Config.KHARYRLL_X, Config.KHARYRLL_Y, 0,
					"ancient");
			break;

		case 51005:
			c.getPA().startTeleport(Config.LASSAR_X, Config.LASSAR_Y, 0,
					"ancient");
			break;

		case 51013:
			c.getPA().startTeleport(Config.DAREEYAK_X, Config.DAREEYAK_Y, 0,
					"ancient");
			break;

		case 51023:
			c.getPA().startTeleport(Config.CARRALLANGAR_X,
					Config.CARRALLANGAR_Y, 0, "ancient");
			break;

		case 51031:
			c.getPA().startTeleport(Config.ANNAKARL_X, Config.ANNAKARL_Y, 0,
					"ancient");
			break;

		case 51039:
			c.getPA().startTeleport(Config.GHORROCK_X, Config.GHORROCK_Y, 0,
					"ancient");
			break;

		case 9125: // Accurate
		case 6221: // range accurate
		case 22228: // punch (unarmed)
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 22229: // block (unarmed)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 22230: // kick (unarmed)
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 8236: // slash (dagger)
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		/** Prayers **/
		case 21233: // thick skin
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			c.getCombat().activatePrayer(17);
			break;
		case 21247: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		case 2171: // retrui
			c.getCombat().activatePrayer(21);
			break;
		case 2172: // redem
			c.getCombat().activatePrayer(22);
			break;
		case 2173: // smite
			c.getCombat().activatePrayer(23);
			break;
		case 70092: // chiv
			c.getCombat().activatePrayer(24);
			break;
		case 70094: // piety
			c.getCombat().activatePrayer(25);
			break;

		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 125003:// Accept
			if (c.ruleAgreeButton) {
				c.getPA().showInterface(3559);
				c.newPlayer = false;
			} else if (!c.ruleAgreeButton) {
				c.sendMessage("You need to click on you agree before you can continue on.");
			}
			break;
		case 125006:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 74180:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 74188:
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74192:
			if (!c.isRunning2) {
				c.isRunning2 = true;
				c.getPA().sendFrame36(504, 1);
				c.getPA().sendFrame36(173, 1);
			} else {
				c.isRunning2 = false;
				c.getPA().sendFrame36(504, 0);
				c.getPA().sendFrame36(173, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 168:
			c.playAnimation(Animation.create(855));
			break;
		case 169:
			c.playAnimation(Animation.create(856));
			break;
		case 162:
			c.playAnimation(Animation.create(857));
			break;
		case 164:
			c.playAnimation(Animation.create(858));
			break;
		case 165:
			c.playAnimation(Animation.create(859));
			break;
		case 161:
			c.playAnimation(Animation.create(860));
			break;
		case 170:
			c.playAnimation(Animation.create(861));
			break;
		case 171:
			c.playAnimation(Animation.create(862));
			break;
		case 163:
			c.playAnimation(Animation.create(863));
			break;
		case 167:
			c.playAnimation(Animation.create(864));
			break;
		case 172:
			c.playAnimation(Animation.create(865));
			break;
		case 166:
			c.playAnimation(Animation.create(866));
			break;
		case 52050:
			c.playAnimation(Animation.create(2105));
			break;
		case 52051:
			c.playAnimation(Animation.create(2106));
			break;
		case 52052:
			c.playAnimation(Animation.create(2107));
			break;
		case 52053:
			c.playAnimation(Animation.create(2108));
			break;
		case 52054:
			c.playAnimation(Animation.create(2109));
			break;
		case 52055:
			c.playAnimation(Animation.create(2110));
			break;
		case 52056:
			c.playAnimation(Animation.create(2111));
			break;
		case 52057:
			c.playAnimation(Animation.create(2112));
			break;
		case 52058:
			c.playAnimation(Animation.create(2113));
			break;
		case 43092:
			c.playAnimation(Animation.create(0x558));
			break;
		case 2155:
			c.playAnimation(Animation.create(0x46B));
			break;
		case 25103:
			c.playAnimation(Animation.create(0x46A));
			break;
		case 25106:
			c.playAnimation(Animation.create(0x469));
			break;
		case 2154:
			c.playAnimation(Animation.create(0x468));
			break;
		case 52071:
			c.playAnimation(Animation.create(0x84F));
			break;
		case 52072:
			c.playAnimation(Animation.create(0x850));
			break;
		case 59062:
			c.playAnimation(Animation.create(2836));
			break;
		case 72032:
			c.playAnimation(Animation.create(3544));
			break;
		case 72033:
			c.playAnimation(Animation.create(3543));
			break;
		case 72254:
			c.playAnimation(Animation.create(3866));
			break;
		/* END OF EMOTES */

		case 24017:
			c.getPA().resetAutocast();
			c.getItems().sendWeapon(
					c.getEquipment()[PlayerConstants.PLAYER_WEAPON],
					c.getItems().getItemName(
							c.getEquipment()[PlayerConstants.PLAYER_WEAPON]));
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}
}
