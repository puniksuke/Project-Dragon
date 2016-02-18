package org.osrspvp.model;

import org.osrspvp.Config;
import org.osrspvp.Server;
import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.content.minigame.Barrows;
import org.osrspvp.model.npc.PetHandler;
import org.osrspvp.model.object.Object;
import org.osrspvp.model.object.SlashWebs;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;

public class ActionHandler {

    private Client c;

    public ActionHandler(Client Client) {
        this.c = Client;
    }

    public void firstClickObject(int objectType, int obX, int obY) {
        c.clickObjectType = 0;
        // c.sendMessage("Object type: " + objectType);
        switch (objectType) {

        case 23271:
            if (c.goodDistance(c.getX(), c.getY(), c.objectX, c.objectY, 2)) {
                c.turnPlayerTo(c.objectX, c.objectY);
                if (c.isBusy()) {
                    return;
                }
                c.setBusyState(true);
                c.playAnimation(Animation.create(6132));
                c.isRunning = false;
                c.isRunning2 = false;
                c.getPA().sendFrame36(173, 0);
                if (c.getY() <= c.objectY) {
                    c.setForceMovement(c.localX(), c.localY(), c.localX(), c.localY() + 3, 33, 60, 0);
                } else {
                    c.setForceMovement(c.localX(), c.localY(), c.localX(), c.localY() - 3, 33, 60, 4);
                }
                CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

                    @Override
                    public void execute(CycleEventContainer container) {
                        int newY = c.absY >= 3523 ? c.absY - 3 : c.absY + 3;
                        c.getPA().movePlayer(c.absX, newY, 0);
                        c.isRunning = true;
                        c.isRunning2 = true;
                        c.setBusyState(false);
                        c.clearUpdateFlags();
                        container.stop();
                    }

                }, 3);
            }
            break;
        case 3192:
            c.sendMessage("Yes, we know this is bugged. Come back soon.");
            break;
        case 26258:
            c.getDH().sendOption3("Modern Spellbook", "Ancients Spellbook", "Lunar Spellbook");
            c.dialogueAction = 21;
            break;

        case 733:
            SlashWebs.slashWeb(c, objectType, obX, obY);
            break;

        case 6084:
        case 24101:
            c.getPA().openUpBank();
            break;

        case 2492:
            if (c.killCount >= 20) {
                c.getDH().sendOption4("Armadyl", "Bandos", "Saradomin", "Zamorak");
                c.dialogueAction = 20;
            } else {
                c.sendMessage("You need 20 kill count before teleporting to a boss chamber.");
            }
            break;

        case 1765:
            c.getPA().movePlayer(3067, 10256, 0);
            break;
        case 2882:
        case 2883:
            if (c.objectX == 3268) {
                if (c.absX < c.objectX) {
                    c.getPA().walkTo(1, 0);
                } else {
                    c.getPA().walkTo(-1, 0);
                }
            }
            break;
	case 13371:
		c.getPA().sendFrame126("Your Bet", 2493);
		c.getPA().sendFrame126("500K", 2494);
		c.getPA().sendFrame126("1000K", 2495);
		c.getPA().sendFrame126("2500K", 2496);
		c.getPA().sendFrame126("5000K", 2497);
		c.getPA().sendFrame126("10M", 2498);
		c.getPA().sendFrame164(2492);
		c.dialogueAction = 13371;
		break;
	case 13372:
		c.getPA().sendFrame126("Flower Colour", 2493);
		c.getPA().sendFrame126("Red", 2494);
		c.getPA().sendFrame126("Blue", 2495);
		c.getPA().sendFrame126("Yellow", 2496);
		c.getPA().sendFrame126("Purple", 2497);
		c.getPA().sendFrame126("Orange", 2498);
		c.getPA().sendFrame164(2492);
		c.dialogueAction = 13372;
		break;
        case 272:
            c.getPA().movePlayer(c.absX, c.absY, 1);
            break;

