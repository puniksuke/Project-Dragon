package org.osrspvp.core.cycle;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Handles all of our cycle based events
 *
 * @author Stuart <RogueX>
 * @author Null++
 */
public class CycleEventHandler {

	/**
	 * The instance of this class
	 */
	private static CycleEventHandler instance;

	/**
	 * Returns the instance of this class
	 *
	 * @return
	 */
	public static CycleEventHandler getSingleton() {
		if (instance == null) {
			instance = new CycleEventHandler();
		}
		return instance;
	}

	/**
	 * Holds all of our events currently being ran
	 */
	private final List<CycleEventContainer> events = new LinkedList<>();
	private final Queue<CycleEventContainer> runQueue = new ArrayDeque<>();

	/**
	 * Add an event to the list
	 *
	 * @param id
	 * @param owner
	 * @param event
	 * @param cycles
	 */
	public void addEvent(int id, Object owner, CycleEvent event, int cycles) {
		events.add(new CycleEventContainer(id, owner, event, cycles));
	}

	/**
	 * Add an event to the list
	 *
	 * @param owner
	 * @param event
	 * @param cycles
	 */
	public void addEvent(Object owner, CycleEvent event, int cycles) {
		addEvent(-1, owner, event, cycles);
	}

	public void addEvent(CycleEvent event, int cycles) {
		addEvent(null, event, cycles);
	}

	/**
	 * Execute and remove events
	 */
	public void process() {
		Iterator<CycleEventContainer> $it = events.iterator();
		while ($it.hasNext()) {
			CycleEventContainer next = $it.next();
			if (!next.isRunning()) {
				$it.remove();
				continue;
			}
			if (next.needsExecution()) {
				runQueue.add(next);
			}
		}

		for (;;) {
			CycleEventContainer next = runQueue.poll();
			if (next == null) {
				break;
			}
			try {
				next.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the amount of events currently running
	 *
	 * @return amount
	 */
	public int getEventsCount() {
		return this.events.size();
	}

	/**
	 * Stops all events for a specific owner and id
	 *
	 * @param owner
	 */
	public void stopEvents(Object owner) {
		for (CycleEventContainer c : events) {
			if (c.getOwner() == owner) {
				c.stop();
			}
		}
	}

	/**
	 * Stops all events for a specific owner and id
	 *
	 * @param owner
	 * @param id
	 */
	public void stopEvents(Object owner, int id) {
		for (CycleEventContainer c : events) {
			if (c.getOwner() == owner && id == c.getID()) {
				c.stop();
			}
		}
	}

	/**
	 * Stops all events for a specific owner and id
	 *
	 * @param id
	 */
	public void stopEvents(int id) {
		for (CycleEventContainer c : events) {
			if (id == c.getID()) {
				c.stop();
			}
		}
	}

}
