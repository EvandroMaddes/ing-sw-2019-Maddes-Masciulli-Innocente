package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

/**
 * SE sceglie di non usare i power up per pagare la i poerup name e color sono settati a null
 */
public class WeaponEffectPaymentChoiceEvent extends PowerUpListChoiceEvent {

    public WeaponEffectPaymentChoiceEvent(String user, String[] powerUpType, CubeColour[] powerUpColour) {
        super(user, powerUpType, powerUpColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().payEffect(getPowerUpType(), getPowerUpColour());
    }
}
