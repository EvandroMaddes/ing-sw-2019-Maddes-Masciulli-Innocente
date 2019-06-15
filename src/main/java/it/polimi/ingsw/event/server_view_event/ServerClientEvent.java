package it.polimi.ingsw.event.server_view_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

public abstract class ServerClientEvent extends Event {
    public ServerClientEvent(String user) {
        super(user);
    }
    public abstract Event performAction(ClientInterface clientImplementation, RemoteView remoteView);
}
