package it.polimi.ingsw.event;

import it.polimi.ingsw.view.RemoteView;

public abstract class ClientEvent extends Event{
    public ClientEvent(String user) {
        super(user);
    }

    public abstract Event performAction(RemoteView remoteView);
}
