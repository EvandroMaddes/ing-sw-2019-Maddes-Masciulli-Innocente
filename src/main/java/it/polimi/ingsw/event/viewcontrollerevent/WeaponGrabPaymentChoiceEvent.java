package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message to notify the choice for the pay of the weapon grab
 *
 * @author Federico Inncoente
 */
public class WeaponGrabPaymentChoiceEvent extends PowerUpListChoiceEvent {

    /**
     * Contsturctot
     *
     * @param user          is the player username
     * @param powerUpType   are the powerUps type
     * @param powerUpColour are the powerUps colour
     */
    public WeaponGrabPaymentChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().payWeaponGrab(getPowerUpType(), getPowerUpColour());
    }
}
