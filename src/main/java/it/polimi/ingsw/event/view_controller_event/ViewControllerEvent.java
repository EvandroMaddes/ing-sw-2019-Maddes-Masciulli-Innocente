package it.polimi.ingsw.event.view_controller_event;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.event.Event;

public abstract class ViewControllerEvent extends Event {

    public ViewControllerEvent(String user){
        super(user);
    }

    public abstract void performAction(Controller controller);
}
