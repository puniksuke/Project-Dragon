package org.osrspvp.model.content;

import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.util.cache.defs.ItemDef;

import java.util.HashMap;

public class PotionHandler {

    private static enum PotionData {

        SUPER_STRENGTH(new int[] { 161, 159, 157, 2440 }, new int[] { 2 }, "super"), SUPER_ATTACK(
            new int[] { 149, 147, 145, 2436 }, new int[] { 0 }, "super"), SUPER_DEFENCE(new int[] { 167, 165, 163, 2442 },
            new int[] { 1 }, "super"), SUPER_RESTORE(new int[] { 3030, 3028, 3026, 3024 },
            new int[] { 0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 },
            "super_restore"), MAGIC_POTION(new int[] { 3046, 3044, 3042, 3040 }, new int[] { 6 },
            "raise_by_4"), RANGING_POTION(new int[] { 173, 171, 169, 2444 }, new int[] { 4 }, "ranging"), SUPER_COMBAT(
            new int[] { 12701, 12699, 12697, 12695 }, new int[] { 0, 1, 2 }, "super"), ANTI_VENOM(
            new int[] { 12911, 12909, 12907, 12905 }, new int[] {}, "venom"), PRAYER_POTION(
            new int[] { 143, 141, 139, 2434 }, new int[] { 5 }, "prayer"), SARADOMIN_BREW(
            new int[] { 6691, 6689, 6687, 6685 }, new int[] {}, "sara_brew"), ANTI_FIRE(new int[] { 2458, 2456, 2454, 2452 },
            new int[] {}, "antifire"), OVERLOAD(new int[] { 15335, 15334, 15333, 15332 }, new int[] { 0, 1, 2, 4, 6 },
            "overload"), ZAMORAK_BREW(new int[] { 193, 191, 189, 2450 }, new int[] {}, "zamorak");

        private PotionData(int[] potionIds, int[] skills, String potionType) {
            this.potionIds = potionIds;
            this.skills = skills;
            this.potionType = potionType;
        }

        private int[] potionIds;
        private int[] skills;
        private String potionType;

        private int[] getPotionIds() {
            return this.potionIds;
        }

        public int[] getSkills() {
            return this.skills;
        }

        private static HashMap<Integer, PotionData> potionMap = new HashMap<Integer, PotionData>();

        public static PotionData forId(int id) {
            return potionMap.get(id);
        }

        static {
            for (PotionData d : PotionData.values()) {
                for (int i = 0; i < d.getPotionIds().length; i++) {
                    potionMap.put(d.getPotionIds()[i], d);
                }
            }
        }

    }

