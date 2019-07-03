package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message to notify the controller about the choice of end-round powerUps
 */
public class EndRoundPowerUpChoiceEvent extends PowerUpListChoiceEvent {

    /**
     * Constructor
     *
     * @param user          is the player username
     * @param powerUpType   is an array of chosen powerUps type
     * @param powerUpColour is an array of chosen powerUps colour
     */
    public EndRoundPowerUpChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().performEndRoundPowerUpEffect(getUser(), getPowerUpType(), getPowerUpColour());
    }
}
