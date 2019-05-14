package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class SpownChoiceEvent extends CardChoiceEvent {

    public SpownChoiceEvent(String user, String card, String cardColour) {
        super(user, card, cardColour);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().spawn();
    }
}
