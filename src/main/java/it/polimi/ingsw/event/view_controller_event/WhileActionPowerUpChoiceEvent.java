package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class WhileActionPowerUpChoiceEvent extends PowerUpChoiceEvent {

    public WhileActionPowerUpChoiceEvent(String user, String powerUpType, CubeColour powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().askForGenericPay(getCard(), getPowerUpColour());
    }
}