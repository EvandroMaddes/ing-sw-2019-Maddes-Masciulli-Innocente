package it.polimi.ingsw.event.model_view_event;

import it.polimi.ingsw.event.ClientEvent;
import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public abstract class ModelViewEvent extends ClientEvent {

    public ModelViewEvent(String user) {
        super(user);
    }



}
