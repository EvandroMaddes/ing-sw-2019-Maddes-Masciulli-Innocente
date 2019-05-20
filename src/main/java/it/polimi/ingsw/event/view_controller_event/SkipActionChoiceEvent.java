package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class SkipActionChoiceEvent extends ViewControllerEvent {

    public SkipActionChoiceEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getCurrentRound().nextPhase();
    }
}
