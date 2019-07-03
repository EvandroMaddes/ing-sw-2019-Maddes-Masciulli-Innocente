package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the reconnection of a player
 *
 * @author Federico Innocente
 */
public class ReconnectedEvent extends ViewControllerEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     */
    public ReconnectedEvent(String user) {
        super(user);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getDisconnectionManager().reconnectPlayer(getUser());
    }
}
