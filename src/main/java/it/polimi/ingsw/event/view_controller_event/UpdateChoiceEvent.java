package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class UpdateChoiceEvent extends ViewControllerEvent {
    public UpdateChoiceEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
    }
}
