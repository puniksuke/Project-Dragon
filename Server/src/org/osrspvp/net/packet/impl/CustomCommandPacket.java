package org.osrspvp.net.packet.impl;

import org.osrspvp.Server;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.Graphic;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.sanction.SanctionHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.NPCDef;
import org.osrspvp.world.ShopHandler;

/**
 * Commands reconfigured by Jack
 */
public class CustomCommandPacket implements PacketType {

	private static final String[] invalidInput = { "\n", "\r" }; // thats why
	// lol

	public static int amountVoted = 0;

	// u using a new class fr cmmands,

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if (AccountPinManager.hasToTypePin(c)
				&& !playerCommand.startsWith("pin")) {
			AccountPinManager.openPinInterface(c);
			return;
		}
		for (String characters : invalidInput) {
			if (playerCommand.toLowerCase().contains(characters.toLowerCase())) {
				c.sendMessage("You can't type this.");
				return;
			}
		}
		Misc.println(c.playerName + " playerCommand: " + playerCommand);
		playerCommands(c, playerCommand);
		if (RankHandler.isDeveloper(c.playerName)) {
			ownerCommands(c, playerCommand);
		}
		if (RankHandler.isModerator(c.playerName)
				|| RankHandler.isAdministrator(c.playerName)
				|| RankHandler.isDeveloper(c.playerName)) {
			moderatorCommands(c, playerCommand);
		}
		if (RankHandler.isAdministrator(c.playerName)
				|| RankHandler.isDeveloper(c.playerName)) {
			adminCommands(c, playerCommand);
		}
		if (RankHandler.isPremium(c.playerName)
				|| RankHandler.isSuperPremium(c.playerName)
				|| RankHandler.isExtremePremium(c.playerName)
				|| RankHandler.isModerator(c.playerName)
				|| RankHandler.isSupporter(c.playerName)
				|| RankHandler.isDicer(c.playerName)
				|| RankHandler.isVeteran(c.playerName)
				|| RankHandler.isAdministrator(c.playerName)
				|| RankHandler.isDeveloper(c.playerName)) {
			customCommands(c, playerCommand);
		}
	}

	public static void customCommands(Client c, String playerCommand) {

		
		if (playerCommand.startsWith("bank")) {
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			if (RankHandler.isSuperPremium(c.playerName)) {
			c.getPA().openUpBank();
		} else if (RankHandler.isExtremePremium(c.playerName)) {
				c.getPA().openUpBank();
		} else if (RankHandler.isDeveloper(c.playerName)) {
			c.getPA().openUpBank();
				 }
		}
		if (playerCommand.startsWith("yell")) {
			String msg = playerCommand.substring(5);
			if (msg.length() > 0) {
				if (RankHandler.isPremium(c.playerName)) {
					c.getPA().globalYell(
							"[@red@Donator@bla@]" + c.playerName
									+ "@blu@: @red@" + msg);
				} else if (RankHandler.isSuperPremium(c.playerName)) {
					c.getPA().globalYell(
							"[@blu@Super Donator@bla@]" + c.playerName
									+ "@blu@: @blu@" + msg);
				} else if (RankHandler.isExtremePremium(c.playerName)) {
					c.getPA().globalYell(
							"[@gre@Extreme Donator@bla@]" + c.playerName
									+ "@blu@: @gre@" + msg);
				} else if (RankHandler.isSupporter(c.playerName)) {
					c.getPA().globalYell(
							"[@blu@Supporter@bla@]@blu@" + c.playerName
									+ "@bla@: @red@" + msg);
				} else if (RankHandler.isModerator(c.playerName)) {
					c.getPA().globalYell(
							"[@blu@Moderator@bla@]@blu@" + c.playerName
									+ "@bla@: @red@" + msg);
				} else if (RankHandler.isAdministrator(c.playerName)) {
					c.getPA().globalYell(
							"[@yel@Administrator@bla@]@blu@" + c.playerName
									+ "@bla@: @red@" + msg);
				} else if (RankHandler.isDeveloper(c.playerName)) {
					if (c.playerName.equalsIgnoreCase("tyler"))
						c.getPA().globalYell(
								"[@red@Owner@bla@]@blu@" + c.playerName
										+ "@bla@: @red@" + msg);
					else
						c.getPA().globalYell(
								"[@red@Community Manager@bla@]@blu@"
										+ c.playerName + "@bla@: @red@" + msg);
				} else if (RankHandler.isDicer(c.playerName)) {
					c.getPA().globalYell(
							"[@blu@MM@bla@]@blu@" + c.playerName
									+ "@bla@: @red@" + msg);
				} else if (RankHandler.isVeteran(c.playerName)) {
					c.getPA().globalYell(
							"[@yel@Veteran@bla@]@blu@" + c.playerName
									+ "@bla@: @red@" + msg);
				}
			}
		}
	}

	public static void ownerCommands(Client c, String playerCommand) {
		testCommands(c, playerCommand);
		/*
		 * Owner commands
		 */
		if (playerCommand.startsWith("item")) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 25000) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
						System.out.println("Spawned: " + newItemID + " by: "
								+ c.playerName);
					} else {
						c.sendMessage("No such item.");
					}
				} else {
					c.sendMessage("Use as ::item 995 200");
				}
			} catch (Exception e) {
			}
		}
		if (playerCommand.equalsIgnoreCase("alltome")) {
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] == null) {
					continue;
				}
				c.sendMessage("You have teleported to @blu@"
						+ PlayerHandler.players[i].playerName + "@bla@ to you.");
				((Client) PlayerHandler.players[i]).getPA().movePlayer(
						c.getX(), c.getY(), c.heightLevel);
			}
		}
		if (playerCommand.equalsIgnoreCase("infpray")) {
			c.getPA().requestUpdates();
			c.getLevel()[5] = 99999999;
			c.getPA().refreshSkill(5);
		}
		if (playerCommand.equalsIgnoreCase("infhp")) {
			c.getPA().requestUpdates();
			c.getLevel()[3] = 99999999;
			c.getPA().refreshSkill(3);
		}
		if (playerCommand.startsWith("pnpc")) {
			int npc = Integer.parseInt(playerCommand.substring(5));
			if (npc < 9999) {
				c.npcId2 = npc;
				c.isNpc = true;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
			}
		}
		if (playerCommand.startsWith("invisible")) { // do ::unpc
			c.npcId2 = 748;
			c.isNpc = true;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
		if (playerCommand.startsWith("unpc")) {
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
		if (playerCommand.startsWith("empty")) {
			c.getItems().removeAllItems();
		}
		if (playerCommand.startsWith("getid")) {
			String name = playerCommand.substring(6);
			if (name.length() > 0) {
				for (int i = 0; i < NPCDef.totalNpcs; i++) {
					if (NPCDef.forId(i) == null)
						continue;
					if (NPCDef.forId(i).name == null)
						continue;
					if (name.contains(NPCDef.forId(i).name)) {
						c.sendMessage(NPCDef.forId(i).name);
					}
				}
			}
		}

		if (playerCommand.startsWith("getanim")) {
			String[] args = playerCommand.split(" ");
			if (args.length == 2) {
				int npcId = Integer.valueOf(args[1]);
				c.sendMessage("[NPC_EMOTES] Stand: "
						+ NPCDef.forId(npcId).standAnim + "	Walk: "
						+ NPCDef.forId(npcId).walkAnim + "");
			}
		}
		if (playerCommand.startsWith("reloadshops")) {
			Server.shopHandler = new ShopHandler();
			Server.shopHandler.loadShops("shops.cfg");
		}
		if (playerCommand.startsWith("update")) {
			PlayerHandler.updateSeconds = 120;
			PlayerHandler.updateAnnounced = false;
			PlayerHandler.updateRunning = true;
			PlayerHandler.updateStartTime = System.currentTimeMillis();
		}
		if (playerCommand.startsWith("giveadmin")) {
			String playerName = playerCommand.substring(10);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveAdmin(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givedev")) {
			String playerName = playerCommand.substring(8);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveDev(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givemod")) {
			String playerName = playerCommand.substring(8);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveMod(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givedonor")) {
			String playerName = playerCommand.substring(10);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.givePremium(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givesuper")) {
			String playerName = playerCommand.substring(10);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveSuperPremium(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("giveextreme")) {
			String playerName = playerCommand.substring(12);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveExtremePremium(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givesupporter")) {
			String playerName = playerCommand.substring(14);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveSupporter(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givedicer")) {
			String playerName = playerCommand.substring(10);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveDicer(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
		if (playerCommand.startsWith("givevet")) {
			String playerName = playerCommand.substring(8);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				RankHandler.giveVeteran(playerName);
				c.sendMessage("You have promoted: @blu@" + playerName
						+ "@bla@.");
			}
		}
	}

	public static void adminCommands(Client c, String playerCommand) {
		if (playerCommand.startsWith("unban")) {
			String playerName = playerCommand.substring(6);
			if (playerName.length() > 0) {
				SanctionHandler.deleteFromSactionList(SanctionHandler.LOCATION
						+ "bans.txt", playerName);
				c.sendMessage("You have unbanned: @blu@" + playerName
						+ "@bla@.");
			}
		}

		if (playerCommand.startsWith("ban")) {
			String playerName = playerCommand.substring(4);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					PlayerHandler.players[i].disconnected = true;
				}
				SanctionHandler.banPlayer(playerName);
				c.sendMessage("You have banned: @blu@" + playerName + "@bla@.");
			}
		}
		if (playerCommand.startsWith("ipban")) {
			String playerName = playerCommand.substring(6);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					c.sendMessage("You have banned IP "
							+ PlayerHandler.players[i].connectedFrom);
					SanctionHandler
							.IPBanPlayer(PlayerHandler.players[i].connectedFrom);
					PlayerHandler.players[i].disconnected = true;
				}
			}
		}
		if (playerCommand.startsWith("ipmute")) {
			String playerName = playerCommand.substring(7);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					((Client) PlayerHandler.players[i])
							.sendMessage("Your account has been IP muted by @blu@"
									+ c.playerName + "@bla@.");
					SanctionHandler
							.IPMutePlayer(PlayerHandler.players[i].connectedFrom);
				}
			}
		}

	}

	public static void moderatorCommands(Client c, String playerCommand) {
		if (playerCommand.startsWith("xteletome")) {
			String playerName = playerCommand.substring(10);
			System.out.println("we get here.");
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] == null) {
					continue;
				}
				if (!PlayerHandler.players[i].playerName
						.equalsIgnoreCase(playerName)) {
					continue;
				}
				c.sendMessage("You have teleported to @blu@"
						+ PlayerHandler.players[i].playerName + "@bla@ to you.");
				((Client) PlayerHandler.players[i])
						.sendMessage("You have been teleported to @blu@"
								+ c.playerName + "@bla@.");
				((Client) PlayerHandler.players[i]).getPA().movePlayer(
						c.getX(), c.getY(), c.heightLevel);
			}
		}

		if (playerCommand.startsWith("xteleto")) {
			String playerName = playerCommand.substring(8);
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				if (PlayerHandler.players[i] == null) {
					continue;
				}
				if (!PlayerHandler.players[i].playerName
						.equalsIgnoreCase(playerName)) {
					continue;
				}
				c.sendMessage("You have teleported to @blu@"
						+ PlayerHandler.players[i].playerName + "@bla@.");
				c.getPA().movePlayer(PlayerHandler.players[i].getX(),
						PlayerHandler.players[i].getY(),
						PlayerHandler.players[i].heightLevel);
			}
		}

		if (playerCommand.startsWith("unmute")) {
			String playerName = playerCommand.substring(7);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					((Client) PlayerHandler.players[i])
							.sendMessage("Your account has been unmuted by @blu@"
									+ c.playerName + "@bla@.");
				}
				SanctionHandler.deleteFromSactionList(SanctionHandler.LOCATION
						+ "mutes.txt", playerName);
				c.sendMessage("You have unmuted: @blu@" + playerName + "@bla@.");
			}
		}

		if (playerCommand.startsWith("mute")) {
			String playerName = playerCommand.substring(5);
			if (playerName.length() > 0) {
				for (int i = 0; i < PlayerHandler.players.length; i++) {
					if (PlayerHandler.players[i] == null) {
						continue;
					}
					if (!PlayerHandler.players[i].playerName
							.equalsIgnoreCase(playerName)) {
						continue;
					}
					((Client) PlayerHandler.players[i])
							.sendMessage("Your account has temporarily been muted by @blu@"
									+ c.playerName + "@bla@.");
				}
				SanctionHandler.mutePlayer(playerName);
				c.sendMessage("You have muted: @blu@" + playerName + "@bla@.");
			}
		}
	}

	public static void playerCommands(Client c, String playerCommand) {
		if (playerCommand.startsWith("/")) {
			if (c.clan != null) {
				if (SanctionHandler.isMuted(c) || SanctionHandler.isIPMuted(c)) {
					c.sendMessage("You are muted and can't speak.");
					return;
				}
				String message = playerCommand.substring(1);
				c.clan.sendChat(c, message);
			} else {
				c.sendMessage("You can only do this in a clan chat..");
			}
		}
		if (playerCommand.equalsIgnoreCase("stuck")) {
			if (System.currentTimeMillis() - c.lastStuck < 30000) {
				c.sendMessage("You can only use this command every once 30 seconds.");
				return;
			}
			if (c.duelStatus > 0 && c.duelStatus <= 5 || c.underAttackBy > 0) {
				c.sendMessage("You can't do this right now.");
			}
			c.getPA().globalYell(
					"@cr6@[@blu@STUCK@bla@]@red@" + c.playerName
							+ " is stuck and needs help.");
			c.lastStuck = System.currentTimeMillis();
		}
		if (playerCommand.equalsIgnoreCase("resetkdr")) {
			if (RankHandler.isPremium(c.playerName)
					|| RankHandler.isSuperPremium(c.playerName)
					|| RankHandler.isExtremePremium(c.playerName)) {
				c.kills = 0;
				c.deaths = 0;
			} else {
				c.sendMessage("You need to be a donator or higher rank to use this command.");
			}
		}
		if (playerCommand.startsWith("newpin")) {
			if (c.getAccountPin()[1] != null) {
				c.sendMessage("You already have a security pin...");
				return;
			}
			String pin = playerCommand.substring(7);
			if (pin.length() <= 0) {
				c.sendMessage("That pin is too short.");
				return;
			}
			c.getAccountPin()[0] = pin;
			c.getAccountPin()[1] = pin;
			c.getDH().lineText(
					"Your security pin has been set to @red@'@blu@" + pin
							+ "@red@'@bla@.");
			c.nextChat = 0;
		}
		if (playerCommand.startsWith("pin")) {
			if (c.getAccountPin()[1] == null) {
				c.sendMessage("You have not set a security pin yet.");
				c.sendMessage("You can set a security pin by typing ::newpin pinhere");
				return;
			}
			String pin = playerCommand.substring(4);
			if (pin.length() <= 0) {
				c.sendMessage("That's not an actual pin.");
				return;
			}
			if (pin.equalsIgnoreCase(c.getAccountPin()[1])) {
				c.getDH().lineText("Your account has been unlocked.");
				c.nextChat = 0;
				c.getAccountPin()[0] = pin;
			} else {
				c.getDH().lineText("That pin is incorrect for this account.");
				c.nextChat = 0;
			}
		}
		if (playerCommand.startsWith("setlevel")
				|| playerCommand.startsWith("setlvl")) {
			c.sendMessage("@blu@To set your levels click the Skill tab, click the skill and set the values");
		}
		if (playerCommand.equalsIgnoreCase("commands")) {
			c.sendMessage("::gamble, ::duel, ::players, ::barrage, ::veng, ::changepassword <pass>, ::kdr");
			c.sendMessage("::vote, ::donate, ::funpk (you don't lose items here), ::stuck");
			c.sendMessage("::resetkdr");
		}
		if (playerCommand.equalsIgnoreCase("kdr")) {
			c.getDH().sendStartInfo(
					"Kills: " + Misc.formatNumbers(c.kills),
					"Deaths: " + Misc.formatNumbers(c.deaths),
					"Radtio: " + c.getPA().getKdr(),
					"",
					Misc.formatPlayerName(c.playerName)
							+ "'s Kill, Death, Ratio");
		}
		if (playerCommand.equalsIgnoreCase("vote")) {
			c.getPA().sendFrame126("www.dragonicpk.com/vote/", 12000);
		}
		if (playerCommand.equalsIgnoreCase("donate")) {
			c.getPA().sendFrame126("www.dragonicpk.com/store/", 12000);
		}
		if (playerCommand.equalsIgnoreCase("barrage")) {
			if (c.inDuelArena()) {
				c.sendMessage("You can't do this here.");
				return;
			}
			c.getItems().addItem(560, 5000);
			c.getItems().addItem(565, 5000);
			c.getItems().addItem(555, 5000);
		}
		if (playerCommand.equalsIgnoreCase("veng")) {
			if (c.inDuelArena()) {
				c.sendMessage("You can't do this here.");
				return;
			}
			c.getItems().addItem(9075, 5000);
			c.getItems().addItem(557, 5000);
			c.getItems().addItem(560, 5000);
		}

		if (playerCommand.startsWith("changepassword")
				&& playerCommand.length() > 15) {
			c.playerPass = playerCommand.substring(15);
			c.sendMessage("Your password is now: " + c.playerPass);
		}

		if (playerCommand.equalsIgnoreCase("funpk")) {
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(3328, 4751, 0,
					c.playerMagicBook == 1 ? "ancient" : "modern");
			c.sendMessage("Risk free PKing zone.");
		}
		if (playerCommand.equalsIgnoreCase("gamble")) {
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(2086, 4466, 0,
					c.playerMagicBook == 1 ? "ancient" : "modern");
			c.sendMessage("Gambling is at your own risk.");
		}
		if (playerCommand.equalsIgnoreCase("duel")) {
			if (!c.inSafeZone()) {
				c.sendMessage("Please run to safety first.");
				return;
			}
			c.getPA().startTeleport(3367, 3269, 0,
					c.playerMagicBook == 1 ? "ancient" : "modern");
		}
		if (playerCommand.equals("home")) {
 			c.getPA().startTeleport(2905, 3611, 0, "modern"); 
 			} 
		if (playerCommand.equalsIgnoreCase("players")) {
			if (PlayerHandler.getPlayerCount() > 1)
				c.sendMessage("There are currently @blu@"
						+ PlayerHandler.getPlayerCount()
						+ "@bla@ players online!");
			else
				c.sendMessage("There are currently @blu@"
						+ PlayerHandler.getPlayerCount()
						+ "@bla@ player online!");
		}
	}

	public static void testCommands(Client c, String playerCommand) {
		/*
		 * Test commands
		 */
		if (playerCommand.startsWith("dialogue")) {
			int npcType = 1552;
			int id = Integer.parseInt(playerCommand.split(" ")[1]);
			c.getDH().sendDialogues(id, npcType);
		}
		if (playerCommand.startsWith("interface")) {
			String[] args = playerCommand.split(" ");
			c.getPA().showInterface(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("gfx")) {
			String[] args = playerCommand.split(" ");
			c.playGraphic(Graphic.create(Integer.parseInt(args[1]), 0, 0));
		}
		if (playerCommand.startsWith("anim")) {
			String[] args = playerCommand.split(" ");
			c.playAnimation(Animation.create(Integer.parseInt(args[1])));
			c.getPA().requestUpdates();
		}
		if (playerCommand.equalsIgnoreCase("mypos")) {
			c.sendMessage("X: " + c.absX);
			c.sendMessage("Y: " + c.absY);
			c.sendMessage("H: " + c.heightLevel);
		}
		if (playerCommand.startsWith("head")) {
			String[] args = playerCommand.split(" ");
			c.sendMessage("new head = " + Integer.parseInt(args[1]));
			c.headIcon = Integer.parseInt(args[1]);
			c.getPA().requestUpdates();
		}
		if (playerCommand.startsWith("spec")) {
			String[] args = playerCommand.split(" ");
			c.specAmount = (Integer.parseInt(args[1]));
			c.getItems().updateSpecialBar();
		}
		if (playerCommand.startsWith("tele")) {
			String[] arg = playerCommand.split(" ");
			if (arg.length > 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),
						Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]),
						Integer.parseInt(arg[2]), c.heightLevel);
		}
		if (playerCommand.startsWith("seth")) {
			try {
				String[] args = playerCommand.split(" ");
				c.heightLevel = Integer.parseInt(args[1]);
				c.getPA().requestUpdates();
			} catch (Exception e) {
				c.sendMessage("fail");
			}
		}

		if (playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if (newNPC > 0) {
					Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY,
							c.heightLevel, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch (Exception e) {

			}
		}
		if (playerCommand.startsWith("interface")) {
			try {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				c.getPA().showInterface(a);
			} catch (Exception e) {
				c.sendMessage("::interface ####");
			}
		}
	}
}