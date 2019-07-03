package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Message to notify the choice of a powerUp as an action
 *
 * @author Federico Innocente
 */
public class PowerUpChoiceEvent extends CardChoiceEvent {
    /**
     * Is the chosen powerUp colour
     */
    private CubeColour powerUpColour;

    /**
     * Constructor
     *
     * @param user          is the player username
     * @param powerUpType   is the type of the powerUp
     * @param powerUpColour is teh colour of the powerUp
     */
    public PowerUpChoiceEvent(String user, String powerUpType, CubeColour powerUpColour) {
        super(user, powerUpType);
        this.powerUpColour = powerUpColour;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().usePowerUp(getCard(), powerUpColour);
    }

    /**
     * Getter method
     *
     * @return the powerUp colour
     */
    public CubeColour getPowerUpColour() {
        return powerUpColour;
    }
}
