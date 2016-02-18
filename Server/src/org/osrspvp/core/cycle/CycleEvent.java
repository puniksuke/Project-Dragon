package org.osrspvp.core.cycle;

/**
 * What the event must implement
 *
 * @author Stuart <RogueX>
 */
public abstract class CycleEvent {

    /**
     * Code which should be ran when the event is executed
     *
     * @param container
     */
    public abstract void execute(CycleEventContainer container);

    /**
     * Code which should be ran when the event stops
     */
    public void stop() {

    }

}
