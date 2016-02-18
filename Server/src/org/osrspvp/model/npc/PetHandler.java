package org.osrspvp.model.npc;

import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;
import org.osrspvp.util.cache.defs.NPCDef;

import java.util.HashMap;

public class PetHandler {

    private static enum PetData {
        CHAOS_ELEMENTAL(11995, 2055);

        private PetData(int id, int id1) {
            this.itemId = id;
            this.npcId = id1;
        }

        private int itemId;
        private int npcId;

        public int getItemId() {
            return this.itemId;
        }

        public int getPetId() {
            return this.npcId;
        }

        private static HashMap<Integer, PetData> petMap = new HashMap<Integer, PetData>();

        public static PetData forId(int npcType) {
            return petMap.get(npcType);
        }

        static {
            for (PetData p : PetData.values()) {
                petMap.put(p.itemId, p);
            }
        }
    }

    public static void dropPet(Client client, int slot, int id) {
        if (client.getPetId() > 0) {
            client.sendMessage("Please pick up your other pet first.");
            return;
        }
        PetData pet = PetData.forId(id);
        if (pet == null) {
            return;
        }
        client.setPetId(id);
        client.getItems().deleteItem(id, slot, 1);
        Server.npcHandler.spawnPet(client, pet.getPetId(), client.absX - 2, client.absY, client.heightLevel);
        return;
    }

    public static void pickupPet(Client client, int npcIndex) {
        if (client.getPetId() > 0) {
            PetData pet = PetData.forId(client.getPetId());
            if (pet == null) {
                // client.sendMessage("This is not your pet.");
                return;
            }
            if (NPCHandler.npcs[npcIndex].followIndex != client.getId()) {
                client.sendMessage("This is not your pet.");
                return;
            }
            if (NPCHandler.npcs[npcIndex].npcType == pet.getPetId()) {
                client.getItems().addItem(pet.getItemId(), 1);
                client.setPetId(-1);
                NPCHandler.npcs[npcIndex].absX = 8888;
                NPCHandler.npcs[npcIndex].absY = 8888;
                NPCHandler.npcs[npcIndex].followIndex = -1;
                NPCHandler.npcs[npcIndex] = null;
            }
        }
    }

    public static void followPlayer(int i, int followId) {
        if (NPCHandler.npcs[i] == null) {
            return;
        }
        if (PlayerHandler.players[followId] == null) {
            NPCHandler.npcs[i].absX = 8888;
            NPCHandler.npcs[i].absY = 8888;
            NPCHandler.npcs[i].followIndex = -1;
            NPCHandler.npcs[i].updateRequired = true;
            return;
        }
        int playerX = PlayerHandler.players[followId].absX;
        int playerY = PlayerHandler.players[followId].absY;
        NPCHandler.npcs[i].randomWalk = false;
        NPCHandler.npcs[i].facePlayer(followId);
        int size = NPCDef.forId(NPCHandler.npcs[i].npcType).boundDim;
        if (!Server.npcHandler.goodDistance(NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), playerX, playerY, 15)) {
            NPCHandler.npcs[i].absX = playerX;
            NPCHandler.npcs[i].absY = playerY;
        }
        if (Server.npcHandler.goodDistance(NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), playerX, playerY, size)) {
            return;
        }
        if (NPCHandler.npcs[i].heightLevel == PlayerHandler.players[followId].heightLevel) {
            if (playerY < NPCHandler.npcs[i].absY) {
                NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
            } else if (playerY > NPCHandler.npcs[i].absY) {
                NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
            } else if (playerX < NPCHandler.npcs[i].absX) {
                NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
            } else if (playerX > NPCHandler.npcs[i].absX) {
                NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
            } else if (playerX == NPCHandler.npcs[i].absX || playerY == NPCHandler.npcs[i].absY) {
                int o = Misc.random(3);
                switch (o) {
                case 0:
                    NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                    NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY + 1);
                    break;

                case 1:
                    NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX);
                    NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY - 1);
                    break;

                case 2:
                    NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX + 1);
                    NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
                    break;

                case 3:
                    NPCHandler.npcs[i].moveX = Server.npcHandler.GetMove(NPCHandler.npcs[i].absX, playerX - 1);
                    NPCHandler.npcs[i].moveY = Server.npcHandler.GetMove(NPCHandler.npcs[i].absY, playerY);
                    break;
                }
            }
            NPCHandler.npcs[i].facePlayer(followId);
            NPCHandler.npcs[i].getNextNPCMovement(i);
            NPCHandler.npcs[i].facePlayer(followId);
            NPCHandler.npcs[i].updateRequired = true;
        }
    }

}
