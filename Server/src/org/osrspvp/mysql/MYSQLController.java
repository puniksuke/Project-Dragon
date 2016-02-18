package org.osrspvp.mysql;

public class MYSQLController {

    public MYSQLController() {
    }

    private static MYSQLController CONTROLLER;

    private static Store STORE = new Store();

    public static MYSQLController getController() {
        return CONTROLLER;
    }

    public static Store getStore() {
        return STORE;
    }

}
