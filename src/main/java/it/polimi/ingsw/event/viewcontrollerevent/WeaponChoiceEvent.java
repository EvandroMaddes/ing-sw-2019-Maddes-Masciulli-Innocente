package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message for the shot weapon choice
 *
 * @author Federico Inncente
 */
public class WeaponChoiceEvent extends CardChoiceEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     * @param card is the weapon name
     */
    public WeaponChoiceEvent(String user, String card) {
        super(user, card);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().saveWeapon(getCard());
    }
}
