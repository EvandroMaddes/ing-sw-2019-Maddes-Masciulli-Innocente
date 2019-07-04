package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.player.Character;

/**
 * Message for the targeting scope target choice
 *
 * @author Federico Inncente
 */
public class TargetingScopeTargetChoiceEvent extends ViewControllerEvent {
    /**
     * Is the targeting scope target
     */
    private Character target;

    /**
     * Constructor
     *
     * @param user   is the player username
     * @param target is the target character
     */
    public TargetingScopeTargetChoiceEvent(String user, Character target) {
        super(user);
        this.target = target;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().performTargetingScopeEffect(target);
    }

    /**
     * getter
     *
     * @return target
     */
    public Character getTarget() {
        return target;
    }
}
