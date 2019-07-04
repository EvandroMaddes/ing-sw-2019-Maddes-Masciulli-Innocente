package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

/**
 * Notify the choosen while action powerUps
 *
 * @author Federico Inncoente
 */
public class WhileActionPowerUpChoiceEvent extends PowerUpChoiceEvent {
    /**
     * Flag set to true if the player want to use while action powerUps
     */
    private boolean wantToUse;

    /**
     * Constuctor
     *
     * @param user          player username
     * @param wantToUse     slag choce
     * @param powerUpType   powerUps type
     * @param powerUpColour powerUps colour
     */
    public WhileActionPowerUpChoiceEvent(String user, boolean wantToUse, String powerUpType, CubeColour powerUpColour) {
        super(user, powerUpType, powerUpColour);
        this.wantToUse = wantToUse;
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        if (wantToUse)
            controller.getGameManager().getCurrentRound().getActionManager().askForGenericPay(getCard(), getPowerUpColour());
        else
            controller.getGameManager().getCurrentRound().getActionManager().sendPossibleEffects();
    }
}
