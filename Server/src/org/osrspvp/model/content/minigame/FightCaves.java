package org.osrspvp.model.content.minigame;

import org.osrspvp.Server;
import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.util.Misc;

import java.util.HashMap;

/**
 * @author Jack
 */

public class FightCaves {

    private static enum TzhaarData {

        WAVE_1(0, new int[] { 3116, 3116 }), WAVE_2(1, new int[] { 3116, 3116, 3119 }), WAVE_3(2,
            new int[] { 3116, 3119, 3119, 3121 }), WAVE_4(3, new int[] { 3119, 3121, 3121, 3123 }), WAVE_5(4,
            new int[] { 3121, 3123, 3123, 3125 }), WAVE_6(5, new int[] { 3127 });

        private TzhaarData(int wave, int[] npcs) {
            this.waveId = wave;
            this.NPCs = npcs;
        }

        private int waveId;
        private int[] NPCs;

        public int getWaveId() {
            return this.waveId;
        }

        public int[] getNPCs() {
            return this.NPCs;
        }

        public int getNPCs(int index) {
            return this.NPCs[index];
        }

        private static HashMap<Integer, TzhaarData> tzhaarMap = new HashMap<Integer, TzhaarData>();

        public static TzhaarData forId(int waveId) {
            return tzhaarMap.get(waveId);
        }

        static {
            for (TzhaarData tz : TzhaarData.values()) {
                tzhaarMap.put(tz.getWaveId(), tz);
            }
        }
    }

    public static void defeatGame(Client client) {
        client.getPA().movePlayer(2438, 5168, 0);

        client.getItems().addItem(6570, 1);

        client.getItems().addItem(6529, 16064);

        client.setTzhaarToKill(0);

        client.setTzhaarWaveId(0);

        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {

                client.getDH().sendDialogues(23, -1);

                container.stop();
            }
        }, 1);
    }

    public static void startWave(Client client) {
        TzhaarData tzhaar = TzhaarData.forId(client.getTzhaarWaveId());
        if (tzhaar == null) {
            client.sendMessage("Something went wrong with the minigame, report it on the forums.");
            return;
        }
        String msg = client.getTzhaarWaveId() == 0 ? "The wave will start shortly." : "The next wave will start shortly";
        client.sendMessage(msg);
        client.setTzhaarToKill(tzhaar.getNPCs().length);
        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {
                for (int i = 0; i < tzhaar.getNPCs().length; i++) {
                    int[][] coords = { { 2382, 5105 }, { 2379, 5072 }, { 2418, 5082 }, { 2399, 5087 } };
                    int index = Misc.random(coords.length - 1);
                    Server.npcHandler
                        .spawnNpc(client, tzhaar.getNPCs(i), coords[index][0], coords[index][1], client.heightLevel, 1,
                            getConstitution(tzhaar.getNPCs(i)), getMaxHit(tzhaar.getNPCs(i)), getAttack(tzhaar.getNPCs(i)),
                            getDefence(tzhaar.getNPCs(i)), true, false);
                    System.out.println(tzhaar
                        .getNPCs(i) + " spawned at " + coords[index][0] + "	" + coords[index][1] + ", to kill: " + client
                        .getTzhaarToKill());
                }
                container.stop();
            }

        }, 4);
    }

    /**
     * @param client
     */
    public static void enterCave(Client client) {
        client.getPA().movePlayer(2413, 5117, client.getId() * 4);
        client.setTzhaarWaveId(client.getTzhaarWaveId());
        startWave(client);
    }

    public static void resetFightCaves(Client client) {
        client.getPA().movePlayer(2438, 5168, 0);

        client.getItems().addItem(6529, client.getTzhaarWaveId() * 200);

        client.setTzhaarToKill(0);

        client.setTzhaarWaveId(0);

        CycleEventHandler.getSingleton().addEvent(new CycleEvent() {

            @Override
            public void execute(CycleEventContainer container) {

                client.getDH().sendDialogues(22, -1);

                container.stop();
            }
        }, 1);
    }

    public static boolean fightCaveMonsters(int i) {
        switch (NPCHandler.npcs[i].npcType) {
        case 3116:
        case 3119:
        case 3121:
        case 3123:
        case 3125:
        case 3127:
            return true;

        default:
            return false;
        }
    }

    private static int getConstitution(int npcType) {
        switch (npcType) {

        case 3127:
            return 255;

        case 3125:
            return 160;

        case 3123:
            return 80;

        case 3121:
            return 40;

        case 3119:
            return 20;

        case 3116:
            return 10;

        default:
            return 0;
        }
    }

    private static int getMaxHit(int npcType) {
        switch (npcType) {

        case 3127:
            return 97;

        case 3125:
            return 50;

        case 3123:
            return 25;

        case 3121:
            return 13;

        case 3119:
            return 7;

        case 3116:
            return 4;

        default:
            return 0;
        }
    }

    private static int getDefence(int npcType) {
        switch (npcType) {

        case 3127:
            return 250;

        case 3125:
            return 160;

        case 3123:
            return 80;

        case 3121:
            return 20;

        case 3119:
            return 10;

        case 3116:
            return 5;

        default:
            return 0;
        }
    }

    private static int getAttack(int npcType) {
        switch (npcType) {

        case 3127:
            return 500;

        case 3125:
            return 250;

        case 3123:
            return 140;

        case 3121:
            return 100;

        case 3119:
            return 10;

        case 3116:
            return 5;

        default:
            return 0;
        }
    }

}