        case 273:
            c.getPA().movePlayer(c.absX, c.absY, 0);
            break;
        case 245:
            c.getPA().movePlayer(c.absX, c.absY + 2, 2);
            break;
        case 246:
            c.getPA().movePlayer(c.absX, c.absY - 2, 1);
            break;
        case 1766:
            c.getPA().movePlayer(3016, 3849, 0);
            break;
        case 6552:
            if (c.playerMagicBook == 0) {
                c.playerMagicBook = 1;
                c.setSidebarInterface(6, 12855);
                c.sendMessage("An ancient wisdomin fills your mind.");
                c.getPA().resetAutocast();
            } else {
                c.setSidebarInterface(6, 1151); // modern
                c.playerMagicBook = 0;
                c.sendMessage("You feel a drain on your memory.");
                c.autocastId = -1;
                c.getPA().resetAutocast();
            }
            break;

        case 410:
            if (c.playerMagicBook == 0) {
                c.playerMagicBook = 2;
                c.setSidebarInterface(6, 29999);
                c.sendMessage("Lunar wisdom fills your mind.");
                c.getPA().resetAutocast();
            } else {
                c.setSidebarInterface(6, 1151); // modern
                c.playerMagicBook = 0;
                c.sendMessage("You feel a drain on your memory.");
                c.autocastId = -1;
                c.getPA().resetAutocast();
            }
            break;

        case 1816:
            c.getPA().startTeleport2(2271, 4680, 0);
            break;
        case 1817:
            c.getPA().startTeleport(3067, 10253, 0, "modern");
            break;
        case 1814:
            // ardy lever
            c.getPA().startTeleport(3153, 3923, 0, "modern");
            break;

        case 9356:
            // c.getPA().enterCaves();
            c.sendMessage("Temporarily removed due to bugs.");
            break;
        case 1733:
            c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
            break;

        case 1734:
            c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
            break;

