package it.polimi.ingsw.event.controllerviewevent;

import it.polimi.ingsw.event.Event;
import it.polimi.ingsw.view.RemoteView;

public class GameRequestEvent extends ControllerViewEvent {

    public GameRequestEvent(String user) {
        super(user);
    }

    @Override
    public Event performAction(RemoteView remoteView) {
        Event message = remoteView.gameChoice();
        remoteView.printScreen();
        return  message;

    }
}
