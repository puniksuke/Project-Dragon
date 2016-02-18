package org.osrspvp.mysql;

public enum CartProducts {

    POINTS(1, "5 x OSRS Points", 1);

    private int prodId;
    private String itemName;
    private double value;

    CartProducts(int prodId, String itemName, double value) {
        this.prodId = prodId;
        this.itemName = itemName;
        this.value = value;
    }

    public int getProductId() {
        return prodId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getValue() {
        return value;
    }

    public static CartProducts getProduct(int id) {
        for (CartProducts c : CartProducts.values()) {
            if (c.getProductId() == id) {
                return c;
            }
        }
        return null;
    }
}
