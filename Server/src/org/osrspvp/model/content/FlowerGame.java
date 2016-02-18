package org.osrspvp.model.content;

import org.osrspvp.Server;
import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.model.object.Object;

import java.util.HashMap;

public class FlowerGame {

    private static final int[] OBJECT_IDS = { 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988 };

    private enum FlowerTable {

        FLOWER_1(2980, 2460), FLOWER_2(2981, 2462), FLOWER_3(2982, 2464), FLOWER_4(2983, 2466), FLOWER_5(2984,
            2468), FLOWER_6(2985, 2470), FLOWER_7(2986, 2472), FLOWER_8(2987, 2474), FLOWER_9(2988, 2476);

        private FlowerTable(int id, int iId) {
            this.objectId = id;
            this.itemId = iId;
        }

        private int objectId;
        private int itemId;

        public int getFlowerId() {
            return this.itemId;
        }

        private static HashMap<Integer, FlowerTable> flowerMap = new HashMap<Integer, FlowerTable>();

        public static FlowerTable forId(int id) {
            return flowerMap.get(id);
        }

        static {
            for (FlowerTable f : FlowerTable.values()) {
                flowerMap.put(f.objectId, f);
            }
        }
    }

    public static void pickupFlower(Client client, int objectId) {
        FlowerTable flower = FlowerTable.forId(objectId);
        if (flower == null) {
            return;
        }
        client.playAnimation(Animation.create(827));
        client.turnPlayerTo(client.flowerX, client.flowerY);
        client.getItems().addItem(flower.getFlowerId(), 1);
        Server.objectManager.removeObject(client.flowerX, client.flowerY);
        client.lastFlowerPlanted = -1;
        client.flowerX = -1;
        client.flowerY = -1;
    }

    public static void planFlower(Client client) {
        if (client.isBusy()) {
            return;
        }
        if (!client.inGambleZone()) {
            client.sendMessage("You can only do this at ::gamble");
            return;
        }
        if (Server.objectManager.objectExist(client.getX(), client.getY())) {
            client.sendMessage("You can't plan your seed here.");
            return;
        }
        client.setBusyState(true);
        client.playAnimation(Animation.create(827));
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                int objectId = OBJECT_IDS[(int) (Math.random() * OBJECT_IDS.length)];
                client.lastFlowerPlanted = objectId;
                client.flowerX = client.getX();
                client.flowerY = client.getY();
                client.getPA().clippedWalkTo();
                client.getItems().deleteItem(299, client.getItems().getItemSlot(299), 1);
                new Object(objectId, client.getX(), client.getY(), client.heightLevel, 1, 10, -1, 60);
                client.getDH().sendOption2("Pick up flowers", "Leave flowers");
                client.dialogueAction = 22;
                client.setBusyState(false);
                container.stop();
            }

        }, 1);
    }

}
