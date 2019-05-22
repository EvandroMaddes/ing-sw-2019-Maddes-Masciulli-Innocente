package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public abstract class ControllerViewEvent extends ClientEvent {

    public ControllerViewEvent(String user) {
        super(user);
    }

}
