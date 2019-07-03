package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

/**
 * Notify the weapon to reload
 *
 * @author federico Innocente
 */
public class WeaponReloadChoiceEvent extends CardChoiceEvent {

    /**
     * Constructor
     *
     * @param user is the player username
     * @param card is the waeapon name
     */
    public WeaponReloadChoiceEvent(String user, String card) {
        super(user, card);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().askForWeaponReloadPay(getCard());
    }
}
