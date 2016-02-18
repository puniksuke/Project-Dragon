package org.osrspvp.mysql;

import com.rspserver.motivote.MotivoteHandler;
import com.rspserver.motivote.Vote;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.Player;
import org.osrspvp.model.player.PlayerHandler;

public class RewardHandler extends MotivoteHandler<Vote> {

    @Override
    public void onCompletion(Vote reward) {
        int itemID = -1;
        itemID = 6199;
        if (PlayerHandler.isPlayerOn(reward.username())) {
            Player p = PlayerHandler.getPlayer(reward.username());

            if (p != null && p.isActive == true) {
                synchronized (p) {
                    Client c = (Client) p;
                    if (c.getItems().addItem(itemID, 1)) {
                        c.sendMessage("You've received your vote reward! Congratulations!");
                        reward.complete();
                    } else {
                        c.sendMessage("Could not give you your reward item, try creating space.");
                    }
                }
            }
        }
    }
}