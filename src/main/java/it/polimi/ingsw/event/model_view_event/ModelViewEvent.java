package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public abstract class ModelViewEvent extends Event {

    public ModelViewEvent(String user) {
        super(user);
    }

    public abstract void performAction(RemoteView remoteView);
}
