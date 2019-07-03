package it.polimi.ingsw.event.viewcontrollerevent;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;

public abstract class ViewControllerEvent extends Event {

    public ViewControllerEvent(String user){
        super(user);
    }

    public abstract void performAction(Controller controller);
}
