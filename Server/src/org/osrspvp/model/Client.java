package org.osrspvp.model;

import org.apache.mina.common.IoSession;
import org.osrspvp.Config;
import org.osrspvp.Server;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.content.BankHandler;
import org.osrspvp.model.content.ShopAssistant;
import org.osrspvp.model.content.TradeHandler;
import org.osrspvp.model.content.combat.CombatAssistant;
import org.osrspvp.model.content.combat.Experience;
import org.osrspvp.model.content.minigame.Duelling;
import org.osrspvp.model.item.ItemAssistant;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.model.player.NewPlayer;
import org.osrspvp.model.player.Player;
import org.osrspvp.model.player.PlayerAssistant;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.model.player.PotionMixing;
import org.osrspvp.net.HostList;
import org.osrspvp.net.Packet;
import org.osrspvp.net.StaticPacketBuilder;
import org.osrspvp.net.packet.PacketHandler;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.Stream;
import org.osrspvp.util.cache.defs.ItemDef;
import org.osrspvp.world.Clan;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

public class Client extends Player {

	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new ConcurrentLinkedQueue<>();
	private Queue<Packet> queuedSubPackets = new ConcurrentLinkedQueue<>();
	private PotionMixing potionMixing = new PotionMixing(this);

	private TradeHandler tradeHandler = new TradeHandler(this);

	public TradeHandler getTradeHandler() {
		return tradeHandler;
	}

	private BankHandler bankHandler = new BankHandler(this);

	public BankHandler getBankHandler() {
		return bankHandler;
	}

	private Duelling duelling = new Duelling(this);

