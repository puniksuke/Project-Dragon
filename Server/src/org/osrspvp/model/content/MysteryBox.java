package org.osrspvp.model.content;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.ItemDef;

import java.util.Random;

public class MysteryBox {

    private static final int[] COMMON_TABLE = { 4151, 11840, 6737, 6731, 6733, 6735, 10605, 12419, 12420, 12421, 12457,
        12458, 12459, 12480, 12482, 12484, 12486, 12488, 12253, 12255, 12257, 12259, 12261, 12470, 12472, 12474, 12476,
        12478, 12506, 12508, 12510, 12512, 1187, 6585, 3481, 3483, 3485, 3486, 3488, 12389, 12391, 10370, 10386, 12603,
        12601, 11924, 11926, 12265, 12267, 12269, 12271, 12273, 12275 };

    private static final int[] UNCOMMON_TABLE = { 12873, 12875, 12877, 12879, 12881, 12883, 4151, 6737, 6731, 6733, 6735,
        13092, 11840, 12415, 12416, 3140, 12414, 11335, 12417, 6585, 12806, 12807, 11905, 12899 };

    private static final int[] RARE_TABLE = { 11804, 11832, 11834, 11836, 11785, 11802, 11826, 11828, 11830, 11791, 12904,
        12006, 13199, 12931, 13197, 12691, 12692, 11770, 11771, 11772, 11773, 15492, 12809, 11730, 1038, 1040, 1042, 1044,
        1046, 1048, 1053, 1055, 1057 };

    private static Random r = new Random();

    public static void openBox(Client client, int itemId, int itemSlot) {
        if (!client.getItems().playerHasItem(6199)) {
            return;
        }
        if (client.isBusy()) {
            client.sendMessage("You are way to busy to do this right now.");
            return;
        }
        if (client.getItems().freeSlots() < 2) {
            client.sendMessage("You need 2 available slots in your inventory to open this box.");
            return;
        }
        client.setBusyState(true);
        client.getItems().deleteItem(itemId, itemSlot, 1);
        client.sendMessage("You open the mystery box and...");
        CycleEventHandler.getSingleton().stopEvents(client);
        client.getPA().showInterface(24000);
        CycleEventHandler.getSingleton().addEvent(1, client, new CycleEvent() {
            int timer = 12;

            @Override
            public void execute(CycleEventContainer container) {
                if (client == null || client.getLevel()[3] <= 0 || client.isDead) {
                    container.stop();
                    return;
                }
                int chance = r.nextInt(100);
                int item = 0;
                if (chance == 0) {
                    item = RARE_TABLE[(int) (Math.random() * RARE_TABLE.length)];
                }
                if (chance > 40 && chance < 100) {
                    item = UNCOMMON_TABLE[(int) (Math.random() * UNCOMMON_TABLE.length)];
                }
                if (chance > 0 && chance <= 40) {
                    item = COMMON_TABLE[(int) (Math.random() * COMMON_TABLE.length)];
                }
                timer--;
                if (timer >= 2) {
                    client.getPA().sendFrame34a(10494, item, 0, 1);
                }
                if (timer == 2) {
                    client.sendMessage("...Find a @blu@" + ItemDef.forId(item).getName() + ".");
                    for (int lol : RARE_TABLE) {
                        if (item == lol) {
                            client.getPA().globalYell(
                                "@cr6@@blu@" + client.playerName + " @red@ just got a @blu@" + ItemDef.forId(item)
                                    .getName() + "@red@.");
                            client.getPA().globalYell("@cr6@@red@From the rare table of a @blu@Mystery Box@red@.");
                        }
                    }
                    client.getItems().addItem(item, 1);
                    client.getItems().addItem(995, 1 + Misc.random(499999));
                } else if (timer == 0) {
                    container.stop();
                }
            }

            @Override
            public void stop() {
                client.setBusyState(false);
                client.getPA().removeAllWindows();
            }

        }, 1);
    }

}
