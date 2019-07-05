package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

/**
 * Message to notify the target choice of a Newton powerUp
 *
 * @author Federico Innocente
 */
public class NewtonPlayerTargetChoiceEvent extends ViewControllerEvent {
    /**
     * Is the chosen target
     */
    private Character chosenTarget;

    /**
     * Constructor
     *
     * @param user         is the player username
     * @param chosenTarget is the target character
     */
    public NewtonPlayerTargetChoiceEvent(String user, Character chosenTarget) {
        super(user);
        this.chosenTarget = chosenTarget;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performEffetcNewton(chosenTarget);
    }

}
