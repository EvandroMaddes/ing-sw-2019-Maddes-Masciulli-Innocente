package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.game_components.ammo.CubeColour;

public class WeaponGrabChoiceEvent extends CardChoiceEvent {

    public WeaponGrabChoiceEvent(String user, String card, CubeColour cardColour) {
        super(user, card, cardColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().grabWeapon(getCard());
    }
}
