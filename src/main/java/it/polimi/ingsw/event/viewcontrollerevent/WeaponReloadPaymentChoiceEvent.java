package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * notify the weapon reload choice
 *
 * @author Federico Inncente
 */
public class WeaponReloadPaymentChoiceEvent extends PowerUpListChoiceEvent {

    /**
     * Constructor
     *
     * @param user          is the player username
     * @param powerUpType   are the powerUps type
     * @param powerUpColour are the powerUps colour
     */
    public WeaponReloadPaymentChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().payWeaponReload(getPowerUpType(), getPowerUpColour());
    }
}
