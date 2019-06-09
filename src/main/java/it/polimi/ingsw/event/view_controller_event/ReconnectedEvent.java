package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

public class ReconnectedEvent extends ViewControllerEvent {
    public ReconnectedEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        //todo riattiva il client relativo al newUser;
    }
}
