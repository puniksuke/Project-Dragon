import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ItemDef {

	public static void nullLoader() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean method192(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		return flag;
	}

	private static void writeOut() {
		BufferedWriter writer = null;
		for (int i = 0; i < totalItems; i++) {
			ItemDef item = ItemDef.forID(i);
			try {
				writer = new BufferedWriter(new FileWriter("sql.txt", true));
				if (item == null)
					continue;
				if (item.name == null || item.description == null)
					continue;
				writer.write("(" + i + ",'" + item.name.replace("'", "") + "','" + item.description.replace("'", "")
						+ "'),");
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean hasAction(String action) {
		for (String s : itemActions) {
			if (s != null && s.equalsIgnoreCase(action)) {
				return true;
			}
		}
		return false;
	}

	private static void dumpDefinitions() {
		BufferedWriter writer = null;
		ItemDef def = null;
		for (int i = 0; i < totalItems; i++) {
			def = ItemDef.forID(i);
			try {
				writer = new BufferedWriter(new FileWriter("defs.txt", true));
				if (def == null) {
					continue;
				}
				if (def.name == null || def.description == null) {
					continue;
				}
				if (!def.hasAction("Wield") && !def.hasAction("Wear")) {
					continue;
				}
				int slot = 3;
				String name = def.name.toLowerCase();
				if (name.contains("helm") || name.contains("coif") || name.contains("hat")
						|| name.contains("mask") || name.contains("hood") || name.equals("afro")
						|| name.contains("headband") || name.contains("sallet") || name.contains("crown")
						|| name.contains("mitre")) {
					slot = 0;
				} else if (name.contains("amulet")) {
					slot =  2;
				} else if (name.contains("legs") || name.contains("tasset") || name.contains("bottom")
						|| name.contains("robeskirt") || name.contains("skirt") || name.contains("chaps")
						|| name.contains("knight robe") || name.contains("cuisse") || name.contains("pantaloons")
						|| name.contains("trouser")) {
					slot = 7;
				} else if (name.contains("shield") || name.contains("defender") || name.contains("defender")
						|| name.contains("ward") || name.contains("book")) {
					slot = 5;
				} else if (name.contains("body") || name.contains("torso") || name.contains("top")
						|| name.contains("brassard") || name.contains("hauberk") || name.contains("jacket")
						|| name.contains("tunic") || name.contains("chest") || name.contains("domin d'hide")
						|| name.contains("orak d'hide") || name.contains("wizard robe")) {
					slot = 4;
				} else if (name.contains("cloak") || name.contains("cape") || name.contains("accumulator")
						|| name.contains("attractor")) {
					slot = 1;
				} else if (name.contains("boots")) {
					slot = 10;
				} else if (name.contains("glove") || name.contains("bracelet") || name.contains("vamb")
						|| name.contains("bracers")) {
					slot = 9;
				} else if (name.contains("arrow") || name.contains("bolt")) {
					slot = 13;
				} else if (name.contains("ring")) {
					slot = 12;
				} else if (name.contains("amulet") || name.contains("necklace") || name.contains("stole")
						|| name.contains("scarf")) {
					slot = 2;
				}
				writer.write("<slot>");
				writer.newLine();
				writer.write("<id>");
				writer.write("" + i);
				writer.write("</id>");
				writer.newLine();
				writer.write("<equipId>");
				writer.write("" + slot);
				writer.write("</equipId>");
				writer.newLine();
				writer.write("</slot>");
				writer.newLine();
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void unpackConfig(StreamLoader archive) {

		stream = new Stream(archive.getDataForName("obj.dat"));
		Stream stream = new Stream(archive.getDataForName("obj.idx"));
		// stream = new Stream(FileOperations.ReadFile(signlink.findcachedir()+
		// "obj.dat"));
		// Stream stream = new
		// Stream(FileOperations.ReadFile(signlink.findcachedir()+ "obj.idx"));
		totalItems = stream.readUnsignedWord() + 21;
		streamIndices = new int[totalItems + 5000];
		int i = 2;
		for (int j = 0; j < totalItems - 21; j++) {
			streamIndices[j] = i;
			i += stream.readUnsignedWord();
		}

		cache = new ItemDef[10];
		for (int k = 0; k < 10; k++)
			cache[k] = new ItemDef();
		 //dumpDefinitions();
	}

	public Model method194(int j) {
		int k = anInt175;
		int l = anInt166;
		if (j == 1) {
			k = anInt197;
			l = anInt173;
		}
		if (k == -1)
			return null;
		Model model = Model.method462(k);
		if (l != -1) {
			Model model_1 = Model.method462(l);
			Model aclass30_sub2_sub4_sub6s[] = { model, model_1 };
			model = new Model(2, aclass30_sub2_sub4_sub6s);
		}
		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);

		}
		return model;
	}

	public boolean method195(int j) {
		int k = anInt165;
		int l = anInt188;
		int i1 = anInt185;
		if (j == 1) {
			k = anInt200;
			l = anInt164;
			i1 = anInt162;
		}
		if (k == -1)
			return true;
		boolean flag = true;
		if (!Model.method463(k))
			flag = false;
		if (l != -1 && !Model.method463(l))
			flag = false;
		if (i1 != -1 && !Model.method463(i1))
			flag = false;
		return flag;
	}

	public Model method196(int i) {
		int j = anInt165;
		int k = anInt188;
		int l = anInt185;
		if (i == 1) {
			j = anInt200;
			k = anInt164;
			l = anInt162;
		}
		if (j == -1)
			return null;
		Model model = Model.method462(j);
		if (k != -1)
			if (l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model aclass30_sub2_sub4_sub6_1s[] = { model, model_1, model_3 };
				model = new Model(3, aclass30_sub2_sub4_sub6_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model aclass30_sub2_sub4_sub6s[] = { model, model_2 };
				model = new Model(2, aclass30_sub2_sub4_sub6s);
			}
		if (i == 0 && aByte205 != 0)
			model.method475(0, aByte205, 0);
		if (i == 1 && aByte154 != 0)
			model.method475(0, aByte154, 0);
		if (modifiedModelColors != null) {
			for (int i1 = 0; i1 < modifiedModelColors.length; i1++)
				model.method476(modifiedModelColors[i1], originalModelColors[i1]);

		}
		return model;
	}

	private void setDefaults() {
		modelID = 0;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		modelZoom = 2000;
		modelRotationY = 0;
		modelRotationX = 0;
		anInt204 = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		itemActions = null;
		anInt165 = -1;
		anInt188 = -1;
		aByte205 = 0;
		anInt200 = -1;
		anInt164 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
		team = 0;
	}

	public static int[] getResults(String name, int range, boolean limited) {
		int[] items = new int[range];
		int position = 0;
		if (limited) {
			RSInterface bank = RSInterface.interfaceCache[5382];
			for (int i = 0; i < bank.inv.length; i++) {
				if (bank.inv[i] <= 0) {
					continue;
				}
				ItemDef itemDef = forID(bank.inv[i] - 1);
				if (position >= range) {
					break;
				}
				if (itemDef == null) {
					continue;
				}
				if (itemDef.stackable) {
					if (itemDef.description != null) {
						if (itemDef.description.startsWith("Swap this note")) {
							continue;
						}
					}
				}
				String itemName = itemDef.name;
				if (itemName == null) {
					continue;
				}
				if (itemName.toLowerCase().contains(name.toLowerCase())) {
					if (Arrays.binarySearch(items, bank.inv[i] - 1) >= 0) {
						continue;
					}
					items[position] = bank.inv[i] - 1;
					position++;
				}
			}
		} else {
			for (int i = 0; i < 14485; i++) {
				ItemDef itemDef = forID(i);
				if (position >= range) {
					break;
				}
				if (itemDef == null) {
					continue;
				}
				if (itemDef.stackable) {
					if (itemDef.description != null) {
						if (itemDef.description.startsWith("Swap this note")) {
							continue;
						}
					}
				}
				String itemName = itemDef.name;
				if (itemName == null) {
					continue;
				}
				if (itemName.toLowerCase().contains(name.toLowerCase())) {
					if (Arrays.binarySearch(items, itemDef.id) >= 0) {
						continue;
					}
					items[position] = itemDef.id;
					position++;
				}
			}
		}
		return items;
	}

	public static ItemDef forID(int i) {
		for (int j = 0; j < 10; j++)
			if (cache[j].id == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 10;
		ItemDef itemDef = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		itemDef.id = i;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		/* Customs added here? */
		if (itemDef.certTemplateID != -1)
			itemDef.toNote();
		if (i == 13217) {
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wield";
			itemDef.modelID = 44590;
			itemDef.anInt165 = 43660;// anInt165
			itemDef.anInt200 = 43660;// anInt200
			itemDef.modelZoom = 789;
			itemDef.modelRotationY = 240;
			itemDef.modelRotationX = 60;
			itemDef.modelOffset1 = -1;
			itemDef.modelOffset2 = -23;
			itemDef.name = "Dragon claws";
			itemDef.description = "A set of fighting claws.";
		}
		if (i == 7901) {
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			itemDef.modifiedModelColors = new int[1];
			itemDef.originalModelColors = new int[1];
			itemDef.modifiedModelColors[0] = 528;
			itemDef.originalModelColors[0] = 17350;
			itemDef.modelID = 5412;
			itemDef.modelZoom = 840;
			itemDef.modelRotationY = 280;
			itemDef.modelRotationX = 0;
			itemDef.anInt204 = 0;
			itemDef.modelOffset1 = -2;
			itemDef.modelOffset2 = 56;
			itemDef.anInt165 = 5409;
			itemDef.anInt200 = 5409;
			itemDef.anInt188 = -1;
			itemDef.anInt164 = -1;
			itemDef.anInt175 = -1;
			itemDef.anInt197 = -1;
			itemDef.name = "Lime Whip";
			itemDef.description = "A weapon from the abyss.";
		}
		switch (i) {

		case 13219:
			itemDef.name = "Vesta's Longsword";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			itemDef.modelID = 42597;
			itemDef.modelZoom = 1744;
			itemDef.modelRotationY = 738;
			itemDef.modelRotationX = 1985;
			itemDef.modelOffset2 = 0;
			itemDef.modelOffset1 = 0;
			itemDef.anInt204 = 0;
			itemDef.aByte205 = -10;
			itemDef.aByte154 = -10;
			itemDef.anInt165 = 42615;
			itemDef.anInt200 = 42615;
			itemDef.description = "An ancient longsword once worn by Vesta.";
			break;

		case 2677:
			itemDef.name = "10$ scroll";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[0] = "Claim donator points";
			break;

		case 2678:
			itemDef.name = "20$ scroll";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[0] = "Claim donator points";
			break;

		case 2679:
			itemDef.name = "50$ scroll";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[0] = "Claim donator points";
			break;

		case 2680:
			itemDef.name = "100$ scroll";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[0] = "Claim donator points";
			break;

		case 4067:
			itemDef.name = "PVP Ticket";
			itemDef.description = "This item is obtained by killing players.";
			itemDef.itemActions = new String[5];
			itemDef.itemActions[0] = "Claim Points";
			break;

		case 13202:
			itemDef.name = "Abyssal bludgeon";
			itemDef.modelZoom = 1400;
			itemDef.modelRotationY = 1549;
			itemDef.modelRotationX = 1818;
			itemDef.modelOffset2 = 9;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.itemActions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
			itemDef.modelID = 29597;
			itemDef.anInt165 = 29424;
			itemDef.anInt200 = 29424;
			itemDef.description = "Something sharp from the body of a defeated Abyssal Sire.";
			break;

		case 13205:
			itemDef.name = "Abyssal dagger";
			itemDef.modelZoom = 1275;
			itemDef.modelRotationY = 1549;
			itemDef.modelRotationX = 1818;
			itemDef.modelOffset2 = 9;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.itemActions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
			itemDef.modelID = 29598;
			itemDef.anInt165 = 29425;
			itemDef.anInt200 = 29425;
			itemDef.description = "Something sharp from the body of a defeated Abyssal Sire.";
			break;

		case 13207:
			itemDef.name = "Abyssal dagger ( p )";
			itemDef.modelZoom = 1275;
			itemDef.modelRotationY = 1549;
			itemDef.modelRotationX = 1818;
			itemDef.modelOffset2 = 9;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.itemActions = new String[] { null, "Wield", "Check", "Uncharge", "Drop" };
			itemDef.modelID = 29598;
			itemDef.anInt165 = 29426;
			itemDef.anInt200 = 29426;
			break;
		case 13209:
			itemDef.name = "Overseer's book";
			itemDef.modelZoom = 1450;
			itemDef.modelRotationY = 498;
			itemDef.modelRotationX = 256;
			itemDef.modelOffset2 = 8;
			itemDef.modelOffset1 = 8;
			itemDef.groundActions = new String[] { null, null, "Take", null, null };
			itemDef.modelID = 29599;
			break;

		case 13211:
			itemDef.name = "Pegasian boots";
			itemDef.modelID = 29396;
			itemDef.modelZoom = 900;
			itemDef.modelRotationY = 165;
			itemDef.modelRotationX = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.anInt165 = 29252;
			itemDef.anInt200 = 29253;
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			break;

		case 13213:
			itemDef.name = "Primordial Boots";
			itemDef.modelID = 29397;
			itemDef.modelZoom = 900;
			itemDef.modelRotationY = 165;
			itemDef.modelRotationX = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.anInt165 = 29250;
			itemDef.anInt200 = 29255;
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			break;

		case 13215:
			itemDef.name = "Eternal boots";
			itemDef.modelID = 29394;
			itemDef.modelZoom = 900;
			itemDef.modelRotationY = 165;
			itemDef.modelRotationX = 99;
			itemDef.modelOffset1 = 3;
			itemDef.modelOffset2 = -7;
			itemDef.anInt165 = 29249;
			itemDef.anInt200 = 29254;
			itemDef.itemActions = new String[5];
			itemDef.itemActions[1] = "Wear";
			break;
		}
		return itemDef;
	}

	private void toNote() {
		ItemDef itemDef = forID(certTemplateID);
		modelID = itemDef.modelID;
		modelZoom = itemDef.modelZoom;
		modelRotationY = itemDef.modelRotationY;
		modelRotationX = itemDef.modelRotationX;

		anInt204 = itemDef.anInt204;
		modelOffset1 = itemDef.modelOffset1;
		modelOffset2 = itemDef.modelOffset2;
		modifiedModelColors = itemDef.modifiedModelColors;
		originalModelColors = itemDef.originalModelColors;
		ItemDef itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		char c = itemDef_1.name.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			s = "an";
		stackable = true;
	}

	public static Sprite getSprite(int i, int j, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
			if (sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {

				sprite.unlink();
				sprite = null;
			}
			if (sprite != null)
				return sprite;
		}
		ItemDef itemDef = forID(i);
		if (itemDef.stackIDs == null)
			j = -1;
		if (j > 1) {
			int i1 = -1;
			for (int j1 = 0; j1 < 10; j1++)
				if (j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];

			if (i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if (model == null)
			return null;
		Sprite sprite = null;
		if (itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if (sprite == null)
				return null;
		}
		Sprite enabledSprite = new Sprite(32, 32);
		int k1 = Texture.textureInt1;
		int l1 = Texture.textureInt2;
		int ai[] = Texture.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Texture.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, enabledSprite.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Texture.method364();
		int k3 = itemDef.modelZoom;
		if (k == -1)
			k3 = (int) ((double) k3 * 1.5D);
		if (k > 0)
			k3 = (int) ((double) k3 * 1.04D);
		int l3 = Texture.anIntArray1470[itemDef.modelRotationY] * k3 >> 16;
		int i4 = Texture.anIntArray1471[itemDef.modelRotationY] * k3 >> 16;
		model.method482(itemDef.modelRotationX, itemDef.anInt204, itemDef.modelRotationY, itemDef.modelOffset1,
				l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		for (int i5 = 31; i5 >= 0; i5--) {
			for (int j4 = 31; j4 >= 0; j4--)
				if (enabledSprite.myPixels[i5 + j4 * 32] == 0)
					if (i5 > 0 && enabledSprite.myPixels[(i5 - 1) + j4 * 32] > 1)
						enabledSprite.myPixels[i5 + j4 * 32] = 1;
					else if (j4 > 0 && enabledSprite.myPixels[i5 + (j4 - 1) * 32] > 1)
						enabledSprite.myPixels[i5 + j4 * 32] = 1;
					else if (i5 < 31 && enabledSprite.myPixels[i5 + 1 + j4 * 32] > 1)
						enabledSprite.myPixels[i5 + j4 * 32] = 1;
					else if (j4 < 31 && enabledSprite.myPixels[i5 + (j4 + 1) * 32] > 1)
						enabledSprite.myPixels[i5 + j4 * 32] = 1;

		}

		if (k > 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--)
					if (enabledSprite.myPixels[j5 + k4 * 32] == 0)
						if (j5 > 0 && enabledSprite.myPixels[(j5 - 1) + k4 * 32] == 1)
							enabledSprite.myPixels[j5 + k4 * 32] = k;
						else if (k4 > 0 && enabledSprite.myPixels[j5 + (k4 - 1) * 32] == 1)
							enabledSprite.myPixels[j5 + k4 * 32] = k;
						else if (j5 < 31 && enabledSprite.myPixels[j5 + 1 + k4 * 32] == 1)
							enabledSprite.myPixels[j5 + k4 * 32] = k;
						else if (k4 < 31 && enabledSprite.myPixels[j5 + (k4 + 1) * 32] == 1)
							enabledSprite.myPixels[j5 + k4 * 32] = k;

			}

		} else if (k == 0) {
			for (int k5 = 31; k5 >= 0; k5--) {
				for (int l4 = 31; l4 >= 0; l4--)
					if (enabledSprite.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0
							&& enabledSprite.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						enabledSprite.myPixels[k5 + l4 * 32] = 0x302020;

			}

		}
		if (itemDef.certTemplateID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if (k == 0)
			mruNodes1.removeFromCache(enabledSprite, i);
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Texture.textureInt1 = k1;
		Texture.textureInt2 = l1;
		Texture.anIntArray1472 = ai;
		Texture.aBoolean1464 = true;
		if (itemDef.stackable)
			enabledSprite.anInt1444 = 33;
		else
			enabledSprite.anInt1444 = 32;
		enabledSprite.anInt1445 = j;
		return enabledSprite;
	}

	public Model method201(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];

			if (j != -1)
				return forID(j).method201(1);
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if (model != null)
			return model;
		model = Model.method462(modelID);
		if (model == null)
			return null;
		if (anInt167 != 128 || anInt192 != 128 || anInt191 != 128)
			model.method478(anInt167, anInt191, anInt192);
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++)
				if (i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];

			if (j != -1)
				return forID(j).method202(1);
		}
		Model model = Model.method462(modelID);
		if (model == null)
			return null;
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		return model;
	}

	private void readValues(Stream stream) {
		do {
			int i = stream.readUnsignedByte();
			if (i == 0)
				return;
			if (i == 1)
				modelID = stream.readUnsignedWord();
			else if (i == 2)
				name = stream.readString();
			else if (i == 3)
				description = stream.readString();
			else if (i == 4)
				modelZoom = stream.readUnsignedWord();
			else if (i == 5)
				modelRotationY = stream.readUnsignedWord();
			else if (i == 6)
				modelRotationX = stream.readUnsignedWord();
			else if (i == 7) {
				modelOffset1 = stream.readUnsignedWord();
				if (modelOffset1 > 32767)
					modelOffset1 -= 0x10000;
			} else if (i == 8) {
				modelOffset2 = stream.readUnsignedWord();
				if (modelOffset2 > 32767)
					modelOffset2 -= 0x10000;
			} else if (i == 10)
				stream.readUnsignedWord();
			else if (i == 11)
				stackable = true;
			else if (i == 12)
				value = stream.readDWord();
			else if (i == 16)
				membersObject = true;
			else if (i == 23) {
				anInt165 = stream.readUnsignedWord();
				aByte205 = stream.readSignedByte();
			} else if (i == 24)
				anInt188 = stream.readUnsignedWord();
			else if (i == 25) {
				anInt200 = stream.readUnsignedWord();
				aByte154 = stream.readSignedByte();
			} else if (i == 26)
				anInt164 = stream.readUnsignedWord();
			else if (i >= 30 && i < 35) {
				if (groundActions == null)
					groundActions = new String[5];
				groundActions[i - 30] = stream.readString();
				if (groundActions[i - 30].equalsIgnoreCase("hidden"))
					groundActions[i - 30] = null;
			} else if (i >= 35 && i < 40) {
				if (itemActions == null)
					itemActions = new String[5];
				itemActions[i - 35] = stream.readString();
			} else if (i == 40) {
				int j = stream.readUnsignedByte();
				originalModelColors = new int[j];
				modifiedModelColors = new int[j];
				for (int k = 0; k < j; k++) {
					originalModelColors[k] = stream.readUnsignedWord();
					modifiedModelColors[k] = stream.readUnsignedWord();
				}

			} else if (i == 78)
				anInt185 = stream.readUnsignedWord();
			else if (i == 79)
				anInt162 = stream.readUnsignedWord();
			else if (i == 90)
				anInt175 = stream.readUnsignedWord();
			else if (i == 91)
				anInt197 = stream.readUnsignedWord();
			else if (i == 92)
				anInt166 = stream.readUnsignedWord();
			else if (i == 93)
				anInt173 = stream.readUnsignedWord();
			else if (i == 95)
				anInt204 = stream.readUnsignedWord();
			else if (i == 97)
				certID = stream.readUnsignedWord();
			else if (i == 98)
				certTemplateID = stream.readUnsignedWord();
			else if (i == 100) {
				int length = stream.readUnsignedByte();
				stackIDs = new int[length];
				stackAmounts = new int[length];
				for (int i2 = 0; i2 < length; i2++) {
					stackIDs[i2] = stream.readUnsignedWord();
					stackAmounts[i2] = stream.readUnsignedWord();
				}
			} else if (i == 110)
				anInt167 = stream.readUnsignedWord();
			else if (i == 111)
				anInt192 = stream.readUnsignedWord();
			else if (i == 112)
				anInt191 = stream.readUnsignedWord();
			else if (i == 113)
				anInt196 = stream.readSignedByte();
			else if (i == 114)
				anInt184 = stream.readSignedByte() * 5;
			else if (i == 115)
				team = stream.readUnsignedByte();
		} while (true);
	}

	private ItemDef() {
		id = -1;
	}

	private byte aByte154;
	public int value;// anInt155
	public int[] modifiedModelColors;// newModelColor
	public int id;// anInt157
	static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
	public int[] originalModelColors;
	public boolean membersObject;// aBoolean161
	private int anInt162;
	private int certTemplateID;
	public int anInt164;// femArmModel
	public int anInt165;// maleWieldModel
	private int anInt166;
	private int anInt167;
	public String groundActions[];
	public int modelOffset1;
	public String name;// itemName
	private static ItemDef[] cache;
	private int anInt173;
	public int modelID;// dropModel
	public int anInt175;
	public boolean stackable;// itemStackable
	public String description;// itemExamine
	public int certID;
	private static int cacheIndex;
	public int modelZoom;
	public static boolean isMembers = true;
	private static Stream stream;
	private int anInt184;
	private int anInt185;
	public int anInt188;// maleArmModel
	public String itemActions[];// itemMenuOption
	public int modelRotationY;// modelRotateUp
	private int anInt191;
	private int anInt192;
	public int[] stackIDs;// modelStack
	public int modelOffset2;//
	private static int[] streamIndices;
	private int anInt196;
	public int anInt197;
	public int modelRotationX;// modelRotateRight
	public int anInt200;// femWieldModel
	public int[] stackAmounts;// itemAmount
	public int team;
	public static int totalItems;
	public int anInt204;// modelPositionUp
	private byte aByte205;

}
