package org.osrspvp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.osrspvp.core.cycle.CycleEventHandler;
import org.osrspvp.model.player.Player;
import org.osrspvp.model.player.PlayerHandler;

/**
 * @author lare96 <http://github.org/lare96>
 */
public final class GameEngine implements Runnable {

	private static final AtomicBoolean LOCK = new AtomicBoolean();
	private static final ScheduledExecutorService GAME_SERVICE = Executors
			.newSingleThreadScheduledExecutor();
	private static final ExecutorService EXECUTOR_SERVICE = Executors
			.newCachedThreadPool(); // try now

	private static State currentState = State.GAME;

	private static final GameEngine engine = new GameEngine();

	public static GameEngine getEngine() {
		return engine;
	}

	public static void initialize() {
		if (LOCK.compareAndSet(false, true)) {
			GameEngine gameEngine = new GameEngine();
			GAME_SERVICE.scheduleAtFixedRate(gameEngine, 300, 300,
					TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public void run() {
		try {
			if (currentState == State.GAME) {
				cycle();
				currentState = State.IO;
			} else if (currentState == State.IO) {
				for(Player player : PlayerHandler.players) {
					if(player == null) {
						continue;
					}
					player.processSubQueuedPackets();
					player.tick(); 
				}
				currentState = State.GAME;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void cycle() {
		Server.playerHandler.process();
		Server.npcHandler.process();
		Server.objectManager.process();
		Server.itemHandler.process();
		CycleEventHandler.getSingleton().process();
	}

	public static void execute(Runnable task) {
		EXECUTOR_SERVICE.execute(task);
	}

	private enum State {
		IO, GAME
	}

	private GameEngine() {
	}
}
