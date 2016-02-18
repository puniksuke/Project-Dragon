package org.osrspvp.model.player;

import org.osrspvp.Config;
import org.osrspvp.Server;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.Graphic;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.region.Region;
import org.osrspvp.world.Clan;
import org.osrspvp.world.path.PathRequest;

public class PlayerAssistant {

    private Client c;

    public PlayerAssistant(Client Client) {
        this.c = Client;
    }

    public int CraftInt, Dcolor, FletchInt;

    /**
     * MulitCombat icon
     *
     * @param i1 0 = off 1 = on
     */
    public void multiWay(int i1) {
        // synchronized(c) {
        c.outStream.createFrame(61);
        c.outStream.writeByte(i1);
        c.updateRequired = true;
        c.setAppearanceUpdateRequired(true);
    }

    public int getTotalWealth() {
        int wealth = 0;
        for (int i = 0; i < c.playerItems.length; i++) {
            if (c.playerItems[i] > 0) {
                wealth += c.getShops().getItemShopValue(c.playerItems[i] - 1);
            }
        }
        for (int i = 0; i < c.getEquipment().length; i++) {
            if (c.getEquipment()[i] > 0) {
                wealth += c.getShops().getItemShopValue(c.getEquipment()[i]);
            }
        }
        return wealth;
    }

    /*
     * Vengeance
     */
    public void castVeng() {
        if (c.getLevel()[6] < 94) {
            c.sendMessage("You need a magic level of 94 to cast this spell.");
            return;
        }
        if (c.getLevel()[1] < 40) {
            c.sendMessage("You need a defence level of 40 to cast this spell.");
            return;
        }
        if (!c.getItems().playerHasItem(9075, 4) || !c.getItems().playerHasItem(557, 10) || !c.getItems()
            .playerHasItem(560, 2)) {
            c.sendMessage("You don't have the required runes to cast this spell.");
            return;
        }
        if (c.duelRule[4]) {
            c.sendMessage("Magic has been disabled in this duel.");
            return;
        }
        if (System.currentTimeMillis() - c.lastCast < 30000) {
            c.sendMessage("You can only cast vengeance every 30 seconds.");
            return;
        }
        if (c.vengOn) {
            c.sendMessage("You already have vengeance casted.");
            return;
        }
        c.playAnimation(Animation.create(4410));
        c.playGraphic(Graphic.create(726, 0, 100));// Just use c.gfx100
        c.getItems().deleteItem2(9075, 4);
        c.getItems().deleteItem2(557, 10);// For these you need to change to
        // deleteItem(item, itemslot,
        // amount);.
        c.getItems().deleteItem2(560, 2);
        addSkillXP(112, 6);
        refreshSkill(6);
        c.vengOn = true;
        c.lastCast = System.currentTimeMillis();
    }

    public void resetAutocast() {
        c.autocastId = 0;
        c.autocasting = false;
        c.getPA().sendFrame36(108, 0);
    }

