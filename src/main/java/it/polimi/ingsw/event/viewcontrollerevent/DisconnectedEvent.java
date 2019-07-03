package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;


public class DisconnectedEvent extends ViewControllerEvent {

    private boolean isDisconnected = true;

    public DisconnectedEvent(String user) {
        super(user);
    }

    @Override
    public void performAction(Controller controller) {
        controller.getGameManager().getDisconnectionManager().disconnectionManage(getUser());
    }
}
