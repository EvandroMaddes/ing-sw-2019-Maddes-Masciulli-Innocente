package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.ClientEvent;

public abstract class ControllerViewEvent extends ClientEvent {

    public ControllerViewEvent(String user) {
        super(user);
    }

}
