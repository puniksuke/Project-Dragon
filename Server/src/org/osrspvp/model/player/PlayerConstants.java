package org.osrspvp.model.player;

import org.osrspvp.model.Client;

public class PlayerConstants {

    /**
     * Item slots
     */
    public static final int PLAYER_HAT = 0;
    public static final int PLAYER_CAPE = 1;
    public static final int PLAYER_AMULET = 2;
    public static final int PLAYER_WEAPON = 3;
    public static final int PLAYER_BODY = 4;
    public static final int PLAYER_SHIELD = 5;
    public static final int PLAYER_LEGS = 7;
    public static final int PLAYER_GLOVES = 9;
    public static final int PLAYER_FEET = 10;
    public static final int PLAYER_RING = 12;
    public static final int PLAYER_ARROWS = 13;

    /**
     * Skill level ids
     */
    public static final int PLAYER_ATTACK = 0;
    public static final int PLAYER_DEFENCE = 1;
    public static final int PLAYER_STRENGTH = 2;
    public static final int PLAYER_CONSTITUTION = 3;
    public static final int PLAYER_RANGED = 4;
    public static final int PLAYER_PRAYER = 5;
    public static final int PLAYER_MAGIC = 6;
    public static final int PLAYER_COOKING = 7;
    public static final int PLAYER_WOODCUTTING = 8;
    public static final int PLAYER_FLETCHING = 9;
    public static final int PLAYER_FISHING = 10;
    public static final int PLAYER_FIREMAKING = 11;
    public static final int PLAYER_CRAFTING = 12;
    public static final int PLAYER_SMITHING = 13;
    public static final int PLAYER_MINING = 14;
    public static final int PLAYER_HERBLORE = 15;
    public static final int PLAYER_AGILITY = 16;
    public static final int PLAYER_THIEVING = 17;
    public static final int PLAYER_SLAYER = 18;
    public static final int PLAYER_FARMING = 19;
    public static final int PLAYER_RUNECRAFTING = 20;

    public static int getCombatLevel(Client client) {
        int combatLevel = 0;
        double baseLevel = 0.25 * (client.getLevelForXP(client.getExperience()[1]) + client
            .getLevelForXP(client.getExperience()[3]) + Math.floor(client.getLevelForXP(client.getExperience()[5]) / 2));
        double meleeLevel = 0.325 * (client.getLevelForXP(client.getExperience()[0]) + client
            .getLevelForXP(client.getExperience()[2]));
        double rangedLevel = 0.325 * (Math.floor(client.getLevelForXP(client.getExperience()[4]) / 2) + client
            .getLevelForXP(client.getExperience()[4]));
        double magicLevel = 0.325 * (Math.floor(client.getLevelForXP(client.getExperience()[6]) / 2) + client
            .getLevelForXP(client.getExperience()[4]));
        double highestLevel = (double) multiMax(meleeLevel, rangedLevel, magicLevel);
        combatLevel = (int) Math.floor(baseLevel + highestLevel);
        return combatLevel;
    }

    private static Object multiMax(Object... values) {
        Object returnValue = null;
        for (Object value : values)
            returnValue = (returnValue != null) ? ((((value instanceof Integer) ? (Integer) value :
                (value instanceof Double) ? (Double) value : (Float) value) > ((returnValue instanceof Integer) ?
                (Integer) returnValue : (returnValue instanceof Double) ? (Double) returnValue : (Float) returnValue)) ?
                value : returnValue) : value;
        return returnValue;
    }

}
