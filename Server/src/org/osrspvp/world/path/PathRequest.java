package org.osrspvp.world.path;

import org.osrspvp.GameEngine;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;

import java.util.ArrayDeque;
import java.util.Queue;

public final class PathRequest {

    private static Queue<PathRequestWorker> requestWorkers = new ArrayDeque<>();

    public static void addRequest(Client c, Client a) {
        requestWorkers.add(new PathRequestWorker(c, a));
    }

    public static void addRequest(Client c, NPC a) {
        requestWorkers.add(new PathRequestWorker(c, a));
    }

    public static void pollPendingRequests() {
        for (; ; ) {
            PathRequestWorker requestWorker = requestWorkers.poll();
            if (requestWorker == null) {
                break;
            }
            GameEngine.execute(requestWorker);
        }
    }
}
