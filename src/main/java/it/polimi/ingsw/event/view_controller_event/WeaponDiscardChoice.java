package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class WeaponDiscardChoice extends CardChoiceEvent {

    public WeaponDiscardChoice(String user, String card ) {
        super(user, card );
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().discardWeapon(getCard());
    }
}
