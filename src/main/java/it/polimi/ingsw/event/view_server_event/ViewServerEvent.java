package it.polimi.ingsw.event.view_server_event;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.server.WaitServer;

public abstract class ViewServerEvent  extends Event {
    public ViewServerEvent(String user) {
        super(user);
    }

    public abstract void performAction(WaitServer server);
}
