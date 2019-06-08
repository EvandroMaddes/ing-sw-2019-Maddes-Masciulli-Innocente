package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;

public abstract class ModelViewEvent extends ClientEvent {

    public ModelViewEvent(String user) {
        super(user);
    }

}
