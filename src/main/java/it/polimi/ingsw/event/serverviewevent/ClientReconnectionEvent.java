package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

/**
 * This event handle the reconnection of a Client to a new port of the server
 * @author Francesco Masciulli
 */
public class ClientReconnectionEvent extends ServerClientEvent {

    /**
     * Is the new server port
     */
    private int lobbyServerPort;

    /**
     * Constructor: set the new port and call the super-class constructor
     *
     * @param user            is the client username
     * @param lobbyServerPort is the new server port
     */
    public ClientReconnectionEvent(String user, int lobbyServerPort) {
        super(user);
        this.lobbyServerPort = lobbyServerPort;
    }

    /**
     * Implement the ServerClientEvent method: handle the ClientInterface implementation reconnection
     *
     * @param clientImplementation is the client ClientInterface implementation
     * @param remoteView           is the client RemoteView Implementation
     * @return null because is a network message that doesn't need an answer;
     */
    @Override
    public Event performAction(ClientInterface clientImplementation, RemoteView remoteView) {
        clientImplementation.setServerPort(lobbyServerPort);
        clientImplementation.reconnectClient();
        return null;
    }
}