	public Duelling getDuel() {
		return this.duelling;
	}

	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	private Future<?> currentTask;

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0)
			return;
		StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		out.addBytes(temp);
		session.write(out.toPacket());
		outStream.currentOffset = 0;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		message = message.substring(0, 1).toUpperCase() + message.substring(1);
		clan = clan.substring(0, 1).toUpperCase() + clan.substring(1);
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public void joinOSRSPVPCC() {
		if (clan == null) {
			Clan localClan = Server.clanManager.getClan("help");
			if (localClan != null)
				localClan.addMember(this);
			else if ("help".equalsIgnoreCase(this.playerName))
				Server.clanManager.create(this);
			else {
				sendMessage(Misc.formatPlayerName("Help")
						+ " has disabled this clan for now.");
			}
			getPA().refreshSkill(21);
			getPA().refreshSkill(22);
			getPA().refreshSkill(23);
		}
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 0, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0, // 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, /* 0 */-1, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			6, 10, -1, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0, // 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 6, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, /* 0 */4, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4, // 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void destruct() {
		if (session == null)
			return;
		if (clan != null) {
			clan.removeMember(this);
		}
		CycleEventHandler.getSingleton().stopEvents(this);
		if (tradeHandler.getCurrentTrade() != null) {
			if (tradeHandler.getCurrentTrade().isOpen()) {
				tradeHandler.decline();
			}
		}
		if (duelStatus > 0 && duelStatus < 4) {
			Client o = ((Client) PlayerHandler.players[duelingWith]);
			if (o == null) {
				getDuel().declineDuel();
				return;
			}
			getDuel().declineDuel();
			o.getDuel().declineDuel();
		}
		Misc.println("[DEREGISTERED]: " + playerName + "");
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}

	public void sendMessage(String s) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}
	}

	private void addToBank() {
		int[] items = { 2444, 2440, 2436, 2442, 2434, 6685, 12905, 3024, 3751,
				3755, 3749, 10828, 8846, 8847, 8849, 8850, 1127, 4091, 2503,
				5575, 3840, 1199, 1201, 1079, 4093, 2497, 5576, 5698, 9244,
				1434, 4131, 4097, 6666, 3105, 4587, 4675, 9185, 3204, 1725,
				1704, 1731, 2550, 4327, 2414, 10499, 4331, 7462, 4095, 2491,
				7459, 379, 385, 11936, 3144 };
		for (int i = 0; i < items.length; i++) {
			this.bankItems[i] = items[i] + 1;
			this.bankItemsN[i] = ItemDef.isStackable(items[i] + 1) ? 10000
					: 100;
		}
		this.sendMessage("@blu@" + items.length
				+ "@bla@ items has been added to your bank.");
	}
	
	public void initialize() {
getPA().sendFrame126("Players Online: "+PlayerHandler.getPlayerCount()+ " ", 13136);
	getPA().sendFrame126("", 673);
	getPA().sendFrame126("", 7332);
	getPA().sendFrame126("", 7333);
        getPA().sendFrame126("", 7334);
	getPA().sendFrame126("", 7336);
	getPA().sendFrame126("", 7383);
	getPA().sendFrame126("", 7339);
	getPA().sendFrame126("", 7338);
	getPA().sendFrame126("", 7340);
	getPA().sendFrame126("", 7346);
	getPA().sendFrame126("", 7341);
	getPA().sendFrame126("", 7342);
	getPA().sendFrame126("", 7337);
	getPA().sendFrame126("", 7343);
	getPA().sendFrame126("", 7335);
	getPA().sendFrame126("", 7344);
	getPA().sendFrame126("", 7345);
	getPA().sendFrame126("", 7347);
	getPA().sendFrame126("", 7348);
	
	/*Members Quests*/
	getPA().sendFrame126("", 13356);
	getPA().sendFrame126("", 7351);
	getPA().sendFrame126("", 12772);
	getPA().sendFrame126("", 7352);
	getPA().sendFrame126("", 12129);
	getPA().sendFrame126("", 8438);
	getPA().sendFrame126("", 18517);
	getPA().sendFrame126("", 15847);
	getPA().sendFrame126("", 15487);
	getPA().sendFrame126("", 12852);
	getPA().sendFrame126("", 7354);
	getPA().sendFrame126("", 7355);
	getPA().sendFrame126("", 7356);
	getPA().sendFrame126("", 8679);
	getPA().sendFrame126("", 7459);
	getPA().sendFrame126("", 7357);
	getPA().sendFrame126("", 14912);
	getPA().sendFrame126("", 249);
	getPA().sendFrame126("", 6024);
	getPA().sendFrame126("", 191);
	getPA().sendFrame126("", 15235);
	getPA().sendFrame126("", 15592);
	getPA().sendFrame126("", 6987);
	getPA().sendFrame126("", 15098);
	getPA().sendFrame126("", 15352);
	getPA().sendFrame126("", 18306);
	getPA().sendFrame126("", 15499);
	getPA().sendFrame126("", 668);
	getPA().sendFrame126("", 18684);
	getPA().sendFrame126("", 6027);
	getPA().sendFrame126("", 18157);
	getPA().sendFrame126("", 15847);
	getPA().sendFrame126("", 16128);
	getPA().sendFrame126("", 12836);
	getPA().sendFrame126("", 16149);
	getPA().sendFrame126("", 15841);
	getPA().sendFrame126("", 7353);
	getPA().sendFrame126("", 7358);
	getPA().sendFrame126("", 17510);
	getPA().sendFrame126("", 7359);
	getPA().sendFrame126("", 14169);
	getPA().sendFrame126("", 10115);
	getPA().sendFrame126("", 14604);
	getPA().sendFrame126("", 7360);
	getPA().sendFrame126("", 12282);
	getPA().sendFrame126("", 13577);
	getPA().sendFrame126("", 12839);
	getPA().sendFrame126("", 7361);
	getPA().sendFrame126("", 11857);
	getPA().sendFrame126("", 7362);
	getPA().sendFrame126("", 7363);
	getPA().sendFrame126("", 7364);
	getPA().sendFrame126("", 10135);
	getPA().sendFrame126("", 4508);
	getPA().sendFrame126("", 11907);
	getPA().sendFrame126("", 7365);
	getPA().sendFrame126("", 7366);
	getPA().sendFrame126("", 7367);
	getPA().sendFrame126("", 13389);
	getPA().sendFrame126("", 7368);
	getPA().sendFrame126("", 11132);
	getPA().sendFrame126("", 7369);
	getPA().sendFrame126("", 12389);
	getPA().sendFrame126("", 13974);
	getPA().sendFrame126("", 7370);
	getPA().sendFrame126("", 8137);
	getPA().sendFrame126("", 7371);
	getPA().sendFrame126("", 12345);
	getPA().sendFrame126("", 7372);
	getPA().sendFrame126("", 8115);
	getPA().sendFrame126("", 8576);
	getPA().sendFrame126("", 12139);
	getPA().sendFrame126("", 7373);
	getPA().sendFrame126("", 7374);
	getPA().sendFrame126("", 8969);
	getPA().sendFrame126("", 7375);
	getPA().sendFrame126("", 7376);
	getPA().sendFrame126("", 1740);
	getPA().sendFrame126("", 3278);
	getPA().sendFrame126("", 7378);
	getPA().sendFrame126("", 6518);
	getPA().sendFrame126("", 7379);
	getPA().sendFrame126("", 7380);
	getPA().sendFrame126("", 7381);
	getPA().sendFrame126("", 11858);
	getPA().sendFrame126("", 9927);
	getPA().sendFrame126("", 7349);
	getPA().sendFrame126("", 7350);
	getPA().sendFrame126("", 7351);
	getPA().sendFrame126("", 13356);
	/*END OF ALL QUESTS*/
		outStream.createFrame(249);
		outStream.writeByteA(1); // 1 for members, zero for free
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		this.getPA().sendFrame126("Lumbridge", 28070);
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, getLevel()[i], getExperience()[i]);
			getPA().refreshSkill(i);
		}
		for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		if (this.inDuelArena()) {
			this.getPA().movePlayer(3362 + Misc.random(5),
					3263 + Misc.random(5), 0);
		}
		this.getItems().resetItems(3214);
		sendMessage("@cr6@Welcome to @blu@Dragonic-Pk@bla@! There are currently @blu@"
				+ PlayerHandler.playerCount + "@bla@ players online!@cr6@");
		sendMessage("You can join the clanchat '@blu@help@bla@' if you got any questions or just wanna chat!");
		sendMessage("Find all working commands by typing @blu@::commands");
		if (this.getAccountPin()[1] == null) {
			this.getDH().lineText3(
					"It seems that you do not have a security pin!",
					"We we recommend that you set a pin by typing",
					"@blu@::newpin @red@pinhere");
		}
		NewPlayer.newPlayer(this);
		getPA().handleWeaponStyle();
		getPA().handleLoginText();
		accountFlagged = getPA().checkForFlags();
		// getPA().sendFrame36(43, fightMode-1);
		getPA().sendFrame36(108, 0);// resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		setSidebarInterface(1, 3917);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		} else {
			if (playerMagicBook == 2) {
				setSidebarInterface(6, 29999); // lunar
			} else {
				setSidebarInterface(6, 12855); // ancient
			}
		}
		correctCoordinates();
		setSidebarInterface(7, 18128);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715);
		setSidebarInterface(10, 2449);
		// setSidebarInterface(11, 4445); // wrench tab - bugged don't use
		setSidebarInterface(11, 904); // wrench tab
		setSidebarInterface(12, 147); // run tab
		setSidebarInterface(13, 28060);
		setSidebarInterface(14, 28100);
		setSidebarInterface(0, 2423);
		getPA().sendOption("Follow", 2);
		getPA().sendOption("Trade With", 3);
		getItems().sendWeapon(
				getEquipment()[PlayerConstants.PLAYER_WEAPON],
				getItems().getItemName(
						getEquipment()[PlayerConstants.PLAYER_WEAPON]));
		getItems().resetBonus();
		getItems().getBonus();
		getItems().writeBonus();
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_HAT], 1,
				PlayerConstants.PLAYER_HAT);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_CAPE], 1,
				PlayerConstants.PLAYER_CAPE);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_AMULET],
				1, PlayerConstants.PLAYER_AMULET);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_ARROWS],
				playerEquipmentN[PlayerConstants.PLAYER_ARROWS],
				PlayerConstants.PLAYER_ARROWS);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_BODY], 1,
				PlayerConstants.PLAYER_BODY);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_SHIELD],
				1, PlayerConstants.PLAYER_SHIELD);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_LEGS], 1,
				PlayerConstants.PLAYER_LEGS);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_GLOVES],
				1, PlayerConstants.PLAYER_GLOVES);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_FEET], 1,
				PlayerConstants.PLAYER_FEET);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_RING], 1,
				PlayerConstants.PLAYER_RING);
		getItems().setEquipment(getEquipment()[PlayerConstants.PLAYER_WEAPON],
				playerEquipmentN[PlayerConstants.PLAYER_WEAPON],
				PlayerConstants.PLAYER_WEAPON);
		combatLevel = PlayerConstants.getCombatLevel(this);
		getCombat().getPlayerAnimIndex();
		getPA().logIntoPM();
		getItems().addSpecialBar(getEquipment()[PlayerConstants.PLAYER_WEAPON]);
		saveTimer = Config.SAVE_TIMER;
		saveCharacter = true;
		Misc.println("[REGISTERED]: " + playerName + "");
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		getPA().clearClanChat();
		getPA().setClanData();
		getPA().resetFollow();
		if (autoRet == 1)
			getPA().sendFrame36(172, 1);
		else
			getPA().sendFrame36(172, 0);
	}

	public void update() {
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
	}

	public void logout() {
		if (this.duelStatus > 4) {
			this.sendMessage("You can not log out in a duel!");
			return;
		}
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			if (this.clan != null) {
				this.clan.removeMember(this);
			}
			CycleEventHandler.getSingleton().stopEvents(this);
			outStream.createFrame(109);
			properLogout = true;
		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}
	}

	public int packetSize = 0, packetType = -1;
	public int donatorPoints = 0;
	public int funds = 0;
	public boolean hideMe;

	public void process() {



		if (System.currentTimeMillis() - this.duelDelay > 800L
				&& this.duelCount > 0
				&& PlayerHandler.players[this.duelingWith] != null) {
			if (this.duelCount != 1) {
				this.forcedChat("" + (this.duelCount -= 1));
				this.duelDelay = System.currentTimeMillis();
			} else {
				this.forcedChat("FIGHT!");
				this.duelCount = 0;
			}
		}

		if (System.currentTimeMillis() - lastVenom > 20000 && venomDamage >= 6) {
			if (!getHitUpdateRequired()) {
				setHitUpdateRequired(true);
				setHitDiff(venomDamage);
				updateRequired = true;
				venomMask = 1;
			} else if (!getHitUpdateRequired2()) {
				setHitUpdateRequired2(true);
				setHitDiff2(venomDamage);
				updateRequired = true;
				venomMask = 2;
			}
			dealDamage(venomDamage);
			venomDamage += 2;
			if (venomDamage > 20) {
				venomDamage = 20;
			}
			this.getPA().refreshSkill(3);
			lastVenom = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage / 2;
			if (damage > 0) {
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				if (damage > getLevel()[3])
					damage = getLevel()[3];
				dealDamage(damage);
				getPA().refreshSkill(3);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}
		}

		if (clickObjectType > 0
				&& goodDistance(objectX + objectXOffset, objectY
						+ objectYOffset, getX(), getY(), objectDistance)) {
			if (clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}

		if ((clickNpcType > 0) && NPCHandler.npcs[npcClickIndex] != null) {
			if (goodDistance(getX(), getY(),
					NPCHandler.npcs[npcClickIndex].getX(),
					NPCHandler.npcs[npcClickIndex].getY(), 1)) {
				if (clickNpcType == 1) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(),
							NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if (clickNpcType == 2) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(),
							NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if (clickNpcType == 3) {
					turnPlayerTo(NPCHandler.npcs[npcClickIndex].getX(),
							NPCHandler.npcs[npcClickIndex].getY());
					NPCHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}

		if (walkingToItem) {
			if (getX() == pItemX && getY() == pItemY
					|| goodDistance(getX(), getY(), pItemX, pItemY, 1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX,
						pItemY, true);
			}
		}

		if (followId2 > 0) {
			getPA().followNpc();
		}

		getCombat().handlePrayerDrain();

		if (System.currentTimeMillis() - singleCombatDelay > 4400) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - teleGrabDelay > 1550 && usingMagic) {
			usingMagic = false;
			if (Server.itemHandler.itemExists(teleGrabItem, teleGrabX,
					teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem,
						teleGrabX, teleGrabY, true);
			}
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == 0) {
			getPA().applyDead();
		}

		if (respawnTimer == 3) {
			respawnTimer = 0;
			getPA().giveLife();
		}

		if (respawnTimer > 0) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (PlayerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						PlayerHandler.players[frozenBy].absX,
						PlayerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					playGraphic(Graphic.create(teleGfx, 0, 100));
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().appendDamageNPC(oldNpcIndex, combatStyle, 1);
				if (doubleHit) {
					Experience.calculateDamageNPC(this, oldNpcIndex,
							combatStyle);
					getCombat().appendDamageNPC(oldNpcIndex, combatStyle, 2);
				}
			} else if (oldPlayerIndex > 0) {
				getCombat().appendDamage(oldPlayerIndex, combatStyle, 1);
			}
		}

		if (this.dbowTimer > 0) {
			this.dbowTimer--;
			if (this.dbowTimer == 2) {
				if (dbowIndex > 0) {
					getCombat().appendDamage(dbowIndex, 1, 1);
				}
				if (dbowIndex1 > 1) {
					getCombat().appendDamageNPC(dbowIndex1, 1, 1);
				}
			}
			if (this.dbowTimer == 1) {
				if (dbowIndex > 0) {
					if (this.dbowSpec) {
						((Client) PlayerHandler.players[this.dbowIndex])
								.playGraphic(Graphic.create(1100, 0, 100));
					}
					Experience.calculateDamage(this,
							(Client) PlayerHandler.players[this.dbowIndex], 1);
					getCombat().appendDamage(dbowIndex, 1, 1);
				}
				if (dbowIndex1 > 1) {
					if (this.dbowSpec) {
						NPCHandler.npcs[dbowIndex1].gfx100(1100);
					}
					Experience.calculateDamageNPC(this, dbowIndex1, 1);
					getCombat().appendDamageNPC(dbowIndex1, 1, 1);
				}
				dbowSpec = false;
				dbowIndex = -1;
				dbowIndex1 = -1;
				dbowTimer = 0;
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public IoSession getSession() {
		return session;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	/**
	 * End of Skill Constructors
	 */

	public void queueMessage(Packet arg1) {
		packetsReceived.incrementAndGet();
		queuedPackets.add(arg1);
	}

	public void queueSubMessage(Packet arg1) {
		queuedSubPackets.add(arg1);
	}

	public void processSubQueuedPackets() {
		packetsReceived.set(0);
		for (;;) {
			Packet next = queuedSubPackets.poll();
			if (next == null) {
				break;
			}
			inStream.currentOffset = 0;
			packetType = next.getId();
			packetSize = next.getLength();
			inStream.buffer = next.getData();
			if (packetType == 41 || packetType == 185) {
				PacketHandler.processSubPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
		}
	}

	public void processQueuedPackets() {
		packetsReceived.set(0);
		for (;;) {
			Packet next = queuedPackets.poll();
			if (next == null) {
				break;
			}
			inStream.currentOffset = 0;
			packetType = next.getId();
			packetSize = next.getLength();
			inStream.buffer = next.getData();
			if (packetType > 0 && packetType != 41 && packetType != 185) {// but where
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
		}
	}

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");

		}

	}

	@Override
	public void tick() {
		if (this.updateBankItems) {
			this.getItems().rearrangeBank();
			this.getItems().resetBank();
			this.getItems().resetTempItems();
		}
		this.getItems().resetItems(3214);
	}
}
