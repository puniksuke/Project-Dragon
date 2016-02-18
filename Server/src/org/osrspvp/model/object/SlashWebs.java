package org.osrspvp.model.object;

import org.osrspvp.model.Animation;
import org.osrspvp.model.Client;
import org.osrspvp.util.Misc;
import org.osrspvp.util.WeaponAnimations;
import org.osrspvp.util.cache.region.Region;

public class SlashWebs {

    private static final boolean isWeb(int objectId) {
        switch (objectId) {
        case 733:
            return true;

        default:
            return false;
        }
    }

    private static boolean canSlashWeb(Client client) {
        String name = client.getItems().getItemName(client.getEquipment()[3]).toLowerCase();
        if (name.contains("scimitar") || name.contains("sword") || name.contains("abyssal") || name.contains("dagger")) {
            return true;
        }
        return false;
    }

    public static void slashWeb(Client client, int objectId, int obX, int obY) {
        if (System.currentTimeMillis() - client.buryDelay <= 1000) {
            return;
        }
        if (!isWeb(objectId)) {
            return;
        }
        client.turnPlayerTo(obX, obY);
        for (Objects o : Region.realObjects) {
            if (objectId == o.objectId && obX == o.objectX && obY == o.objectY) {
                client.playAnimation(Animation.create(WeaponAnimations.attackAnimation(client)));
                if (!canSlashWeb(client)) {
                    client.sendMessage("You fail to slash the web...");
                    client.sendMessage("...Perhaps you should try and use a melee weapon...");
                    client.buryDelay = System.currentTimeMillis();
                    return;
                }
                if (Misc.random(2) != 0) {
                    client.sendMessage("You fail to slash the web...");
                    client.buryDelay = System.currentTimeMillis();
                    return;
                }
                client.sendMessage("You manage to slash the web.");
                if (o.objectX == 3095 && o.objectY == 3957 || o.objectX == 3092 && o.objectY == 3957)
                    new Object(734, o.objectX, o.objectY, 0, o.objectFace, 0, client.objectId, 10);
                else
                    new Object(734, o.objectX, o.objectY, 0, o.objectFace, 10, client.objectId, 10);
                client.buryDelay = System.currentTimeMillis();
                return;
            }
        }
    }

}
