package org.osrspvp.net.packet;

import org.osrspvp.model.Client;
import org.osrspvp.net.packet.impl.ActionButtonPacket;
import org.osrspvp.net.packet.impl.AttackPlayer;
import org.osrspvp.net.packet.impl.Bank10;
import org.osrspvp.net.packet.impl.Bank5;
import org.osrspvp.net.packet.impl.BankAll;
import org.osrspvp.net.packet.impl.BankX;
import org.osrspvp.net.packet.impl.ChangeAppearance;
import org.osrspvp.net.packet.impl.ChangeRegions;
import org.osrspvp.net.packet.impl.Chat;
import org.osrspvp.net.packet.impl.ClickItem;
import org.osrspvp.net.packet.impl.ClickNPC;
import org.osrspvp.net.packet.impl.ClickObject;
import org.osrspvp.net.packet.impl.ClickingInGame;
import org.osrspvp.net.packet.impl.ClickingStuff;
import org.osrspvp.net.packet.impl.CustomCommandPacket;
import org.osrspvp.net.packet.impl.Dialogue;
import org.osrspvp.net.packet.impl.DropItem;
import org.osrspvp.net.packet.impl.FollowPlayer;
import org.osrspvp.net.packet.impl.IdleLogout;
import org.osrspvp.net.packet.impl.InterfaceAction;
import org.osrspvp.net.packet.impl.ItemClick2;
import org.osrspvp.net.packet.impl.ItemClick3;
import org.osrspvp.net.packet.impl.ItemOnGroundItem;
import org.osrspvp.net.packet.impl.ItemOnItem;
import org.osrspvp.net.packet.impl.ItemOnNpc;
import org.osrspvp.net.packet.impl.ItemOnObject;
import org.osrspvp.net.packet.impl.JoinChat;
import org.osrspvp.net.packet.impl.MagicOnFloorItems;
import org.osrspvp.net.packet.impl.MagicOnItems;
import org.osrspvp.net.packet.impl.MoveItems;
import org.osrspvp.net.packet.impl.PickupItem;
import org.osrspvp.net.packet.impl.PrivateMessaging;
import org.osrspvp.net.packet.impl.ReceiveString;
import org.osrspvp.net.packet.impl.RemoveItem;
import org.osrspvp.net.packet.impl.SilentPacket;
import org.osrspvp.net.packet.impl.TradeAction;
import org.osrspvp.net.packet.impl.Walking;
import org.osrspvp.net.packet.impl.WearItem;

public class PacketHandler {

	private static PacketType packetId[] = new PacketType[256];

	private static SubPacketType subPacketId[] = new SubPacketType[256];

	static {
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[16] = new ItemClick2();
		packetId[75] = new ItemClick3();
		packetId[122] = new ClickItem();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		subPacketId[185] = new ActionButtonPacket();
		packetId[130] = new ClickingStuff();
		packetId[103] = new CustomCommandPacket();
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[249] = ap;
		packetId[128] = ap;
		packetId[73] = new TradeAction();
		packetId[139] = new TradeAction();
		packetId[153] = new FollowPlayer();
		subPacketId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[59] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		BankX bx = new BankX();
		packetId[135] = bx;
		packetId[208] = bx;
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packetId[121] = cr;
		packetId[210] = cr;
		packetId[60] = new JoinChat();
		packetId[127] = new ReceiveString();
		packetId[213] = new InterfaceAction();
	}

	public static void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == -1) { // this
			return;
		}
		PacketType p = packetId[packetType];
		if (p != null) {
			try {
				p.processPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			c.disconnected = true;
			System.out.println(c.playerName + "is sending invalid PacketType: "
					+ packetType + ". PacketSize: " + packetSize);
		}
	}

	public static void processSubPacket(Client c, int packetType, int packetSize) {
		if (packetType == -1) {
			return;
		}
		SubPacketType p = subPacketId[packetType];
		if (p != null) {
			try {
				p.processSubPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			c.disconnected = true;
			System.out.println(c.playerName + "is sending invalid PacketType: "
					+ packetType + ". PacketSize: " + packetSize);
		}
	}
}
