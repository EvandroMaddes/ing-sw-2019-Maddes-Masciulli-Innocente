package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.ClientEvent;

/**
 * Class for a generic message of request send to the payer by the controller
 *
 * @author Federico Innocente
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public abstract class ControllerViewEvent extends ClientEvent {

    /**
     * Constructor
     *
     * @param user is the username of the player to who send the message
     */
    public ControllerViewEvent(String user) {
        super(user);
    }

}
