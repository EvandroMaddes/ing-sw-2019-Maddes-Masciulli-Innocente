package it.polimi.ingsw.event.viewserverevent;

import it.polimi.ingsw.event.Event;

/**
 * Abstract class that handle the messages sent from the client to the Server
 *
 * @author Francesco Masciulli
 */
public abstract class ViewServerEvent extends Event {

    /**
     * Constructor: call the Event constructor
     *
     * @param user is the client username
     */
    public ViewServerEvent(String user) {
        super(user);
    }

    /**
     * This performAction will be implemented to return the user choice
     *
     * @return the String containing the user choice
     */
    public abstract String performAction();
}
