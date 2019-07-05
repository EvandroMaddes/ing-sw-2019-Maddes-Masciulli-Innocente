package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the client about a player disconnection
 *
 * @author Federico Innocente
 */
public class DisconnectedEvent extends ViewControllerEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     */
    public DisconnectedEvent(String user) {
        super(user);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
         controller.getGameManager().getDisconnectionManager().disconnectionManage(getUser());
    }
}
