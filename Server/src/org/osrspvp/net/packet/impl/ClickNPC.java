package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.net.packet.PacketType;

/**
 * Click NPC
 */
public class ClickNPC implements PacketType {
    public static final int ATTACK_NPC = 72, MAGE_NPC = 131, FIRST_CLICK = 155, SECOND_CLICK = 17, THIRD_CLICK = 21;

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        if (AccountPinManager.hasToTypePin(c)) {
            AccountPinManager.openPinInterface(c);
            return;
        }
        c.npcIndex = 0;
        c.npcClickIndex = 0;
        c.playerIndex = 0;
        c.clickNpcType = 0;
        c.getPA().resetFollow();
        switch (packetType) {

        /**
         * Attack npc melee or range
         **/
        case ATTACK_NPC:
            if (!c.mageAllowed) {
                c.mageAllowed = true;
                c.sendMessage("I can't reach that.");
                break;
            }
            c.npcIndex = c.getInStream().readUnsignedWordA();
            if (NPCHandler.npcs[c.npcIndex] == null) {
                c.npcIndex = 0;
                break;
            }
            if (NPCHandler.npcs[c.npcIndex].MaxHP == 0) {
                c.npcIndex = 0;
                break;
            }
            if (NPCHandler.npcs[c.npcIndex] == null) {
                break;
            }
            if (c.autocastId > 0)
                c.autocasting = true;
            if (!c.autocasting && c.spellId > 0) {
                c.spellId = 0;
            }
            c.faceUpdate(c.npcIndex);
            c.usingMagic = false;
            c.usingBow = false;
            c.usingRangeWeapon = false;
            boolean usingArrows = false;
            if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] >= 4214 && c
                .getEquipment()[PlayerConstants.PLAYER_WEAPON] <= 4223)
                c.usingBow = true;
            for (int bowId : c.BOWS) {
                if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == bowId) {
                    c.usingBow = true;
                    for (int arrowId : c.ARROWS) {
                        if (c.getEquipment()[PlayerConstants.PLAYER_ARROWS] == arrowId) {
                            usingArrows = true;
                        }
                    }
                }
            }
            for (int otherRangeId : c.OTHER_RANGE_WEAPONS) {
                if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == otherRangeId) {
                    c.usingRangeWeapon = true;
                }
            }
            if ((c.usingBow || c.autocasting) && c
                .goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(), NPCHandler.npcs[c.npcIndex].getY(),
                    7)) {
                c.stopMovement();
            }

            if (c.usingRangeWeapon && c
                .goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(), NPCHandler.npcs[c.npcIndex].getY(),
                    4)) {
                c.stopMovement();
            }
            if (!c.getCombat().usingCorrectArrows() && Config.CORRECT_ARROWS && c.usingBow && !c.getCombat()
                .usingCrystalBow()) {
                c.sendMessage("You can't use " + c.getItems().getItemName(c.getEquipment()[PlayerConstants.PLAYER_ARROWS])
                    .toLowerCase() + "s with a " + c.getItems().getItemName(c.getEquipment()[PlayerConstants.PLAYER_WEAPON])
                    .toLowerCase() + ".");
                c.stopMovement();
                c.getCombat().resetPlayerAttack();
                return;
            }
            if (c.getEquipment()[PlayerConstants.PLAYER_WEAPON] == 9185 && !c.getCombat().properBolts()) {
                c.sendMessage("You must use bolts with a crossbow.");
                c.stopMovement();
                c.getCombat().resetPlayerAttack();
                return;
            }

            if (c.followId > 0) {
                c.getPA().resetFollow();
            }
            if (c.attackTimer <= 0) {
                c.getCombat().attackNpc(c.npcIndex);
                c.attackTimer++;
            }

            break;

        /**
         * Attack npc with magic
         **/
        case MAGE_NPC:
            if (!c.mageAllowed) {
                c.mageAllowed = true;
                c.sendMessage("I can't reach that.");
                break;
            }
            // c.usingSpecial = false;
            // c.getItems().updateSpecialBar();

            c.npcIndex = c.getInStream().readSignedWordBigEndianA();
            int castingSpellId = c.getInStream().readSignedWordA();
            c.usingMagic = false;

            if (NPCHandler.npcs[c.npcIndex] == null) {
                break;
            }

            if (NPCHandler.npcs[c.npcIndex].MaxHP == 0 || NPCHandler.npcs[c.npcIndex].npcType == 944) {
                c.sendMessage("You can't attack this npc.");
                break;
            }

            for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
                if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
                    c.spellId = i;
                    c.usingMagic = true;
                    break;
                }
            }
            if (castingSpellId == 1171) { // crumble undead
                for (int npc : Config.UNDEAD_NPCS) {
                    if (NPCHandler.npcs[c.npcIndex].npcType != npc) {
                        c.sendMessage("You can only attack undead monsters with this spell.");
                        c.usingMagic = false;
                        c.stopMovement();
                        break;
                    }
                }
            }
            /*
             * if(!c.getCombat().checkMagicReqs(c.spellId)) { c.stopMovement();
			 * break; }
			 */

            if (c.autocasting)
                c.autocasting = false;

            if (c.usingMagic) {
                if (c
                    .goodDistance(c.getX(), c.getY(), NPCHandler.npcs[c.npcIndex].getX(), NPCHandler.npcs[c.npcIndex].getY(),
                        6)) {
                    c.stopMovement();
                }
                if (c.attackTimer <= 0) {
                    c.getCombat().attackNpc(c.npcIndex);
                    c.attackTimer++;
                }
            }

            break;

        case FIRST_CLICK:
            c.npcClickIndex = c.inStream.readSignedWordBigEndian();
            c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
            if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(), c.getX(),
                c.getY(), 1)) {
                c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
                NPCHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
                c.getActions().firstClickNpc(c.npcType);
            } else {
                c.clickNpcType = 1;
            }
            break;

        case SECOND_CLICK:
            c.npcClickIndex = c.inStream.readUnsignedWordBigEndianA();
            if (c.npcClickIndex < 0)
                return;
            c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
            if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(), c.getX(),
                c.getY(), 1)) {
                c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
                NPCHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
                c.getActions().secondClickNpc(c.npcType);
            } else {
                c.clickNpcType = 2;
            }
            break;

        case THIRD_CLICK:
            c.npcClickIndex = c.inStream.readSignedWord();
            if (c.npcClickIndex < 0)
                return;
            c.npcType = NPCHandler.npcs[c.npcClickIndex].npcType;
            if (c.goodDistance(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY(), c.getX(),
                c.getY(), 1)) {
                c.turnPlayerTo(NPCHandler.npcs[c.npcClickIndex].getX(), NPCHandler.npcs[c.npcClickIndex].getY());
                NPCHandler.npcs[c.npcClickIndex].facePlayer(c.playerId);
                c.getActions().thirdClickNpc(c.npcType);
            } else {
                c.clickNpcType = 3;
            }
            break;
        }

    }
}
