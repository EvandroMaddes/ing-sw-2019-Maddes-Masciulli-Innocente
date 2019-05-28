package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class WeaponChoiceEvent extends CardChoiceEvent {
    @Override
    public String getCard() {
        return super.getCard();
    }

    public WeaponChoiceEvent(String user, String card) {
        super(user, card);
    }

    @Override
    public void performAction(Controller controller) {
        //TODO return;
    }
}
