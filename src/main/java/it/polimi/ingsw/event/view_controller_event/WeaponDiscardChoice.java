package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class WeaponDiscardChoice extends CardChoiceEvent {

    public WeaponDiscardChoice(String user, String card, String cardColour) {
        super(user, card, cardColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().discardWeapon(getCard());
    }
}
