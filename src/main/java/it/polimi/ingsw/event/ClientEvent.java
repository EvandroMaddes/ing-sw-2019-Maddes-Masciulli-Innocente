package it.polimi.ingsw.event;

import it.polimi.ingsw.view.RemoteView;

/**
 * Abstract class that represent the messages received by the client
 *
 * @author Francesco Masciulli
 */
public abstract class ClientEvent extends Event {
    /**
     * Constructor: call the Event constructor
     *
     * @param user is the addressee
     */
    public ClientEvent(String user) {
        super(user);
    }

    /**
     * The performAction will be implemented in each class to handle the message's requests
     *
     * @param remoteView is the Client RemoteView implementation
     * @return an answer message
     */
    public abstract Event performAction(RemoteView remoteView);
}
