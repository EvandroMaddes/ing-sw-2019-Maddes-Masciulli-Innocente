package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class PowerUpChoiceEvent extends CardChoiceEvent {
    public PowerUpChoiceEvent(String user, String card) {
        super(user, card);
    }

    @Override
    public String getCard() {
        return super.getCard();
    }

    @Override
    public void performAction(Controller controller) {

    }
}
