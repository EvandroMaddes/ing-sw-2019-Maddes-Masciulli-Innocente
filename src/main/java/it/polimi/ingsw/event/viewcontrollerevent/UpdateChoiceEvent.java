package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Notify the update of the client
 *
 * @author Evandro Maddes
 * @author Francesco Masciulli
 */
public class UpdateChoiceEvent extends ViewControllerEvent {
    /**
     * Constructor
     *
     * @param user is the player username
     */
    public UpdateChoiceEvent(String user) {
        super(user);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        /*
         * no need to perform actions
         */
    }
}
