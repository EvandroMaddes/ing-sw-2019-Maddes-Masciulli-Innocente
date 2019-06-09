package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class PowerUpChoiceEvent extends CardChoiceEvent {
    private CubeColour powerUpColour;

    public PowerUpChoiceEvent(String user, String powerUpType, CubeColour powerUpColour) {
        super(user, powerUpType);
        this.powerUpColour = powerUpColour;
    }

    @Override
    public void performAction(Controller controller){
        controller.getGameManager().getCurrentRound().getActionManager().usePowerUp(getCard(), powerUpColour);
    }

    public CubeColour getPowerUpColour() {
        return powerUpColour;
    }

    @Override
    public String getCard() {
        return super.getCard();
    }
}
