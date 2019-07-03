package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;


/**
 * Message sent by a player to skip the current action
 *
 * @author Federico Innocente
 */
public class SkipActionChoiceEvent extends ViewControllerEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     */
    public SkipActionChoiceEvent(String user) {
        super(user);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().nextPhase();
    }
}
