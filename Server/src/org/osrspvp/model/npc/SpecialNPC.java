package org.osrspvp.model.npc;

import org.osrspvp.Server;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.impl.Callisto;
import org.osrspvp.model.npc.impl.ChaoticFanatic;
import org.osrspvp.model.npc.impl.CorporealBeast;
import org.osrspvp.model.npc.impl.FlockleaderGerin;
import org.osrspvp.model.npc.impl.GeneralGraardor;
import org.osrspvp.model.npc.impl.KetZek;
import org.osrspvp.model.npc.impl.KreeArra;
import org.osrspvp.model.npc.impl.SergeantGrimspike;
import org.osrspvp.model.npc.impl.SergeantSteelwill;
import org.osrspvp.model.npc.impl.TokXil;
import org.osrspvp.model.npc.impl.TzTokJad;
import org.osrspvp.model.npc.impl.Vetion;
import org.osrspvp.model.npc.impl.WingmanSkree;

import java.util.HashMap;

public abstract class SpecialNPC {

    public abstract void execute(Client client, NPC n);

    private static HashMap<Integer, SpecialNPC> npcMap = new HashMap<Integer, SpecialNPC>();

    static {
        npcMap.put(6611, new Vetion());
        npcMap.put(6619, new ChaoticFanatic());
        npcMap.put(6609, new Callisto());
        npcMap.put(319, new CorporealBeast());
        npcMap.put(3162, new KreeArra());
        npcMap.put(3164, new FlockleaderGerin());
        npcMap.put(3163, new WingmanSkree());
        npcMap.put(2215, new GeneralGraardor());
        npcMap.put(2218, new SergeantGrimspike());
        npcMap.put(2217, new SergeantSteelwill());
        npcMap.put(3125, new KetZek());
        npcMap.put(3121, new TokXil());
        npcMap.put(3127, new TzTokJad());
    }

    public static SpecialNPC forId(int npcType) {
        return npcMap.get(npcType);
    }

    public static void executeAttack(Client client, int i) {
        SpecialNPC n = SpecialNPC.forId(NPCHandler.npcs[i].npcType);
        if (n == null || client == null) {
            return;
        }
        if (client.isDead || client.getLevel()[3] <= 0 || NPCHandler.npcs[i].isDead || NPCHandler.npcs[i].HP <= 0) {
            return;
        }
        if (client.distanceToPoint(NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY()) > Server.npcHandler
            .followDistance(i)) {
            return;
        }
        if (client.playerIndex <= 0 && client.npcIndex <= 0)
            if (client.autoRet == 1)
                client.npcIndex = i;
        client.getPA().removeAllWindows();
        NPCHandler.npcs[i].facePlayer(client.playerId);
        client.logoutDelay = System.currentTimeMillis();
        client.underAttackBy2 = NPCHandler.npcs[i].npcId;
        client.singleCombatDelay = System.currentTimeMillis();
        NPCHandler.npcs[i].killerId = client.playerId;
        n.execute(client, NPCHandler.npcs[i]);
        client.getPA().removeAllWindows();
    }
}
