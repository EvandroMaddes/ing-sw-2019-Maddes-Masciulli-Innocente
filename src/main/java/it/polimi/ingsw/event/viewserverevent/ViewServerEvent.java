package it.polimi.ingsw.event.viewserverevent;

import it.polimi.ingsw.event.Event;

/**
 * Abstract class that handle the messages sent from the client to the Server
 *
 * @author Francesco Masciulli
 */
public abstract class ViewServerEvent extends Event {
    private boolean isNewGame;

    /**
     * Constructor: call the Event constructor and set isNewGame value
     *
     * @param user is the client username
     */
    public ViewServerEvent(String user, boolean isNewGame) {
        super(user);
        this.isNewGame = isNewGame;
    }

    /**
     * Getter method:
     *
     * @return isNewGame value
     */
    public boolean isNewGame() {
        return isNewGame;
    }

    /**
     * This performAction will be implemented to return the user choice
     *
     * @return the String containing the user choice
     */
    public abstract String performAction();
}
