package org.osrspvp.mysql;

import org.osrspvp.GameEngine;
import org.osrspvp.model.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class Store {
    private static final String SECRET = ""; // YOUR SECRET KEY!

    @SuppressWarnings("deprecation")
    public void claim(Client player) {
        boolean canClaim = true;
        if (!player.inSafeZone()) {
            player.sendMessage("Run to safety before doing this.");
            canClaim = false;
        }
        if (player.getItems().freeSlots() < 28) {
            player.sendMessage("You need at least 28 free inventory slots to claim purchased items.");
            canClaim = false;
        }
        if (player.getSqlTimer().elapsed() <= 30000) {
            player.sendMessage("You can only do this once every 30 seconds.");
            canClaim = false;
        }
        if (!canClaim) {
            return;
        }
        player.getSqlTimer().reset();

        GameEngine.execute(() -> {
            try {
                URL url = new URL("http://osrs-pvp.com/store/callback.php?secret=" + SECRET + "&username=" + URLEncoder
                    .encode(player.playerName.toLowerCase().replaceAll(" ", "_")));
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String string = null;
                loop:
                while ((string = reader.readLine()) != null) {
                    synchronized (player) {
                        switch (string.toUpperCase()) {
                        case "ERROR":
                            player.sendMessage("An error occured while claiming your items.");
                            break loop;
                        case "NO_RESULTS":
                            player.sendMessage("There were no results found while claiming items.");
                            break loop;
                        default:
                            String[] split = string.split(",");
                            int quantity = Integer.parseInt(split[1]);
                            switch (split[0]) {

                            case "$10_scroll":
                                player.getItems().addItem(2677, quantity);
                                break;

                            case "$20_scroll":
                                player.getItems().addItem(2678, quantity);
                                break;

                            case "$30_scroll":
                                player.getItems().addItem(2679, quantity);
                                break;

                            case "mb":
                                player.getItems().addItem(6199, quantity);
                                break;

                            case "abyssal_whip":
                                player.getItems().addItem(4151, quantity);
                                break;

                            case "tentacle":
                                player.getItems().addItem(12006, quantity);
                                break;

                            case "ddf":
                                player.getItems().addItem(12954, quantity);
                                break;

                            case "stofd":
                                player.getItems().addItem(11791, quantity);
                                break;

                            case "tstofd":
                                player.getItems().addItem(12904, quantity);
                                break;

                            case "acb":
                                player.getItems().addItem(11785, quantity);
                                break;

                            case "ags":
                                player.getItems().addItem(11802, quantity);
                                break;

                            case "serpentine":
                                player.getItems().addItem(12931, quantity);
                                break;

                            case "dfs":
                                player.getItems().addItem(11283, quantity);
                                break;

                            case "bandos":
                                int[] items = { 11832, 11834, 11836 };
                                for (int i = 0; i < items.length; i++) {
                                    player.getItems().addItem(items[i], quantity);
                                }
                                break;

                            case "phatset":
                                int[] phats = { 1038, 1040, 1042, 1044, 1046, 1048 };
                                for (int i = 0; i < quantity; i++) {
                                    player.getItems().addItem(phats[(int) (Math.random() * phats.length)], 1);
                                }
                                break;

                            case "hweenset":
                                int[] hHats = { 1053, 1055, 1057 };
                                for (int i = 0; i < quantity; i++) {
                                    player.getItems().addItem(hHats[(int) (Math.random() * hHats.length)], 1);
                                }
                                break;

                            case "blackhat":
                                player.getItems().addItem(11862, quantity);
                                break;
                                
                            case "lime":
                            	player.getItems().addItem(7901, quantity);
                            	break;
                                
                            }
                            continue loop;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                synchronized (player) {
                    player.sendMessage("Currently not available.");
                }
            }
        });
    }
}
