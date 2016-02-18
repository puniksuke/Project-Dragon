// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class RSInterface {

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public static void clientSettings(TextDrawingArea[] tda) {
		RSInterface rsi = addTabInterface(17300);
		addSprite(17301, 8, "orbs/SPRITE");
		addText(17302, "@yel@Settings", tda, 2, 0xFFFFFF, true, true);
		addHoverButton(17303, "orbs/Sprite", 7, 16, 16, "Close", -1, 17304, 1);
		addHoveredButton(17304, "orbs/Sprite", 6, 16, 16, 17305);
		addSprite(17306, 9, "orbs/SPRITE");
		addSprite(17307, 9, "orbs/SPRITE");
		addHoverButton(17295, "orbs/sprite", 5, 23, 24, "Next", -1, 17296, 1);
		addHoveredButton(17296, "orbs/sprite", 4, 23, 24, 17297);
		int child1 = 0;
		int base = 17310;
		String[] settingName = { "Gameframe: @gr3@OSRS", "Hitsplats: @gr3@Old School", "Old Wild level",
				"Zoom: @gr3@Normal", "XP counter  ", "X10 damage  ", "New HP bar  " };
		int settingsAmount = settingName.length;
		int order1 = 0;
		for (int i = 0; i < settingsAmount; i++) {
			addHoverButton(base + child1, "orbs/sprite", 5, 122, 24, "Toggle", -1, (base + child1) + 1, 1);
			child1++;
			addHoveredButton(base + child1, "orbs/sprite", 4, 122, 24, (base + child1) + 1);
			child1 += 2;
			addText(base + child1, settingName[order1], tda, 1, 0xffb000, true, true);
			child1++;
			order1++;
		}
		int child = 0;
		int y = 25;
		int order = 6;
		rsi.totalChildren((settingsAmount * 3) + order);
		rsi.child(0, 17301, 0, 30); // Background.
		rsi.child(1, 17302, 90, 6); // Settings text.
		rsi.child(2, 17303, 171, 10); // Close.
		rsi.child(3, 17304, 171, 10); // Close hover.
		rsi.child(4, 17306, 0, 28); // Line.
		rsi.child(5, 17307, 0, 250); // Line.
		for (int i = 0; i < settingsAmount; i++) {
			rsi.child(order, base + child, 34, y + 10); // Hover.
			child++;
			order++;
			rsi.child(order, base + child, 34, y + 10); // Hover.
			order++;
			child++;
			child++;
			rsi.child(order, base + child, 93, y + 15);
			order++;
			child++;
			y += 31;
		}
	}

	private static void quickSetUpInterface(TextDrawingArea[] TDA) {
		RSInterface Interface = addInterface(24280);
		addSprite(24281, 10, "orbs/sprite");
		addHoverButton(24282, "orbs/sprite", 7, 16, 16, "View", -1, 24283, 1);
		addHoveredButton(24283, "orbs/sprite", 6, 16, 16, 24284);
		addText(24286, "Your items and equipment will be banked.", TDA, 0, 0xffff00, true, true);
		addChar(15125);
		addText(24289, "Quick set-up", TDA, 2, 0xffff00, true, true);
		String[] equipmentSets = { "Main melee", "Main hybrid", "Berserker melee", "Berserker hybrid", "Pure",
				"Pure tribrid", "Ranged tank" };
		String[] consumables = { "Vengeance runes", "Barrage runes", "Teleblock runes" };
		int interfaceIdStart = 24290;
		int interfaceIdModifier = 0;
		for (int i = 0; i < (equipmentSets.length + consumables.length); i++) {
			addHoverButton(interfaceIdStart + interfaceIdModifier, "orbs/sprite", 2, 90, 20, "View", -1,
					(interfaceIdStart + 1) + interfaceIdModifier, 1);
			interfaceIdModifier++;

			addHoveredButton(interfaceIdStart + interfaceIdModifier, "orbs/sprite", 3, 90, 20,
					(interfaceIdStart + 1) + interfaceIdModifier);
			interfaceIdModifier++;
			interfaceIdModifier++;
			if (i <= equipmentSets.length - 1) {
				addText(interfaceIdStart + interfaceIdModifier, equipmentSets[i], TDA, 0, 0xffb000, true, false);
			} else {
				addText(interfaceIdStart + interfaceIdModifier, consumables[i - (equipmentSets.length)], TDA, 0,
						0xffb000, true, false);
			}
			interfaceIdModifier++;
		}

		int childIdStart = 6;
		setChildren(((equipmentSets.length + consumables.length) * 3) + childIdStart, Interface);
		setBounds(24281, 49, 6, 0, Interface); // Background.
		setBounds(24282, 435, 9, 1, Interface); // Close button.
		setBounds(24283, 435, 9, 2, Interface); // Close button hover.
		setBounds(24286, 165, 40, 3, Interface); // Your items and equipment.
		setBounds(15125, 300, 200, 4, Interface); // Character
		setBounds(24289, 245, 10, 5, Interface); // Quick set-up text.
		int yPosition = 0;
		int interfaceIdStart1 = 24290;
		int interfaceIdApplied = 0;
		int yStart = 65;
		int ySTart2 = 69;
		for (int a = 0; a < equipmentSets.length; a++) {
			setBounds(interfaceIdStart1 + interfaceIdApplied, 68, yStart + yPosition, childIdStart, Interface); // Spawn
																												// 1
																												// button.
			interfaceIdApplied++;
			childIdStart++;
			setBounds(interfaceIdStart1 + interfaceIdApplied, 68, yStart + yPosition, childIdStart, Interface); // Spawn
																												// 1
																												// hover
																												// button..
			interfaceIdApplied++;
			interfaceIdApplied++;
			childIdStart++;
			setBounds(interfaceIdStart1 + interfaceIdApplied, 113, ySTart2 + yPosition, childIdStart, Interface); // Spawn
																													// 1
																													// text..
			interfaceIdApplied++;
			childIdStart++;
			yPosition += 29;
		}

		yPosition = 0;
		for (int a = 0; a < consumables.length; a++) {
			setBounds(interfaceIdStart1 + interfaceIdApplied, 170, yStart + yPosition, childIdStart, Interface); // Spawn
																													// 1
																													// button.
			interfaceIdApplied++;
			childIdStart++;
			setBounds(interfaceIdStart1 + interfaceIdApplied, 170, yStart + yPosition, childIdStart, Interface); // Spawn
																													// 1
																													// hover
																													// button..
			interfaceIdApplied++;
			interfaceIdApplied++;
			childIdStart++;
			setBounds(interfaceIdStart1 + interfaceIdApplied, 215, ySTart2 + yPosition, childIdStart, Interface); // Spawn
																													// 1
																													// text..
			interfaceIdApplied++;
			childIdStart++;
			yPosition += 29;
		}
	}

	public static void questTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(19500);
		RSInterface list = addTabInterface(29025);
		addSprite(29024, 1, "orbs/sprite");
		addHoverButton(29000, "orbs/sprite", 2, 85, 20, "View", -1, 29001, 1);
		addHoveredButton(29001, "orbs/sprite", 3, 85, 20, 29002);
		addText(29003, "Quick setup", tda, 1, 0xffb000, true, true);
		tab.totalChildren(5);
		int x = 46;
		tab.child(0, 29024, 2, 37); // Statistics background.
		tab.child(1, 29025, 0, 43); // Scroll.
		tab.child(2, 29000, 5 + x, 9); // Achievements button.
		tab.child(3, 29001, 5 + x, 9); // Achievements button hover.
		tab.child(4, 29003, 47 + x, 11); // Achievements text.
		addText(29026, "1", tda, 0, 0xff7000, false, true);
		addText(29027, "2", tda, 0, 0xff7000, false, true);
		addText(29028, "3", tda, 0, 0xff7000, false, true);
		addText(29029, "", tda, 0, 0xff7000, false, true);
		addText(29030, "", tda, 0, 0xff7000, false, true);
		addText(29031, "", tda, 0, 0xff7000, false, true);
		addText(29032, "", tda, 0, 0xff7000, false, true);
		addText(29033, "", tda, 0, 0xff7000, false, true);
		addText(29034, "", tda, 0, 0xff7000, false, true);
		addText(29035, "", tda, 0, 0xff7000, false, true);
		addText(29036, "11", tda, 0, 0xff7000, false, true);
		addText(29037, "12", tda, 0, 0xff7000, false, true);
		addText(29038, "13", tda, 0, 0xff7000, false, true);
		addText(29039, "", tda, 0, 0xff7000, false, true);
		addText(29040, "", tda, 0, 0xff7000, false, true);
		addText(29041, "", tda, 0, 0xff7000, false, true);
		addText(29042, "15", tda, 0, 0xff7000, false, true);
		list.totalChildren(17);
		int y = 0;
		for (int id = 0; id < 17; id++) {
			list.child(id, 29026 + id, 9, 0 + y);
			y += 12;
		}
	}

	public static void unpack(StreamLoader streamLoader, TextDrawingArea textDrawingAreas[],
			StreamLoader streamLoader_1) {
		aMRUNodes_238 = new MRUNodes(50000);
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		int j = stream.readUnsignedWord();
		interfaceCache = new RSInterface[j + 50000];
		while (stream.currentOffset < stream.buffer.length) {
			int k = stream.readUnsignedWord();
			if (k == 65535) {
				i = stream.readUnsignedWord();
				k = stream.readUnsignedWord();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.readUnsignedByte();
			rsInterface.atActionType = stream.readUnsignedByte();
			rsInterface.contentType = stream.readUnsignedWord();
			rsInterface.width = stream.readUnsignedWord();
			rsInterface.height = stream.readUnsignedWord();
			rsInterface.aByte254 = (byte) stream.readUnsignedByte();
			rsInterface.mOverInterToTrigger = stream.readUnsignedByte();
			if (rsInterface.mOverInterToTrigger != 0)
				rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8)
						+ stream.readUnsignedByte();
			else
				rsInterface.mOverInterToTrigger = -1;
			int i1 = stream.readUnsignedByte();
			if (i1 > 0) {
				rsInterface.anIntArray245 = new int[i1];
				rsInterface.anIntArray212 = new int[i1];
				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.anIntArray245[j1] = stream.readUnsignedByte();
					rsInterface.anIntArray212[j1] = stream.readUnsignedWord();
				}

			}
			int k1 = stream.readUnsignedByte();
			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.readUnsignedWord();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++)
						rsInterface.valueIndexArray[l1][l4] = stream.readUnsignedWord();

				}

			}
			if (rsInterface.type == 0) {
				rsInterface.drawsTransparent = false;
				rsInterface.scrollMax = stream.readUnsignedWord();
				rsInterface.isMouseoverTriggered = stream.readUnsignedByte() == 1;
				int i2 = stream.readUnsignedWord();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];
				for (int j3 = 0; j3 < i2; j3++) {
					// if (interfaceCache[2156] != null) {
					// if (interfaceCache[2156].children[j3] != 0)
					// continue;
					// System.out.println(interfaceCache[2156].children[j3]);
					// }
					rsInterface.children[j3] = stream.readUnsignedWord();
					rsInterface.childX[j3] = stream.readSignedWord();
					rsInterface.childY[j3] = stream.readSignedWord();
				}
			}
			if (rsInterface.type == 1) {
				stream.readUnsignedWord();
				stream.readUnsignedByte();
			}
			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.aBoolean259 = stream.readUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.readUnsignedByte() == 1;
				rsInterface.aBoolean235 = stream.readUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.readUnsignedByte();
				rsInterface.invSpritePadY = stream.readUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];
				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.readUnsignedByte();
					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.readSignedWord();
						rsInterface.spritesY[j2] = stream.readSignedWord();
						String s1 = stream.readString();
						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1,
									s1.substring(0, i5));
						}
					}
				}

				rsInterface.actions = new String[5];
				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.readString();
					if (rsInterface.actions[l3].length() == 0)
						rsInterface.actions[l3] = null;
				}

			}
			if (rsInterface.type == 3)
				rsInterface.aBoolean227 = stream.readUnsignedByte() == 1;
			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int k2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
			}
			if (rsInterface.type == 4) {
				rsInterface.message = stream.readString();
				rsInterface.aString228 = stream.readString();
			}
			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4)
				rsInterface.textColor = stream.readDWord();
			if (rsInterface.type == 3 || rsInterface.type == 4) {
				rsInterface.anInt219 = stream.readDWord();
				rsInterface.anInt216 = stream.readDWord();
				rsInterface.anInt239 = stream.readDWord();
			}
			if (rsInterface.type == 5) {
				rsInterface.drawsTransparent = false;
				String s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1,
							s.substring(0, i4));
				}
				s = stream.readString();
				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1,
							s.substring(0, j4));
				}
			}
			if (rsInterface.type == 6) {
				int l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt233 = 1;
					rsInterface.mediaID = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + stream.readUnsignedByte();
				}
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt257 = (l - 1 << 8) + stream.readUnsignedByte();
				else
					rsInterface.anInt257 = -1;
				l = stream.readUnsignedByte();
				if (l != 0)
					rsInterface.anInt258 = (l - 1 << 8) + stream.readUnsignedByte();
				else
					rsInterface.anInt258 = -1;
				rsInterface.anInt269 = stream.readUnsignedWord();
				rsInterface.modelRotation1 = stream.readUnsignedWord();
				rsInterface.modelRotation2 = stream.readUnsignedWord();
			}
			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = stream.readUnsignedByte() == 1;
				int l2 = stream.readUnsignedByte();
				if (textDrawingAreas != null)
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				rsInterface.textShadow = stream.readUnsignedByte() == 1;
				rsInterface.textColor = stream.readDWord();
				rsInterface.invSpritePadX = stream.readSignedWord();
				rsInterface.invSpritePadY = stream.readSignedWord();
				rsInterface.isInventoryInterface = stream.readUnsignedByte() == 1;
				rsInterface.actions = new String[5];
				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.readString();
					if (rsInterface.actions[k4].length() == 0)
						rsInterface.actions[k4] = null;
				}

			}
			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.readString();
				rsInterface.spellName = stream.readString();
				rsInterface.spellUsableOn = stream.readUnsignedWord();
			}

			if (rsInterface.type == 8)
				rsInterface.message = stream.readString();

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5
					|| rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.readString();
				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1)
						rsInterface.tooltip = "Ok";
					if (rsInterface.atActionType == 4)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 5)
						rsInterface.tooltip = "Select";
					if (rsInterface.atActionType == 6)
						rsInterface.tooltip = "Continue";
				}
			}
		}
		aClass44 = streamLoader;
		Sidebar0(textDrawingAreas);
		equipmentScreen(textDrawingAreas);
		bank(textDrawingAreas);
		magicTab(textDrawingAreas);
		ancientMagicTab(textDrawingAreas);
		optionTab(textDrawingAreas);
		emoteTab();
		equipmentTab(textDrawingAreas);
		itemsOnDeathDATA(textDrawingAreas);
		itemsOnDeath(textDrawingAreas);
		wildyInterface(textDrawingAreas);
		teleportTab(textDrawingAreas);
		spawnInterface(textDrawingAreas);
		shop(textDrawingAreas);
		questTab(textDrawingAreas);
		quickSetUpInterface(textDrawingAreas);
		clientSettings(textDrawingAreas);
		spinInterface(textDrawingAreas);
		prayerTab(textDrawingAreas);
		configureLunar(textDrawingAreas);
		constructLunar();
		clanChatTab(textDrawingAreas);
		clanChatSetup(textDrawingAreas);
		lootingBag(textDrawingAreas);
		highscores(textDrawingAreas);
		aMRUNodes_238 = null;
	}
	
	public static void highscores(TextDrawingArea[] tda) {
		RSInterface rs = addInterface(36000);
		addSprite(36001, 0, "quest/QUESTBG");
		addSprite(36002, 1, "quest/QUESTBG");
		addText(36003, "TragicPKz - Highscores", tda, 3, 0xe4a146, true, true);
		setChildren(3, rs);
		setBounds(36002, 18, 4, 0, rs);
		setBounds(36001, 18, 62, 1, rs);
		setBounds(36003, 251, 15, 2, rs);
	}

	public static void addLootBagItems(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[25];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.actions[0] = "Take 1";
		rsi.actions[1] = "Take 5";
		rsi.actions[2] = "Take 10";
		rsi.actions[3] = "Take All";
		rsi.actions[4] = "Take X";
		rsi.centerText = true;
		rsi.aBoolean227 = false;
		rsi.aBoolean235 = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.aBoolean259 = true;
		rsi.textShadow = false;
   		rsi.invSpritePadX = 10;
   		rsi.invSpritePadY = 5; 
		rsi.height = 6;
		rsi.width = 4;
		rsi.parentID = 35935;
		rsi.id = 4393;
		rsi.type = 2;
	}

	private static void lootingBag(TextDrawingArea[] tda) {
		RSInterface rs = addTabInterface(35931);
		addSprite(35932, 0, "inventory/SPRITE");
		addText(35933, "Looting Bag", tda, 3, 0xe4a146, true, true);
		addText(35934, "Value: 1.000,00 GP", tda, 3, 0xe4a146, true, true);
		addLootBagItems(35935);
		addHoverButton(35936, "/inventory/SPRITE", 1, 16, 16, "Exit", 5500, 35937, 10);
		addHoveredButton(35937, "/inventory/SPRITE", 2, 16, 16, 35936);
		setChildren(6, rs);
		rs.child(0, 35932, 10, 18);
		rs.child(1, 35933, 91, 0);
		rs.child(2, 35934, 91, 244);
		rs.child(3, 35935, 17, 25);
		rs.child(4, 35936, 168, 2);
		rs.child(5, 35937, 168, 2);
	}

	private static void raidInterface(TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(35931);
		addSprite(35932, 0, "/raid/main");
		addText(35933, "Bank Raid", tda, 2, 0xFF981F, true, true);
		addHoveredButton(35935, "/raid/bag", 1, 40, 40, 35931);
		addHoverButton(35934, "/raid/bag", 0, 40, 40, "Raid Bank", -1, 35934, 10);
		setChildren(4, rsi);
		rsi.child(0, 35932, 110, 7);
		rsi.child(1, 35933, 262, 20);
		rsi.child(2, 35934, 318, 295);
		rsi.child(3, 35935, 318, 295);
	}

	public Sprite disabledHover;
	public Sprite enabledHover;

	public static void clanChatSetup(TextDrawingArea[] tda) {
		RSInterface rsi = addTabInterface(18300);
		rsi.totalChildren(12 + 15);
		int count = 0;
		/* Background */
		addSprite(18301, 1, "/Clan Chat/sprite");
		rsi.child(count++, 18301, 14, 18);
		/* Close button */
		addButton(18302, 0, "/Clan Chat/close", "Close");
		interfaceCache[18302].atActionType = 3;
		rsi.child(count++, 18302, 475, 26);
		/* Clan Setup title */
		addText(18303, "Clan Setup", tda, 2, 0xFF981F, true, true);
		rsi.child(count++, 18303, 256, 26);
		/* Setup buttons */
		String[] titles = { "Clan name:", "Who can enter chat?", "Who can talk on chat?", "Who can kick on chat?",
				"Who can ban on chat?" };
		String[] defaults = { "Chat Disabled", "Anyone", "Anyone", "Anyone", "Anyone" };
		String[] whoCan = { "Anyone", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General",
				"Only Me" };
		for (int index = 0, id = 18304, y = 50; index < titles.length; index++, id += 3, y += 40) {
			addButton(id, 2, "/Clan Chat/sprite", "");
			interfaceCache[id].atActionType = 0;
			if (index > 0) {
				interfaceCache[id].actions = whoCan;
			} else {
				interfaceCache[id].actions = new String[] { "Change title", "Delete clan" };
				;
			}
			addText(id + 1, titles[index], tda, 0, 0xFF981F, true, true);
			addText(id + 2, defaults[index], tda, 1, 0xFFFFFF, true, true);
			rsi.child(count++, id, 25, y);
			rsi.child(count++, id + 1, 100, y + 4);
			rsi.child(count++, id + 2, 100, y + 17);
		}
		/* Table */
		addSprite(18319, 5, "/Clan Chat/sprite");
		rsi.child(count++, 18319, 197, 70);
		/* Labels */
		int id = 18320;
		int y = 74;
		addText(id, "Ranked Members", tda, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 202, y);
		addText(id, "Banned Members", tda, 2, 0xFF981F, false, true);
		rsi.child(count++, id++, 339, y);
		/* Ranked members list */
		RSInterface list = addTabInterface(id++);
		int lines = 100;
		list.totalChildren(lines);
		String[] ranks = { "Demote", "Recruit", "Corporal", "Sergeant", "Lieutenant", "Captain", "General", "Owner" };
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			addText(index, "", tda, 1, 0xffffff, false, true);
			interfaceCache[index].actions = ranks;
			list.children[index - id] = index;
			list.childX[index - id] = 2;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = (lines * 14) + 2;
		rsi.child(count++, list.id, 199, 92);
		/* Banned members list */
		list = addTabInterface(id++);
		list.totalChildren(lines);
		list.childY[0] = 2;
		// System.out.println(id);
		for (int index = id; index < id + lines; index++) {
			addText(index, "", tda, 1, 0xffffff, false, true);
			interfaceCache[index].actions = new String[] { "Unban" };
			list.children[index - id] = index;
			list.childX[index - id] = 0;
			list.childY[index - id] = (index - id > 0 ? list.childY[index - id - 1] + 14 : 0);
		}
		id += lines;
		list.width = 119;
		list.height = 210;
		list.scrollMax = (lines * 14) + 2;
		rsi.child(count++, list.id, 339, 92);
		/* Table info text */
		y = 47;
		addText(id, "You can manage both ranked and banned members here.", tda, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y);
		addText(id, "Right click on a name to edit the member.", tda, 0, 0xFF981F, true, true);
		rsi.child(count++, id++, 337, y + 11);
		/* Add ranked member button */
		y = 75;
		addButton(id, 0, "/Clan Chat/plus", "Add ranked member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 319, y);
		/* Add banned member button */
		addButton(id, 0, "/Clan Chat/plus", "Add banned member");
		interfaceCache[id].atActionType = 5;
		rsi.child(count++, id++, 459, y);

		/* Hovers */
		int[] clanSetup = { 18302, 18304, 18307, 18310, 18313, 18316, 18526, 18527 };
		String[] names = { "close", "sprite", "sprite", "sprite", "sprite", "sprite", "plus", "plus" };
		int[] ids = { 1, 3, 3, 3, 3, 3, 1, 1 };
		for (int index = 0; index < clanSetup.length; index++) {
			rsi = interfaceCache[clanSetup[index]];
			rsi.disabledHover = imageLoader(ids[index], "/Clan Chat/" + names[index]);
		}
	}

	public static void clanChatTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(18128);
		addHoverButton(18129, "/Clan Chat/SPRITE", 6, 72, 32, "Join Clan", 550, 18130, 5);
		addHoveredButton(18130, "/Clan Chat/SPRITE", 7, 72, 32, 18131);
		addHoverButton(18132, "/Clan Chat/SPRITE", 6, 72, 32, "Clan Setup", -1, 18133, 5);
		addHoveredButton(18133, "/Clan Chat/SPRITE", 7, 72, 32, 18134);
		addText(18135, "Join Clan", tda, 0, 0xff9b00, true, true);
		addText(18136, "Clan Setup", tda, 0, 0xff9b00, true, true);
		addSprite(18137, 37, "/Clan Chat/SPRITE");
		addText(18138, "Clan Chat", tda, 1, 0xff9b00, true, true);
		addText(18139, "Talking in: Not in clan", tda, 0, 0xff9b00, false, true);
		addText(18140, "Owner: None", tda, 0, 0xff9b00, false, true);
		addSprite(16126, 4, "/Clan Chat/SPRITE");
		tab.totalChildren(13);
		tab.child(0, 16126, 0, 221);
		tab.child(1, 16126, 0, 59);
		tab.child(2, 18137, 0, 62);
		tab.child(3, 18143, 0, 62);
		tab.child(4, 18129, 15, 226);
		tab.child(5, 18130, 15, 226);
		tab.child(6, 18132, 103, 226);
		tab.child(7, 18133, 103, 226);
		tab.child(8, 18135, 51, 237);
		tab.child(9, 18136, 139, 237);
		tab.child(10, 18138, 95, 1);
		tab.child(11, 18139, 10, 23);
		tab.child(12, 18140, 25, 38);
		/* Text area */
		RSInterface list = addTabInterface(18143);
		list.totalChildren(100);
		for (int i = 18144; i <= 18244; i++) {
			addText(i, "", tda, 0, 0xffffff, false, true);
		}
		for (int id = 18144, i = 0; id <= 18243 && i <= 99; id++, i++) {
			interfaceCache[id].actions = new String[] { "Edit Rank", "Kick", "Ban" };
			list.children[i] = id;
			list.childX[i] = 5;
			for (int id2 = 18144, i2 = 1; id2 <= 18243 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		list.height = 158;
		list.width = 174;
		list.scrollMax = 1405;
	}

	public static void emoteTab() {
		RSInterface tab = addTabInterface(147);
		RSInterface scroll = addTabInterface(148);
		tab.totalChildren(1);
		tab.child(0, 148, 0, 1);
		addButton(168, 0, "Emotes/EMOTE", "Yes");
		addButton(169, 1, "Emotes/EMOTE", "No");
		addButton(164, 2, "Emotes/EMOTE", "Bow");
		addButton(165, 3, "Emotes/EMOTE", "Angry");
		addButton(162, 4, "Emotes/EMOTE", "Think");
		addButton(163, 5, "Emotes/EMOTE", "Wave");
		addButton(13370, 6, "Emotes/EMOTE", "Shrug");
		addButton(171, 7, "Emotes/EMOTE", "Cheer");
		addButton(167, 8, "Emotes/EMOTE", "Beckon");
		addButton(170, 9, "Emotes/EMOTE", "Laugh");
		addButton(13366, 10, "Emotes/EMOTE", "Jump for Joy");
		addButton(13368, 11, "Emotes/EMOTE", "Yawn");
		addButton(166, 12, "Emotes/EMOTE", "Dance");
		addButton(13363, 13, "Emotes/EMOTE", "Jig");
		addButton(13364, 14, "Emotes/EMOTE", "Spin");
		addButton(13365, 15, "Emotes/EMOTE", "Headbang");
		addButton(161, 16, "Emotes/EMOTE", "Cry");
		addButton(11100, 17, "Emotes/EMOTE", "Blow kiss");
		addButton(13362, 18, "Emotes/EMOTE", "Panic");
		addButton(13367, 19, "Emotes/EMOTE", "Raspberry");
		addButton(172, 20, "Emotes/EMOTE", "Clap");
		addButton(13369, 21, "Emotes/EMOTE", "Salute");
		addButton(13383, 22, "Emotes/EMOTE", "Goblin Bow");
		addButton(13384, 23, "Emotes/EMOTE", "Goblin Salute");
		addButton(667, 24, "Emotes/EMOTE", "Glass Box");
		addButton(6503, 25, "Emotes/EMOTE", "Climb Rope");
		addButton(6506, 26, "Emotes/EMOTE", "Lean On Air");
		addButton(666, 27, "Emotes/EMOTE", "Glass Wall");
		addButton(18464, 28, "Emotes/EMOTE", "Zombie Walk");
		addButton(18465, 29, "Emotes/EMOTE", "Zombie Dance");
		addButton(15166, 30, "Emotes/EMOTE", "Scared");
		addButton(18686, 31, "Emotes/EMOTE", "Rabbit Hop");
		addConfigButton(154, 147, 32, 33, "Emotes/EMOTE", 41, 47, "Skillcape Emote", 0, 1, 700);
		scroll.totalChildren(33);
		scroll.child(0, 168, 10, 7);
		scroll.child(1, 169, 54, 7);
		scroll.child(2, 164, 98, 14);
		scroll.child(3, 165, 137, 7);
		scroll.child(4, 162, 9, 56);
		scroll.child(5, 163, 48, 56);
		scroll.child(6, 13370, 95, 56);
		scroll.child(7, 171, 137, 56);
		scroll.child(8, 167, 7, 105);
		scroll.child(9, 170, 51, 105);
		scroll.child(10, 13366, 95, 104);
		scroll.child(11, 13368, 139, 105);
		scroll.child(12, 166, 6, 154);
		scroll.child(13, 13363, 50, 154);
		scroll.child(14, 13364, 90, 154);
		scroll.child(15, 13365, 135, 154);
		scroll.child(16, 161, 8, 204);
		scroll.child(17, 11100, 51, 203);
		scroll.child(18, 13362, 99, 204);
		scroll.child(19, 13367, 137, 203);
		scroll.child(20, 172, 10, 253);
		scroll.child(21, 13369, 53, 253);
		scroll.child(22, 13383, 88, 258);
		scroll.child(23, 13384, 138, 252);
		scroll.child(24, 667, 2, 303);
		scroll.child(25, 6503, 49, 302);
		scroll.child(26, 6506, 93, 302);
		scroll.child(27, 666, 137, 302);
		scroll.child(28, 18464, 9, 352);
		scroll.child(29, 18465, 50, 352);
		scroll.child(30, 15166, 94, 356);
		scroll.child(31, 18686, 141, 353);
		scroll.child(32, 154, 5, 401);
		scroll.width = 173;
		scroll.height = 258;
		scroll.scrollMax = 450;
	}

	public static void optionTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(904);
		RSInterface energy = interfaceCache[149];
		energy.textColor = 0xff9933;
		addSprite(905, 9, "Options/SPRITE");
		addSprite(907, 18, "Options/SPRITE");
		addSprite(909, 29, "Options/SPRITE");
		addSprite(951, 32, "Options/SPRITE");
		addSprite(953, 33, "Options/SPRITE");
		addSprite(955, 34, "Options/SPRITE");
		addSprite(947, 36, "Options/SPRITE");
		addSprite(949, 35, "Options/SPRITE");
		addConfigButton(152, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle-run", 1, 5, 173);
		addConfigButton(906, 904, 10, 14, "Options/SPRITE", 32, 16, "Dark", 1, 5, 166);
		addConfigButton(908, 904, 11, 15, "Options/SPRITE", 32, 16, "Normal", 2, 5, 166);
		addConfigButton(910, 904, 12, 16, "Options/SPRITE", 32, 16, "Bright", 3, 5, 166);
		addConfigButton(912, 904, 13, 17, "Options/SPRITE", 32, 16, "Very Bright", 4, 5, 166);
		addConfigButton(930, 904, 19, 24, "Options/SPRITE", 26, 16, "Music Off", 4, 5, 168);
		addConfigButton(931, 904, 20, 25, "Options/SPRITE", 26, 16, "Music Level-1", 3, 5, 168);
		addConfigButton(932, 904, 21, 26, "Options/SPRITE", 26, 16, "Music Level-2", 2, 5, 168);
		addConfigButton(933, 904, 22, 27, "Options/SPRITE", 26, 16, "Music Level-3", 1, 5, 168);
		addConfigButton(934, 904, 23, 28, "Options/SPRITE", 24, 16, "Music Level-4", 0, 5, 168);
		addConfigButton(941, 904, 19, 24, "Options/SPRITE", 26, 16, "Sound Effects Off", 4, 5, 169);
		addConfigButton(942, 904, 20, 25, "Options/SPRITE", 26, 16, "Sound Effects Level-1", 3, 5, 169);
		addConfigButton(943, 904, 21, 26, "Options/SPRITE", 26, 16, "Sound Effects Level-2", 2, 5, 169);
		addConfigButton(944, 904, 22, 27, "Options/SPRITE", 26, 16, "Sound Effects Level-3", 1, 5, 169);
		addConfigButton(945, 904, 23, 28, "Options/SPRITE", 24, 16, "Sound Effects Level-4", 0, 5, 169);
		addConfigButton(913, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle-Mouse Buttons", 0, 5, 170);
		addConfigButton(915, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle-Chat Effects", 0, 5, 171);
		addConfigButton(957, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle-Split Private Chat", 1, 5, 287);
		addConfigButton(12464, 904, 30, 31, "Options/SPRITE", 40, 40, "Toggle-Accept Aid", 0, 5, 427);
		tab.totalChildren(28);
		int x = 0;
		int y = 2;
		tab.child(0, 905, 13 + x, 10 + y);
		tab.child(1, 906, 48 + x, 18 + y);
		tab.child(2, 908, 80 + x, 18 + y);
		tab.child(3, 910, 112 + x, 18 + y);
		tab.child(4, 912, 144 + x, 18 + y);
		tab.child(5, 907, 14 + x, 55 + y);
		tab.child(6, 930, 49 + x, 61 + y);
		tab.child(7, 931, 75 + x, 61 + y);
		tab.child(8, 932, 101 + x, 61 + y);
		tab.child(9, 933, 127 + x, 61 + y);
		tab.child(10, 934, 151 + x, 61 + y);
		tab.child(11, 909, 13 + x, 99 + y);
		tab.child(12, 941, 49 + x, 104 + y);
		tab.child(13, 942, 75 + x, 104 + y);
		tab.child(14, 943, 101 + x, 104 + y);
		tab.child(15, 944, 127 + x, 104 + y);
		tab.child(16, 945, 151 + x, 104 + y);
		tab.child(17, 913, 15, 153);
		tab.child(18, 955, 19, 159);
		tab.child(19, 915, 75, 153);
		tab.child(20, 953, 79, 160);
		tab.child(21, 957, 135, 153);
		tab.child(22, 951, 139, 159);
		tab.child(23, 12464, 15, 208);
		tab.child(24, 949, 20, 213);
		tab.child(25, 152, 75, 208);
		tab.child(26, 947, 87, 212);
		tab.child(27, 149, 80, 231);
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height,
			String tT, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.aByte254 = 0;
		Tab.mOverInterToTrigger = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(bID, bName);
		Tab.sprite2 = imageLoader(bID2, bName);
		Tab.tooltip = tT;
	}

	public static void equipmentTab(TextDrawingArea[] wid) {
		RSInterface Interface = interfaceCache[1644];
		addSprite(15101, 0, "Equipment/bl");// cheap hax
		addSprite(15102, 1, "Equipment/bl");// cheap hax
		addSprite(15109, 2, "Equipment/bl");// cheap hax
		removeSomething(15103);
		removeSomething(15104);
		Interface.children[23] = 15101;
		Interface.childX[23] = 40;
		Interface.childY[23] = 205;
		Interface.children[24] = 15102;
		Interface.childX[24] = 110;
		Interface.childY[24] = 205;
		Interface.children[25] = 15109;
		Interface.childX[25] = 39;
		Interface.childY[25] = 240;
		Interface.children[26] = 27650;
		Interface.childX[26] = 0;
		Interface.childY[26] = 0;
		Interface = addInterface(27650);
		addButton(27653, 1, "Equipment/BOX", "Show Equipment Stats", 27655, 1, 40, 39);
		addTooltip(27655, "Show Equipment Stats");
		addButton(27654, 2, "Equipment/BOX", "Show Items Kept on Death", 27657, 1, 40, 39);
		addTooltip(27657, "Show Items Kept on Death");
		setChildren(4, Interface);
		setBounds(27653, 29, 205, 0, Interface);
		setBounds(27654, 124, 205, 1, Interface);
		setBounds(27655, 39, 240, 2, Interface);
		setBounds(27657, 39, 220, 3, Interface);
	}

	public static void equipmentScreen(TextDrawingArea[] wid) {
		RSInterface Interface = RSInterface.interfaceCache[1644];
		addButton(19144, 6, "Equipment/CUSTOM", "Show Equipment Stats");
		removeSomething(19145);
		removeSomething(19146);
		removeSomething(19147);
		setBounds(19144, 21, 210, 23, Interface);
		setBounds(19145, 40, 210, 24, Interface);
		setBounds(19146, 40, 210, 25, Interface);
		setBounds(19147, 40, 210, 26, Interface);
		RSInterface tab = addTabInterface(15106);
		addSprite(15107, 7, "Equipment/CUSTOM");
		addHoverButton(15210, "Equipment/CUSTOM", 8, 21, 21, "Close", 250, 15211, 3);
		addHoveredButton(15211, "Equipment/CUSTOM", 9, 21, 21, 15212);
		addText(15111, "Equip Your Character...", wid, 2, 0xe4a146, false, true);
		addText(15112, "Attack bonus", wid, 2, 0xe4a146, false, true);
		addText(15113, "Defence bonus", wid, 2, 0xe4a146, false, true);
		addText(15114, "Other bonuses", wid, 2, 0xe4a146, false, true);
		addText(15115, "Magic Damage:", wid, 2, 0xe4a146, false, true);
		addText(15116, "Prayer:", wid, 2, 0xe4a146, false, true);
		for (int i = 1675; i <= 1684; i++) {
			textSize(i, wid, 1);
		}
		textSize(1686, wid, 1);
		textSize(1687, wid, 1);
		textSize(15115, wid, 1);
		textSize(15116, wid, 1);
		addChar(15125);
		tab.totalChildren(46);
		tab.child(0, 15107, 4, 20);
		tab.child(1, 15210, 476, 29);
		tab.child(2, 15211, 476, 29);
		tab.child(3, 15111, 14, 30);
		int Child = 4;
		int Y = 69;
		for (int i = 1675; i <= 1679; i++) {
			tab.child(Child, i, 20, Y);
			Child++;
			Y += 14;
		}
		tab.child(44, 15115, 20, 289);
		tab.child(45, 15116, 20, 302);
		tab.child(9, 1680, 20, 161);
		tab.child(10, 1681, 20, 177);
		tab.child(11, 1682, 20, 192);
		tab.child(12, 1683, 20, 207);
		tab.child(13, 1684, 20, 221);
		tab.child(14, 1686, 20, 262);
		tab.child(15, 15125, 170, 200);
		tab.child(16, 15112, 16, 55);
		tab.child(17, 1687, 20, 276);
		tab.child(18, 15113, 16, 147);
		tab.child(19, 15114, 16, 248);
		tab.child(20, 1645, 104 + 295, 149 - 52);
		tab.child(21, 1646, 399, 163);
		tab.child(22, 1647, 399, 163);
		tab.child(23, 1648, 399, 58 + 146);
		tab.child(24, 1649, 26 + 22 + 297 - 2, 110 - 44 + 118 - 13 + 5);
		tab.child(25, 1650, 321 + 22, 58 + 154);
		tab.child(26, 1651, 321 + 134, 58 + 118);
		tab.child(27, 1652, 321 + 134, 58 + 154);
		tab.child(28, 1653, 321 + 48, 58 + 81);
		tab.child(29, 1654, 321 + 107, 58 + 81);
		tab.child(30, 1655, 321 + 58, 58 + 42);
		tab.child(31, 1656, 321 + 112, 58 + 41);
		tab.child(32, 1657, 321 + 78, 58 + 4);
		tab.child(33, 1658, 321 + 37, 58 + 43);
		tab.child(34, 1659, 321 + 78, 58 + 43);
		tab.child(35, 1660, 321 + 119, 58 + 43);
		tab.child(36, 1661, 321 + 22, 58 + 82);
		tab.child(37, 1662, 321 + 78, 58 + 82);
		tab.child(38, 1663, 321 + 134, 58 + 82);
		tab.child(39, 1664, 321 + 78, 58 + 122);
		tab.child(40, 1665, 321 + 78, 58 + 162);
		tab.child(41, 1666, 321 + 22, 58 + 162);
		tab.child(42, 1667, 321 + 134, 58 + 162);
		tab.child(43, 1688, 50 + 297 - 2, 110 - 13 + 5);
		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xe4a146;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			rsi.textColor = 0xe4a146;
			rsi.centerText = false;
		}
	}

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.aByte254 = 0;
		t.mOverInterToTrigger = 0;
		t.anInt269 = 560;
		t.modelRotation1 = 150;
		t.modelRotation2 = 0;
		t.anInt257 = -1;
		t.anInt258 = -1;
	}

	public static void itemsOnDeathDATA(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17115);
		addText(17109, "a", wid, 0, 0xff981f);
		addText(17110, "b", wid, 0, 0xff981f);
		addText(17111, "c", wid, 0, 0xff981f);
		addText(17112, "d", wid, 0, 0xff981f);
		addText(17113, "e", wid, 0, 0xff981f);
		addText(17114, "f", wid, 0, 0xff981f);
		addText(17117, "g", wid, 0, 0xff981f);
		addText(17118, "h", wid, 0, 0xff981f);
		addText(17119, "i", wid, 0, 0xff981f);
		addText(17120, "j", wid, 0, 0xff981f);
		addText(17121, "k", wid, 0, 0xff981f);
		addText(17122, "l", wid, 0, 0xff981f);
		addText(17123, "m", wid, 0, 0xff981f);
		addText(17124, "n", wid, 0, 0xff981f);
		addText(17125, "o", wid, 0, 0xff981f);
		addText(17126, "p", wid, 0, 0xff981f);
		addText(17127, "q", wid, 0, 0xff981f);
		addText(17128, "r", wid, 0, 0xff981f);
		addText(17129, "s", wid, 0, 0xff981f);
		addText(17130, "t", wid, 0, 0xff981f);
		rsinterface.parentID = 17115;
		rsinterface.id = 17115;
		rsinterface.type = 0;
		rsinterface.atActionType = 0;
		rsinterface.contentType = 0;
		rsinterface.width = 130;
		rsinterface.height = 197;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.scrollMax = 280;
		rsinterface.children = new int[20];
		rsinterface.childX = new int[20];
		rsinterface.childY = new int[20];
		rsinterface.children[0] = 17109;
		rsinterface.childX[0] = 0;
		rsinterface.childY[0] = 0;
		rsinterface.children[1] = 17110;
		rsinterface.childX[1] = 0;
		rsinterface.childY[1] = 12;
		rsinterface.children[2] = 17111;
		rsinterface.childX[2] = 0;
		rsinterface.childY[2] = 24;
		rsinterface.children[3] = 17112;
		rsinterface.childX[3] = 0;
		rsinterface.childY[3] = 36;
		rsinterface.children[4] = 17113;
		rsinterface.childX[4] = 0;
		rsinterface.childY[4] = 48;
		rsinterface.children[5] = 17114;
		rsinterface.childX[5] = 0;
		rsinterface.childY[5] = 60;
		rsinterface.children[6] = 17117;
		rsinterface.childX[6] = 0;
		rsinterface.childY[6] = 72;
		rsinterface.children[7] = 17118;
		rsinterface.childX[7] = 0;
		rsinterface.childY[7] = 84;
		rsinterface.children[8] = 17119;
		rsinterface.childX[8] = 0;
		rsinterface.childY[8] = 96;
		rsinterface.children[9] = 17120;
		rsinterface.childX[9] = 0;
		rsinterface.childY[9] = 108;
		rsinterface.children[10] = 17121;
		rsinterface.childX[10] = 0;
		rsinterface.childY[10] = 120;
		rsinterface.children[11] = 17122;
		rsinterface.childX[11] = 0;
		rsinterface.childY[11] = 132;
		rsinterface.children[12] = 17123;
		rsinterface.childX[12] = 0;
		rsinterface.childY[12] = 144;
		rsinterface.children[13] = 17124;
		rsinterface.childX[13] = 0;
		rsinterface.childY[13] = 156;
		rsinterface.children[14] = 17125;
		rsinterface.childX[14] = 0;
		rsinterface.childY[14] = 168;
		rsinterface.children[15] = 17126;
		rsinterface.childX[15] = 0;
		rsinterface.childY[15] = 180;
		rsinterface.children[16] = 17127;
		rsinterface.childX[16] = 0;
		rsinterface.childY[16] = 192;
		rsinterface.children[17] = 17128;
		rsinterface.childX[17] = 0;
		rsinterface.childY[17] = 204;
		rsinterface.children[18] = 17129;
		rsinterface.childX[18] = 0;
		rsinterface.childY[18] = 216;
		rsinterface.children[19] = 17130;
		rsinterface.childX[19] = 0;
		rsinterface.childY[19] = 228;
	}

	public static void addText(int id, String text, TextDrawingArea wid[], int idx, int color) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.aString228 = "";
		rsinterface.textColor = color;
		rsinterface.anInt219 = 0;
		rsinterface.anInt216 = 0;
		rsinterface.anInt239 = 0;
	}

	public static void itemsOnDeath(TextDrawingArea[] wid) {
		RSInterface rsinterface = addInterface(17100);
		addSprite(17101, 2, 2);
		addHover(17102, 3, 0, 10601, 1, "Equipment/SPRITE", 17, 17, "Close Window");
		addHovered(10601, 3, "Equipment/SPRITE", 17, 17, 10602);
		addText(17103, "Items kept on death", wid, 2, 0xff981f);
		addText(17104, "Items I will keep...", wid, 1, 0xff981f);
		addText(17105, "Items I will lose...", wid, 1, 0xff981f);
		addText(17106, "Info", wid, 1, 0xff981f);
		addText(17107, "Shit fuck", wid, 1, 0xffcc33);
		addText(17108, "", wid, 1, 0xffcc33);
		rsinterface.scrollMax = 0;
		rsinterface.isMouseoverTriggered = false;
		rsinterface.children = new int[12];
		rsinterface.childX = new int[12];
		rsinterface.childY = new int[12];
		rsinterface.children[0] = 17101;
		rsinterface.childX[0] = 7;
		rsinterface.childY[0] = 8;
		rsinterface.children[1] = 17102;
		rsinterface.childX[1] = 480;
		rsinterface.childY[1] = 17;
		rsinterface.children[2] = 17103;
		rsinterface.childX[2] = 185;
		rsinterface.childY[2] = 18;
		rsinterface.children[3] = 17104;
		rsinterface.childX[3] = 22;
		rsinterface.childY[3] = 50;
		rsinterface.children[4] = 17105;
		rsinterface.childX[4] = 22;
		rsinterface.childY[4] = 110;
		rsinterface.children[5] = 17106;
		rsinterface.childX[5] = 347;
		rsinterface.childY[5] = 47;
		rsinterface.children[6] = 17107;
		rsinterface.childX[6] = 349;
		rsinterface.childY[6] = 270;
		rsinterface.children[7] = 17108;
		rsinterface.childX[7] = 398;
		rsinterface.childY[7] = 298;
		rsinterface.children[8] = 17115;
		rsinterface.childX[8] = 348;
		rsinterface.childY[8] = 64;
		rsinterface.children[9] = 10494;
		rsinterface.childX[9] = 26;
		rsinterface.childY[9] = 74;
		rsinterface.children[10] = 10600;
		rsinterface.childX[10] = 26;
		rsinterface.childY[10] = 133;
		rsinterface.children[11] = 10601;
		rsinterface.childX[11] = 480;
		rsinterface.childY[11] = 17;

	}

	public static void configureLunar(TextDrawingArea[] TDA) {
		homeTeleport();
		drawRune(30003, 1, "Fire");
		drawRune(30004, 2, "Water");
		drawRune(30005, 3, "Air");
		drawRune(30006, 4, "Earth");
		drawRune(30007, 5, "Mind");
		drawRune(30008, 6, "Body");
		drawRune(30009, 7, "Death");
		drawRune(30010, 8, "Nature");
		drawRune(30011, 9, "Chaos");
		drawRune(30012, 10, "Law");
		drawRune(30013, 11, "Cosmic");
		drawRune(30014, 12, "Blood");
		drawRune(30015, 13, "Soul");
		drawRune(30016, 14, "Astral");
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Bake Pie",
				"Bake pies without a stove", TDA, 0, 16, 2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Plant", "Cure disease on farming patch", TDA, 1,
				4, 2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine",
				"Detect the combat statistics of a\\nmonster", TDA, 2, 2, 2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "NPC Contact",
				"Speak with varied NPCs", TDA, 3, 0, 2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players",
				TDA, 4, 8, 2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify",
				"fills certain vessels with water", TDA, 5, 0, 5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Training Teleport",
				"Various Training Teleports", TDA, 6, 0, 5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Skill Teleports",
				"Various skilling locations", TDA, 7, 0, 5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Dungeon Teleports",
				"Various dungeons to explore", TDA, 8, 0, 5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", TDA, 9, 0,
				5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Hunter Kit", "Get a kit of hunting gear", TDA, 10, 0,
				5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Boss Teleports",
				"various bosses to slay", TDA, 11, 0, 5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Minigame Teleports",
				"Various Minigames", TDA, 12, 0, 5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group",
				"Cures Poison on players", TDA, 13, 0, 5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy",
				"Cast on another player to see their\\nskill levels", TDA, 14, 8, 2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Pking Teleports",
				"Various PK locations", TDA, 15, 0, 5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian",
				"Teleports players to the Barbarian\\noutpost", TDA, 16, 0, 5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make",
				"Make glass without a furnace", TDA, 17, 16, 2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "Khazard Teleport",
				"Teleports you to Port khazard", TDA, 18, 0, 5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard",
				"Teleports players to Port khazard", TDA, 19, 0, 5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream",
				"Take a rest and restore hitpoints 3\\n times faster", TDA, 20, 0, 5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery",
				"String amulets without wool", TDA, 21, 0, 5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare",
				"Share a potion with up to 4 nearby\\nplayers", TDA, 22, 0, 5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue",
				"Combine runes without a talisman", TDA, 23, 0, 5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil",
				"Fertilise a farming patch with super\\ncompost", TDA, 24, 4, 2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share",
				"Shares a potion with up to 4 nearby\\nplayers", TDA, 25, 0, 5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport",
				"Teleports you to the fishing guild", TDA, 26, 0, 5);
		addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing\\nGuild",
				"Teleports players to the Fishing\\nGuild", TDA, 27, 0, 5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks",
				TDA, 28, 16, 5);
		/******** Cut Off Limit **********/
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport",
				"Teleports you to Catherby", TDA, 29, 0, 5);
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby",
				"Teleports players to Catherby", TDA, 30, 0, 5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport",
				"Teleports you to Ice Plateau", TDA, 31, 0, 5);
		addLunar3RunesBigBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice\\n Plateau",
				"Teleports players to Ice Plateau", TDA, 32, 0, 5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer",
				"Spend hitpoints and SA Energy to\\n give another player hitpoints and run energy", TDA, 33, 8, 2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other",
				"Transfer up to 75% of hitpoints\\n to another player", TDA, 34, 8, 2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other",
				"Allows another player to rebound\\ndamage to an opponent", TDA, 35, 8, 2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance",
				"Rebound damage to an opponent", TDA, 36, 0, 5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group",
				"Transfer up to 75% of hitpoints to a group", TDA, 37, 0, 5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap",
				"Change to another spellbook for 1\\nspell cast", TDA, 38, 0, 5);
	}

	public static void drawRune(int i, int id, String runeName) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 52;
		RSInterface.sprite1 = loadSprite(id, "Lunar/RUNE");
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	public static void constructLunar() {
		RSInterface Interface = addTabInterface(29999);
		setChildren(80, Interface);
		setBounds(30000, 11, 10, 0, Interface);
		setBounds(30017, 40, 9, 1, Interface);
		setBounds(30025, 71, 12, 2, Interface);
		setBounds(30032, 103, 10, 3, Interface);
		setBounds(30040, 135, 12, 4, Interface);
		setBounds(30048, 165, 10, 5, Interface);
		setBounds(30056, 8, 38, 6, Interface);
		setBounds(30064, 39, 39, 7, Interface);
		setBounds(30075, 71, 39, 8, Interface);
		setBounds(30083, 103, 39, 9, Interface);
		setBounds(30091, 135, 39, 10, Interface);
		setBounds(30099, 165, 37, 11, Interface);
		setBounds(30106, 12, 68, 12, Interface);
		setBounds(30114, 42, 68, 13, Interface);
		setBounds(30122, 71, 68, 14, Interface);
		setBounds(30130, 103, 68, 15, Interface);
		setBounds(30138, 135, 68, 16, Interface);
		setBounds(30146, 165, 68, 17, Interface);
		setBounds(30154, 14, 97, 18, Interface);
		setBounds(30162, 42, 97, 19, Interface);
		setBounds(30170, 71, 97, 20, Interface);
		setBounds(30178, 101, 97, 21, Interface);
		setBounds(30186, 135, 98, 22, Interface);
		setBounds(30194, 168, 98, 23, Interface);
		setBounds(30202, 11, 125, 24, Interface);
		setBounds(30210, 42, 124, 25, Interface);
		setBounds(30218, 74, 125, 26, Interface);
		setBounds(30226, 103, 125, 27, Interface);
		setBounds(30234, 135, 125, 28, Interface);
		setBounds(30242, 164, 126, 29, Interface);
		setBounds(30250, 10, 155, 30, Interface);
		setBounds(30258, 42, 155, 31, Interface);
		setBounds(30266, 71, 155, 32, Interface);
		setBounds(30274, 103, 155, 33, Interface);
		setBounds(30282, 136, 155, 34, Interface);
		setBounds(30290, 165, 155, 35, Interface);
		setBounds(30298, 13, 185, 36, Interface);
		setBounds(30306, 42, 185, 37, Interface);
		setBounds(30314, 71, 184, 38, Interface);
		setBounds(30322, 104, 184, 39, Interface);
		setBounds(30001, 6, 184, 40, Interface);// hover
		setBounds(30018, 5, 176, 41, Interface);// hover
		setBounds(30026, 5, 176, 42, Interface);// hover
		setBounds(30033, 5, 163, 43, Interface);// hover
		setBounds(30041, 5, 176, 44, Interface);// hover
		setBounds(30049, 5, 176, 45, Interface);// hover
		setBounds(30057, 5, 176, 46, Interface);// hover
		setBounds(30065, 5, 176, 47, Interface);// hover
		setBounds(30076, 5, 163, 48, Interface);// hover
		setBounds(30084, 5, 176, 49, Interface);// hover
		setBounds(30092, 5, 176, 50, Interface);// hover
		setBounds(30100, 5, 176, 51, Interface);// hover
		setBounds(30107, 5, 176, 52, Interface);// hover
		setBounds(30115, 5, 163, 53, Interface);// hover
		setBounds(30123, 5, 176, 54, Interface);// hover
		setBounds(30131, 5, 163, 55, Interface);// hover
		setBounds(30139, 5, 163, 56, Interface);// hover
		setBounds(30147, 5, 163, 57, Interface);// hover
		setBounds(30155, 5, 176, 58, Interface);// hover
		setBounds(30163, 5, 176, 59, Interface);// hover
		setBounds(30171, 5, 176, 60, Interface);// hover
		setBounds(30179, 5, 163, 61, Interface);// hover
		setBounds(30187, 5, 176, 62, Interface);// hover
		setBounds(30195, 5, 149, 63, Interface);// hover
		setBounds(30203, 5, 176, 64, Interface);// hover
		setBounds(30211, 5, 163, 65, Interface);// hover
		setBounds(30219, 5, 163, 66, Interface);// hover
		setBounds(30227, 5, 176, 67, Interface);// hover
		setBounds(30235, 5, 149, 68, Interface);// hover
		setBounds(30243, 5, 176, 69, Interface);// hover
		setBounds(30251, 5, 5, 70, Interface);// hover
		setBounds(30259, 5, 5, 71, Interface);// hover
		setBounds(30267, 5, 5, 72, Interface);// hover
		setBounds(30275, 5, 5, 73, Interface);// hover
		setBounds(30283, 5, 5, 74, Interface);// hover
		setBounds(30291, 5, 5, 75, Interface);// hover
		setBounds(30299, 5, 5, 76, Interface);// hover
		setBounds(30307, 5, 5, 77, Interface);// hover
		setBounds(30323, 5, 5, 78, Interface);// hover
		setBounds(30315, 5, 5, 79, Interface);// hover
	}

	public static void homeTeleport() {
		RSInterface RSInterface = addTabInterface(30000);
		RSInterface.tooltip = "Cast @gre@Lunar Home Teleport";
		RSInterface.id = 30000;
		RSInterface.parentID = 30000;
		RSInterface.type = 5;
		RSInterface.atActionType = 5;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 30001;
		RSInterface.sprite1 = loadSprite(1, "Lunar/SPRITE");
		RSInterface.width = 20;
		RSInterface.height = 20;
		RSInterface hover = addTabInterface(30001);
		hover.mOverInterToTrigger = -1;
		hover.isMouseoverTriggered = true;
		setChildren(1, hover);
		addLunarSprite(30002, 0, "Lunar/SPRITE");
		setBounds(30002, 0, 0, 0, hover);
	}

	public static void addLunarSprite(int i, int j, String name) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
	}

	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = loadSprite(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = loadSprite(sid, "Lunar/LUNAROFF");
		RSInterface hover = addTabInterface(ID + 1);
		hover.mOverInterToTrigger = -1;
		hover.isMouseoverTriggered = true;
		setChildren(9, hover);
		addLunarSprite(ID + 2, 1, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, hover);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
		setBounds(ID + 3, 90, 4, 1, hover);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
		setBounds(ID + 4, 90, 21, 2, hover);
		setBounds(30016, 14, 48, 3, hover);
		setBounds(rune1, 74, 48, 4, hover);
		setBounds(rune2, 130, 48, 5, hover);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 79, 6, hover);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 79, 7, hover);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 79, 8, hover);
	}

	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = loadSprite(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = loadSprite(sid, "Lunar/LUNAROFF");
		RSInterface hover = addTabInterface(ID + 1);
		hover.isMouseoverTriggered = true;
		hover.mOverInterToTrigger = -1;
		setChildren(9, hover);
		addLunarSprite(ID + 2, 2, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, hover);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
		setBounds(ID + 3, 90, 4, 1, hover);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
		setBounds(ID + 4, 90, 34, 2, hover);
		setBounds(30016, 14, 61, 3, hover);
		setBounds(rune1, 74, 61, 4, hover);
		setBounds(rune2, 130, 61, 5, hover);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 92, 6, hover);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 92, 7, hover);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 92, 8, hover);
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, int j) {
		RSInterface rsinterface = addTabInterface(i);
		rsinterface.parentID = i;
		rsinterface.id = i;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 0;
		rsinterface.height = 0;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = a;
		rsinterface.centerText = l;
		rsinterface.textShadow = m;
		rsinterface.textDrawingAreas = RSInterface.fonts[j];
		rsinterface.message = s;
		rsinterface.textColor = k;
	}

	public static TextDrawingArea[] fonts;

	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name,
			String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[3];
		rsInterface.anIntArray212 = new int[3];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.sprite2 = loadSprite(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = loadSprite(sid, "Lunar/LUNAROFF");
		RSInterface hover = addTabInterface(ID + 1);
		hover.mOverInterToTrigger = -1;
		hover.isMouseoverTriggered = true;
		setChildren(7, hover);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, hover);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
		setBounds(ID + 3, 90, 4, 1, hover);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
		setBounds(ID + 4, 90, 19, 2, hover);
		setBounds(30016, 37, 35, 3, hover);// Rune
		setBounds(rune1, 112, 35, 4, hover);// Rune
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 50, 66, 5, hover);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 123, 66, 6, hover);

	}

	public static Sprite loadSprite(int i, String s) {

		Sprite sprite;
		try {
			sprite = new Sprite(s + " " + i);
			if (sprite != null) {
				return sprite;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
		return sprite;
	}

	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1,
			int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.mOverInterToTrigger = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.anIntArray245 = new int[4];
		rsInterface.anIntArray212 = new int[4];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = ra1;
		rsInterface.anIntArray245[1] = 3;
		rsInterface.anIntArray212[1] = ra2;
		rsInterface.anIntArray245[2] = 3;
		rsInterface.anIntArray212[2] = ra3;
		rsInterface.anIntArray245[3] = 3;
		rsInterface.anIntArray212[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.sprite2 = loadSprite(sid, "Lunar/LUNARON");
		rsInterface.sprite1 = loadSprite(sid, "Lunar/LUNAROFF");
		RSInterface hover = addTabInterface(ID + 1);
		hover.mOverInterToTrigger = -1;
		hover.isMouseoverTriggered = true;
		setChildren(9, hover);
		addLunarSprite(ID + 2, 0, "Lunar/BOX");
		setBounds(ID + 2, 0, 0, 0, hover);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, 1);
		setBounds(ID + 3, 90, 4, 1, hover);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, 0);
		setBounds(ID + 4, 90, 19, 2, hover);
		setBounds(30016, 14, 35, 3, hover);
		setBounds(rune1, 74, 35, 4, hover);
		setBounds(rune2, 130, 35, 5, hover);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 66, 6, hover);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 66, 7, hover);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 66, 8, hover);
	}

	public static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.aByte254 = 0;
		rsInterface.mOverInterToTrigger = -1;
		rsInterface.anIntArray245 = new int[1];
		rsInterface.anIntArray212 = new int[1];
		rsInterface.anIntArray245[0] = 3;
		rsInterface.anIntArray212[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		rsInterface.textShadow = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.aString228 = "";
		rsInterface.textColor = 12582912;
		rsInterface.anInt219 = 49152;
	}

	public static void prayerTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(5608);
		RSInterface currentPray = interfaceCache[687];
		addSprite(5651, 0, "Prayer/PRAYER");
		currentPray.textColor = 0xFF981F;
		currentPray.textShadow = true;
		currentPray.message = "%1/%2";

		int[] ID1 = { 18016, 18017, 18018, 18019, 18020, 18021, 18022, 18023, 18024, 18025, 18026, 18027, 18028, 18029,
				18030, 18031, 18032, 18033, 18034, 18035, 18036, 18037, 18038, 18039, 18040, 18041 };
		int[] X = { 8, 44, 80, 114, 150, 8, 44, 80, 116, 152, 8, 42, 78, 116, 152, 8, 44, 80, 116, 150, 6, 44, 80, 116,
				150, 6 };
		int[] Y = { 6, 6, 6, 4, 4, 42, 42, 42, 42, 42, 79, 76, 76, 78, 78, 114, 114, 114, 114, 112, 148, 150, 150, 150,
				148, 184 };

		int[] hoverIDs = { 18050, 18052, 18054, 18056, 18058, 18060, 18062, 18064, 18066, 18068, 18070, 18072, 18074,
				18076, 18078, 18080, 18082, 18084, 18086, 18088, 18090, 18092, 18094, 18096, 18098, 18100 };
		int[] hoverX = { 12, 8, 20, 12, 24, 2, 2, 6, 6, 50, 6, 6, 10, 6, 6, 5, 5, 5, 5, 5, 18, 28, 28, 50, 1, 1 };
		int[] hoverY = { 42, 42, 42, 42, 42, 80, 80, 80, 80, 80, 118, 118, 118, 118, 118, 150, 150, 150, 150, 150, 105,
				80, 65, 65, 65, 110 };
		String[] hoverStrings = { "Level 01\nThick Skin\nIncreases your Defence by 5%",
				"Level 04\nBurst of Strength\nIncreases your Strength by 5%",
				"Level 07\nCharity of Thought\nIncreases your Attack by 5%",
				"Level 08\nSharp Eye\nIncreases your Ranged by 5%", "Level 09\nMystic Will\nIncreases your Magic by 5%",
				"Level 10\nRock Skin\nIncreases your Defence by 10%",
				"Level 13\nSuperhuman Strength\nIncreases your Strength by 10%",
				"Level 16\nImproved Reflexes\nIncreases your Attack by 10%",
				"Level 19\nRapid Restore\n2x restore rate for all stats\nexcept Hitpoints and Prayer",
				"Level 22\nRapid Heal\n2x restore rate for the\nHitpoints stat",
				"Level 25\nProtect Item\nKeep one extra item if you die",
				"Level 26\nHawk Eye\nIncreases your Ranged by 10%",
				"Level 27\nMystic Lore\nIncreases your Magic by 10%",
				"Level 28\nSteel Skin\nIncreases your Defence by 15%",
				"Level 31\nUltimate Strength\nIncreases your Strength by 15%",
				"Level 34\nIncredible Reflexes\nIncreases your Attack by 15%",
				"Level 37\nProtect from Magic\nProtection from magical attacks",
				"Level 40\nProtect from Missiles\nProtection from ranged attacks",
				"Level 43\nProtect from Melee\nProtection from close attacks",
				"Level 44\nEagle Eye\nIncreases your Ranged by 15%",
				"Level 45\nMystic Might\nIncreases your Magic by 15%",
				"Level 46\nRetribution\nInflicts damage to nearby\ntargets if you die",
				"Level 49\nRedemption\nHeals you when damaged\nand Hitpoints falls\nbelow 10%",
				"Level 52\nSmite\n1/4 of damage dealt is\nalso removed from\nopponents Prayer",
				"Level 60\nChivalry\nIncreases your Defence by 20%,\nStrength by 18% and Attack by\n15%",
				"Level 70\nPiety\nIncreases your Defence by 25%,\nStrength by 23% and Attack by\n20%" };

		int ID2[] = { 5609, 5610, 5611, 5612, 5613, 5614, 5615, 5616, 5617, 5618, 5619, 5620, 5621, 5622, 5623, 683,
				684, 685, 5632, 5633, 5634, 5635, 5636, 5637, 5638, 5639, 5640, 5641, 5642, 5643, 5644, 686, 5645, 5649,
				5647, 5648, 18000, 18001, 18002, 18003, 18004, 18005, 18006, 18007, 18008, 18009, 18010, 18011, 18012,
				18013, 18014, 18015, 5651, 687 };
		int X2[] = { 6, 42, 78, 6, 42, 78, 114, 150, 6, 114, 150, 6, 42, 78, 114, 42, 78, 114, 8, 44, 80, 8, 44, 80,
				116, 152, 8, 116, 152, 8, 44, 80, 116, 44, 80, 116, 114, 117, 150, 153, 42, 45, 78, 81, 150, 153, 6, 9,
				150, 157, 6, 8, 65, 14 };
		int Y2[] = { 4, 4, 4, 40, 40, 40, 40, 40, 76, 76, 76, 112, 112, 112, 112, 148, 148, 148, 6, 6, 6, 42, 42, 42,
				42, 42, 79, 78, 78, 114, 114, 114, 114, 150, 150, 150, 4, 8, 4, 7, 76, 80, 76, 79, 112, 116, 148, 151,
				148, 151, 184, 194, 242, 244 };

		String[] oldPrayerNames = { "Thick Skin", "Burst of Strength", "Charity of Thought", "Rock Skin",
				"Superhuman Strength", "Improved Reflexes", "Rapid Restore", "Rapid Heal", "Protect Item", "Steel Skin",
				"Ultimate Strength", "Incredible Reflexes", "Protect from Magic", "Protect from Missiles",
				"Protect from Melee", "Retribution", "Redemption", "Smite" };
		addPrayer(18000, 0, 601, 7, 0, "Sharp Eye");
		addPrayer(18002, 0, 602, 8, 1, "Mystic Will");
		addPrayer(18004, 0, 603, 25, 2, "Hawk Eye");
		addPrayer(18006, 0, 604, 26, 3, "Mystic Lore");
		addPrayer(18008, 0, 605, 43, 4, "Eagle Eye");
		addPrayer(18010, 0, 606, 44, 5, "Mystic Might");
		addPrayer(18012, 0, 607, 59, 6, "Chivalry");
		addPrayer(18014, 0, 608, 69, 7, "Piety");

		for (int i = 0; i < 18; i++) {
			addOldPrayer(ID2[i], oldPrayerNames[i]);
		}

		for (int i = 0; i < 26; i++) {
			addPrayerHover(ID1[i], hoverIDs[i], i, hoverStrings[i]);
		}

		tab.totalChildren(106); // 54
		tab.child(52, 5651, 70, 242);
		for (int ii = 0; ii < 54; ii++) {
			tab.child(ii, ID2[ii], X2[ii], Y2[ii]);
		}

		int frame = 54;
		int frame2 = 0;
		for (int i : ID1) {
			tab.child(frame, i, X[frame2], Y[frame2]);
			frame++;
			frame2++;
		}

		int frame3 = 0;
		for (int i : hoverIDs) {
			tab.child(frame, i, hoverX[frame3], hoverY[frame3]);
			frame++;
			frame3++;
		}
	}

	protected static void addOldPrayer(int id, String prayerName) {
		RSInterface rsi = interfaceCache[id];
		rsi.tooltip = "Activate@or2@ " + prayerName;
	}

	public static void addPrayerHover(int i, int hoverID, int prayerSpriteID, String hoverText) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 5608;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = hoverID;
		Interface.sprite2 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.sprite1 = imageLoader(0, "tabs/prayer/hover/PRAYERH");
		Interface.width = 34;
		Interface.height = 34;

		Interface = addTabInterface(hoverID);
		Interface.id = hoverID;
		Interface.parentID = 5608;
		Interface.type = 0;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.aByte254 = 0;
		Interface.mOverInterToTrigger = -1;
		Interface.width = 512;
		Interface.height = 334;
		Interface.isMouseoverTriggered = true;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, Interface);
		setBounds(hoverID + 1, 0, 0, 0, Interface);
	}

	public static void addPrayer(int i, int configId, int configFrame, int requiredValues, int spriteID,
			String prayerName) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = 5608;
		tab.type = 5;
		tab.atActionType = 4;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.sprite1 = imageLoader(0, "PRAYERGLOW");
		tab.sprite2 = imageLoader(1, "PRAYERGLOW");
		tab.width = 34;
		tab.height = 34;
		tab.anIntArray245 = new int[1];
		tab.anIntArray212 = new int[1];
		tab.anIntArray245[0] = 1;
		tab.anIntArray212[0] = configId;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
		// tab.tooltip = "Activate@or2@ " + prayerName;
		tab.tooltip = "Select";
		RSInterface tab2 = addTabInterface(i + 1);
		tab2.id = i + 1;
		tab2.parentID = 5608;
		tab2.type = 5;
		tab2.atActionType = 0;
		tab2.contentType = 0;
		tab2.aByte254 = 0;
		tab2.mOverInterToTrigger = -1;
		tab2.sprite1 = imageLoader(spriteID, "Prayer/PRAYON");
		tab2.sprite2 = imageLoader(spriteID, "Prayer/PRAYOFF");
		tab2.width = 34;
		tab2.height = 34;
		tab2.anIntArray245 = new int[1];
		tab2.anIntArray212 = new int[1];
		tab2.anIntArray245[0] = 2;
		tab2.anIntArray212[0] = requiredValues + 1;
		tab2.valueIndexArray = new int[1][3];
		tab2.valueIndexArray[0][0] = 2;
		tab2.valueIndexArray[0][1] = 5;
		tab2.valueIndexArray[0][2] = 0;
	}

	public static void spinInterface(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(24000);
		addSprite(24001, 0, "spin/sprite");
		addSprite(24002, 2, "spin/sprite");
		addSprite(24003, 3, "spin/sprite");
		addSprite(24004, 4, "spin/sprite");
		addSprite(24005, 5, "spin/sprite");
		setChildren(6, tab);
		tab.child(0, 24001, 432, 250);
		tab.child(1, 24002, 464, 250);
		tab.child(2, 24003, 432, 270);
		tab.child(3, 24004, 464, 270);
		tab.child(4, 24005, 439, 257);
		tab.child(5, 10494, 449, 261);
	}

	public static void Sidebar0(TextDrawingArea[] tda) {
		Sidebar0a(1698, 1701, 7499, "Chop", "Hack", "Smash", "Block", 42, 75, 127, 75, 39, 128, 125, 128, 122, 103, 40,
				50, 122, 50, 40, 103, tda, "Accurate\nSlash\nAttack XP", "Aggressive\nSlash\nStrength XP",
				"Aggressive\nCrush\nStrength XP", "Defensive\nSlash\nDefence XP", 40132, 40136, 40140, 40144); // OK

		Sidebar0a(2276, 2279, 7574, "Stab", "Lunge", "Slash", "Block", 43, 75, 124, 75, 41, 128, 125, 128, 122, 103, 40,
				50, 122, 50, 40, 103, tda, "Accurate\nStab\nAttack XP", "Aggressive\nStab\nStrength XP",
				"Aggressive\nSlash\nStrength XP", "Defensive\nStab\nDefence XP", 40020, 40024, 40028, 40032); // OK

		Sidebar0a(2423, 2426, 7599, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40,
				50, 122, 50, 40, 103, tda, "Accurate\nSlash\nAttack XP", "Aggressive\nSlash\nStrength XP",
				"Controlled\nStab\nShared XP", "Defensive\nSlash\nDefence XP", 40036, 40040, 40044, 40048); // OK

		Sidebar0a(3796, 3799, 7624, "Pound", "Pummel", "Spike", "Block", 39, 75, 121, 75, 41, 128, 125, 128, 122, 103,
				40, 50, 122, 50, 40, 103, tda, "", "", "", "", 40052, 40056, 40060, 40064); // WTF
																							// IS
																							// THIS?!

		Sidebar0a(4679, 4682, 7674, "Lunge", "Swipe", "Pound", "Block", 40, 75, 124, 75, 39, 128, 125, 128, 122, 103,
				40, 50, 122, 50, 40, 103, tda, "Controlled\nStab\nShared XP", "Controlled\nSlash\nShared XP",
				"Controlled\nCrush\nShared XP", "Defensive\nStab\nDefence XP", 40068, 40072, 40076, 40080); // OK

		Sidebar0a(4705, 4708, 7699, "Chop", "Slash", "Smash", "Block", 42, 75, 125, 75, 39, 128, 125, 128, 122, 103, 40,
				50, 122, 50, 40, 103, tda, "Accurate\nSlash\nAttack XP", "Aggressive\nSlash\nStrength XP",
				"Aggressive\nCrush\nStrength XP", "Defensive\nSlash\nDefence XP", 40084, 40088, 40092, 40096); // ???

		Sidebar0a(5570, 5573, 7724, "Spike", "Impale", "Smash", "Block", 41, 75, 123, 75, 39, 128, 125, 128, 122, 103,
				40, 50, 122, 50, 40, 103, tda, "Accurate\nStab\nAttack XP", "Aggressive\nStab\nStrength XP",
				"Aggressive\nCrush\nStrength XP", "Defensive\nStab\nDefence XP", 40010, 40104, 40108, 40112); // OK

		Sidebar0a(7762, 7765, 7800, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40,
				50, 122, 50, 40, 103, tda, "Accurate\nSlash\nAttack XP", "Aggressive\nSlash\nStrength XP",
				"Controlled\nStab\nShared XP", "Defensive\nSlash\nDefence XP", 40116, 40120, 40124, 40128); // OK

		Sidebar0b(776, 779, "Reap", "Chop", "Jab", "Block", 42, 75, 126, 75, 46, 128, 125, 128, 122, 103, 122, 50, 40,
				103, 40, 50, tda, "", "", "", "", 40132, 40136, 40140, 40144); // ???

		Sidebar0c(425, 428, 7474, "Pound", "Pummel", "Block", 39, 75, 121, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda,
				"Accurate\nCrush\nAttack XP", "Aggressive\nCrush\nDefence XP", "Defensive\nCrush\nDefence XP", 40148,
				40152, 40156); // OK

		Sidebar0c(1749, 1752, 7524, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122,
				50, tda, "Accurate\nRanged XP", "Rapid\nRanged XP", "Long range\nRanged XP\nDefence XP", 40160, 40164,
				40168); // OK

		Sidebar0c(1764, 1767, 7549, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122,
				50, tda, "Accurate\nRanged XP", "Rapid\nRanged XP", "Long range\nRanged XP\nDefence XP", 40172, 40176,
				40180); // OK

		Sidebar0c(4446, 4449, 7649, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122,
				50, tda, "Accurate\nRanged XP", "Rapid\nRanged XP", "Long range\nRanged XP\nDefence XP", 40184, 40188,
				40192); // OK

		Sidebar0c(5855, 5857, 7749, "Punch", "Kick", "Block", 40, 75, 129, 75, 42, 128, 40, 50, 122, 50, 40, 103, tda,
				"Accurate\nCrush\nAttack XP", "Aggressive\nCrush\nStrength XP", "Defensive\nCrush\nDefence XP", 40196,
				40200, 40204); // OK

		Sidebar0c(6103, 6132, 6117, "Bash", "Pound", "Block", 43, 75, 124, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda,
				"Accurate\nCrush\nAttack XP", "Aggressive\nCrush\nStrength XP", "Defensive\nCrush\nDefence XP", 40208,
				40212, 40216); // ???

		Sidebar0c(8460, 8463, 8493, "Jab", "Swipe", "Fend", 46, 75, 124, 75, 43, 128, 40, 103, 40, 50, 122, 50, tda,
				"Controlled\nStabbed\nShared XP", "Aggressive\nSlash\nStrength XP", "Defensive\nStab\nDefence XP",
				40224, 40228, 40232); // OK

		Sidebar0c(12290, 12293, 12323, "Flick", "Lash", "Deflect", 44, 75, 127, 75, 36, 128, 40, 50, 40, 103, 122, 50,
				tda, "Accurate\nSlash\nAttack XP", "Controlled\nSlash\nShared XP", "Defensive\nSlash\nDefence XP",
				40236, 40240, 40244); // OK

		Sidebar0d(328, 331, "Bash", "Pound", "Focus", 42, 66, 39, 101, 41, 136, 40, 120, 40, 50, 40, 85, tda);

		RSInterface rsi = addTabInterface(19300);
		textSize(3983, tda, 0);
		addAttackStyleButton2(150, 150, 172, 150, 44, "Auto Retaliate", 40000, 154, 42,
				"When active, you will\nautomatically fight back if\nattacked.", tda);

		rsi.totalChildren(3);
		rsi.child(0, 3983, 52, 25); // combat level
		rsi.child(1, 150, 21, 153); // auto retaliate
		rsi.child(2, 40000, 26, 200);

		rsi = interfaceCache[3983];
		rsi.centerText = true;
		rsi.textColor = 0xff981f;
	}

	public static void textSize(int id, TextDrawingArea tda[], int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	public static void addAttackStyleButton2(int id, int sprite, int setconfig, int width, int height, String s,
			int hoverID, int hW, int hH, String hoverText, TextDrawingArea[] TDA) {
		RSInterface rsi = addTabInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = hoverID;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
		rsi = addTabInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	protected static Sprite CustomSpriteLoader(int id, String s) {
		long l = (TextClass.method585(s) << 8) + (long) id;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite("/Attack/" + id + s);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4,
			int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x,
			int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda,
			String popupString1, String popupString2, String popupString3, String popupString4, int hoverID1,
			int hoverID2, int hoverID3, int hoverID4) // 4button
														// spec
	{
		RSInterface rsi = addTabInterface(id); // 2423
		addAttackText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addAttackText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addAttackText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addAttackText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addAttackText(id2 + 14, text4, tda, 0, 0xff981f, false);

		rsi.specialBar(id3, tda); // 7599

		addAttackHover(id2 + 3, hoverID1, popupString1, tda);
		addAttackHover(id2 + 6, hoverID2, popupString2, tda);
		addAttackHover(id2 + 5, hoverID3, popupString3, tda);
		addAttackHover(id2 + 4, hoverID4, popupString4, tda);

		rsi.width = 190;
		rsi.height = 261;

		int frame = 0;
		rsi.totalChildren(20);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599
		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, hoverID1, 25, 96);
		frame++;
		rsi.child(frame, hoverID2, 108, 96);
		frame++;
		rsi.child(frame, hoverID3, 25, 149);
		frame++;
		rsi.child(frame, hoverID4, 108, 149);
		frame++;
		rsi.child(frame, 40005, 28, 149);
		frame++; // special bar tooltip

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.sprite1 = CustomSpriteLoader(sprite, "");
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	public static void removeSomething(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
	}

	public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		if (sprite2 == sprite)
			rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		else
			rsi.sprite2 = CustomSpriteLoader(sprite2, "");
		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.mOverInterToTrigger = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public void specialBar(int id, TextDrawingArea[] tda) // 7599
	{
		addActionButton(id - 12, 7587, -1, 150, 26, "Use @gre@Special Attack");
		for (int i = id - 11; i < id; i++)
			removeSomething(i);

		RSInterface rsi = interfaceCache[id - 12];
		rsi.width = 150;
		rsi.height = 26;
		rsi.mOverInterToTrigger = 40005;

		rsi = interfaceCache[id];
		rsi.width = 150;
		rsi.height = 26;

		rsi.child(0, id - 12, 0, 0);

		rsi.child(12, id + 1, 3, 7);

		rsi.child(23, id + 12, 16, 8);

		for (int i = 13; i < 23; i++) {
			rsi.childY[i] -= 1;
		}

		rsi = interfaceCache[id + 1];
		rsi.type = 5;
		rsi.sprite1 = CustomSpriteLoader(7600, "");

		for (int i = id + 2; i < id + 12; i++) {
			rsi = interfaceCache[i];
			rsi.type = 5;
		}

		sprite1(id + 2, 7601);
		sprite1(id + 3, 7602);
		sprite1(id + 4, 7603);
		sprite1(id + 5, 7604);
		sprite1(id + 6, 7605);
		sprite1(id + 7, 7606);
		sprite1(id + 8, 7607);
		sprite1(id + 9, 7608);
		sprite1(id + 10, 7609);
		sprite1(id + 11, 7610);

		rsi = addInterface(40005);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = 40005;
		rsi.id = 40005;
		addBox(40006, 0, false, 0x000000, "Select to perform a special\nattack.");
		setChildren(1, rsi);
		setBounds(40006, 0, 0, 0, rsi);
	}

	public static void addAttackHover(int id, int hoverID, String hoverText, TextDrawingArea[] TDA) {
		RSInterface rsi = interfaceCache[id];
		rsi.mOverInterToTrigger = hoverID;

		rsi = addTabInterface(hoverID);
		rsi.isMouseoverTriggered = true;
		rsi.type = 0;
		rsi.atActionType = 0;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = hoverID;
		rsi.id = hoverID;
		addBox(hoverID + 1, 0, false, 0x000000, hoverText);
		setChildren(1, rsi);
		setBounds(hoverID + 1, 0, 0, 0, rsi);
	}

	public static void addBox(int id, int byte1, boolean filled, int color, String text) {
		RSInterface Interface = addTabInterface(id);
		Interface.id = id;
		Interface.parentID = id;
		Interface.type = 9;
		Interface.aByte254 = (byte) byte1;
		Interface.aBoolean227 = filled;
		Interface.mOverInterToTrigger = -1;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.textColor = color;
		Interface.message = text;
	}

	public static void addAttackText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		if (centered)
			rsi.centerText = true;
		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.textColor = color;
		rsi.id = id;
		rsi.type = 4;
	}

	public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x,
			int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y,
			int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda,
			String popupString1, String popupString2, String popupString3, String popupString4, int hoverID1,
			int hoverID2, int hoverID3, int hoverID4) // 4button
														// nospec
	{
		RSInterface rsi = addTabInterface(id); // 2423
		addAttackText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addAttackText(id2 + 11, text1, tda, 0, 0xff981f, false);
		addAttackText(id2 + 12, text2, tda, 0, 0xff981f, false);
		addAttackText(id2 + 13, text3, tda, 0, 0xff981f, false);
		addAttackText(id2 + 14, text4, tda, 0, 0xff981f, false);

		addAttackHover(id2 + 3, hoverID1, popupString1, tda);
		addAttackHover(id2 + 6, hoverID2, popupString2, tda);
		addAttackHover(id2 + 5, hoverID3, popupString3, tda);
		addAttackHover(id2 + 4, hoverID4, popupString4, tda);

		rsi.width = 190;
		rsi.height = 261;

		int frame = 0;
		rsi.totalChildren(18);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, hoverID1, 25, 96);
		frame++;
		rsi.child(frame, hoverID2, 108, 96);
		frame++;
		rsi.child(frame, hoverID3, 25, 149);
		frame++;
		rsi.child(frame, hoverID4, 108, 149);
		frame++;

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x,
			int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y,
			int img3x, int img3y, TextDrawingArea[] tda, String popupString1, String popupString2, String popupString3,
			int hoverID1, int hoverID2, int hoverID3) // 3button spec
	{
		RSInterface rsi = addTabInterface(id); // 2423
		addAttackText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addAttackText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addAttackText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addAttackText(id2 + 11, text3, tda, 0, 0xff981f, false);

		rsi.specialBar(id3, tda); // 7599

		addAttackHover(id2 + 5, hoverID1, popupString1, tda);
		addAttackHover(id2 + 4, hoverID2, popupString2, tda);
		addAttackHover(id2 + 3, hoverID3, popupString3, tda);

		rsi.width = 190;
		rsi.height = 261;

		int frame = 0;
		rsi.totalChildren(16);

		rsi.child(frame, id2 + 3, 21, 99);
		frame++;
		rsi.child(frame, id2 + 4, 105, 46);
		frame++;
		rsi.child(frame, id2 + 5, 21, 46);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // chop
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // slash
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // lunge

		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599
		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon

		rsi.child(frame, hoverID1, 25, 96);
		frame++;
		rsi.child(frame, hoverID2, 108, 96);
		frame++;
		rsi.child(frame, hoverID3, 25, 149);
		frame++;
		rsi.child(frame, 40005, 28, 149);
		frame++; // special bar tooltip

		for (int i = id2 + 3; i < id2 + 6; i++) {
			rsi = interfaceCache[i];
			rsi.sprite1 = CustomSpriteLoader(19301, "");
			rsi.sprite2 = CustomSpriteLoader(19301, "a");
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0d(int id, int id2, String text1, String text2, String text3, int str1x, int str1y,
			int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x,
			int img3y, TextDrawingArea[] tda) // 3button nospec
												// (magic intf)
	{
		RSInterface rsi = addTabInterface(id); // 2423
		addAttackText(id2, "-2", tda, 3, 0xff981f, true); // 2426
		addAttackText(id2 + 9, text1, tda, 0, 0xff981f, false);
		addAttackText(id2 + 10, text2, tda, 0, 0xff981f, false);
		addAttackText(id2 + 11, text3, tda, 0, 0xff981f, false);

		addAttackText(353, "Spell", tda, 0, 0xff981f, false);
		addAttackText(354, "Spell", tda, 0, 0xff981f, false);

		addCacheSprite(337, 19, 0, "combaticons");
		addCacheSprite(338, 13, 0, "combaticons2");
		addCacheSprite(339, 14, 0, "combaticons2");

		/* addToggleButton(id, sprite, config, width, height, tooltip); */
		// addToggleButton(349, 349, 108, 68, 44, "Select");
		removeSomething(349);
		addToggleButton(350, 350, 108, 68, 44, "Select");

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last);

		rsi.child(frame, id2 + 3, 20, 115);
		frame++;
		rsi.child(frame, id2 + 4, 20, 80);
		frame++;
		rsi.child(frame, id2 + 5, 20, 45);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // bash
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // pound
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // focus

		rsi.child(frame, 349, 105, 46);
		frame++; // spell1
		rsi.child(frame, 350, 104, 106);
		frame++; // spell2

		rsi.child(frame, 353, 125, 74);
		frame++; // spell
		rsi.child(frame, 354, 125, 134);
		frame++; // spell

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
	}

	public static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.sprite1 = method207(sprite1, aClass44, sprites);
		rsi.sprite2 = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.sprite1 = CustomSpriteLoader(sprite, "");
		rsi.sprite2 = CustomSpriteLoader(sprite, "a");
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray212[0] = 1;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.mOverInterToTrigger = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public static void addClickableText(int id, String text, String tooltip, TextDrawingArea tda[], int idx, int color,
			boolean center, boolean shadow, int width) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 1;
		tab.width = width;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0xffffff;
		tab.anInt239 = 0;
		tab.tooltip = tooltip;
	}

	public static void shop(TextDrawingArea[] TDA) {
		RSInterface Interface = addTabInterface(3824);
		setChildren(8, Interface);
		addSprite(3825, 0, "shop/SHOP");
		addHover(3902, 3, 0, 3826, 1, "shop/BANK", 17, 17, "Close Window");
		addHovered(3826, 2, "shop/BANK", 17, 17, 3827);
		addText(19679, "Main Stock", 0xFF981F, false, true, 52, TDA, 1);// 3628////19679
		addText(19680, "Store Info", 0xBF751D, false, true, 52, TDA, 1);// 3629//19680
		addButton(19681, 2, "shop/SHOP", 95, 19, "Store Information", 1);// 3630//19681
		setBounds(3825, 12, 12, 0, Interface);
		setBounds(3902, 471, 22, 1, Interface);
		setBounds(3826, 471, 22, 2, Interface);
		setBounds(3900, 60, 85, 3, Interface);
		setBounds(3901, 240, 21, 4, Interface);
		setBounds(19679, 42, 54, 5, Interface);
		setBounds(19680, 150, 54, 6, Interface);
		setBounds(19681, 129, 50, 7, Interface);
		Interface = interfaceCache[3900];
		Interface.invSpritePadX = 8;
		Interface.width = 10;
		Interface.height = 4;
		Interface.invSpritePadY = 19;
		Interface = addTabInterface(19682);
		addSprite(19683, 1, "shop/SHOP");
		addText(19684, "Main Stock", 0xBF751D, false, true, 52, TDA, 1);
		addText(19685, "Store Info", 0xFF981F, false, true, 52, TDA, 1);
		addButton(19686, 2, "shop/SHOP", 95, 19, "Main Stock", 1);
		setChildren(7, Interface);
		setBounds(19683, 12, 12, 0, Interface);
		setBounds(3901, 240, 21, 1, Interface);
		setBounds(19684, 42, 54, 2, Interface);
		setBounds(19685, 150, 54, 3, Interface);
		setBounds(19686, 23, 50, 4, Interface);
		setBounds(3902, 471, 22, 5, Interface);
		setBounds(3826, 60, 85, 6, Interface);
	}

	public static void addButton(int i, int j, String name, int W, int H, String S, int AT) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = AT;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = 52;
		RSInterface.sprite1 = imageLoader(j, name);
		RSInterface.sprite2 = imageLoader(j, name);
		RSInterface.width = W;
		RSInterface.height = H;
		RSInterface.tooltip = S;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.aByte254 = 0;
		RSInterface.mOverInterToTrigger = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.aString228 = "";
		RSInterface.textColor = k;
	}

	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j,
			int dsc) {
		RSInterface rsinterface = addTabInterface(i);
		rsinterface.parentID = i;
		rsinterface.id = i;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = a;
		rsinterface.centerText = l;
		rsinterface.textShadow = m;
		rsinterface.textDrawingAreas = TDA[j];
		rsinterface.message = s;
		rsinterface.aString228 = "";
		rsinterface.anInt219 = 0;
		rsinterface.textColor = k;
		rsinterface.anInt216 = dsc;
		rsinterface.tooltip = s;
	}

	public static void spawnInterface(TextDrawingArea[] tda) {
		RSInterface rs = addTabInterface(28100);
		addHoverButton(28101, "spawn/button", 0, 68, 32, "Food", 201, 28102, 5);
		addHoveredButton(28102, "spawn/button", 1, 68, 32, 2001);
		addButton(28103, 0, "spawn/food", "Spawn food", 28104, 1, 32, 32);
		addTooltip(28104, "Spawn food");
		addText(28106, "Food", tda, 1, 0xE68A00, true, true);

		addButton(28107, 0, "spawn/potions", "Spawn potions", 28110, 1, 32, 32);
		addHoverButton(28108, "spawn/button", 0, 68, 32, "Potions", 201, 28109, 5);
		addHoveredButton(28109, "spawn/button", 1, 68, 32, 2001);
		addTooltip(28110, "Spawn potions");
		addText(28112, "Potions", tda, 1, 0xE68A00, true, true);

		addButton(28113, 0, "spawn/runes", "Spawn runes", 28116, 1, 32, 32);
		addHoverButton(28114, "spawn/button", 0, 68, 32, "Runes", 201, 28115, 5);
		addHoveredButton(28115, "spawn/button", 1, 68, 32, 2001);
		addTooltip(28116, "Spawn runes");
		addText(28118, "Runes", tda, 1, 0xE68A00, true, true);

		addButton(28119, 0, "spawn/hybrid", "Spawn hybrid set", 28122, 1, 32, 32);
		addHoverButton(28120, "spawn/button", 0, 68, 32, "Hybrid set", 201, 28121, 5);
		addHoveredButton(28121, "spawn/button", 1, 68, 32, 2001);
		addTooltip(28122, "Spawn hybrid set");
		addText(28124, "Hybrid Set", tda, 1, 0xE68A00, true, true);

		addButton(28125, 0, "spawn/melee", "Spawn main set", 28128, 1, 32, 32);
		addHoverButton(28126, "spawn/button", 0, 68, 32, "Main set", 201, 28127, 5);
		addHoveredButton(28127, "spawn/button", 1, 68, 32, 2001);
		addTooltip(28128, "Spawn main set");
		addText(28130, "Main set", tda, 1, 0xE68A00, true, true);

		addButton(28131, 0, "spawn/pure", "Spawn pure set", 28134, 1, 32, 32);
		addHoverButton(28132, "spawn/button", 0, 68, 32, "Pure set", 201, 28133, 5);
		addHoveredButton(28133, "spawn/button", 1, 68, 32, 2001);
		addTooltip(28134, "Spawn pure set");
		addText(28136, "Pure set", tda, 1, 0xE68A00, true, true);

		setChildren(30, rs);

		rs.child(0, 28103, 26, 16);
		rs.child(1, 28101, 66, 16);
		rs.child(2, 28102, 66, 16);
		rs.child(3, 28106, 98, 26);
		rs.child(4, 28104, 6, 16);

		rs.child(5, 28107, 26, 56);
		rs.child(6, 28108, 66, 56);
		rs.child(7, 28109, 66, 56);
		rs.child(8, 28112, 98, 66);
		rs.child(9, 28110, 6, 56);

		rs.child(10, 28113, 26, 96);
		rs.child(11, 28114, 66, 96);
		rs.child(12, 28115, 66, 96);
		rs.child(13, 28118, 98, 106);
		rs.child(14, 28116, 6, 96);

		rs.child(15, 28119, 26, 136);
		rs.child(16, 28120, 66, 136);
		rs.child(17, 28121, 66, 136);
		rs.child(18, 28124, 98, 146);
		rs.child(19, 28122, 6, 136);

		rs.child(20, 28125, 26, 176);
		rs.child(21, 28126, 66, 176);
		rs.child(22, 28127, 66, 176);
		rs.child(23, 28130, 98, 186);
		rs.child(24, 28128, 6, 176);

		rs.child(25, 28131, 26, 216);
		rs.child(26, 28132, 66, 216);
		rs.child(27, 28133, 66, 216);
		rs.child(28, 28136, 98, 226);
		rs.child(29, 28134, 6, 216);
	}

	public static void teleportTab(TextDrawingArea[] tda) {
		RSInterface rs = addTabInterface(28060);
		addHoverButton(28061, "teleport/button", 0, 150, 43, "Teleport", 201, 28062, 5);
		addHoveredButton(28062, "teleport/button", 1, 150, 43, 2000);

		addHoverButton(28063, "teleport/button", 0, 150, 43, "Teleport", 201, 28064, 5);
		addHoveredButton(28064, "teleport/button", 1, 150, 43, 2000);

		addHoverButton(28065, "teleport/button", 0, 150, 43, "Teleport", 201, 28066, 5);
		addHoveredButton(28066, "teleport/button", 1, 150, 43, 2000);

		addHoverButton(28067, "teleport/button", 0, 150, 43, "Teleport", 201, 28068, 5);
		addHoveredButton(28068, "teleport/button", 1, 150, 43, 2000);

		addText(28069, "Location Teleports", tda, 3, 0xE68A00, true, true);

		addText(28070, "Edgeville", tda, 2, 0xE68A00, true, true);
		addText(28071, "Falador", tda, 2, 0xE68A00, true, true);
		addText(28072, "East Dragons", tda, 2, 0xE68A00, true, true);
		addText(28073, "Mage Bank", tda, 2, 0xE68A00, true, true);

		setChildren(13, rs);
		rs.child(0, 28061, 22, 42);
		rs.child(1, 28062, 22, 42);

		rs.child(2, 28063, 22, 92);
		rs.child(3, 28064, 22, 92);

		rs.child(4, 28065, 22, 142);
		rs.child(5, 28066, 22, 142);

		rs.child(6, 28067, 22, 192);
		rs.child(7, 28068, 22, 192);

		rs.child(8, 28069, 95, 16);

		rs.child(9, 28070, 95, 56);
		rs.child(10, 28071, 95, 106);
		rs.child(11, 28072, 95, 156);
		rs.child(12, 28073, 95, 206);
	}

	public static void wildyInterface(TextDrawingArea[] tda) {
		RSInterface rs = addScreenInterface(28034);
		addSprite(28035, 0, "pvp/skull");
		addText(28036, "53-83", tda, 0, 0xE68A00, true, true);
		addText(28038, "Level:", tda, 0, 0xE68A00, true, true);
		addConfigButton(28037, 28034, 1, 0, "pvp/icon", 19, 19, new String[] { "Safe Zone" }, 1, 600);
		setChildren(4, rs);
		rs.child(0, 28035, 455, 247);
		rs.child(1, 28036, 467, 273);
		rs.child(2, 28037, 455, 247);
		rs.child(3, 28038, 467, 289);
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int mOver, int atAction, int width,
			int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = atAction;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = mOver;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
	}

	public static void bank(TextDrawingArea[] TDA) {
		RSInterface Tab = addInterface(23000);
		addText(23003, "", TDA, 0, 0xFFB000, false, true);
		addHover(23004, 3, 0, 23005, 1, "Bank/BANK", 21, 21, "Close");
		addHovered(23005, 2, "Interfaces/Bank/BANK", 21, 21, 23006);
		addText(23010, "0 / 448", TDA, 0, 0xFFB000, false, true);
		addButton(23011, 5, "Bank/BANK", "Deposit carried items", 23012, 1, 21, 21);
		Tab.scrollMax = 1000;
		addTooltip(23012, "Deposit carried items");
		Tab.children = new int[7];
		Tab.childX = new int[7];
		Tab.childY = new int[7];
		Tab.children[0] = 5292;
		Tab.childX[0] = 0;
		Tab.childY[0] = 0;
		Tab.children[1] = 23003;
		Tab.childX[1] = 410;
		Tab.childY[1] = 30;
		Tab.children[2] = 23004;
		Tab.childX[2] = 472;
		Tab.childY[2] = 27;
		Tab.children[3] = 23005;
		Tab.childX[3] = 472;
		Tab.childY[3] = 27;
		Tab.children[4] = 23010;
		Tab.childX[4] = 26;
		Tab.childY[4] = 33;
		Tab.children[5] = 23011;
		Tab.childX[5] = 245;
		Tab.childY[5] = 295;
		Tab.children[6] = 23012;
		Tab.childX[6] = 208;
		Tab.childY[6] = 273;
		RSInterface rsi = interfaceCache[5292];
		RSInterface Interface = interfaceCache[5385];
		Interface = interfaceCache[5382];
		addText(5384, "", TDA, 0, 0xFFB000, false, true);// cheap hax
		rsi.childX[90] = 410;
		rsi.childY[90] = 288;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addScreenInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.isMouseoverTriggered = true;
		rsi.mOverInterToTrigger = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}

	public static void addTooltipBox(int id, String text) {
		RSInterface rsi = addScreenInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 9;
		rsi.message = text;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addHover(int i, int aT, int cT, int hoverid, int sId, String NAME, int W, int H, String tip) {
		RSInterface rsinterfaceHover = addScreenInterface(i);
		rsinterfaceHover.id = i;
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.type = 5;
		rsinterfaceHover.atActionType = aT;
		rsinterfaceHover.contentType = cT;
		rsinterfaceHover.mOverInterToTrigger = hoverid;
		rsinterfaceHover.sprite1 = imageLoader(sId, NAME);
		rsinterfaceHover.sprite2 = imageLoader(sId, NAME);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}

	public static void addHovered(int i, int j, String imageName, int w, int h, int IMAGEID) {
		RSInterface rsinterfaceHover = addScreenInterface(i);
		rsinterfaceHover.parentID = i;
		rsinterfaceHover.id = i;
		rsinterfaceHover.type = 0;
		rsinterfaceHover.atActionType = 0;
		rsinterfaceHover.width = w;
		rsinterfaceHover.height = h;
		rsinterfaceHover.isMouseoverTriggered = true;
		rsinterfaceHover.mOverInterToTrigger = -1;
		addSprite(IMAGEID, j, imageName);
		setChildren(1, rsinterfaceHover);
		setBounds(IMAGEID, 0, 0, 0, rsinterfaceHover);
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text,
			int contentType, int hoverOver, int aT) {// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = hoverOver;
		tab.sprite1 = imageLoader(j, imageName);
		tab.sprite2 = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {// hoverable
																									// button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.isMouseoverTriggered = true;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, j, j, imageName);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(j, name);
		tab.sprite2 = imageLoader(k, name);
	}

	public static void addBankHover1(int interfaceID, int actionType, int hoverid, int spriteId, String NAME, int Width,
			int Height, String Tooltip, int hoverId2, int hoverSpriteId, String hoverSpriteName, int hoverId3,
			String hoverDisabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.aByte254 = 0;
		hover.sprite1 = imageLoader(spriteId, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId, hoverSpriteName, 0, 0);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverDisabledText, 0, 0);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addSprite(int i, int j, int k) {
		RSInterface rsinterface = interfaceCache[i] = new RSInterface();
		rsinterface.id = i;
		rsinterface.parentID = i;
		rsinterface.type = 5;
		rsinterface.atActionType = 1;
		rsinterface.contentType = 0;
		rsinterface.width = 20;
		rsinterface.height = 20;
		rsinterface.aByte254 = 0;
		rsinterface.mOverInterToTrigger = 52;
		rsinterface.sprite1 = imageLoader(j, "Equipment/SPRITE");
		rsinterface.sprite2 = imageLoader(k, "Equipment/SPRITE");
	}

	public static void addSprite(int ID, int i, int i2, String name, int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.aByte254 = (byte) 0;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(i, name);
		Tab.sprite2 = imageLoader(i2, name);
	}

	public static void addToItemGroup(int id, int w, int h, int x, int y, boolean actions, String action1,
			String action2, String action3) {
		RSInterface rsi = addScreenInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.isMouseoverTriggered = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.actions = new String[5];
		if (actions) {
			rsi.actions[0] = action1;
			rsi.actions[1] = action2;
			rsi.actions[2] = action3;
		}
		rsi.type = 2;
	}

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2,
			String NAME, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2,
			int hoverSpriteId, int hoverSpriteId2, String hoverSpriteName, int hoverId3, String hoverDisabledText,
			String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.sprite1 = imageLoader(spriteId, NAME);
		hover.sprite2 = imageLoader(spriteId2, NAME);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.anIntArray245 = new int[1];
		hover.anIntArray212 = new int[1];
		hover.anIntArray245[0] = 1;
		hover.anIntArray212[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.isMouseoverTriggered = true;
		hover.hoverType = -1;
		addSprites(hoverId2, hoverSpriteId, hoverSpriteId2, hoverSpriteName, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addSprites(int ID, int i, int i2, String name, int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.opacity = (byte) 0;
		Tab.hoverType = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(i, name);
		Tab.sprite2 = imageLoader(i2, name);
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public String hoverText;
	public int opacity;
	private int hoverType;
	private boolean inventoryHover;

	public static void addHoverBox(int id, String text) {
		RSInterface rsi = interfaceCache[id];// addTabInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.isMouseoverTriggered = true;
		rsi.type = 8;
		rsi.hoverText = text;
	}

	public static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.aString228 = text;
		rsi.message = text2;
		rsi.anIntArray245 = new int[1];
		rsi.anIntArray212 = new int[1];
		rsi.anIntArray245[0] = 1;
		rsi.anIntArray212[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addInputField(int identity, int characterLimit, int color, String text, int width, int height,
			boolean asterisks, boolean updatesEveryInput, String regex) {
		RSInterface field = addScreenInterface(identity);
		field.id = identity;
		field.type = 16;
		field.atActionType = 8;
		field.message = text;
		field.width = width;
		field.height = height;
		field.characterLimit = characterLimit;
		field.textColor = color;
		field.displayAsterisks = asterisks;
		field.tooltips = new String[] { "Clear", "Edit" };
		field.defaultInputFieldText = text;
		field.updatesEveryInput = updatesEveryInput;
		field.inputRegex = regex;
	}

	public static void addInputField(int identity, int characterLimit, int color, String text, int width, int height,
			boolean asterisks, boolean updatesEveryInput) {
		RSInterface field = addScreenInterface(identity);
		field.id = identity;
		field.type = 16;
		field.atActionType = 8;
		field.message = text;
		field.width = width;
		field.height = height;
		field.characterLimit = characterLimit;
		field.textColor = color;
		field.displayAsterisks = asterisks;
		field.defaultInputFieldText = text;
		field.tooltips = new String[] { "Clear", "Edit" };
		field.updatesEveryInput = updatesEveryInput;
	}

	public static void addInputField(int identity, int characterLimit, int color, String text, int width, int height,
			boolean asterisks) {
		RSInterface field = addScreenInterface(identity);
		field.id = identity;
		field.type = 16;
		field.atActionType = 8;
		field.message = text;
		field.width = width;
		field.height = height;
		field.characterLimit = characterLimit;
		field.textColor = color;
		field.displayAsterisks = asterisks;
		field.defaultInputFieldText = text;
		field.tooltips = new String[] { "Clear", "Edit" };
	}

	public static void addConfigButton(int ID, int pID, int bID, int bID2, String bName, int width, int height,
			String[] tooltips, int configID, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = 8;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.anIntArray245 = new int[1];
		Tab.anIntArray212 = new int[1];
		Tab.anIntArray245[0] = 1;
		Tab.anIntArray212[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.sprite1 = imageLoader(bID, bName);
		Tab.sprite2 = imageLoader(bID2, bName);
		Tab.tooltips = tooltips;
	}

	public static void setBounds(int ID, int X, int Y, int frame, RSInterface RSinterface) {
		RSinterface.children[frame] = ID;
		RSinterface.childX[frame] = X;
		RSinterface.childY[frame] = Y;
	}

	boolean updatesEveryInput;
	String defaultInputFieldText = "";
	int[] inputFieldTriggers;
	boolean displayAsterisks;
	public int characterLimit;
	public static int currentInputFieldId;
	String inputRegex = "";
	public boolean isInFocus;

	public String[] tooltips;

	public static void ancientMagicTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(12855);
		addButton(12856, 1, "Magic/Home", "Cast @gre@Home Teleport");
		RSInterface homeButton = interfaceCache[12856];
		homeButton.mOverInterToTrigger = 1196;
		int[] itfChildren = { 12856, 12939, 12987, 13035, 12901, 12861, 13045, 12963, 13011, 13053, 12919, 12881, 13061,
				12951, 12999, 13069, 12911, 12871, 13079, 12975, 13023, 13087, 12929, 12891, 13095, 1196, 12940, 12988,
				13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000, 13070, 12912, 12872,
				13080, 12976, 13024, 13088, 12930, 12892, 13096 };
		tab.totalChildren(itfChildren.length);
		for (int i1 = 0, xPos = 18, yPos = 8; i1 < itfChildren.length; i1++, xPos += 45) {
			if (xPos > 175) {
				xPos = 18;
				yPos += 28;
			}
			if (i1 < 25)
				tab.child(i1, itfChildren[i1], xPos, yPos);
			if (i1 > 24) {
				yPos = i1 < 41 ? 181 : 1;
				tab.child(i1, itfChildren[i1], 4, yPos);
			}
		}
	}

	public static void magicTab(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(1151);
		RSInterface homeHover = addTabInterface(1196);
		RSInterface spellButtons = interfaceCache[12424];
		spellButtons.scrollMax = 0;
		spellButtons.height = 260;
		spellButtons.width = 190;
		int[] spellButton = { 1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299,
				1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421,
				1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503, 1512, 1521,
				1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471 };
		tab.totalChildren(63);
		tab.child(0, 12424, 13, 24);
		for (int i1 = 0; i1 < spellButton.length; i1++) {
			int yPos = i1 > 34 ? 8 : 183;
			tab.child(1, 1195, 13, 24);
			tab.child(i1 + 2, spellButton[i1], 5, yPos);
			addButton(1195, 1, "Magic/Home", "Cast @gre@Home Teleport");
			RSInterface homeButton = interfaceCache[1195];
			homeButton.mOverInterToTrigger = 1196;
		}
		for (int i2 = 0; i2 < spellButton.length; i2++) {
			if (i2 < 60)
				spellButtons.childX[i2] = spellButtons.childX[i2] + 24;
			if (i2 == 6 || i2 == 12 || i2 == 19 || i2 == 35 || i2 == 41 || i2 == 44 || i2 == 49 || i2 == 51)
				spellButtons.childX[i2] = 0;
			spellButtons.childY[6] = 24;
			spellButtons.childY[12] = 48;
			spellButtons.childY[19] = 72;
			spellButtons.childY[49] = 96;
			spellButtons.childY[44] = 120;
			spellButtons.childY[51] = 144;
			spellButtons.childY[35] = 170;
			spellButtons.childY[41] = 192;
		}
		homeHover.isMouseoverTriggered = true;
		addText(1197, "Level 0: Home Teleport", tda, 1, 0xFE981F, true, true);
		RSInterface homeLevel = interfaceCache[1197];
		homeLevel.width = 174;
		homeLevel.height = 68;
		addText(1198, "A teleport which requires no", tda, 0, 0xAF6A1A, true, true);
		addText(18998, "runes and no required level that", tda, 0, 0xAF6A1A, true, true);
		addText(18999, "teleports you to the main land.", tda, 0, 0xAF6A1A, true, true);
		homeHover.totalChildren(4);
		homeHover.child(0, 1197, 3, 4);
		homeHover.child(1, 1198, 91, 23);
		homeHover.child(2, 18998, 91, 34);
		homeHover.child(3, 18999, 91, 45);
	}

	public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center,
			boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.aByte254 = 0;
		tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.aString228 = "";
		tab.textColor = color;
		tab.anInt219 = 0;
		tab.anInt216 = 0;
		tab.anInt239 = 0;
	}

	public static void addButton(int id, int sid, String spriteName, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(sid, spriteName);
		tab.sprite2 = imageLoader(sid, spriteName);
		tab.width = tab.sprite1.myWidth;
		tab.height = tab.sprite1.myHeight;
		tab.tooltip = tooltip;
	}

	public static void addSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
	}

	public static void addTransparentSprite(int id, int spriteId, String spriteName) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 52;
		tab.sprite1 = imageLoader(spriteId, spriteName);
		tab.sprite2 = imageLoader(spriteId, spriteName);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static RSInterface addScreenInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = 0;
		return tab;
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.aByte254 = (byte) 0;
		tab.mOverInterToTrigger = -1;// Int 230
		return tab;
	}

	private static Sprite imageLoader(int i, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	private Model method206(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null)
			return model;
		if (i == 1)
			model = Model.method462(j);
		if (i == 2)
			model = EntityDef.forID(j).method160();
		if (i == 3)
			model = client.myPlayer.method453();
		if (i == 4)
			model = ItemDef.forID(j).method202(50);
		if (i == 5)
			model = null;
		if (model != null)
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		return model;
	}

	private static Sprite method207(int i, StreamLoader streamLoader, String s) {
		long l = (TextClass.method585(s) << 8) + (long) i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void method208(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag)
			return;
		aMRUNodes_264.unlinkAll();
		if (model != null && j != 4)
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag)
			model = method206(anInt255, anInt256);
		else
			model = method206(anInt233, mediaID);
		if (model == null)
			return null;
		if (k == -1 && j == -1 && model.anIntArray1640 == null)
			return model;
		Model model_1 = new Model(true, Class36.method532(k) & Class36.method532(j), false, model);
		if (k != -1 || j != -1)
			model_1.method469();
		if (k != -1)
			model_1.method470(k);
		if (j != -1)
			model_1.method470(j);
		model_1.method479(64, 768, -50, -10, -50, true);
		return model_1;
	}

	public RSInterface() {
	}

	public static StreamLoader aClass44;
	public boolean drawsTransparent;
	public Sprite sprite1;
	public int anInt208;
	public Sprite sprites[];
	public static RSInterface interfaceCache[];
	public int anIntArray212[];
	public int contentType;// anInt214
	public int spritesX[];
	public int anInt216;
	public int atActionType;
	public String spellName;
	public int anInt219;
	public int width;
	public String tooltip;
	public String selectedActionName;
	public boolean centerText;
	public int scrollPosition;
	public String actions[];
	public int valueIndexArray[][];
	public boolean aBoolean227;
	public String aString228;
	public int mOverInterToTrigger;
	public int invSpritePadX;
	public int textColor;
	public int anInt233;
	public int mediaID;
	public boolean aBoolean235;
	public int parentID;
	public int spellUsableOn;
	private static MRUNodes aMRUNodes_238;
	public int anInt239;
	public int children[];
	public int childX[];
	public boolean usableItemInterface;
	public TextDrawingArea textDrawingAreas;
	public int invSpritePadY;
	public int anIntArray245[];
	public int anInt246;
	public int spritesY[];
	public String message;
	public boolean isInventoryInterface;
	public int id;
	public int invStackSizes[];
	public int inv[];
	public byte aByte254;
	private int anInt255;
	private int anInt256;
	public int anInt257;
	public int anInt258;
	public boolean aBoolean259;
	public Sprite sprite2;
	public int scrollMax;
	public int type;
	public int anInt263;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public int anInt265;
	public boolean isMouseoverTriggered;
	public int height;
	public boolean textShadow;
	public int anInt269;
	public int modelRotation1;
	public int modelRotation2;
	public int childY[];
	public boolean isItemSearchComponent;
	public int itemSearchSelectedId, itemSearchSelectedSlot = -1;
	public static int selectedItemInterfaceId = -1;

}