package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;

public abstract class ModelViewBroadcastEvent extends ClientEvent {

    public ModelViewBroadcastEvent() {
        super("BROADCAST");
    }

}
