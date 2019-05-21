package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class GameRequestEvent extends ControllerViewEvent {

    public GameRequestEvent(String user) {
        super(user);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        return null;
    }
}
