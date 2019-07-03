package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.utils.Decoder;

/**
 * Message to notify the choice of a taarget square for the as action powerUps
 *
 * @author Federico Innocente
 */
public class PowerUpSquareTargetChoiceEvent extends PositionChoiceEvent {

    /**
     * Constructor
     *
     * @param user      is the player username
     * @param positionX is the position's row
     * @param positionY is the position's coumn
     */
    public PowerUpSquareTargetChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    /**
     * Perform the action according with the player choice
     *
     * @param controller is the controller of the game
     */
    @Override
    public void performAction(Controller controller) {
        Object destination = Decoder.decodeSquare(getPositionX(), getPositionY(), controller.getGameManager().getModel().getGameboard().getMap());
        controller.getGameManager().getCurrentRound().getActionManager().performPowerUp(destination);
        controller.getGameManager().getCurrentRound().getActionManager().endPowerUpPhase();
    }
}
