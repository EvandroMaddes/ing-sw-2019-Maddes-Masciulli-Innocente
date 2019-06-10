package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;

import it.polimi.ingsw.event.view_controller_event.ViewControllerEvent;


public class DisconnectedEvent extends ViewControllerEvent {

    private boolean isDisconnected = true;

    public DisconnectedEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        //todo mette in lista disconessi nel controller
    }
}