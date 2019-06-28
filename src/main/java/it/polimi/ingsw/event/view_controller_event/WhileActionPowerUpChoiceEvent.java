package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class WhileActionPowerUpChoiceEvent extends PowerUpChoiceEvent {
    private boolean wantToUse;

    public WhileActionPowerUpChoiceEvent(String user, boolean wantToUse,String powerUpType, CubeColour powerUpColour) {
        super(user, powerUpType, powerUpColour);
        this.wantToUse = wantToUse;
    }

    @Override
    public void performAction(Controller controller) {
        if (wantToUse)
            controller.getGameManager().getCurrentRound().getActionManager().askForGenericPay(getCard(), getPowerUpColour());
        else
            controller.getGameManager().getCurrentRound().getActionManager().sendPossibleEffects();
    }
}
