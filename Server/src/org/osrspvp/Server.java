package org.osrspvp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.core.cycle.impl.FollowEvent;
import org.osrspvp.core.cycle.impl.GlobalHelpEvent;
import org.osrspvp.core.cycle.impl.InterfaceTextEvent;
import org.osrspvp.core.cycle.impl.RestoreStatsEvent;
import org.osrspvp.core.cycle.impl.SpecialRestoreEvent;
import org.osrspvp.model.npc.NPCDrops;
import org.osrspvp.model.npc.NPCHandler;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.mysql.RewardHandler;
import org.osrspvp.net.ConnectionHandler;
import org.osrspvp.net.ConnectionThrottleFilter;
import org.osrspvp.sanction.RankHandler;
import org.osrspvp.sanction.SanctionHandler;
import org.osrspvp.sanction.StarterHandler;
import org.osrspvp.util.cache.defs.ItemDef;
import org.osrspvp.util.cache.defs.NPCDef;
import org.osrspvp.util.cache.defs.ObjectDef;
import org.osrspvp.util.cache.region.Region;
import org.osrspvp.world.ClanManager;
import org.osrspvp.world.ItemHandler;
import org.osrspvp.world.ObjectHandler;
import org.osrspvp.world.ObjectManager;
import org.osrspvp.world.ShopHandler;

import com.rspserver.motivote.Motivote;

/**
 * Server.java
 *
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 */

public class Server {

	public static boolean UpdateServer = false;
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NPCHandler npcHandler = new NPCHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ClanManager clanManager = new ClanManager();
	private static Logger logger = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) throws Exception {
		logger.info("Initializing " + Config.SERVER_NAME + "...");

		ObjectDef.loadConfig();
		Region.load();
		ItemDef.unpackConfig();
		NPCDef.unpackConfig();
		NPCDrops.loadDrops();
		new Motivote(new RewardHandler(), "",
				"").start();
		SanctionHandler.loadSanctionList();
		RankHandler.loadRankList();
		StarterHandler.initialize();

		GameEngine.initialize();

		Server.setupLoginChannels();
		CycleEventHandler.getSingleton().addEvent(new InterfaceTextEvent(), 1);
		CycleEventHandler.getSingleton().addEvent(new RestoreStatsEvent(), 100);
		CycleEventHandler.getSingleton()
				.addEvent(new SpecialRestoreEvent(), 29);
		CycleEventHandler.getSingleton().addEvent(new FollowEvent(), 1);
		CycleEventHandler.getSingleton().addEvent(new GlobalHelpEvent(), 100);

		logger.info(Config.SERVER_NAME + " is now online!");
	}

	public static void setupLoginChannels() {
		IoAcceptor acceptor = new SocketAcceptor();
		ConnectionHandler connectionHandler = new ConnectionHandler();
		ConnectionThrottleFilter throttleFilter = new ConnectionThrottleFilter(
				Config.CONNECTION_DELAY);

		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay(false);
		sac.setReuseAddress(true);
		sac.setBacklog(100);

		sac.getFilterChain().addFirst("throttleFilter", throttleFilter);
		try {
			acceptor.bind(new InetSocketAddress(43594), connectionHandler, sac);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
