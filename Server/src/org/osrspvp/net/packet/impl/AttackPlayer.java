package org.osrspvp.net.packet.impl;

import org.osrspvp.Config;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerConstants;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.net.packet.PacketType;

/**
 * Attack Player
 **/
public class AttackPlayer implements PacketType {

    public static final int ATTACK_PLAYER = 128, MAGE_PLAYER = 249;

    @Override
    public void processPacket(Client c, int packetType, int packetSize) {
        c.playerIndex = 0;
        c.npcIndex = 0;
        switch (packetType) {

        /**
         * Attack player
         **/
        case ATTACK_PLAYER:
            c.playerIndex = c.getInStream().readSignedWord();
            if (c.playerIndex < 0)
                break;
            if (PlayerHandler.players[c.playerIndex] == null) {
                break;
            }

            if (c.respawnTimer > 0 || c.getLevel()[3] <= 0 || c.isDead) {
                break;
            }

            if (c.inDuelArena() && c.duelStatus != 5) {
                if (c.arenas()) {
                    c.sendMessage("You can't challenge inside the arena.");
                    return;
                }
                c.getDuel().requestDuel(c.playerIndex);
                c.playerIndex = 0;
                return;
            }

            if (c.duelStatus == 5 && c.duelCount > 0) {
                c.sendMessage("The duel hasn't started yet!");
                c.playerIndex = 0;
                return;
            }

            if (c.autocastId > 0)
                c.autocasting = true;

            if (!c.autocasting && c.spellId > 0) {
                c.spellId = 0;
            }
            c.mageFollow = false;
            c.spellId = 0;
            c.usingMagic = false;
            c.usingBow = false;
            c.usingRangeWeapon = false;
            boolean usingArrows = false;
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
                .goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(),
                    PlayerHandler.players[c.playerIndex].getY(), 6)) {
                c.stopMovement();
            }

            if (c.usingRangeWeapon && c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(),
                PlayerHandler.players[c.playerIndex].getY(), 3)) {
                c.usingRangeWeapon = true;
                c.stopMovement();
            }
            if (!c.usingRangeWeapon)
                c.usingRangeWeapon = false;
            if (!c.getCombat().usingCorrectArrows() && Config.CORRECT_ARROWS && c.usingBow && !c.getCombat()
                .usingCrystalBow() && !c.usingMagic) {
                c.sendMessage("You can't use " + c.getItems().getItemName(c.getEquipment()[PlayerConstants.PLAYER_ARROWS])
                    .toLowerCase() + "s with a " + c.getItems().getItemName(c.getEquipment()[PlayerConstants.PLAYER_WEAPON])
                    .toLowerCase() + ".");
                c.stopMovement();
                c.getCombat().resetPlayerAttack();
                return;
            }
            if (c.getCombat().checkReqs()) {
                c.followId = c.playerIndex;
                if (!c.usingMagic && !c.usingBow && !c.usingRangeWeapon) {
                    c.followDistance = 1;
                    c.getPA().followPlayer();
                }
                if (c.attackTimer <= 0) {
                    // c.sendMessage("Tried to attack...");
                    // c.getCombat().attackPlayer(c.playerIndex);
                    // c.attackTimer++;
                }
            }
            break;

        /**
         * Attack player with magic
         **/
        case MAGE_PLAYER:
            if (!c.mageAllowed) {
                c.mageAllowed = true;
                break;
            }
            // c.usingSpecial = false;
            // c.getItems().updateSpecialBar();

            c.playerIndex = c.getInStream().readSignedWordA();
            int castingSpellId = c.getInStream().readSignedWordBigEndian();
            c.usingMagic = false;
            if (PlayerHandler.players[c.playerIndex] == null) {
                break;
            }

            if (c.respawnTimer > 0) {
                break;
            }

            if (c.duelStatus == 5 && c.duelCount > 0) {
                c.sendMessage("The duel hasn't started yet!");
                c.playerIndex = 0;
                return;
            }

            for (int i = 0; i < c.MAGIC_SPELLS.length; i++) {
                if (castingSpellId == c.MAGIC_SPELLS[i][0]) {
                    if (c.MAGIC_SPELLS[i][16] != c.playerMagicBook) {
                        c.getCombat().resetPlayerAttack();
                        return;
                    }
                    c.spellId = i;
                    c.usingMagic = true;
                    break;
                }
            }

            if (c.autocasting) {
                c.getPA().resetAutocast();
            }

            if (!c.getCombat().checkReqs()) {
                break;
            }

            for (int r = 0; r < c.REDUCE_SPELLS.length; r++) { // reducing
                // spells,
                // confuse etc
                if (PlayerHandler.players[c.playerIndex].REDUCE_SPELLS[r] == c.MAGIC_SPELLS[c.spellId][0]) {
                    if ((System
                        .currentTimeMillis() - PlayerHandler.players[c.playerIndex].reduceSpellDelay[r]) < PlayerHandler.players[c.playerIndex].REDUCE_SPELL_TIME[r]) {
                        c.sendMessage("That player is currently immune to this spell.");
                        c.usingMagic = false;
                        c.stopMovement();
                        c.getCombat().resetPlayerAttack();
                    }
                    break;
                }
            }

            if (System
                .currentTimeMillis() - PlayerHandler.players[c.playerIndex].teleBlockDelay < PlayerHandler.players[c.playerIndex].teleBlockLength && c.MAGIC_SPELLS[c.spellId][0] == 12445) {
                c.sendMessage("That player is already affected by this spell.");
                c.usingMagic = false;
                c.stopMovement();
                c.getCombat().resetPlayerAttack();
            }

			/*
             * if(!c.getCombat().checkMagicReqs(c.spellId)) { c.stopMovement();
			 * c.getCombat().resetPlayerAttack(); break; }
			 */

            if (c.usingMagic) {
                if (c.goodDistance(c.getX(), c.getY(), PlayerHandler.players[c.playerIndex].getX(),
                    PlayerHandler.players[c.playerIndex].getY(), 7)) {
                    c.stopMovement();
                }
                if (c.getCombat().checkReqs()) {
                    c.followId = c.playerIndex;
                    c.mageFollow = true;
                    if (c.attackTimer <= 0) {
                        // c.getCombat().attackPlayer(c.playerIndex);
                        // c.attackTimer++;
                    }
                }
            }
            break;

        }

    }

}
