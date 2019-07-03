package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;

/**
 * Is the abstract class for the broadcast Model->View update
 */
public abstract class ModelViewBroadcastEvent extends ClientEvent {

    /**
     * Constructor: set the user to "BROADCAST"
     */
    public ModelViewBroadcastEvent() {
        super("BROADCAST");
    }

}
