package it.polimi.ingsw.event.controller_view_event;

import it.polimi.ingsw.controller.Controller;

import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;


public class DisconnectedEvent extends ViewControllerEvent {

    private boolean isDisconnected = true;

    public DisconnectedEvent(String user) {
        super(user);
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    @Override
    public void performAction(Controller controller) {
        //todo mette in lista disconessi
    }
}
