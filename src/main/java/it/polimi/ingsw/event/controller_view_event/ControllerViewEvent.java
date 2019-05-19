package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public abstract class ControllerViewEvent extends Event {

    public ControllerViewEvent(String user) {
        super(user);
    }

    public abstract void performAction(RemoteView remoteView);
}
