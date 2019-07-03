package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.gamecomponents.ammo.CubeColour;

public class WeaponGrabPaymentChoiceEvent extends PowerUpListChoiceEvent {

    public WeaponGrabPaymentChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().payWeaponGrab(getPowerUpType(), getPowerUpColour());
    }
}
