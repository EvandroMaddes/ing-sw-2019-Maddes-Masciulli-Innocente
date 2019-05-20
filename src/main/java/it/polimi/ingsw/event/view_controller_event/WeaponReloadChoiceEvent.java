package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class WeaponReloadChoiceEvent extends CardChoiceEvent {

    public WeaponReloadChoiceEvent(String user, String card, String cardColour) {
        super(user, card, cardColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().reloadWepon(getCard());
    }
}
