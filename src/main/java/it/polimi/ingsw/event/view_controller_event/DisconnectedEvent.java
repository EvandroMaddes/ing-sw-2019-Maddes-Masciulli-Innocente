package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;


public class DisconnectedEvent extends ViewControllerEvent {

    public DisconnectedEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getDisconnectionManager().removePlayer(getUser());
    }
}
