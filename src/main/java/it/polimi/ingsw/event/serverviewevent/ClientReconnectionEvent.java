package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

public class ClientReconnectionEvent extends ServerClientEvent {

    private int lobbyServerPort;

    public ClientReconnectionEvent(String user, int lobbyServerPort) {
        super(user);
        this.lobbyServerPort = lobbyServerPort;
    }

    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        clientImplementation.setServerPort(lobbyServerPort);
        clientImplementation.reconnectClient();
        return null;
    }
}
