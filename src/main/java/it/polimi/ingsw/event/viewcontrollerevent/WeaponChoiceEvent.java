package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;

public class WeaponChoiceEvent extends CardChoiceEvent {

    public WeaponChoiceEvent(String user, String card) {
        super(user, card);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().getActionManager().saveWeapon(getCard());
    }
}
