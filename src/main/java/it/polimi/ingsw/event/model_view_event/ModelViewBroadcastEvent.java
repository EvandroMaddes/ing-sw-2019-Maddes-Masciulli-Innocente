package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;

public abstract class ModelViewBroadcastEvent extends ClientEvent {

    public ModelViewBroadcastEvent() {
        super("BROADCAST");
    }

}