    public static void drinkPotion(Client client, int itemId, int itemSlot) {
        PotionData p = PotionData.forId(itemId);
        if (p == null) {
            return;
        }
        if (System.currentTimeMillis() - client.potDelay <= 1800 || client.getLevel()[3] <= 0 || client.isDead) {
            return;
        }
        if (client.duelRule[5]) {
            client.sendMessage("You may not drink potions in this duel.");
            return;
        }
        client.playAnimation(Animation.create(829));
        ;
        client.getCombat().resetPlayerAttack();
        client.attackTimer += 1;
        client.getItems().deleteItem(itemId, itemSlot, 1);
        client.sendMessage("You drink some of your " + ItemDef.forId(itemId).name.toLowerCase().replaceAll(" potion", "")
            .replaceAll("(0)", "").replaceAll("(1)", "").replaceAll("(2)", "").replaceAll("(3)", "").replaceAll("(4)", "")
            .replace("()", "") + " potion.");
        switch (p.potionType) {

        case "venom":
            if (client.venomDamage > 0) {
                client.venomDamage = 0;
            }
            client.venomImmunity = System.currentTimeMillis();
            break;

        case "super":
            for (int i = 0; i < p.getSkills().length; i++) {
                int increase = (int) (5 + client.getLevelForXP(client.getExperience()[p.getSkills()[i]]) * .15);
                if (client.getLevel()[p.getSkills()[i]] <= client
                    .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                    client.getLevel()[p.getSkills()[i]] += increase;
                    if (client.getLevel()[p.getSkills()[i]] > client
                        .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase)
                        client.getLevel()[p.getSkills()[i]] = client
                            .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase;
                }
                client.getPA().refreshSkill(p.getSkills()[i]);
            }
            break;

        case "prayer":
            client.getLevel()[5] += (client.getLevelForXP(client.getExperience()[5]) * .33);
            if (client.getLevel()[5] > client.getLevelForXP(client.getExperience()[5]))
                client.getLevel()[5] = client.getLevelForXP(client.getExperience()[5]);
            client.getPA().refreshSkill(5);
            break;

        case "super_restore":
            for (int i = 0; i < p.getSkills().length; i++) {
                int increase = (int) (8 + client.getLevelForXP(client.getExperience()[p.getSkills()[i]]) * .25);
                if (client.getLevel()[p.getSkills()[i]] <= client.getLevelForXP(client.getExperience()[p.getSkills()[i]])) {
                    client.getLevel()[p.getSkills()[i]] += increase;
                    if (client.getLevel()[p.getSkills()[i]] >= client
                        .getLevelForXP(client.getExperience()[p.getSkills()[i]]))
                        client.getLevel()[p.getSkills()[i]] = client.getLevelForXP(client.getExperience()[p.getSkills()[i]]);
                }
                client.getPA().refreshSkill(p.getSkills()[i]);
            }
            break;

        case "raise_by_4":
            for (int i = 0; i < p.getSkills().length; i++) {
                int increase = 4;
                if (client.getLevel()[p.getSkills()[i]] <= client
                    .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                    client.getLevel()[p.getSkills()[i]] += increase;
                    if (client.getLevel()[p.getSkills()[i]] >= client
                        .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                        client.getLevel()[p.getSkills()[i]] = client
                            .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase;
                    }
                }
                client.getPA().refreshSkill(p.getSkills()[i]);
            }
            break;

        case "ranging":
            for (int i = 0; i < p.getSkills().length; i++) {
                int increase = (int) (4 + client.getLevelForXP(client.getExperience()[p.getSkills()[i]]) * .10);
                if (client.getLevel()[p.getSkills()[i]] <= client
                    .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                    client.getLevel()[p.getSkills()[i]] += increase;
                    if (client.getLevel()[p.getSkills()[i]] >= client
                        .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                        client.getLevel()[p.getSkills()[i]] = client
                            .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase;
                    }
                }
                client.getPA().refreshSkill(p.getSkills()[i]);
            }
            break;

        case "overload":
            for (int i = 0; i < p.getSkills().length; i++) {
                int increase = (int) (5 + client.getLevelForXP(client.getExperience()[p.getSkills()[i]]) * .27);
                if (client.getLevel()[p.getSkills()[i]] <= client
                    .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase) {
                    client.getLevel()[p.getSkills()[i]] += increase;
                    if (client.getLevel()[p.getSkills()[i]] > client
                        .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase)
                        client.getLevel()[p.getSkills()[i]] = client
                            .getLevelForXP(client.getExperience()[p.getSkills()[i]]) + increase;
                }
                client.getPA().refreshSkill(p.getSkills()[i]);
            }
            break;

        case "sara_brew":
            int[] toDecrease = { 0, 2, 4, 6 };

            for (int tD : toDecrease) {
                client.getLevel()[tD] -= getBrewStat(client, tD, .10);
                if (client.getLevel()[tD] < 0)
                    client.getLevel()[tD] = 1;
                client.getPA().refreshSkill(tD);
                client.getPA().setSkillLevel(tD, client.getLevel()[tD], client.getExperience()[tD]);
            }
            client.getLevel()[1] += getBrewStat(client, 1, .20);
            if (client.getLevel()[1] > (client.getLevelForXP(client.getExperience()[1]) * 1.2 + 1)) {
                client.getLevel()[1] = (int) (client.getLevelForXP(client.getExperience()[1]) * 1.2);
            }
            client.getPA().refreshSkill(1);

            client.getLevel()[3] += getBrewStat(client, 3, .15);
            if (client.getLevel()[3] > (client.getLevelForXP(client.getExperience()[3]) * 1.17 + 1)) {
                client.getLevel()[3] = (int) (client.getLevelForXP(client.getExperience()[3]) * 1.17);
            }
            client.getPA().refreshSkill(3);
            break;

        case "antifire":
            break;

        case "zamorak":
            client.dealDamage(10);
            client.handleHitMask(10);
            client.getPA().refreshSkill(3);
            client.hitUpdateRequired = true;
            client.updateRequired = true;
            break;
        }
        int currentPotionDose = 0;
        for (int i = 0; i < p.getPotionIds().length; i++) {
            if (itemId == p.getPotionIds()[i]) {
                currentPotionDose = i + 1;
                break;
            }
        }
        client.sendMessage(currentPotionDose > 1 ?
            ("You have " + (currentPotionDose - 1) + " dose" + (currentPotionDose > 2 ? "s" : "") + " of potion left.") :
            "You have finished your potion.");
        int newPotion = 229;
        if (currentPotionDose > 1) {
            newPotion = p.getPotionIds()[currentPotionDose - 2];
        }
        client.getItems().addItem(newPotion, itemSlot, 1);
        client.potDelay = System.currentTimeMillis();
    }

    private static int getBrewStat(Client client, int skill, double amount) {
        return (int) (client.getLevelForXP(client.getExperience()[skill]) * amount);
    }

}
