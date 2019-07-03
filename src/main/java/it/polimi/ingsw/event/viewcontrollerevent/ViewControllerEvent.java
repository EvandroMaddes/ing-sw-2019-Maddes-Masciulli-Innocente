package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;

/**
 * Generic message sent by a player to the controller to notify his choice about a request
 */
public abstract class ViewControllerEvent extends Event {

    /**
     * Constructor
     *
     * @param user is the username of the player who send the message
     */
    public ViewControllerEvent(String user) {
        super(user);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    public abstract void performAction(Controller controller);
}
