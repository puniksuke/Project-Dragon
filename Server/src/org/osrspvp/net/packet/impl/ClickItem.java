package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.content.FlowerGame;
import org.osrspvp.model.content.FoodHandler;
import org.osrspvp.model.content.MysteryBox;
import org.osrspvp.model.content.PotionHandler;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.util.cache.defs.ItemDef;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.getInStream().readSignedWordBigEndianA();
        int itemSlot = c.getInStream().readUnsignedWordA();
        int itemId = c.getInStream().readUnsignedWordBigEndian();
        if (itemId != c.playerItems[itemSlot] - 1) {
            return;
        }
        if (!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
        if (itemId == 8013) {
            c.getItems().deleteItem(itemId, itemSlot, 1);
            c.getPA().teletab(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
        }
        if (itemId == 2677) {
            c.getItems().deleteItem(itemId, itemSlot, 1);
	    c.donatorPoints += 10;
	    c.funds += 10;
c.sendMessage("You have now @blu@" + c.donatorPoints + "@bla@ donator Points.");
        }
        if (itemId == 2678) {
            c.getItems().deleteItem(itemId, itemSlot, 1);
            c.donatorPoints += 20;
	    c.funds += 20;
c.sendMessage("You have now @blu@" + c.donatorPoints + "@bla@ donator Points.");
        }
        if (itemId == 2679) {
            c.getItems().deleteItem(itemId, itemSlot, 1);
            c.donatorPoints += 50;
	    c.funds += 50;
c.sendMessage("You have now @blu@" + c.donatorPoints + "@bla@ donator Points.");
        }
	if (itemId == 2680) {
            c.getItems().deleteItem(itemId, itemSlot, 1);
            c.donatorPoints += 100;
	    c.funds += 100;
c.sendMessage("You have now @blu@" + c.donatorPoints + "@bla@ donator Points.");
c.sendMessage("You have now total claimed @blu@" + c.funds + "@bla@$.");
        }
        if (itemId == 3144) {
            if (c.duelRule[6]) {
                c.sendMessage("You may not eat in this duel.");
                return;
            }
            c.sendMessage("You eat the karambwan.");
            c.getCombat().resetPlayerAttack();
            c.playAnimation(Animation.create(829));
            c.attackTimer += 1;
            c.getItems().deleteItem(itemId, itemSlot, 1);
            if (c.getLevel()[3] < c.getPA().getLevelForXP(c.getExperience()[3])) {
                c.getLevel()[3] += 18;
                c.sendMessage("It heals some health.");
                if (c.getLevel()[3] > c.getPA().getLevelForXP(c.getExperience()[3])) {
                    c.getLevel()[3] = c.getPA().getLevelForXP(c.getExperience()[3]);
                }
            }
            c.getPA().refreshSkill(3);
        }
        if (itemId == 299) {
            FlowerGame.planFlower(c);
            return;
        }
        if (itemId == 6199) {
            MysteryBox.openBox(c, itemId, itemSlot);
        }
        if (itemId == 4067) {
            c.sendMessage("You claimed @blu@" + c.getItems().getItemAmount(4067) + "@bla@ PVP Points.");
            c.pkPoints += c.getItems().getItemAmount(4067);
            c.getItems().deleteItem(4067, itemSlot, c.getItems().getItemAmount(4067));
        }
        if (ItemDef.forId(itemId).getItemAction()[0].equals("Drink")) {
            PotionHandler.drinkPotion(c, itemId, itemSlot);
        }
        if (ItemDef.forId(itemId).getItemAction()[0].equals("Eat")) {
            FoodHandler.eatFood(c, itemId, itemSlot);
        }
        if (itemId == 952) {
            if (c.inArea(3553, 3301, 3561, 3294)) {
                c.teleTimer = 3;
                c.newLocation = 1;
            } else if (c.inArea(3550, 3287, 3557, 3278)) {
                c.teleTimer = 3;
                c.newLocation = 2;
            } else if (c.inArea(3561, 3292, 3568, 3285)) {
                c.teleTimer = 3;
                c.newLocation = 3;
            } else if (c.inArea(3570, 3302, 3579, 3293)) {
                c.teleTimer = 3;
                c.newLocation = 4;
            } else if (c.inArea(3571, 3285, 3582, 3278)) {
                c.teleTimer = 3;
                c.newLocation = 5;
            } else if (c.inArea(3562, 3279, 3569, 3273)) {
                c.teleTimer = 3;
                c.newLocation = 6;
            }
        }
    }

}
