package it.polimi.ingsw.event.serverviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.view.RemoteView;

/**
 * Abstract class that represent the message sent from Server or Lobby to Client
 *
 * @author Francesco Masciulli
 */
public abstract class ServerClientEvent extends Event {
    /**
     * Constructor: call Event constructor to set user attribute
     *
     * @param user the receiving client username
     */
    public ServerClientEvent(String user) {
        super(user);
    }

    /**
     * Implemented in the concrete classes that extend this, could operate on a ClientInterface and a RemoteView
     *
     * @param clientImplementation is the client ClientInterface implementation
     * @param remoteView           is the client RemoteView Implementation
     * @return an answer message
     */
    public abstract Event performAction(ClientInterface clientImplementation, RemoteView remoteView);
}