    public void sendFrame126(String s, int id) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrameVarSizeWord(126);
            c.getOutStream().writeString(s);
            c.getOutStream().writeWordA(id);
            c.getOutStream().endFrameVarSizeWord();
            c.flushOutStream();
        }
    }

    public void sendLink(String s) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrameVarSizeWord(187);
            c.getOutStream().writeString(s);
        }
    }

    public void setSkillLevel(int skillNum, int currentLevel, int XP) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(134);
            c.getOutStream().writeByte(skillNum);
            c.getOutStream().writeDWord_v1(XP);
            c.getOutStream().writeByte(currentLevel);
            c.flushOutStream();
        }
    }

    public void sendFrame106(int sideIcon) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(106);
            c.getOutStream().writeByteC(sideIcon);
            c.flushOutStream();
            requestUpdates();
        }
    }

    public void sendFrame107() {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(107);
            c.flushOutStream();
        }
    }

    public void sendFrame36(int id, int state) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(36);
            c.getOutStream().writeWordBigEndian(id);
            c.getOutStream().writeByte(state);
            c.flushOutStream();
        }
    }

    public void sendFrame185(int Frame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(185);
            c.getOutStream().writeWordBigEndianA(Frame);
        }
    }

    public void showInterface(int interfaceid) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(97);
            c.getOutStream().writeWord(interfaceid);
            c.flushOutStream();
        }
    }

    public void sendFrame248(int MainFrame, int SubFrame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(248);
            c.getOutStream().writeWordA(MainFrame);
            c.getOutStream().writeWord(SubFrame);
            c.flushOutStream();
        }
    }

    public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(246);
            c.getOutStream().writeWordBigEndian(MainFrame);
            c.getOutStream().writeWord(SubFrame);
            c.getOutStream().writeWord(SubFrame2);
            c.flushOutStream();
        }
    }

    public void sendFrame171(int MainFrame, int SubFrame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(171);
            c.getOutStream().writeByte(MainFrame);
            c.getOutStream().writeWord(SubFrame);
            c.flushOutStream();
        }
    }

    public void sendFrame200(int MainFrame, int SubFrame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(200);
            c.getOutStream().writeWord(MainFrame);
            c.getOutStream().writeWord(SubFrame);
            c.flushOutStream();
        }
    }

    public void sendFrame70(int i, int o, int id) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(70);
            c.getOutStream().writeWord(i);
            c.getOutStream().writeWordBigEndian(o);
            c.getOutStream().writeWordBigEndian(id);
            c.flushOutStream();
        }
    }

    public void sendFrame75(int MainFrame, int SubFrame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(75);
            c.getOutStream().writeWordBigEndianA(MainFrame);
            c.getOutStream().writeWordBigEndianA(SubFrame);
            c.flushOutStream();
        }
    }

    public void sendFrame164(int Frame) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(164);
            c.getOutStream().writeWordBigEndian_dup(Frame);
            c.flushOutStream();
        }
    }

    public void setPrivateMessaging(int i) { // friends and ignore list status
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(221);
            c.getOutStream().writeByte(i);
            c.flushOutStream();
        }
    }

    public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(206);
            c.getOutStream().writeByte(publicChat);
            c.getOutStream().writeByte(privateChat);
            c.getOutStream().writeByte(tradeBlock);
            c.flushOutStream();
        }
    }

    public void sendFrame87(int id, int state) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(87);
            c.getOutStream().writeWordBigEndian_dup(id);
            c.getOutStream().writeDWord_v1(state);
            c.flushOutStream();
        }
    }

    public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrameVarSize(196);
            c.getOutStream().writeQWord(name);
            c.getOutStream().writeDWord(c.lastChatId++);
            c.getOutStream().writeByte(rights);
            c.getOutStream().writeBytes(chatmessage, messagesize, 0);
            c.getOutStream().endFrameVarSize();
            c.flushOutStream();
            Misc.textUnpack(chatmessage, messagesize);
            Misc.longToPlayerName(name);
        }
    }

    public void createPlayerHints(int type, int id) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(254);
            c.getOutStream().writeByte(type);
            c.getOutStream().writeWord(id);
            c.getOutStream().write3Byte(0);
            c.flushOutStream();
        }
    }

    public void createObjectHints(int x, int y, int height, int pos) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(254);
            c.getOutStream().writeByte(pos);
            c.getOutStream().writeWord(x);
            c.getOutStream().writeWord(y);
            c.getOutStream().writeByte(height);
            c.flushOutStream();
        }
    }

    public void loadPM(long playerName, int world) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            if (world != 0) {
                world += 9;
            } else if (!Config.WORLD_LIST_FIX) {
                world += 1;
            }
            c.getOutStream().createFrame(50);
            c.getOutStream().writeQWord(playerName);
            c.getOutStream().writeByte(world);
            c.flushOutStream();
        }
    }

    public void removeAllWindows() {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getPA().resetVariables();
            c.getOutStream().createFrame(219);
            c.flushOutStream();
        }
    }

    public void closeAllWindows() {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(219);
            c.flushOutStream();
        }
    }

    public void sendFrame34(int id, int slot, int column, int amount) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.outStream.createFrameVarSizeWord(34); // init item to smith
            // screen
            c.outStream.writeWord(column); // Column Across Smith Screen
            c.outStream.writeByte(4); // Total Rows?
            c.outStream.writeDWord(slot); // Row Down The Smith Screen
            c.outStream.writeWord(id + 1); // item
            c.outStream.writeByte(amount); // how many there are?
            c.outStream.endFrameVarSizeWord();
        }
    }

    public void walkableInterface(int id) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(208);
            c.getOutStream().writeWordBigEndian_dup(id);
            c.flushOutStream();
        }
    }

    public int mapStatus = 0;

    public void sendFrame99(int state) { // used for disabling map
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            if (mapStatus != state) {
                mapStatus = state;
                c.getOutStream().createFrame(99);
                c.getOutStream().writeByte(state);
                c.flushOutStream();
            }
        }
    }

    public void sendCrashFrame() { // used for crashing cheat clients
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(123);
            c.flushOutStream();
        }
    }

    /**
     * Reseting animations for everyone
     **/

    public void frame1() {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            if (PlayerHandler.players[i] != null) {
                Client person = (Client) PlayerHandler.players[i];
                if (person != null) {
                    if (person.getOutStream() != null && !person.disconnected) {
                        if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
                            person.getOutStream().createFrame(1);
                            person.flushOutStream();
                            person.getPA().requestUpdates();
                        }
                    }
                }
            }
        }
    }

    /**
     * Creating projectile
     **/
    public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
            c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
            c.getOutStream().createFrame(117);
            c.getOutStream().writeByte(angle);
            c.getOutStream().writeByte(offY);
            c.getOutStream().writeByte(offX);
            c.getOutStream().writeWord(lockon);
            c.getOutStream().writeWord(gfxMoving);
            c.getOutStream().writeByte(startHeight);
            c.getOutStream().writeByte(endHeight);
            c.getOutStream().writeWord(time);
            c.getOutStream().writeWord(speed);
            c.getOutStream().writeByte(16);
            c.getOutStream().writeByte(64);
            c.flushOutStream();
        }
    }

    public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
            c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
            c.getOutStream().createFrame(117);
            c.getOutStream().writeByte(angle);
            c.getOutStream().writeByte(offY);
            c.getOutStream().writeByte(offX);
            c.getOutStream().writeWord(lockon);
            c.getOutStream().writeWord(gfxMoving);
            c.getOutStream().writeByte(startHeight);
            c.getOutStream().writeByte(endHeight);
            c.getOutStream().writeWord(time);
            c.getOutStream().writeWord(speed);
            c.getOutStream().writeByte(slope);
            c.getOutStream().writeByte(64);
            c.flushOutStream();
        }
    }

    public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
            c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
            c.getOutStream().createFrame(117);
            c.getOutStream().writeByte(angle);
            c.getOutStream().writeByte(offY);
            c.getOutStream().writeByte(offX);
            c.getOutStream().writeWord(lockon);
            c.getOutStream().writeWord(gfxMoving);
            c.getOutStream().writeByte(startHeight);
            c.getOutStream().writeByte(endHeight);
            c.getOutStream().writeWord(time);
            c.getOutStream().writeWord(speed);
            c.getOutStream().writeByte(slope);
            c.getOutStream().writeByte(64);
            c.flushOutStream();
        }
    }

    public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            Player p = PlayerHandler.players[i];
            if (p != null) {
                Client person = (Client) p;
                if (person != null) {
                    if (person.getOutStream() != null) {
                        if (person.distanceToPoint(x, y) <= 25) {
                            if (p.heightLevel == c.heightLevel)
                                person.getPA()
                                    .createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight,
                                        lockon, time, slope);
                        }
                    }
                }
            }
        }
    }

    // projectiles for everyone within 25 squares
    public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time) {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            Player p = PlayerHandler.players[i];
            if (p != null) {
                Client person = (Client) p;
                if (person != null) {
                    if (person.getOutStream() != null) {
                        if (person.distanceToPoint(x, y) <= 25) {
                            if (p.heightLevel == c.heightLevel)
                                person.getPA()
                                    .createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight,
                                        lockon, time);
                        }
                    }
                }
            }
        }
    }

    public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight, int endHeight, int lockon, int time, int slope) {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            Player p = PlayerHandler.players[i];
            if (p != null) {
                Client person = (Client) p;
                if (person != null) {
                    if (person.getOutStream() != null) {
                        if (person.distanceToPoint(x, y) <= 25) {
                            person.getPA()
                                .createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight, endHeight, lockon,
                                    time, slope);
                        }
                    }
                }
            }
        }
    }

    /**
     * * GFX
     **/
    public void stillGfx(int id, int x, int y, int height, int time) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
            c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
            c.getOutStream().createFrame(4);
            c.getOutStream().writeByte(0);
            c.getOutStream().writeWord(id);
            c.getOutStream().writeByte(height);
            c.getOutStream().writeWord(time);
            c.flushOutStream();
        }
    }

    // creates gfx for everyone
    public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
        // synchronized(c) {
        for (int i = 0; i < Config.MAX_PLAYERS; i++) {
            Player p = PlayerHandler.players[i];
            if (p != null) {
                Client person = (Client) p;
                if (person != null) {
                    if (person.getOutStream() != null) {
                        if (person.distanceToPoint(x, y) <= 25) {
                            person.getPA().stillGfx(id, x, y, height, time);
                        }
                    }
                }
            }
        }
    }

    /**
     * Objects, add and remove
     **/
    public void object(int objectId, int objectX, int objectY, int face, int objectType) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
            c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
            c.getOutStream().createFrame(101);
            c.getOutStream().writeByteC((objectType << 2) + (face & 3));
            c.getOutStream().writeByte(0);

            if (objectId != -1) { // removing
                c.getOutStream().createFrame(151);
                c.getOutStream().writeByteS(0);
                c.getOutStream().writeWordBigEndian(objectId);
                c.getOutStream().writeByteS((objectType << 2) + (face & 3));
            }
            c.flushOutStream();
        }
    }

    public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
        if (c.distanceToPoint(objectX, objectY) > 60)
            return;
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            c.getOutStream().createFrame(85);
            c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
            c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
            c.getOutStream().createFrame(101);
            c.getOutStream().writeByteC((objectType << 2) + (face & 3));
            c.getOutStream().writeByte(0);

            if (objectId != -1) { // removing
                c.getOutStream().createFrame(151);
                c.getOutStream().writeByteS(0);
                c.getOutStream().writeWordBigEndian(objectId);
                c.getOutStream().writeByteS((objectType << 2) + (face & 3));
            }
            c.flushOutStream();
        }
    }

    /**
     * Show option, attack, trade, follow etc
     **/
    public String optionType = "null";

    public void sendOption(String s, int pos) {
        if (c.outStream != null && c != null) {
            c.outStream.createFrameVarSize(104);
            c.outStream.writeByteC(pos);
            c.outStream.writeByteA(0);
            c.outStream.writeString(s);
            c.outStream.endFrameVarSize();
            c.flushOutStream();
        }
    }

    public void showOption(int i, int l, String s, int a) {
        // synchronized(c) {
        if (c.getOutStream() != null && c != null) {
            if (!optionType.equalsIgnoreCase(s)) {
                optionType = s;
                c.getOutStream().createFrameVarSize(104);
                c.getOutStream().writeByteC(i);
                c.getOutStream().writeByteA(l);
                c.getOutStream().writeString(s);
                c.getOutStream().endFrameVarSize();
                c.flushOutStream();
            }
        }
    }

    /**
     * Open bank
     **/
    public void sendFrame34a(int frame, int item, int slot, int amount) {
        c.outStream.createFrameVarSizeWord(34);
        c.outStream.writeWord(frame);
        c.outStream.writeByte(slot);
        c.outStream.writeWord(item + 1);
        c.outStream.writeByte(255);
        c.outStream.writeDWord(amount);
        c.outStream.endFrameVarSizeWord();
    }

    public void openUpBank() {
        if (c.getTradeHandler().getCurrentTrade() != null) {
            if (c.getTradeHandler().getCurrentTrade().isOpen()) {
                c.getTradeHandler().decline();
            }
        }
        if (c.getOutStream() != null && c != null) {
            c.isBanking = true;
            c.updateBankItems = true;
            c.getItems().resetItems(5064);
            c.getOutStream().createFrame(248);
            c.getOutStream().writeWordA(23000);
            c.getOutStream().writeWord(5063);
            c.flushOutStream();
        }
    }

    public void setScrollPos(int interfaceId, int scrollPos) {
        if (c.getOutStream() != null && c != null) {
            c.outStream.createFrame(79);
            c.outStream.writeWordBigEndian(interfaceId);
            c.outStream.writeWordA(scrollPos);
        }
    }

    /**
     * Private Messaging
     **/
    public void logIntoPM() {
        setPrivateMessaging(2);
        for (int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
            Player p = PlayerHandler.players[i1];
            if (p != null && p.isActive) {
                Client o = (Client) p;
                if (o != null) {
                    o.getPA().updatePM(c.playerId, 1);
                }
            }
        }
        boolean pmLoaded = false;

        for (int i = 0; i < c.friends.length; i++) {
            if (c.friends[i] != 0) {
                for (int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
                    Player p = PlayerHandler.players[i2];
                    if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == c.friends[i]) {
                        Client o = (Client) p;
                        if (o != null) {
                            if (o.getPA().isInPM(Misc.playerNameToInt64(c.playerName))) {
                                loadPM(c.friends[i], 1);
                                pmLoaded = true;
                            }
                            break;
                        }
                    }
                }
                if (!pmLoaded) {
                    loadPM(c.friends[i], 0);
                }
                pmLoaded = false;
            }
            for (int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
                Player p = PlayerHandler.players[i1];
                if (p != null && p.isActive) {
                    Client o = (Client) p;
                    if (o != null) {
                        o.getPA().updatePM(c.playerId, 1);
                    }
                }
            }
        }
    }

    public void updatePM(int pID, int world) { // used for private chat updates
        Player p = PlayerHandler.players[pID];
        if (p == null || p.playerName == null || p.playerName.equals("null")) {
            return;
        }
        Client o = (Client) p;
        long l = Misc.playerNameToInt64(PlayerHandler.players[pID].playerName);

        if (p.privateChat == 0) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        loadPM(l, world);
                        return;
                    }
                }
            }
        } else if (p.privateChat == 1) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        if (o.getPA().isInPM(Misc.playerNameToInt64(c.playerName))) {
                            loadPM(l, world);
                            return;
                        } else {
                            loadPM(l, 0);
                            return;
                        }
                    }
                }
            }
        } else if (p.privateChat == 2) {
            for (int i = 0; i < c.friends.length; i++) {
                if (c.friends[i] != 0) {
                    if (l == c.friends[i]) {
                        loadPM(l, 0);
                        return;
                    }
                }
            }
        }
    }

    public boolean isInPM(long l) {
        for (int i = 0; i < c.friends.length; i++) {
            if (c.friends[i] != 0) {
                if (l == c.friends[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Magic on items
     **/

    public void magicOnItems(int slot, int itemId, int spellId) {
        if (!c.getItems().playerHasItem(itemId, 1, slot)) {
            return;
        }
        switch (spellId) {
        case 1162: // low alch
            break;

        case 1178: // high alch
            break;
        }
    }

    /**
     * Dieing
     **/

    private String deathMessage(int roll, String name) {
        switch (roll) {
        case 0:
            return "You have defeated " + name + "!";

        case 1:
            return "You have defeated " + name + " in battle.";

        case 2:
            return name + " was clearly no match for you.";

        case 3:
            return "Well done, you've pwned " + name + ".";

        case 4:
            return "You have wiped the floor with " + name + ".";

        case 5:
            return "You rock, " + name + " clearly does not.";

        case 6:
            return "You have proven your superiority over " + name + ".";

        case 7:
            return "You made " + name + " lose the game.";

        case 8:
            return "It's official, you are far more awesome than " + name + ".";

        case 9:
            return "Let all warriors learn from the fate of " + name + " and fear you.";

        case 10:
            return name + " was no match for your unchecked power.";

        case 11:
            return "Your god smiles at you, as " + name + " meets his demise.";
        }
        return "";
    }

    public void applyDead() {
        c.isDead = false;
        c.respawnTimer = 6;
        c.poisonDamage = -1;
        c.venomDamage = -1;
        c.playAnimation(Animation.create(836));
        // c.killerId = c.getCombat().getKillerId(c.playerId);
        c.killerId = findKiller();
        Client o = (Client) PlayerHandler.players[c.killerId];
        if (o != null) {
            if (c.killerId != c.playerId) {
                o.specAmount = 10;
                o.getItems().addSpecialBar(o.getEquipment()[3]);
                o.poisonDamage = -1;
                o.sendMessage(deathMessage(Misc.random(11), c.playerName));
                o.getPA().resetFollow();
                if (!c.inDuelArena()) {
                    o.kills++;
                    c.deaths++;
                    o.getPA().handleLoginText();
                    c.getPA().handleLoginText();
                }
                this.c.getDuel().stakedItems.clear();
            }
        }

        c.faceUpdate(0);
        c.npcIndex = 0;
        c.playerIndex = 0;
        c.stopMovement();
        resetDamageDone();
        c.specAmount = 10;
        c.getItems().addSpecialBar(c.getEquipment()[PlayerConstants.PLAYER_WEAPON]);
        c.lastVeng = 0;
        c.vengOn = false;
        resetFollowers();
        c.attackTimer = 10;
        removeAllWindows();
        c.tradeResetNeeded = true;
    }

    public void resetDamageDone() {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] != null) {
                PlayerHandler.players[i].damageTaken[c.playerId] = 0;
            }
        }
    }

    public void vengMe() {
        if (System.currentTimeMillis() - c.lastVeng > 30000) {
            if (c.getItems().playerHasItem(557, 10) && c.getItems().playerHasItem(9075, 4) && c.getItems()
                .playerHasItem(560, 2)) {
                c.vengOn = true;
                c.lastVeng = System.currentTimeMillis();
                c.playAnimation(Animation.create(4410));
                c.playGraphic(Graphic.create(726, 0, 100));
                c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
                c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
                c.getItems().deleteItem(9075, c.getItems().getItemSlot(9075), 4);
            } else {
                c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
            }
        } else {
            c.sendMessage("You must wait 30 seconds before casting this again.");
        }
    }

    public void resetTb() {
        c.teleBlockLength = 0;
        c.teleBlockDelay = 0;
    }

    public void resetFollowers() {
        for (int j = 0; j < PlayerHandler.players.length; j++) {
            if (PlayerHandler.players[j] != null) {
                if (PlayerHandler.players[j].followId == c.playerId) {
                    Client c = (Client) PlayerHandler.players[j];
                    c.getPA().resetFollow();
                }
            }
        }
    }

    public void giveLife() {
        c.isDead = false;
        c.faceUpdate(-1);
        c.freezeTimer = 0;
        if (!c.inDuelArena() && !RankHandler.isDeveloper(c.playerName)) {
            if (!c.inPits && !c.inFightCaves() && !c.inFunPK()) {
                c.getItems().resetKeepItems();
                if ((c.playerRights == 2 && Config.ADMIN_DROP_ITEMS) || c.playerRights != 2) {
                    if (!c.isSkulled) { // what items to keep
                        c.getItems().keepItem(0, true);
                        c.getItems().keepItem(1, true);
                        c.getItems().keepItem(2, true);
                    }
                    if (c.prayerActive[10] && System.currentTimeMillis() - c.lastProtItem > 700) {
                        c.getItems().keepItem(3, true);
                    }
                    c.getItems().dropAllItems(); // drop all items
                    c.getItems().deleteAllItems(); // delete all items

                    if (!c.isSkulled) { // add the kept items once we finish
                        // deleting and dropping them
                        for (int i1 = 0; i1 < 3; i1++) {
                            if (c.itemKeptId[i1] > 0) {
                                c.getItems().addItem(c.itemKeptId[i1], 1);
                            }
                        }
                    }
                    if (c.prayerActive[10]) { // if we have protect items
                        if (c.itemKeptId[3] > 0) {
                            c.getItems().addItem(c.itemKeptId[3], 1);
                        }
                    }
                }
                c.getItems().resetKeepItems();
            }
        }
        c.getCombat().resetPrayers();
        for (int i = 0; i < 20; i++) {
            c.getLevel()[i] = getLevelForXP(c.getExperience()[i]);
            c.getPA().refreshSkill(i);
        }
        if (c.inDuelArena() && c.duelStatus == 5) {
            c.getPA().movePlayer(Config.DUELING_RESPAWN_X, Config.DUELING_RESPAWN_Y, 0);
            Client otherClient = (Client) PlayerHandler.players[c.duelingWith];
            if (otherClient == null) {
                return;
            }
            otherClient.getDuel().duelVictory();
            if (c.duelStatus != 6) {
                c.getDuel().resetDuel();
            }
        } else if (c.inFunPK()) {
            movePlayer(3328, 4751, 0);
        } else {
            movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
        }
        c.isSkulled = false;
        c.skullTimer = 0;
        c.attackedPlayers.clear();
        // PlayerSaving.getSingleton().requestSave(c.playerId);
        PlayerSave.saveGame(c);
        c.getCombat().resetPlayerAttack();
        frame1();
        resetTb();
        c.isSkulled = false;
        c.attackedPlayers.clear();
        c.headIconPk = -1;
        c.skullTimer = -1;
        c.followId = -1;
        c.damageTaken = new int[Config.MAX_PLAYERS];
        c.getPA().resetAnimation();
        removeAllWindows();
        c.getPA().requestUpdates();
    }

    /**
     * Location change for digging, levers etc
     **/

    public void changeLocation() {
        switch (c.newLocation) {
        case 1:
            sendFrame99(2);
            movePlayer(3578, 9706, -1);
            break;
        case 2:
            sendFrame99(2);
            movePlayer(3568, 9683, -1);
            break;
        case 3:
            sendFrame99(2);
            movePlayer(3557, 9703, -1);
            break;
        case 4:
            sendFrame99(2);
            movePlayer(3556, 9718, -1);
            break;
        case 5:
            sendFrame99(2);
            movePlayer(3534, 9704, -1);
            break;
        case 6:
            sendFrame99(2);
            movePlayer(3546, 9684, -1);
            break;
        }
        c.newLocation = 0;
    }

    /**
     * Teleporting
     **/
    public void spellTeleport(int x, int y, int height) {
        c.getPA().startTeleport(x, y, height, c.playerMagicBook == 1 ? "ancient" : "modern");
    }

    public void startTeleport(int x, int y, int height, String teleportType) {
        if (c.duelStatus == 5) {
            c.sendMessage("You can't teleport during this minigame.");
            return;
        }
        if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
            c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
            return;
        }
        if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
            c.sendMessage("You are teleblocked and can't teleport.");
            return;
        }
        if (c.inFunPK() && !c.inSafeZone()) {
            c.sendMessage("You can't teleport in this area.");
            return;
        }
        if (c.underAttackBy > 0 && !c.inSafeZone()) {
            c.sendMessage("You can't teleport during combat.");
            return;
        }
        if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == 0 && c.getLevel()[3] > 0) {
            if (c.playerIndex > 0 || c.npcIndex > 0)
                c.getCombat().resetPlayerAttack();
            c.stopMovement();
            removeAllWindows();
            c.teleX = x;
            c.teleY = y;
            c.npcIndex = 0;
            c.playerIndex = 0;
            c.faceUpdate(0);
            c.teleHeight = height;
            if (teleportType.equalsIgnoreCase("modern")) {
                c.playAnimation(Animation.create(714));
                c.teleTimer = 10;
                c.teleGfx = 308;
                c.teleEndAnimation = 715;
            }
            if (teleportType.equalsIgnoreCase("ancient")) {
                c.playAnimation(Animation.create(1979));
                c.teleGfx = 0;
                c.teleTimer = 9;
                c.teleEndAnimation = 0;
                c.playGraphic(Graphic.create(392, 0, 0));
            }

        }
    }

    public void teletab(int x, int y, int height) {
        if (c.duelStatus == 5) {
            c.sendMessage("You can't teleport during this minigame.");
            return;
        }
        if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
            c.sendMessage("You are teleblocked and can't teleport.");
            return;
        }
        if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
            c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
            return;
        }
        if (c.inFunPK() && !c.inSafeZone()) {
            c.sendMessage("You can't teleport in this area.");
            return;
        }
        if (!c.isDead && c.getLevel()[3] > 0 && c.teleTimer == 0) {
            c.stopMovement();
            removeAllWindows();
            c.teleX = x;
            c.teleY = y;
            c.npcIndex = 0;
            c.playerIndex = 0;
            c.faceUpdate(0);
            c.teleHeight = height;
            c.playAnimation(Animation.create(4731));
            c.playGraphic(Graphic.create(678));
            c.teleTimer = 8;
        }
    }

    public void startTeleport2(int x, int y, int height) {
        if (c.duelStatus == 5) {
            c.sendMessage("You can't teleport during this minigame.");
            return;
        }
        if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
            c.sendMessage("You are teleblocked and can't teleport.");
            return;
        }
        if (!c.isDead && c.teleTimer == 0) {
            c.stopMovement();
            removeAllWindows();
            c.teleX = x;
            c.teleY = y;
            c.npcIndex = 0;
            c.playerIndex = 0;
            c.faceUpdate(0);
            c.teleHeight = height;
            c.playAnimation(Animation.create(714));
            c.teleTimer = 10;
            c.teleGfx = 308;
            c.teleEndAnimation = 715;

        }
    }

    public void processTeleport() {
        c.teleportToX = c.teleX;
        c.teleportToY = c.teleY;
        c.heightLevel = c.teleHeight;
        if (c.teleEndAnimation > 0) {
            c.playAnimation(Animation.create(c.teleEndAnimation));
        }
    }

    public void movePlayer(int x, int y, int h) {
        c.resetWalkingQueue();
        c.teleportToX = x;
        c.teleportToY = y;
        c.heightLevel = h;
        requestUpdates();
    }

    /**
     * Following
     **/

    public void followPlayer() {
        Client otherPlayer = (Client) PlayerHandler.players[c.followId];
        if (otherPlayer != null) {
            if (c.freezeTimer <= 0) {
                if (!c.isDead && c.getLevel()[3] > 0) {
                    double distanceTo = c.distanceToPoint(otherPlayer.getX(), otherPlayer.getY());
                    if (distanceTo > 25) {
                        c.followId = -1;
                    } else {

                        if (otherPlayer.isDead || otherPlayer.getLevel()[3] <= 0) {
                            c.followId = -1;
                            return;
                        }
                        int followDistance = 1;
                        if (c.usingBow || c.usingMagic || c.autocasting || c.mageFollow) {
                            followDistance = 8;
                        } else if (c.usingRangeWeapon) {
                            followDistance = 4;
                        }
                        c.faceUpdate(otherPlayer.getFace());

                        c.turnPlayerTo(otherPlayer.getX(), otherPlayer.getY());
                        if (distanceTo > followDistance) {
                            PathRequest.addRequest(this.c, otherPlayer);
                        } else if (distanceTo == 0) {
                            int[] step1;
                            switch (Misc.random(3)) {
                            case 0:
                                if (Region.canMove(c.absX, c.absY, c.absX - 1, c.absY, c.heightLevel % 4, 1, 1)) {
                                    step1 = Region.getNextStep(c.absX, c.absY, c.absX - 1, c.absY, c.heightLevel % 4, 1, 1);
                                    c.addToWalkingQueue(step1[0] - c.mapRegionX * 8, step1[1] - c.mapRegionY * 8);
                                    return;
                                }
                            case 1:
                                if (Region.canMove(c.absX, c.absY, c.absX + 1, c.absY, c.heightLevel % 4, 1, 1)) {
                                    step1 = Region.getNextStep(c.absX, c.absY, c.absX + 1, c.absY, c.heightLevel % 4, 1, 1);
                                    c.addToWalkingQueue(step1[0] - c.mapRegionX * 8, step1[1] - c.mapRegionY * 8);
                                    return;
                                }
                            case 2:
                                if (Region.canMove(c.absX, c.absY, c.absX, c.absY - 1, c.heightLevel % 4, 1, 1)) {
                                    step1 = Region.getNextStep(c.absX, c.absY, c.absX, c.absY - 1, c.heightLevel % 4, 1, 1);
                                    c.addToWalkingQueue(step1[0] - c.mapRegionX * 8, step1[1] - c.mapRegionY * 8);
                                    return;
                                }
                            case 3:
                                if (Region.canMove(c.absX, c.absY, c.absX, c.absY + 1, c.heightLevel % 4, 1, 1)) {
                                    step1 = Region.getNextStep(c.absX, c.absY, c.absX, c.absY + 1, c.heightLevel % 4, 1, 1);
                                    c.addToWalkingQueue(step1[0] - c.mapRegionX * 8, step1[1] - c.mapRegionY * 8);
                                    return;
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void followNpc() {
        if (NPCHandler.npcs[c.followId2] == null || NPCHandler.npcs[c.followId2].isDead) {
            c.followId2 = 0;
            return;
        }
        if (c.freezeTimer > 0) {
            return;
        }
        if (c.isDead || c.getLevel()[3] <= 0)
            return;

        int otherX = NPCHandler.npcs[c.followId2].getX();
        int otherY = NPCHandler.npcs[c.followId2].getY();
        boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
        c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
        boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
        boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
        boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
        boolean sameSpot = c.absX == otherX && c.absY == otherY;
        if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
            c.followId2 = 0;
            return;
        }
        if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
            if (otherX != c.getX() && otherY != c.getY()) {
                stopDiagonal(otherX, otherY);
                return;
            }
        }

        if ((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
            return;
        }

        if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
            return;
        }

        if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
            return;
        }

        c.faceUpdate(c.followId2);
        if (otherX == c.absX && otherY == c.absY) {
            int r = Misc.random(3);
            switch (r) {
            case 0:
                walkTo(0, -1);
                break;
            case 1:
                walkTo(0, 1);
                break;
            case 2:
                walkTo(1, 0);
                break;
            case 3:
                walkTo(-1, 0);
                break;
            }
        } else if (c.isRunning2 && !withinDistance) {
            if (otherY > c.getY() && otherX == c.getX()) {
                walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
            } else if (otherY < c.getY() && otherX == c.getX()) {
                walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
            } else if (otherX > c.getX() && otherY == c.getY()) {
                walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
            } else if (otherX < c.getX() && otherY == c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
            } else if (otherX < c.getX() && otherY < c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
                    getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
            } else if (otherX > c.getX() && otherY > c.getY()) {
                walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1),
                    getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
            } else if (otherX < c.getX() && otherY > c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
                    getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
            } else if (otherX > c.getX() && otherY < c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
                    getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
            }
        } else {
            if (otherY > c.getY() && otherX == c.getX()) {
                walkTo(0, getMove(c.getY(), otherY - 1));
            } else if (otherY < c.getY() && otherX == c.getX()) {
                walkTo(0, getMove(c.getY(), otherY + 1));
            } else if (otherX > c.getX() && otherY == c.getY()) {
                walkTo(getMove(c.getX(), otherX - 1), 0);
            } else if (otherX < c.getX() && otherY == c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1), 0);
            } else if (otherX < c.getX() && otherY < c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
            } else if (otherX > c.getX() && otherY > c.getY()) {
                walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
            } else if (otherX < c.getX() && otherY > c.getY()) {
                walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
            } else if (otherX > c.getX() && otherY < c.getY()) {
                walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
            }
        }
        c.faceUpdate(c.followId2);
    }

    public void clippedWalkTo() {
        if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
            c.getPA().walkTo(-1, 0);
        } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
            c.getPA().walkTo(1, 0);
        } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
            c.getPA().walkTo(0, -1);
        } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
            c.getPA().walkTo(0, 1);
        }
        c.postProcessing();
    }

    public int getRunningMove(int i, int j) {
        if (j - i > 2)
            return 2;
        else if (j - i < -2)
            return -2;
        else
            return j - i;
    }

    public void resetFollow() {
        c.followId = 0;
        c.followId2 = 0;
        c.mageFollow = false;
        c.outStream.createFrame(174);
        c.outStream.writeWord(0);
        c.outStream.writeByte(0);
        c.outStream.writeWord(1);
    }

    public void walkTo(int i, int j) {
        c.newWalkCmdSteps = 0;
        if (++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
        int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for (int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

    public void walkTo2(int i, int j) {
        if (c.freezeDelay > 0)
            return;
        c.newWalkCmdSteps = 0;
        if (++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
        int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for (int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

    public void stopDiagonal(int otherX, int otherY) {
        if (c.freezeDelay > 0)
            return;
        c.newWalkCmdSteps = 1;
        int xMove = otherX - c.getX();
        int yMove = 0;
        if (xMove == 0)
            yMove = otherY - c.getY();
        /*
         * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

        int k = c.getX() + xMove;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + yMove;
        l -= c.mapRegionY * 8;

        for (int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }

    }

    public void walkToCheck(int i, int j) {
        if (c.freezeDelay > 0)
            return;
        c.newWalkCmdSteps = 0;
        if (++c.newWalkCmdSteps > 50)
            c.newWalkCmdSteps = 0;
        int k = c.getX() + i;
        k -= c.mapRegionX * 8;
        c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
        int l = c.getY() + j;
        l -= c.mapRegionY * 8;

        for (int n = 0; n < c.newWalkCmdSteps; n++) {
            c.getNewWalkCmdX()[n] += k;
            c.getNewWalkCmdY()[n] += l;
        }
    }

    public int getMove(int place1, int place2) {
        if (System.currentTimeMillis() - c.lastSpear < 4000)
            return 0;
        if ((place1 - place2) == 0) {
            return 0;
        } else if ((place1 - place2) < 0) {
            return 1;
        } else if ((place1 - place2) > 0) {
            return -1;
        }
        return 0;
    }

    public boolean fullVeracs() {
        return c.getEquipment()[PlayerConstants.PLAYER_HAT] == 4753 && c
            .getEquipment()[PlayerConstants.PLAYER_BODY] == 4757 && c
            .getEquipment()[PlayerConstants.PLAYER_LEGS] == 4759 && c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 4755;
    }

    public boolean fullGuthans() {
        return c.getEquipment()[PlayerConstants.PLAYER_HAT] == 4724 && c
            .getEquipment()[PlayerConstants.PLAYER_BODY] == 4728 && c
            .getEquipment()[PlayerConstants.PLAYER_LEGS] == 4730 && c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 4726;
    }

    /**
     * reseting animation
     **/
    public void resetAnimation() {
        c.getCombat().getPlayerAnimIndex();
        c.playAnimation(Animation.create(65535));
        requestUpdates();
    }

    public void requestUpdates() {
        c.updateRequired = true;
        c.setAppearanceUpdateRequired(true);
    }

    public void handleAlt(int id) {
        if (!c.getItems().playerHasItem(id)) {
            c.getItems().addItem(id, 1);
        }
    }

    public void levelUp(int skill) {
        int totalLevel = (getLevelForXP(c.getExperience()[0]) + getLevelForXP(c.getExperience()[1]) + getLevelForXP(
            c.getExperience()[2]) + getLevelForXP(c.getExperience()[3]) + getLevelForXP(
            c.getExperience()[4]) + getLevelForXP(c.getExperience()[5]) + getLevelForXP(
            c.getExperience()[6]) + getLevelForXP(c.getExperience()[7]) + getLevelForXP(
            c.getExperience()[8]) + getLevelForXP(c.getExperience()[9]) + getLevelForXP(
            c.getExperience()[10]) + getLevelForXP(c.getExperience()[11]) + getLevelForXP(
            c.getExperience()[12]) + getLevelForXP(c.getExperience()[13]) + getLevelForXP(
            c.getExperience()[14]) + getLevelForXP(c.getExperience()[15]) + getLevelForXP(
            c.getExperience()[16]) + getLevelForXP(c.getExperience()[17]) + getLevelForXP(
            c.getExperience()[18]) + getLevelForXP(c.getExperience()[19]) + getLevelForXP(c.getExperience()[20]));
        sendFrame126("Total Lvl: " + totalLevel, 3984);
        switch (skill) {
        case 0:
            sendFrame126("Congratulations, you just advanced an attack level!", 6248);
            sendFrame126("Your attack level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6249);
            c.sendMessage("Congratulations, you just advanced an attack level.");
            sendFrame164(6247);
            break;

        case 1:
            sendFrame126("Congratulations, you just advanced a defence level!", 6254);
            sendFrame126("Your defence level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6255);
            c.sendMessage("Congratulations, you just advanced a defence level.");
            sendFrame164(6253);
            break;

        case 2:
            sendFrame126("Congratulations, you just advanced a strength level!", 6207);
            sendFrame126("Your strength level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6208);
            c.sendMessage("Congratulations, you just advanced a strength level.");
            sendFrame164(6206);
            break;

        case 3:
            sendFrame126("Congratulations, you just advanced a hitpoints level!", 6217);
            sendFrame126("Your hitpoints level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6218);
            c.sendMessage("Congratulations, you just advanced a hitpoints level.");
            sendFrame164(6216);
            break;

        case 4:
            sendFrame126("Congratulations, you just advanced a ranged level!", 5453);
            sendFrame126("Your ranged level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6114);
            c.sendMessage("Congratulations, you just advanced a ranging level.");
            sendFrame164(4443);
            break;

        case 5:
            sendFrame126("Congratulations, you just advanced a prayer level!", 6243);
            sendFrame126("Your prayer level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6244);
            c.sendMessage("Congratulations, you just advanced a prayer level.");
            sendFrame164(6242);
            break;

        case 6:
            sendFrame126("Congratulations, you just advanced a magic level!", 6212);
            sendFrame126("Your magic level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6213);
            c.sendMessage("Congratulations, you just advanced a magic level.");
            sendFrame164(6211);
            break;

        case 7:
            sendFrame126("Congratulations, you just advanced a cooking level!", 6227);
            sendFrame126("Your cooking level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6228);
            c.sendMessage("Congratulations, you just advanced a cooking level.");
            sendFrame164(6226);
            break;

        case 8:
            sendFrame126("Congratulations, you just advanced a woodcutting level!", 4273);
            sendFrame126("Your woodcutting level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4274);
            c.sendMessage("Congratulations, you just advanced a woodcutting level.");
            sendFrame164(4272);
            break;

        case 9:
            sendFrame126("Congratulations, you just advanced a fletching level!", 6232);
            sendFrame126("Your fletching level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6233);
            c.sendMessage("Congratulations, you just advanced a fletching level.");
            sendFrame164(6231);
            break;

        case 10:
            sendFrame126("Congratulations, you just advanced a fishing level!", 6259);
            sendFrame126("Your fishing level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6260);
            c.sendMessage("Congratulations, you just advanced a fishing level.");
            sendFrame164(6258);
            break;

        case 11:
            sendFrame126("Congratulations, you just advanced a fire making level!", 4283);
            sendFrame126("Your firemaking level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4284);
            c.sendMessage("Congratulations, you just advanced a fire making level.");
            sendFrame164(4282);
            break;

        case 12:
            sendFrame126("Congratulations, you just advanced a crafting level!", 6264);
            sendFrame126("Your crafting level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6265);
            c.sendMessage("Congratulations, you just advanced a crafting level.");
            sendFrame164(6263);
            break;

        case 13:
            sendFrame126("Congratulations, you just advanced a smithing level!", 6222);
            sendFrame126("Your smithing level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6223);
            c.sendMessage("Congratulations, you just advanced a smithing level.");
            sendFrame164(6221);
            break;

        case 14:
            sendFrame126("Congratulations, you just advanced a mining level!", 4417);
            sendFrame126("Your mining level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4438);
            c.sendMessage("Congratulations, you just advanced a mining level.");
            sendFrame164(4416);
            break;

        case 15:
            sendFrame126("Congratulations, you just advanced a herblore level!", 6238);
            sendFrame126("Your herblore level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 6239);
            c.sendMessage("Congratulations, you just advanced a herblore level.");
            sendFrame164(6237);
            break;

        case 16:
            sendFrame126("Congratulations, you just advanced a agility level!", 4278);
            sendFrame126("Your agility level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4279);
            c.sendMessage("Congratulations, you just advanced an agility level.");
            sendFrame164(4277);
            break;

        case 17:
            sendFrame126("Congratulations, you just advanced a thieving level!", 4263);
            sendFrame126("Your theiving level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4264);
            c.sendMessage("Congratulations, you just advanced a thieving level.");
            sendFrame164(4261);
            break;

        case 18:
            sendFrame126("Congratulations, you just advanced a slayer level!", 12123);
            sendFrame126("Your slayer level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 12124);
            c.sendMessage("Congratulations, you just advanced a slayer level.");
            sendFrame164(12122);
            break;

        case 20:
            sendFrame126("Congratulations, you just advanced a runecrafting level!", 4268);
            sendFrame126("Your runecrafting level is now " + getLevelForXP(c.getExperience()[skill]) + ".", 4269);
            c.sendMessage("Congratulations, you just advanced a runecrafting level.");
            sendFrame164(4267);
            break;
        }
        c.dialogueAction = 0;
        c.nextChat = 0;
    }

    public void refreshSkill(int i) {
        switch (i) {
        case 0:
            sendFrame126("" + c.getLevel()[0] + "", 4004);
            sendFrame126("" + getLevelForXP(c.getExperience()[0]) + "", 4005);
            sendFrame126("" + c.getExperience()[0] + "", 4044);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[0]) + 1) + "", 4045);
            break;

        case 1:
            sendFrame126("" + c.getLevel()[1] + "", 4008);
            sendFrame126("" + getLevelForXP(c.getExperience()[1]) + "", 4009);
            sendFrame126("" + c.getExperience()[1] + "", 4056);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[1]) + 1) + "", 4057);
            break;

        case 2:
            sendFrame126("" + c.getLevel()[2] + "", 4006);
            sendFrame126("" + getLevelForXP(c.getExperience()[2]) + "", 4007);
            sendFrame126("" + c.getExperience()[2] + "", 4050);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[2]) + 1) + "", 4051);
            break;

        case 3:
            sendFrame126("" + c.getLevel()[3] + "", 4016);
            sendFrame126("" + getLevelForXP(c.getExperience()[3]) + "", 4017);
            sendFrame126("" + c.getExperience()[3] + "", 4080);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[3]) + 1) + "", 4081);
            break;

        case 4:
            sendFrame126("" + c.getLevel()[4] + "", 4010);
            sendFrame126("" + getLevelForXP(c.getExperience()[4]) + "", 4011);
            sendFrame126("" + c.getExperience()[4] + "", 4062);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[4]) + 1) + "", 4063);
            break;

        case 5:
            sendFrame126("" + c.getLevel()[5] + "", 4012);
            sendFrame126("" + getLevelForXP(c.getExperience()[5]) + "", 4013);
            sendFrame126("" + c.getExperience()[5] + "", 4068);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[5]) + 1) + "", 4069);
            sendFrame126("" + c.getLevel()[5] + "/" + getLevelForXP(c.getExperience()[5]) + "", 687);// Prayer
            // frame
            break;

        case 6:
            sendFrame126("" + c.getLevel()[6] + "", 4014);
            sendFrame126("" + getLevelForXP(c.getExperience()[6]) + "", 4015);
            sendFrame126("" + c.getExperience()[6] + "", 4074);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[6]) + 1) + "", 4075);
            break;

        case 7:
            sendFrame126("" + c.getLevel()[7] + "", 4034);
            sendFrame126("" + getLevelForXP(c.getExperience()[7]) + "", 4035);
            sendFrame126("" + c.getExperience()[7] + "", 4134);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[7]) + 1) + "", 4135);
            break;

        case 8:
            sendFrame126("" + c.getLevel()[8] + "", 4038);
            sendFrame126("" + getLevelForXP(c.getExperience()[8]) + "", 4039);
            sendFrame126("" + c.getExperience()[8] + "", 4146);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[8]) + 1) + "", 4147);
            break;

        case 9:
            sendFrame126("" + c.getLevel()[9] + "", 4026);
            sendFrame126("" + getLevelForXP(c.getExperience()[9]) + "", 4027);
            sendFrame126("" + c.getExperience()[9] + "", 4110);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[9]) + 1) + "", 4111);
            break;

        case 10:
            sendFrame126("" + c.getLevel()[10] + "", 4032);
            sendFrame126("" + getLevelForXP(c.getExperience()[10]) + "", 4033);
            sendFrame126("" + c.getExperience()[10] + "", 4128);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[10]) + 1) + "", 4129);
            break;

        case 11:
            sendFrame126("" + c.getLevel()[11] + "", 4036);
            sendFrame126("" + getLevelForXP(c.getExperience()[11]) + "", 4037);
            sendFrame126("" + c.getExperience()[11] + "", 4140);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[11]) + 1) + "", 4141);
            break;

        case 12:
            sendFrame126("" + c.getLevel()[12] + "", 4024);
            sendFrame126("" + getLevelForXP(c.getExperience()[12]) + "", 4025);
            sendFrame126("" + c.getExperience()[12] + "", 4104);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[12]) + 1) + "", 4105);
            break;

        case 13:
            sendFrame126("" + c.getLevel()[13] + "", 4030);
            sendFrame126("" + getLevelForXP(c.getExperience()[13]) + "", 4031);
            sendFrame126("" + c.getExperience()[13] + "", 4122);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[13]) + 1) + "", 4123);
            break;

        case 14:
            sendFrame126("" + c.getLevel()[14] + "", 4028);
            sendFrame126("" + getLevelForXP(c.getExperience()[14]) + "", 4029);
            sendFrame126("" + c.getExperience()[14] + "", 4116);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[14]) + 1) + "", 4117);
            break;

        case 15:
            sendFrame126("" + c.getLevel()[15] + "", 4020);
            sendFrame126("" + getLevelForXP(c.getExperience()[15]) + "", 4021);
            sendFrame126("" + c.getExperience()[15] + "", 4092);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[15]) + 1) + "", 4093);
            break;

        case 16:
            sendFrame126("" + c.getLevel()[16] + "", 4018);
            sendFrame126("" + getLevelForXP(c.getExperience()[16]) + "", 4019);
            sendFrame126("" + c.getExperience()[16] + "", 4086);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[16]) + 1) + "", 4087);
            break;

        case 17:
            sendFrame126("" + c.getLevel()[17] + "", 4022);
            sendFrame126("" + getLevelForXP(c.getExperience()[17]) + "", 4023);
            sendFrame126("" + c.getExperience()[17] + "", 4098);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[17]) + 1) + "", 4099);
            break;

        case 18:
            sendFrame126("" + c.getLevel()[18] + "", 12166);
            sendFrame126("" + getLevelForXP(c.getExperience()[18]) + "", 12167);
            sendFrame126("" + c.getExperience()[18] + "", 12171);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[18]) + 1) + "", 12172);
            break;

        case 19:
            sendFrame126("" + c.getLevel()[19] + "", 13926);
            sendFrame126("" + getLevelForXP(c.getExperience()[19]) + "", 13927);
            sendFrame126("" + c.getExperience()[19] + "", 13921);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[19]) + 1) + "", 13922);
            break;

        case 20:
            sendFrame126("" + c.getLevel()[20] + "", 4152);
            sendFrame126("" + getLevelForXP(c.getExperience()[20]) + "", 4153);
            sendFrame126("" + c.getExperience()[20] + "", 4157);
            sendFrame126("" + getXPForLevel(getLevelForXP(c.getExperience()[20]) + 1) + "", 4158);
            break;
        }
    }

    public int getXPForLevel(int level) {
        int points = 0;
        int output = 0;

        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            if (lvl >= level)
                return output;
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    public int getLevelForXP(int exp) {
        int points = 0;
        int output = 0;
        if (exp > 13034430)
            return 99;
        for (int lvl = 1; lvl <= 99; lvl++) {
            points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
            output = (int) Math.floor(points / 4);
            if (output >= exp) {
                return lvl;
            }
        }
        return 0;
    }

    public boolean addSkillXP(int amount, int skill) {
        if (amount + c.getExperience()[skill] < 0 || c.getExperience()[skill] > 200000000) {
            if (c.getExperience()[skill] > 200000000) {
                c.getExperience()[skill] = 200000000;
            }
            return false;
        }
        int oldLevel = getLevelForXP(c.getExperience()[skill]);
        c.getExperience()[skill] += amount;
        if (oldLevel < getLevelForXP(c.getExperience()[skill])) {
            if (c.getLevel()[skill] < c.getLevelForXP(c.getExperience()[skill]) && skill != 3 && skill != 5)
                c.getLevel()[skill] = c.getLevelForXP(c.getExperience()[skill]);
            levelUp(skill);
            c.playGraphic(Graphic.create(199, 0, 100));
            requestUpdates();
        }
        setSkillLevel(skill, c.getLevel()[skill], c.getExperience()[skill]);
        refreshSkill(skill);
        return true;
    }

    public void resetBarrows() {
        c.barrowsNpcs[0][1] = 0;
        c.barrowsNpcs[1][1] = 0;
        c.barrowsNpcs[2][1] = 0;
        c.barrowsNpcs[3][1] = 0;
        c.barrowsNpcs[4][1] = 0;
        c.barrowsNpcs[5][1] = 0;
        c.barrowsKillCount = 0;
        c.randomCoffin = Misc.random(3) + 1;
    }

    public static int Barrows[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736,
        4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759 };
    public static int Runes[] = { 4740, 558, 560, 565 };
    public static int Pots[] = {};

    public int randomBarrows() {
        return Barrows[(int) (Math.random() * Barrows.length)];
    }

    public int randomRunes() {
        return Runes[(int) (Math.random() * Runes.length)];
    }

    public int randomPots() {
        return Pots[(int) (Math.random() * Pots.length)];
    }

    /**
     * Show an arrow icon on the selected player.
     *
     * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
     * @Param j - The player/Npc that the arrow will be displayed above.
     * @Param k - Keep this set as 0
     * @Param l - Keep this set as 0
     */
    public void drawHeadicon(int i, int j, int k, int l) {
        // synchronized(c) {
        c.outStream.createFrame(254);
        c.outStream.writeByte(i);

        if (i == 1 || i == 10) {
            c.outStream.writeWord(j);
            c.outStream.writeWord(k);
            c.outStream.writeByte(l);
        } else {
            c.outStream.writeWord(k);
            c.outStream.writeWord(l);
            c.outStream.writeByte(j);
        }
    }

    public int getNpcId(int id) {
        for (int i = 0; i < NPCHandler.maxNPCs; i++) {
            if (NPCHandler.npcs[i] != null) {
                if (NPCHandler.npcs[i].npcId == id) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void removeObject(int x, int y) {
        object(-1, x, x, 10, 10);
    }

    private void objectToRemove(int X, int Y) {
        object(-1, X, Y, 10, 10);
    }

    private void objectToRemove2(int X, int Y) {
        object(-1, X, Y, -1, 0);
    }

    public void removeObjects() {
        objectToRemove2(3213, 3221);
        objectToRemove2(3213, 3222);
    }

    public void Deletewalls(Client c) {
    }

    public void handleGlory(int gloryId) {
        c.getDH().sendOption4("Edgeville", "Al Kharid", "Karamja", "Mage Bank");
    }

    public Clan getClan() {
        if (Server.clanManager.clanExists(c.playerName)) {
            return Server.clanManager.getClan(c.playerName);
        }
        return null;
    }

    public void sendClan(String name, String message, String clan, int rights) {
        c.outStream.createFrameVarSizeWord(217);
        c.outStream.writeString(name);
        c.outStream.writeString(Misc.formatPlayerName(message));
        c.outStream.writeString(clan);
        c.outStream.writeWord(rights);
        c.outStream.endFrameVarSize();
    }

    public void clearClanChat() {
        c.getPA().sendFrame126("Talking in: ", 18139);
        c.getPA().sendFrame126("Owner: ", 18140);
        for (int j = 18144; j < 18244; j++) {
            c.getPA().sendFrame126("", j);
        }
    }

    public void setClanData() {
        boolean exists = Server.clanManager.clanExists(c.playerName);
        if (!exists || c.clan == null) {
            sendFrame126("Join chat", 18135);
            sendFrame126("Talking in: Not in chat", 18139);
            sendFrame126("Owner: None", 18140);
        }
        if (!exists) {
            sendFrame126("Chat Disabled", 18306);
            String title = "";
            for (int id = 18307; id < 18317; id += 3) {
                if (id == 18307) {
                    title = "Anyone";
                } else if (id == 18310) {
                    title = "Anyone";
                } else if (id == 18313) {
                    title = "General+";
                } else if (id == 18316) {
                    title = "Only Me";
                }
                sendFrame126(title, id + 2);
            }
            for (int index = 0; index < 100; index++) {
                sendFrame126("", 18323 + index);
            }
            for (int index = 0; index < 100; index++) {
                sendFrame126("", 18424 + index);
            }
            return;
        }
        Clan clan = Server.clanManager.getClan(c.playerName);
        sendFrame126(clan.getTitle(), 18306);
        String title = "";
        for (int id = 18307; id < 18317; id += 3) {
            if (id == 18307) {
                title = clan.getRankTitle(clan.whoCanJoin) + (
                    clan.whoCanJoin > Clan.Rank.ANYONE && clan.whoCanJoin < Clan.Rank.OWNER ? "+" : "");
            } else if (id == 18310) {
                title = clan.getRankTitle(clan.whoCanTalk) + (
                    clan.whoCanTalk > Clan.Rank.ANYONE && clan.whoCanTalk < Clan.Rank.OWNER ? "+" : "");
            } else if (id == 18313) {
                title = clan.getRankTitle(clan.whoCanKick) + (
                    clan.whoCanKick > Clan.Rank.ANYONE && clan.whoCanKick < Clan.Rank.OWNER ? "+" : "");
            } else if (id == 18316) {
                title = clan.getRankTitle(clan.whoCanBan) + (
                    clan.whoCanBan > Clan.Rank.ANYONE && clan.whoCanBan < Clan.Rank.OWNER ? "+" : "");
            }
            sendFrame126(title, id + 2);
        }
        if (clan.rankedMembers != null) {
            for (int index = 0; index < 100; index++) {
                if (index < clan.rankedMembers.size()) {
                    sendFrame126("<clan=" + clan.ranks.get(index) + ">" + clan.rankedMembers.get(index), 18323 + index);
                } else {
                    sendFrame126("", 18323 + index);
                }
            }
        }
        if (clan.bannedMembers != null) {
            for (int index = 0; index < 100; index++) {
                if (index < clan.bannedMembers.size()) {
                    sendFrame126(clan.bannedMembers.get(index), 18424 + index);
                } else {
                    sendFrame126("", 18424 + index);
                }
            }
        }
    }

    public void resetVariables() {
        c.skillToChange = -1;
        c.canChangeAppearance = false;
        c.isShopping = false;
        c.isBanking = false;
        c.dialogueAction = 0;
        c.teleAction = 0;
        c.wizardIndex = -1;
    }

    public boolean inPitsWait() {
        return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175 && c.getY() >= 5169;
    }

    public void castleWarsObjects() {
        object(-1, 2373, 3119, -3, 10);
        object(-1, 2372, 3119, -3, 10);
    }

    public int antiFire() {
        int toReturn = 0;
        if (c.antiFirePot)
            toReturn++;
        if (c.getEquipment()[PlayerConstants.PLAYER_SHIELD] == 1540 || c.prayerActive[12] || c
            .getEquipment()[PlayerConstants.PLAYER_SHIELD] == 11284)
            toReturn++;
        return toReturn;
    }

    public boolean checkForFlags() {
        int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 }, { 2402, 5 }, { 746, 5 }, { 4151, 150 },
            { 565, 100000 }, { 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
        for (int j = 0; j < itemsToCheck.length; j++) {
            if (itemsToCheck[j][1] < c.getItems().getTotalCount(itemsToCheck[j][0]))
                return true;
        }
        return false;
    }

    public int getWearingAmount() {
        int count = 0;
        for (int j = 0; j < c.getEquipment().length; j++) {
            if (c.getEquipment()[j] > 0)
                count++;
        }
        return count;
    }

    public int getInventoryAmount() {
        int count = 0;
        for (int j = 0; j < c.playerItems.length; j++) {
            if (c.playerItems[j] > 0)
                count++;
        }
        return count;
    }

    public void useOperate(int itemId) {
        switch (itemId) {
        case 1712:
        case 1710:
        case 1708:
        case 1706:
            handleGlory(itemId);
            break;
        case 11283:
        case 11284:
            if (c.playerIndex > 0) {
                c.getCombat().handleDfs();
            } else if (c.npcIndex > 0) {
                c.getCombat().handleDfsNPC();
            }
            break;
        }
    }

    public void getSpeared(int otherX, int otherY) {
        int x = c.absX - otherX;
        int y = c.absY - otherY;
        if (x > 0)
            x = 1;
        else if (x < 0)
            x = -1;
        if (y > 0)
            y = 1;
        else if (y < 0)
            y = -1;
        moveCheck(x, y);
        c.lastSpear = System.currentTimeMillis();
    }

    public void moveCheck(int xMove, int yMove) {
        movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
    }

    public int findKiller() {
        int killer = c.playerId;
        int damage = 0;
        for (int j = 0; j < Config.MAX_PLAYERS; j++) {
            if (PlayerHandler.players[j] == null)
                continue;
            if (j == c.playerId)
                continue;
            if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) || c
                .goodDistance(c.absX, c.absY + 9400, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40) || c
                .goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY + 9400, 40))
                if (c.damageTaken[j] > damage) {
                    damage = c.damageTaken[j];
                    killer = j;
                }
        }
        return killer;
    }

    public void appendPoison(int damage) {
        if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
            c.sendMessage("You have been poisoned!");
            c.poisonDamage = damage;
        }
    }

    public void appendVenom() {
        if (System.currentTimeMillis() - c.venomImmunity < 600000) {
            return;
        }
        c.venomDamage = 6;
    }

    public boolean checkForPlayer(int x, int y) {
        for (Player p : PlayerHandler.players) {
            if (p != null) {
                if (p.getX() == x && p.getY() == y)
                    return true;
            }
        }
        return false;
    }

    public void checkPouch(int i) {
        if (i < 0)
            return;
        c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");
    }

    public void fillPouch(int i) {
        if (i < 0)
            return;
        int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
        if (toAdd > c.getItems().getItemAmount(1436)) {
            toAdd = c.getItems().getItemAmount(1436);
        }
        if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
            toAdd = c.POUCH_SIZE[i] - c.pouches[i];
        if (toAdd > 0) {
            c.getItems().deleteItem(1436, toAdd);
            c.pouches[i] += toAdd;
        }
    }

    public void emptyPouch(int i) {
        if (i < 0)
            return;
        int toAdd = c.pouches[i];
        if (toAdd > c.getItems().freeSlots()) {
            toAdd = c.getItems().freeSlots();
        }
        if (toAdd > 0) {
            c.getItems().addItem(1436, toAdd);
            c.pouches[i] -= toAdd;
        }
    }

    public void fixAllBarrows() {
        int totalCost = 0;
        int cashAmount = c.getItems().getItemAmount(995);
        for (int j = 0; j < c.playerItems.length; j++) {
            boolean breakOut = false;
            for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
                if (c.playerItems[j] - 1 == c.getItems().brokenBarrows[i][1]) {
                    if (totalCost + 80000 > cashAmount) {
                        breakOut = true;
                        c.sendMessage("You have run out of money.");
                        break;
                    } else {
                        totalCost += 80000;
                    }
                    c.playerItems[j] = c.getItems().brokenBarrows[i][0] + 1;
                }
            }
            if (breakOut)
                break;
        }
        if (totalCost > 0)
            c.getItems().deleteItem(995, c.getItems().getItemSlot(995), totalCost);
    }

    private String getRank() {
        switch (RankHandler.getRank(c.playerName)) {
        case 0:
            return "Player";

        case 1:
            return "Moderastor";

        case 2:
            return "Administrator";

        case 3:
            return c.playerName.equalsIgnoreCase("tyler") ? "CEO" : "CFO";

        case 4:
            return "Donator";

        case 5:
            return "Super Donator";

        case 6:
            return "Extreme Donator";

        case 7:
            return "Supporter";

        case 8:
            return "Middleman";

        case 9:
            return "Veteran";

        case 10:
            return "Youtuber";
        }
        return "";
    }

    public double getKdr() {
        double kdr = 0.0D;
        if (c.kills > 0 && c.deaths > 0)
            kdr = c.kills / c.deaths;
        return kdr;
    }

    public void handleLoginText() {
               c.getPA().sendFrame126("Players Online: "+PlayerHandler.getPlayerCount()+ " ", 640);
               c.getPA().sendFrame126("Players Online: "+PlayerHandler.getPlayerCount()+ " ", 13136);
               c.getPA().sendFrame126("", 673);
           	c.getPA().sendFrame126("@gre@Player Rank: @whi@" + getRank(), 7332);
           c.getPA().sendFrame126("@gre@OSRS-PVP Points: @whi@" + c.osrsPoints, 7333);
                   c.getPA().sendFrame126("@gre@PVP Points: @whi@" + c.pkPoints, 7334);
           	c.getPA().sendFrame126("@gre@Player Kills: @whi@" + c.kills, 7339);
           	c.getPA().sendFrame126("@gre@Player Deaths: @whi@" + c.deaths, 7338);
           	c.getPA().sendFrame126("@gre@K/D/R: @whi@" + getKdr(), 7340);
           	c.getPA().sendFrame126("@gre@Donator Points: @whi@" + c.donatorPoints, 7336);
           	c.getPA().sendFrame126("@gre@Total donated: @whi@" + c.funds + "@bla@$", 7383);
        }

    public void handleWeaponStyle() {
        if (c.fightMode == 0) {
            c.getPA().sendFrame36(43, c.fightMode);
        } else if (c.fightMode == 1) {
            c.getPA().sendFrame36(43, 3);
        } else if (c.fightMode == 2) {
            c.getPA().sendFrame36(43, 1);
        } else if (c.fightMode == 3) {
            c.getPA().sendFrame36(43, 2);
        }
    }

    public void globalYell(String txt) {
        for (int i = 0; i < PlayerHandler.players.length; i++) {
            if (PlayerHandler.players[i] == null)
                continue;
            ((Client) PlayerHandler.players[i]).sendMessage(txt);
        }
    }

    private static int[] optionIds = { 2460, 2470, 2481, 2493, 2493 };

    public void showOptions(Client c, String... lines) {
        if (lines == null || lines.length < 2 || lines.length > 5) {
            return;
        }
        int id = optionIds[lines.length - 2];
        c.getPA().sendFrame126("Select an Option", id++);
        for (int i = 0; i < 5; i++) {
            c.getPA().sendFrame126(i >= lines.length ? "" : lines[i], id++);
        }
        c.getPA().sendFrame164(optionIds[lines.length - 2] - 1);
    }
}
