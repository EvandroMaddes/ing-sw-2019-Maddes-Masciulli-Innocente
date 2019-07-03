package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Message to notify the weapon grab choice
 *
 * @author Federico Inncoente
 */
public class WeaponGrabChoiceEvent extends CardChoiceEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     * @param card is teh weapon name
     */
    public WeaponGrabChoiceEvent(String user, String card) {
        super(user, card);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().grabWeapon(getCard());
    }
}
