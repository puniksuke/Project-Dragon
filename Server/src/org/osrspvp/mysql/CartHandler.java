package org.osrspvp.mysql;

import org.osrspvp.model.Client;

import java.sql.ResultSet;

public class CartHandler implements Runnable {

    private Client client;

    public CartHandler(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            // host, user, pass, database
            Database data = new Database("91.223.82.39", "osrspvpc_shop", "4_hqt+^Tv@K5", "osrspvpc_shop");

            if (!data.init()) {
                System.err.println("[CartHandler] Error connecting to database!");
                return;
            }
            String name = client.playerName;
            ResultSet rs = data.executeQuery("SELECT * FROM payments WHERE player_name=" + name + " AND claimed=0");
            if (rs == null) {
                client.sendMessage("You have no items waiting for you.");
                return;
            }
            while (rs.next()) {
                CartProducts item = CartProducts.getProduct(rs.getInt("item_number"));

                // purchase validation
                if (item == null
                    // if naming is causing an issue, just remove the line
                    // directly below.
                    || !item.getItemName().equalsIgnoreCase(rs.getString("item_name")) || rs.getDouble("amount") != item
                    .getValue()) {
                    client.sendMessage("This purchase is invalid: " + rs.getString("item_name") + " (Id: " + rs
                        .getInt("item_number") + ")");
                    return;
                }
                if (client.getItems().freeSlots() < 1) {
                    client.sendMessage("You need atleast 1 available inventory slot.");
                    return;
                }
                switch (item.getProductId()) {
                case 1:
                    client.getItems().addItem(1464, 5); // OSRSPVP Points
                    break;
                }

                rs.updateInt("claimed", 1);
                rs.updateRow();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
