package org.osrspvp.model.content;

import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.util.cache.defs.ItemDef;

import java.util.HashMap;

public class FoodHandler {

    private enum FoodTable {

        LOBSTER(379, 12), SWORDFISH(373, 14), MONKFISH(7946, 16), SHARK(385, 20), MANTARAY(391, 22), TUNA_POTATO(7060,
            22), DARK_CRAB(11936, 22);

        private FoodTable(int id, int health) {
            this.foodId = id;
            this.healingFactor = health;
        }

        private int foodId;
        private int healingFactor;

        public int getHealingFactor() {
            return this.healingFactor;
        }

        private static HashMap<Integer, FoodTable> foodMap = new HashMap<Integer, FoodTable>();

        public static FoodTable getFoodTable(int id) {
            return foodMap.get(id);
        }

        static {
            for (FoodTable f : FoodTable.values()) {
                foodMap.put(f.foodId, f);
            }
        }
    }

    public static void eatFood(Client client, int itemId, int itemSlot) {
        FoodTable food = FoodTable.getFoodTable(itemId);
        if (food == null) {
            return;
        }
        if (System.currentTimeMillis() - client.foodDelay <= 1200) {
            return;
        }
        if (client.getLevel()[3] <= 0 || client.isDead) {
            return;
        }
        if (client.duelRule[6]) {
            client.sendMessage("You may not eat in this duel.");
            return;
        }
        client.getCombat().resetPlayerAttack();
        client.playAnimation(Animation.create(829));
        client.attackTimer += 1;
        client.getItems().deleteItem(itemId, itemSlot, 1);
        client.sendMessage("You eat the " + ItemDef.forId(itemId).getName() + ".");
        if (client.getLevel()[3] < client.getPA().getLevelForXP(client.getExperience()[3])) {
            client.sendMessage("It heals some health.");
            client.getLevel()[3] += food.getHealingFactor();
            if (client.getLevel()[3] >= client.getPA().getLevelForXP(client.getExperience()[3])) {
                client.getLevel()[3] = client.getPA().getLevelForXP(client.getExperience()[3]);
            }
        }
        client.getPA().refreshSkill(3);
        client.foodDelay = System.currentTimeMillis();
    }

}
