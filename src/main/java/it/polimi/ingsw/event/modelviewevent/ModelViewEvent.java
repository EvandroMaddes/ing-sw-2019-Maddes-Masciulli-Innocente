package it.polimi.ingsw.event.modelviewevent;

import it.polimi.ingsw.event.ClientEvent;

public abstract class ModelViewEvent extends ClientEvent {
    public ModelViewEvent(String user) {
        super(user);
    }
}
