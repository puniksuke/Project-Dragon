package org.osrspvp.net.packet.impl;

import org.osrspvp.model.AccountPinManager;
import org.osrspvp.model.Client;
import org.osrspvp.net.packet.PacketType;
import org.osrspvp.net.packet.SubPacketType;
import org.osrspvp.util.cache.defs.ItemDef;

/**
 * Wear Item
 **/
public class WearItem implements SubPacketType {

	@Override
	public void processSubPacket(Client client, int packetType, int packetSize) {
		client.wearId = client.getInStream().readUnsignedWord();
		client.wearSlot = client.getInStream().readUnsignedWordA();
		client.interfaceId = client.getInStream().readUnsignedWordA();
		if (client.wearId <= 0) {
			System.out.println("[WARNING]: " + client.playerName
					+ " is trying to send negative values.");
			return;
		}
		if (AccountPinManager.hasToTypePin(client)) {
			AccountPinManager.openPinInterface(client);
			return;
		}
		if (ItemDef.forId(client.wearId).getItemAction() == null)
			return;
		if (!ItemDef.forId(client.wearId).getItemAction()[1].equals("Wield")
				&& !ItemDef.forId(client.wearId).getItemAction()[1]
						.equals("Wear")
				&& !ItemDef.forId(client.wearId).getItemAction()[1]
						.equals("Ride")) {
			return;
		}
		if (!client.getItems().playerHasItem(client.wearId, 1)) {
			return;
		}
		if (client.getLevel()[3] <= 0 || client.isDead) {
			return;
		}
		if (client.playerIndex > 0 || client.npcIndex > 0) {
			if (client.wearId != 4153)
				client.getCombat().resetPlayerAttack();
		}
		client.getItems().wearItem(client.wearId, client.wearSlot);
	}
}