        case 8959:
            if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
                if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
                    new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);
                }
            }
            break;

        case 2213:
        case 14367:
        case 11758:
        case 3193:
        case 11744:
            c.getPA().openUpBank();
            break;

        case 10177:
            c.getPA().movePlayer(1890, 4407, 0);
            break;
        case 10230:
            c.getPA().movePlayer(2900, 4449, 0);
            break;
        case 10229:
            c.getPA().movePlayer(1912, 4367, 0);
            break;
        case 2623:
            if (c.absX >= c.objectX)
                c.getPA().walkTo(-1, 0);
            else
                c.getPA().walkTo(1, 0);
            break;
        // pc boat
        case 14315:
            c.getPA().movePlayer(2661, 2639, 0);
            break;
        case 14314:
            c.getPA().movePlayer(2657, 2639, 0);
            break;

        case 1596:
        case 1597:
            if (c.getY() >= c.objectY)
                c.getPA().walkTo(0, -1);
            else
                c.getPA().walkTo(0, 1);
            break;

        case 14235:
        case 14233:
            if (c.objectX == 2670)
                if (c.absX <= 2670)
                    c.absX = 2671;
                else
                    c.absX = 2670;
            if (c.objectX == 2643)
                if (c.absX >= 2643)
                    c.absX = 2642;
                else
                    c.absX = 2643;
            if (c.absX <= 2585)
                c.absY += 1;
            else
                c.absY -= 1;
            c.getPA().movePlayer(c.absX, c.absY, 0);
            break;

        case 14829:
        case 14830:
        case 14827:
        case 14828:
        case 14826:
        case 14831:
            // Server.objectHandler.startObelisk(objectType);
            Server.objectManager.startObelisk(objectType);
            break;
        case 4387:
            // Server.castleWars.joinWait(c,1);
            break;

        case 4388:
            // Server.castleWars.joinWait(c,2);
            break;

        case 4408:
            // Server.castleWars.joinWait(c,3);
            break;

        case 9369:
            if (c.getY() > 5175)
                c.getPA().movePlayer(2399, 5175, 0);
            else
                c.getPA().movePlayer(2399, 5177, 0);
            break;

        case 4411:
        case 4415:
        case 4417:
        case 4418:
        case 4419:
        case 4420:
        case 4469:
        case 4470:
        case 4911:
        case 4912:
        case 1747:
        case 1757:
            // Server.castleWars.handleObjects(c, objectType, obX, obY);
            break;

		/*
         * Barrows Chest
		 */
        case 10284:
            if (c.barrowsKillCount < 5) {
                c.sendMessage("You haven't killed all the brothers");
            }
            if (c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
                c.sendMessage("I have already summoned this npc.");
            }
            if (c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
                Server.npcHandler
                    .spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694 - 1, 0, 0, 120, 30, 200, 200, true, true);
                c.barrowsNpcs[c.randomCoffin][1] = 1;
            }
            if ((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
                c.getPA().resetBarrows();
                c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
                if (Misc.random(2) == 1)
                    c.getItems().addItem(c.getPA().randomBarrows(), 1);
                c.getPA().startTeleport(3564, 3288, 0, "modern");
            } else if (c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
                c.sendMessage("You need at least 2 inventory slot opened.");
            }
            break;
        /*
         * Doors
		 */
        case 6749:
            if (obX == 3562 && obY == 9678) {
                c.getPA().object(3562, 9678, 6749, -3, 0);
                c.getPA().object(3562, 9677, 6730, -1, 0);
            } else if (obX == 3558 && obY == 9677) {
                c.getPA().object(3558, 9677, 6749, -1, 0);
                c.getPA().object(3558, 9678, 6730, -3, 0);
            }
            break;
        case 6730:
            if (obX == 3558 && obY == 9677) {
                c.getPA().object(3562, 9678, 6749, -3, 0);
                c.getPA().object(3562, 9677, 6730, -1, 0);
            } else if (obX == 3558 && obY == 9678) {
                c.getPA().object(3558, 9677, 6749, -1, 0);
                c.getPA().object(3558, 9678, 6730, -3, 0);
            }
            break;
        case 6727:
            if (obX == 3551 && obY == 9684) {
                c.sendMessage("You cant open this door..");
            }
            break;
        case 6746:
            if (obX == 3552 && obY == 9684) {
                c.sendMessage("You cant open this door..");
            }
            break;
        case 6748:
            if (obX == 3545 && obY == 9678) {
                c.getPA().object(3545, 9678, 6748, -3, 0);
                c.getPA().object(3545, 9677, 6729, -1, 0);
            } else if (obX == 3541 && obY == 9677) {
                c.getPA().object(3541, 9677, 6748, -1, 0);
                c.getPA().object(3541, 9678, 6729, -3, 0);
            }
            break;
        case 6729:
            if (obX == 3545 && obY == 9677) {
                c.getPA().object(3545, 9678, 6748, -3, 0);
                c.getPA().object(3545, 9677, 6729, -1, 0);
            } else if (obX == 3541 && obY == 9678) {
                c.getPA().object(3541, 9677, 6748, -1, 0);
                c.getPA().object(3541, 9678, 6729, -3, 0);
            }
            break;
        case 6726:
            if (obX == 3534 && obY == 9684) {
                c.getPA().object(3534, 9684, 6726, -4, 0);
                c.getPA().object(3535, 9684, 6745, -2, 0);
            } else if (obX == 3535 && obY == 9688) {
                c.getPA().object(3535, 9688, 6726, -2, 0);
                c.getPA().object(3534, 9688, 6745, -4, 0);
            }
            break;
        case 6745:
            if (obX == 3535 && obY == 9684) {
                c.getPA().object(3534, 9684, 6726, -4, 0);
                c.getPA().object(3535, 9684, 6745, -2, 0);
            } else if (obX == 3534 && obY == 9688) {
                c.getPA().object(3535, 9688, 6726, -2, 0);
                c.getPA().object(3534, 9688, 6745, -4, 0);
            }
            break;
        case 6743:
            if (obX == 3545 && obY == 9695) {
                c.getPA().object(3545, 9694, 6724, -1, 0);
                c.getPA().object(3545, 9695, 6743, -3, 0);
            } else if (obX == 3541 && obY == 9694) {
                c.getPA().object(3541, 9694, 6724, -1, 0);
                c.getPA().object(3541, 9695, 6743, -3, 0);
            }
            break;
        case 6724:
            if (obX == 3545 && obY == 9694) {
                c.getPA().object(3545, 9694, 6724, -1, 0);
                c.getPA().object(3545, 9695, 6743, -3, 0);
            } else if (obX == 3541 && obY == 9695) {
                c.getPA().object(3541, 9694, 6724, -1, 0);
                c.getPA().object(3541, 9695, 6743, -3, 0);
            }
            break;
        /*
         * Cofins
		 */
        case 6707: // verac
            c.getPA().movePlayer(3556, 3298, 0);
            break;

        case 6823:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[0][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY() - 1, -1, 0, 120, 25, 200, 200, true, true);
                c.barrowsNpcs[0][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        case 6706: // torag
            c.getPA().movePlayer(3553, 3283, 0);
            break;

        case 6772:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[1][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2029, c.getX() + 1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
                c.barrowsNpcs[1][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        case 6705: // karil stairs
            c.getPA().movePlayer(3565, 3276, 0);
            break;
        case 6822:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[2][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY() - 1, -1, 0, 90, 17, 200, 200, true, true);
                c.barrowsNpcs[2][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        case 6704: // guthan stairs
            c.getPA().movePlayer(3578, 3284, 0);
            break;
        case 6773:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[3][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY() - 1, -1, 0, 120, 23, 200, 200, true, true);
                c.barrowsNpcs[3][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        case 6703: // dharok stairs
            c.getPA().movePlayer(3574, 3298, 0);
            break;
        case 6771:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[4][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY() - 1, -1, 0, 120, 45, 250, 250, true, true);
                c.barrowsNpcs[4][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        case 6702: // ahrim stairs
            c.getPA().movePlayer(3565, 3290, 0);
            break;
        case 6821:
            if (Barrows.selectCoffin(c, objectType)) {
                return;
            }
            if (c.barrowsNpcs[5][1] == 0) {
                Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY() - 1, -1, 0, 90, 19, 200, 200, true, true);
                c.barrowsNpcs[5][1] = 1;
            } else {
                c.sendMessage("You have already searched in this sarcophagus.");
            }
            break;

        // DOORS
        case 1516:
        case 1519:
            if (c.objectY == 9698) {
                if (c.absY >= c.objectY)
                    c.getPA().walkTo(0, -1);
                else
                    c.getPA().walkTo(0, 1);
                break;
            }
        case 1530:
        case 1531:
        case 1533:
        case 1534:
        case 11712:
        case 11711:
        case 11707:
        case 11708:
        case 6725:
        case 3198:
        case 3197:
            Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);
            break;

        case 9319:
            if (c.heightLevel == 0)
                c.getPA().movePlayer(c.absX, c.absY, 1);
            else if (c.heightLevel == 1)
                c.getPA().movePlayer(c.absX, c.absY, 2);
            break;

        case 9320:
            if (c.heightLevel == 1)
                c.getPA().movePlayer(c.absX, c.absY, 0);
            else if (c.heightLevel == 2)
                c.getPA().movePlayer(c.absX, c.absY, 1);
            break;

        case 4496:
        case 4494:
            if (c.heightLevel == 2) {
                c.getPA().movePlayer(c.absX - 5, c.absY, 1);
            } else if (c.heightLevel == 1) {
                c.getPA().movePlayer(c.absX + 5, c.absY, 0);
            }
            break;

        case 4493:
            if (c.heightLevel == 0) {
                c.getPA().movePlayer(c.absX - 5, c.absY, 1);
            } else if (c.heightLevel == 1) {
                c.getPA().movePlayer(c.absX + 5, c.absY, 2);
            }
            break;

        case 4495:
            if (c.heightLevel == 1) {
                c.getPA().movePlayer(c.absX + 5, c.absY, 2);
            }
            break;

        case 5126:
            if (c.absY == 3554)
                c.getPA().walkTo(0, 1);
            else
                c.getPA().walkTo(0, -1);
            break;

        case 1755:
            if (c.objectX == 2884 && c.objectY == 9797)
                c.getPA().movePlayer(c.absX, c.absY - 6400, 0);
            break;
        case 1759:
            if (c.objectX == 2884 && c.objectY == 3397)
                c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
            break;
        case 409:
            if (c.getLevel()[5] < c.getPA().getLevelForXP(c.getExperience()[5])) {
                c.playAnimation(Animation.create(645));
                c.getLevel()[5] = c.getPA().getLevelForXP(c.getExperience()[5]);
                c.sendMessage("You recharge your prayer points.");
                c.getPA().refreshSkill(5);
            } else {
                c.sendMessage("You already have full prayer points.");
            }
            break;
        case 2873:
            if (!c.getItems().ownsCape()) {
                c.playAnimation(Animation.create(645));
                c.sendMessage("Saradomin blesses you with a cape.");
                c.getItems().addItem(2412, 1);
            }
            break;
        case 2875:
            if (!c.getItems().ownsCape()) {
                c.playAnimation(Animation.create(645));
                c.sendMessage("Guthix blesses you with a cape.");
                c.getItems().addItem(2413, 1);
            }
            break;
        case 2874:
            if (!c.getItems().ownsCape()) {
                c.playAnimation(Animation.create(645));
                c.sendMessage("Zamorak blesses you with a cape.");
                c.getItems().addItem(2414, 1);
            }
            break;
        case 2879:
            c.getPA().movePlayer(2538, 4716, 0);
            break;
        case 2878:
            c.getPA().movePlayer(2509, 4689, 0);
            break;
        case 5960:
            c.getPA().startTeleport2(3090, 3956, 0);
            break;

        case 1815:
            c.getPA().startTeleport2(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
            break;

        case 9706:
            c.getPA().startTeleport2(3105, 3951, 0);
            break;
        case 9707:
            c.getPA().startTeleport2(3105, 3956, 0);
            break;

        case 5959:
            c.getPA().startTeleport2(2539, 4712, 0);
            break;

        case 2558:
            c.sendMessage("This door is locked.");
            break;

        case 9294:
            if (c.absX < c.objectX) {
                c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
            } else if (c.absX > c.objectX) {
                c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
            }
            break;

        case 9293:
            if (c.absX < c.objectX) {
                c.getPA().movePlayer(2892, 9799, 0);
            } else {
                c.getPA().movePlayer(2886, 9799, 0);
            }
            break;
        case 10529:
        case 10527:
            if (c.absY <= c.objectY)
                c.getPA().walkTo(0, 1);
            else
                c.getPA().walkTo(0, -1);
            break;

        case 11726:
            c.sendMessage("This door is locked.");
            break;
        }
    }

    public void secondClickObject(int objectType, int obX, int obY) {
        c.clickObjectType = 0;
        // c.sendMessage("Object type: " + objectType);
        switch (objectType) {

        case 11726:
            if (System.currentTimeMillis() - c.buryDelay <= 1000) {
                return;
            }
            if (!c.getItems().playerHasItem(1523, 1)) {
                c.sendMessage("You need a " + ItemDef.forId(1523).getName() + " to pick this lock.");
                return;
            }
            if (Misc.random(4) != 0) {
                c.sendMessage("You failed to pick the lock.");
                return;
            }
            if (c.getY() < obY) {
                c.getPA().walkTo(0, 1);
            } else {
                c.getPA().walkTo(0, -1);
            }
            c.buryDelay = System.currentTimeMillis();
            break;

        case 6084:
        case 24101:
            c.getPA().openUpBank();
            break;

        case 11744:
            c.getPA().openUpBank();
            break;

        case 2213:
        case 14367:
        case 11758:
            c.getPA().openUpBank();
            break;

        case 2558:
            if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
                break;
            if (c.getItems().playerHasItem(1523, 1)) {
                c.lastLockPick = System.currentTimeMillis();
                if (Misc.random(10) <= 3) {
                    c.sendMessage("You fail to pick the lock.");
                    break;
                }
                if (c.objectX == 3044 && c.objectY == 3956) {
                    if (c.absX == 3045) {
                        c.getPA().walkTo2(-1, 0);
                    } else if (c.absX == 3044) {
                        c.getPA().walkTo2(1, 0);
                    }

                } else if (c.objectX == 3038 && c.objectY == 3956) {
                    if (c.absX == 3037) {
                        c.getPA().walkTo2(1, 0);
                    } else if (c.absX == 3038) {
                        c.getPA().walkTo2(-1, 0);
                    }
                } else if (c.objectX == 3041 && c.objectY == 3959) {
                    if (c.absY == 3960) {
                        c.getPA().walkTo2(0, -1);
                    } else if (c.absY == 3959) {
                        c.getPA().walkTo2(0, 1);
                    }
                }
            } else {
                c.sendMessage("I need a lockpick to pick this lock.");
            }
            break;
        }
    }

    public void thirdClickObject(int objectType, int obX, int obY) {
        c.clickObjectType = 0;
        c.sendMessage("Object type: " + objectType);
        switch (objectType) {
        }
    }

    public void firstClickNpc(int i) {
    	if (i == 1160) {
    		c.getDH().sendOption2("Bosses", "Minigames");
    		c.wizardIndex = c.npcClickIndex;
    		c.npcClickIndex = 0;
    		return;
    	}
        c.clickNpcType = 0;
        c.npcClickIndex = 0;
        switch (i) {
        case 1152:
            if (RankHandler.getRank(c.playerName) < 1) {
                c.sendMessage("You need to be a donator or a higher rank to use this feature.");
                c.sendMessage("You can donate at @blu@::donate");
                return;
            }
            for (int i1 = 0; i1 < 7; i1++) {
                c.getLevel()[i1] = 99;
                c.getExperience()[i1] = c.getPA().getXPForLevel(100);
                c.getPA().refreshSkill(i1);
                c.getPA().requestUpdates();
            }
            c.specAmount = 10.0D;
            c.getItems().addSpecialBar(c.getEquipment()[3]);
            c.playGraphic(Graphic.create(436, 0, 0));
            break;

        case 1011:
            c.getShops().openShop(9);
            break;

        case 1567:
            c.getShops().openShop(11);
            break;

	case 5792:
            c.getShops().openShop(8);
            break;

        case 5438:
            c.getShops().openShop(1);
            break;

        case 5437:
            c.getShops().openShop(2);
            break;

        case 5436:
            c.getShops().openShop(3);
            break;

        case 1576:
            c.getShops().openShop(4);
            break;

        case 505:
            c.getShops().openShop(5);
            break;

        case 315:
            c.getDH().sendOption2("PK Exchange", "PVP Point Exchange");
            c.dialogueAction = 20;
            break;

        case 1306:
            c.getPA().showInterface(3559);
            c.canChangeAppearance = true;
            break;

        case 904:
            c.sendMessage((new StringBuilder()).append("You have ").append(c.magePoints).append(" points.").toString());
            break;
        }
    }

    public void secondClickNpc(int i) {
        PetHandler.pickupPet(c, c.npcClickIndex);
        c.clickNpcType = 0;
        c.npcClickIndex = 0;
        switch (i) {

        case 315:
            if (c.isSkulled) {
                c.sendMessage("You are already skulled.");
                return;
            }
            c.isSkulled = true;
            c.skullTimer = Config.SKULL_TIMER;
            c.headIconPk = 0;
            c.getPA().requestUpdates();
            break;

        }
    }

    public void thirdClickNpc(int npcType) {
        c.clickNpcType = 0;
        c.npcClickIndex = 0;
        switch (npcType) {

        }
    }

}