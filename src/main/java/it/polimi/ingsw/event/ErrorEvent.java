package it.polimi.ingsw.event;

/**
 * An error event
 *
 * @author Francesco Masciulli
 */
public class ErrorEvent extends Event {
    /**
     * Constructor: call the Event constructor
     *
     * @param user is the client username
     */
    public ErrorEvent(String user) {
        super(user);
    }
}
