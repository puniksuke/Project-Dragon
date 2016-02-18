package org.osrspvp.model;

public class AccountPinManager {

    public static boolean hasToTypePin(Client client) {
        return client.getAccountPin()[1] != null && client.getAccountPin()[0] == null;
    }

    public static void openPinInterface(Client client) {
        client.getDH().lineText2("Please enter your account pin to unlock your account.", "@blu@::pin @red@****");
        client.nextChat = 0;
    }

}
