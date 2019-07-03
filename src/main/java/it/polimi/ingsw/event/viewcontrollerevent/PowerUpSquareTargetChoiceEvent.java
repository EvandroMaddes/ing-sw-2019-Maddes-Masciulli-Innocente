package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.utils.Decoder;

/**
 * vale sia per il Square target di newton e teletrasporto
 */
public class PowerUpSquareTargetChoiceEvent extends PositionChoiceEvent {

    public PowerUpSquareTargetChoiceEvent(String user, int positionX, int positionY) {
        super(user, positionX, positionY);
    }

    @Override
    public void performAction(Controller controller) {
        Object destination = Decoder.decodeSquare(getPositionX(), getPositionY(), controller.getGameManager().getModel().getGameboard().getMap());
        controller.getGameManager().getCurrentRound().getActionManager().performPowerUp(destination);
        controller.getGameManager().getCurrentRound().getActionManager().endPowerUpPhase();
    }
}
